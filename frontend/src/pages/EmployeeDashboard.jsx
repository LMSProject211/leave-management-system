import { useState, useEffect } from 'react'
import { leaveService, payrollService, employeeService } from '../services/api'
import { StatCard, InfoCard } from '../components/Cards'

function EmployeeDashboard() {
  const [stats, setStats] = useState({
    leaveBalance: 0,
    pendingRequests: 0,
    thisMonthSalary: 0,
    totalLeavesTaken: 0,
  })
  const [recentLeaves, setRecentLeaves] = useState([])
  const [recentPayrolls, setRecentPayrolls] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [message, setMessage] = useState({ type: '', text: '' })

  const userStr = localStorage.getItem('user')
  const user = userStr ? JSON.parse(userStr) : null
  const userEmail = user?.email

  useEffect(() => {
    if (userEmail) {
      fetchDashboardData()
    }
  }, [userEmail])

  const fetchDashboardData = async () => {
    if (!userEmail) {
      setMessage({ type: 'error', text: 'User email not found. Please login again.' })
      setIsLoading(false)
      return
    }

    try {
      setIsLoading(true)

      // Get employee by email to get employee ID
      let employeeId = null
      try {
        const employeeResponse = await employeeService.getByEmail(userEmail)
        if (employeeResponse.data.success) {
          employeeId = employeeResponse.data.data.id
        }
      } catch (error) {
        console.error('Error fetching employee:', error)
      }

      // Fetch leave requests by email
      const leavesResponse = await leaveService.getByEmployeeEmail(userEmail)
      const leaves = leavesResponse.data.success ? leavesResponse.data.data || [] : []

      // Calculate stats from leaves
      const currentYear = new Date().getFullYear()
      const approvedLeaves = leaves.filter(
        (leave) => leave.status === 'APPROVED' && new Date(leave.startDate).getFullYear() === currentYear
      )
      const pendingLeaves = leaves.filter((leave) => leave.status === 'PENDING')
      
      // Calculate total days taken this year
      const totalDaysTaken = approvedLeaves.reduce((total, leave) => {
        const start = new Date(leave.startDate)
        const end = new Date(leave.endDate)
        const days = Math.ceil((end - start) / (1000 * 60 * 60 * 24)) + 1
        return total + days
      }, 0)

      // Default leave balance (you can make this configurable or fetch from employee record)
      const defaultLeaveBalance = 20
      const leaveBalance = Math.max(0, defaultLeaveBalance - totalDaysTaken)

      // Get recent leave requests (last 5)
      const sortedLeaves = [...leaves].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
      setRecentLeaves(sortedLeaves.slice(0, 5))

      // Fetch payroll data
      let thisMonthSalary = 0
      let payrolls = []
      
      if (employeeId) {
        try {
          const payrollResponse = await payrollService.getByEmployee(employeeId)
          if (payrollResponse.data.success) {
            payrolls = payrollResponse.data.data || []
            
            // Get current month payroll
            const currentMonth = new Date().toISOString().slice(0, 7) // YYYY-MM
            const currentMonthPayroll = payrolls.find((p) => p.month === currentMonth)
            if (currentMonthPayroll) {
              thisMonthSalary = parseFloat(currentMonthPayroll.netSalary || 0)
            }
          }
        } catch (error) {
          console.error('Error fetching payroll:', error)
        }
      }

      // Get recent payrolls (last 3)
      const sortedPayrolls = [...payrolls].sort((a, b) => new Date(b.month) - new Date(a.month))
      setRecentPayrolls(sortedPayrolls.slice(0, 3))

      // Update stats
      setStats({
        leaveBalance,
        pendingRequests: pendingLeaves.length,
        thisMonthSalary,
        totalLeavesTaken: approvedLeaves.length,
      })
    } catch (error) {
      const errorMessage =
        error.response?.data?.message || error.message || 'Failed to fetch dashboard data'
      setMessage({ type: 'error', text: errorMessage })
    } finally {
      setIsLoading(false)
    }
  }

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A'
    const date = new Date(dateString)
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' })
  }

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount)
  }

  const getStatusBadge = (status) => {
    const statusClass =
      status === 'APPROVED'
        ? 'bg-success'
        : status === 'PENDING'
        ? 'bg-warning text-dark'
        : 'bg-danger'
    return <span className={`badge ${statusClass}`}>{status}</span>
  }

  return (
    <div style={{ animation: 'fadeIn 0.3s ease-in' }}>
      <h2 className="mb-4" style={{ fontFamily: "'Source Serif 4', serif", color: '#2F3C7E' }}>
        Employee Dashboard
      </h2>

      {message.text && (
        <div
          className={`alert alert-${message.type === 'success' ? 'success' : 'danger'} alert-dismissible fade show`}
          role="alert"
        >
          {message.text}
          <button
            type="button"
            className="btn-close"
            onClick={() => setMessage({ type: '', text: '' })}
            aria-label="Close"
          ></button>
        </div>
      )}

      {isLoading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3 text-muted">Loading dashboard data...</p>
        </div>
      ) : (
        <>
          <div className="row mb-4">
            <StatCard
              title="My Leave Balance"
              value={stats.leaveBalance}
              icon="📅"
              color="info"
              subtitle="Days remaining"
            />
            <StatCard
              title="Pending Requests"
              value={stats.pendingRequests}
              icon="⏳"
              color="warning"
              subtitle="Awaiting approval"
            />
            <StatCard
              title="This Month Salary"
              value={formatCurrency(stats.thisMonthSalary)}
              icon="💰"
              color="success"
              subtitle="Net amount"
            />
            <StatCard
              title="Total Leaves Taken"
              value={stats.totalLeavesTaken}
              icon="✅"
              color="primary"
              subtitle="This year"
            />
          </div>

          <div className="row">
            <div className="col-md-6 mb-4">
              <InfoCard title="My Leave Requests">
                {recentLeaves.length === 0 ? (
                  <p className="text-muted mb-0">No leave requests found</p>
                ) : (
                  <div className="table-responsive">
                    <table className="table table-sm">
                      <thead>
                        <tr>
                          <th>Type</th>
                          <th>Date</th>
                          <th>Status</th>
                        </tr>
                      </thead>
                      <tbody>
                        {recentLeaves.map((leave) => (
                          <tr key={leave.id}>
                            <td>
                              <span className="badge bg-info text-dark">{leave.leaveType}</span>
                            </td>
                            <td>
                              <small>{formatDate(leave.startDate)}</small>
                            </td>
                            <td>{getStatusBadge(leave.status)}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </InfoCard>
            </div>
            <div className="col-md-6 mb-4">
              <InfoCard title="Recent Payroll">
                {recentPayrolls.length === 0 ? (
                  <p className="text-muted mb-0">No payroll records found</p>
                ) : (
                  <div className="table-responsive">
                    <table className="table table-sm">
                      <thead>
                        <tr>
                          <th>Month</th>
                          <th>Net Salary</th>
                        </tr>
                      </thead>
                      <tbody>
                        {recentPayrolls.map((payroll) => (
                          <tr key={payroll.id}>
                            <td>
                              <strong>
                                {new Date(payroll.month + '-01').toLocaleDateString('en-US', {
                                  month: 'long',
                                  year: 'numeric',
                                })}
                              </strong>
                            </td>
                            <td>
                              <strong className="text-success">
                                {formatCurrency(parseFloat(payroll.netSalary || 0))}
                              </strong>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </InfoCard>
            </div>
          </div>
        </>
      )}
    </div>
  )
}

export default EmployeeDashboard


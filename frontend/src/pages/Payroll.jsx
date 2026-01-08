import { useState, useEffect } from 'react'
import { payrollService } from '../services/api'
import PayslipCard from '../components/PayslipCard'
import { InfoCard } from '../components/Cards'

function Payroll() {
  const [payrolls, setPayrolls] = useState([])
  const [filteredPayrolls, setFilteredPayrolls] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [selectedMonth, setSelectedMonth] = useState('')
  const [message, setMessage] = useState({ type: '', text: '' })
  const [selectedPayroll, setSelectedPayroll] = useState(null)

  useEffect(() => {
    fetchPayrolls()
  }, [])

  useEffect(() => {
    if (selectedMonth) {
      const filtered = payrolls.filter((p) => p.month === selectedMonth)
      setFilteredPayrolls(filtered)
    } else {
      setFilteredPayrolls(payrolls)
    }
  }, [selectedMonth, payrolls])

  const fetchPayrolls = async () => {
    try {
      setIsLoading(true)
      
      
      const currentMonth = new Date().toISOString().slice(0, 7) 
      const response = await payrollService.getByMonth(currentMonth)
      if (response.data.success) {
        setPayrolls(response.data.data || [])
        setFilteredPayrolls(response.data.data || [])
      } else {
        setMessage({ type: 'error', text: response.data.message || 'Failed to fetch payrolls' })
      }
    } catch (error) {
      const errorMessage =
        error.response?.data?.message || error.message || 'Failed to fetch payrolls'
      setMessage({ type: 'error', text: errorMessage })
      setPayrolls([])
      setFilteredPayrolls([])
    } finally {
      setIsLoading(false)
    }
  }

  const handleMonthChange = async (month) => {
    if (!month) {
      setSelectedMonth('')
      setFilteredPayrolls(payrolls)
      return
    }

    try {
      setIsLoading(true)
      const response = await payrollService.getByMonth(month)
      if (response.data.success) {
        setFilteredPayrolls(response.data.data || [])
        setSelectedMonth(month)
      } else {
        setMessage({ type: 'error', text: response.data.message || 'Failed to fetch payrolls' })
      }
    } catch (error) {
      const errorMessage =
        error.response?.data?.message || error.message || 'Failed to fetch payrolls'
      setMessage({ type: 'error', text: errorMessage })
    } finally {
      setIsLoading(false)
    }
  }

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
    }).format(amount)
  }

  const formatMonth = (monthString) => {
    if (!monthString) return 'N/A'
    const [year, month] = monthString.split('-')
    const date = new Date(year, parseInt(month) - 1)
    return date.toLocaleDateString('en-US', { month: 'short', year: 'numeric' })
  }

  const getAvailableMonths = () => {
    const months = [...new Set(payrolls.map((p) => p.month))].sort().reverse()
    return months
  }

  return (
    <div style={{ animation: 'fadeIn 0.3s ease-in' }}>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-0" style={{ fontFamily: "'Source Serif 4', serif", color: '#2F3C7E' }}>Payroll Management</h2>
          <p className="text-muted mb-0" style={{ fontSize: '0.9375rem' }}>View and manage employee payrolls</p>
        </div>
        <div className="d-flex gap-2">
          <input
            type="month"
            className="form-control"
            value={selectedMonth}
            onChange={(e) => handleMonthChange(e.target.value)}
            style={{ width: 'auto' }}
          />
        </div>
      </div>

      
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

      {selectedPayroll ? (
        <div>
          <button
            className="btn btn-outline-secondary mb-3"
            onClick={() => setSelectedPayroll(null)}
          >
            <i className="bi bi-arrow-left me-2"></i>
            Back to List
          </button>
          <PayslipCard payroll={selectedPayroll} />
        </div>
      ) : (
        <InfoCard>
          {isLoading ? (
            <div className="text-center py-5">
              <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Loading...</span>
              </div>
              <p className="mt-3 text-muted">Loading payrolls...</p>
            </div>
          ) : (
            <div className="table-responsive">
              <table className="table table-striped table-hover align-middle">
                <thead className="table-dark">
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Employee</th>
                    <th scope="col">Month</th>
                    <th scope="col">Basic Salary</th>
                    <th scope="col">Tax</th>
                    <th scope="col">Leave Deduction</th>
                    <th scope="col">Net Salary</th>
                    <th scope="col" className="text-center">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredPayrolls.length === 0 ? (
                    <tr>
                      <td colSpan="8" className="text-center text-muted py-5">
                        <i className="bi bi-inbox fs-1 d-block mb-2"></i>
                        No payroll records found.
                      </td>
                    </tr>
                  ) : (
                    filteredPayrolls.map((payroll) => (
                      <tr key={payroll.id}>
                        <td>{payroll.id}</td>
                        <td>
                          <strong>{payroll.employeeName || 'N/A'}</strong>
                          <br />
                          <small className="text-muted">ID: {payroll.employeeId}</small>
                        </td>
                        <td>{formatMonth(payroll.month)}</td>
                        <td>{formatCurrency(payroll.basicSalary)}</td>
                        <td className="text-danger">-{formatCurrency(payroll.taxAmount)}</td>
                        <td className="text-danger">-{formatCurrency(payroll.leaveDeduction)}</td>
                        <td>
                          <strong className="text-success fs-5">
                            {formatCurrency(payroll.netSalary)}
                          </strong>
                        </td>
                        <td>
                          <div className="d-flex justify-content-center gap-2">
                            <button
                              className="btn btn-sm btn-outline-primary"
                              onClick={() => setSelectedPayroll(payroll)}
                              title="View Payslip"
                            >
                              <i className="bi bi-eye"></i> View
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          )}
        </InfoCard>
      )}
    </div>
  )
}

export default Payroll

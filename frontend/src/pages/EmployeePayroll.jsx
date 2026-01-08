import { useState, useEffect } from 'react'
import { payrollService } from '../services/api'
import PayslipCard from '../components/PayslipCard'

function EmployeePayroll() {
  const [payrolls, setPayrolls] = useState([])
  const [filteredPayrolls, setFilteredPayrolls] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [selectedMonth, setSelectedMonth] = useState('')
  const [message, setMessage] = useState({ type: '', text: '' })
  const [selectedPayroll, setSelectedPayroll] = useState(null)

  
  const userStr = localStorage.getItem('user')
  const user = userStr ? JSON.parse(userStr) : null
  const employeeId = user?.id

  useEffect(() => {
    if (employeeId) {
      fetchPayrolls()
    }
  }, [employeeId])

  useEffect(() => {
    if (selectedMonth) {
      const filtered = payrolls.filter((p) => p.month === selectedMonth)
      setFilteredPayrolls(filtered)
    } else {
      setFilteredPayrolls(payrolls)
    }
  }, [selectedMonth, payrolls])

  const fetchPayrolls = async () => {
    if (!employeeId) {
      setMessage({ type: 'error', text: 'Employee ID not found. Please login again.' })
      setIsLoading(false)
      return
    }

    try {
      setIsLoading(true)
      const response = await payrollService.getByEmployee(employeeId)
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

  const formatMonth = (monthString) => {
    if (!monthString) return 'N/A'
    const [year, month] = monthString.split('-')
    const date = new Date(year, parseInt(month) - 1)
    return date.toLocaleDateString('en-US', { month: 'long', year: 'numeric' })
  }

  const getAvailableMonths = () => {
    return [...new Set(payrolls.map((p) => p.month))].sort().reverse()
  }

  return (
    <div style={{ animation: 'fadeIn 0.3s ease-in' }}>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-0" style={{ fontFamily: "'Source Serif 4', serif", color: '#2F3C7E' }}>My Payroll</h2>
          <p className="text-muted mb-0" style={{ fontSize: '0.9375rem' }}>View your salary payslips</p>
        </div>
        {payrolls.length > 0 && (
          <div>
            <select
              className="form-select"
              value={selectedMonth}
              onChange={(e) => setSelectedMonth(e.target.value)}
              style={{ width: 'auto', display: 'inline-block' }}
            >
              <option value="">All Months</option>
              {getAvailableMonths().map((month) => (
                <option key={month} value={month}>
                  {formatMonth(month)}
                </option>
              ))}
            </select>
          </div>
        )}
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

      {isLoading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
          <p className="mt-3 text-muted">Loading payrolls...</p>
        </div>
      ) : selectedPayroll ? (
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
      ) : filteredPayrolls.length === 0 ? (
        <div className="card shadow-sm">
          <div className="card-body text-center py-5">
            <i className="bi bi-inbox fs-1 text-muted d-block mb-3"></i>
            <h5 className="text-muted">No payroll records found</h5>
            <p className="text-muted mb-0">
              Your payroll records will appear here once they are generated.
            </p>
          </div>
        </div>
      ) : (
        <div className="row">
          {filteredPayrolls.map((payroll) => (
            <div key={payroll.id} className="col-md-6 mb-4">
              <div className="card shadow-sm h-100" style={{ cursor: 'pointer', borderRadius: '8px' }} onClick={() => setSelectedPayroll(payroll)}>
                <div className="card-header text-white" style={{ background: 'linear-gradient(135deg, #2F3C7E 0%, #253061 100%)', borderRadius: '8px 8px 0 0' }}>
                  <div className="d-flex justify-content-between align-items-center">
                    <h6 className="mb-0" style={{ fontFamily: "'Source Serif 4', serif", fontWeight: 600 }}>{formatMonth(payroll.month)}</h6>
                    <i className="bi bi-chevron-right"></i>
                  </div>
                </div>
                <div className="card-body">
                  <div className="mb-3">
                    <small className="text-muted d-block">Basic Salary</small>
                    <strong>{new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(payroll.basicSalary)}</strong>
                  </div>
                  <div className="mb-3">
                    <small className="text-muted d-block">Deductions</small>
                    <div className="small">
                      <div className="text-danger">
                        Tax: -{new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(payroll.taxAmount)}
                      </div>
                      <div className="text-danger">
                        Leave: -{new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(payroll.leaveDeduction)}
                      </div>
                    </div>
                  </div>
                  <hr />
                  <div>
                    <small className="text-muted d-block mb-1">Net Salary</small>
                    <h4 className="text-success mb-0 fw-bold">
                      {new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(payroll.netSalary)}
                    </h4>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default EmployeePayroll

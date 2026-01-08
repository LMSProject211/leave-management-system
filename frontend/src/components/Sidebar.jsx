import { NavLink } from 'react-router-dom'

function Sidebar() {
  return (
    <aside
      className="text-white"
      style={{ width: '250px', minHeight: '100vh', position: 'fixed', left: 0, top: 0, zIndex: 1000 }}
    >
      <div className="p-4">
        <h5 className="text-white mb-4" style={{ fontFamily: "'Source Serif 4', serif", fontWeight: 600 }}>Menu</h5>
        <nav className="nav flex-column">
          <NavLink
            to="/dashboard"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-speedometer2 me-2"></i>
            Dashboard
          </NavLink>
          <NavLink
            to="/employees"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-people me-2"></i>
            Employees
          </NavLink>
          <NavLink
            to="/leaves"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-calendar-check me-2"></i>
            Leaves
          </NavLink>
          <NavLink
            to="/calendar"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-calendar3 me-2"></i>
            Calendar
          </NavLink>
          <NavLink
            to="/payroll"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-cash-coin me-2"></i>
            Payroll
          </NavLink>
          <NavLink
            to="/attendance"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-calendar-event me-2"></i>
            Attendance
          </NavLink>
          <NavLink
            to="/holidays"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-calendar-heart me-2"></i>
            Holidays
          </NavLink>
          <NavLink
            to="/departments"
            className={({ isActive }) =>
              `nav-link text-white ${isActive ? 'active' : ''}`
            }
          >
            <i className="bi bi-building me-2"></i>
            Departments
          </NavLink>
        </nav>
      </div>
    </aside>
  )
}

export default Sidebar


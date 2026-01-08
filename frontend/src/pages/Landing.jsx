import { Link } from "react-router-dom";
import { LuFileSpreadsheet } from "react-icons/lu";
import { TbReportSearch } from "react-icons/tb";
import { RiTwitterLine } from "react-icons/ri";
import { FaWhatsapp, FaFacebookF, FaLinkedinIn } from "react-icons/fa";

const Landing = () => {
  return (
    <div className="lms-container">
      {/* Navigation */}
      <nav className="navbar">
        <div className="logo">
          <img
            src="logo.png"
            alt=""
            className="logo-img"
            style={{ width: "100px", height: "auto" }}
          />
        </div>
        <ul className="nav-links">
          <li>
            <a href="#home">Home</a>
          </li>
          <li>
            <a href="#features">Features</a>
          </li>
          <li>
            <a href="#contact">Contact</a>
          </li>
        </ul>
        <div className="log-reg-btn">
          <Link className="login-btn" to="/login">Login</Link>
          <Link className="login-btn" to="/register">Register</Link>
        </div>
        
      </nav>

      {/* Hero Section */}
      <header className="hero-section">
        <h1>Efficient Leave Management Made Simple</h1>
        <p>
          Streamline leave applications for students and faculty with our
          intuitive and comprehensive leave management system.
        </p>
        <div className="hero-btns">
          <Link className="btn-primary" to="/register">Get Started</Link>
          <button className="btn-secondary">Request Demo</button>
        </div>
        <div className="wave-bg"></div>
      </header>

      {/* Features Section */}
      <section className="features-grid">
        <div className="feature-card">
          <div className="icon blue-icon">
            <LuFileSpreadsheet />
          </div>
          <h3>Apply for Leave</h3>
          <p>
            Submit leave applications quickly and easily with our streamlined
            form.
          </p>
        </div>
        <div className="feature-card">
          <div className="icon blue-icon">⊞</div>
          <h3>Unified Dashboard</h3>
          <p>
            Manage all pending and approved leave applications from a single
            dashboard.
          </p>
        </div>
        <div className="feature-card">
          <div className="icon blue-icon">✉</div>
          <h3>Real-Time Updates</h3>
          <p>
            Receive immediate email notifications when leave statuses change.
          </p>
        </div>
        <div className="feature-card">
          <div className="icon blue-icon">
            <TbReportSearch />
          </div>
          <h3>Leave History & Reports</h3>
          <p>
            Access and download detailed reports of all leave requests and
            approvals.
          </p>
        </div>
      </section>

      {/* Footer Section */}
      <footer className="footer">
        <div className="footer-content">
          <div className="footer-column">
            <h4>About</h4>
            <ul>
              <li>Home</li>
              <li>About Us</li>
            </ul>
          </div>
          <div className="footer-column">
            <h4>Company</h4>
            <ul>
              <li>Privacy Policy</li>
              <li>Contact</li>
            </ul>
          </div>
          <div className="footer-column">
            <h4>Resources</h4>
            <ul>
              <li>Privacy Policy</li>
              <li>Terms of Service</li>
            </ul>
          </div>
          <div className="footer-column">
            <h4>
                Contact & Support
            </h4>
            <ul>
              <li>+91 9876543210</li>
              <li>123@gmail.com</li>
              <li>BVC Eng col., odalarevu</li>
            </ul>
          </div>
          <div className="footer-column social">
            <h4>Follow Us</h4>
            <div className="social-icons">
              <a href="#" className="social-icon twitter">
                <RiTwitterLine />
              </a>

              <a href="#" className="social-icon linkedin">
                <FaLinkedinIn />
              </a>

              <a href="#" className="social-icon facebook">
                <FaFacebookF />
              </a>

              <a href="#" className="social-icon whatsapp">
                <FaWhatsapp />
              </a>
            </div>

            <p className="copyright">
              © 2025 LeaveSphere. All rights reserved.
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Landing;

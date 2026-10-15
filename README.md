# Role-Based Leave Management System

A full-stack **Role-Based Leave Management System** developed using Java, Spring Boot, React.js, and MySQL. The application provides a centralized platform for managing employee records, leave requests, attendance, departments, holidays, and payroll.

The project follows a layered backend architecture using Controllers, Services, Repositories, DTOs, and Models. The React frontend communicates with the Spring Boot backend through REST APIs using Axios.

## 📌 Project Overview

The Role-Based Leave Management System aims to simplify employee administration and leave management by replacing manual processes with a centralized web application.

It provides separate interfaces for administrators and employees, allowing users to access relevant dashboards and functionality based on their assigned application role.

### Key Objectives

- Simplify employee and department management.
- Digitize leave application and approval workflows.
- Maintain employee attendance records.
- Manage holidays and company information.
- Generate and view payroll records.
- Provide separate administrative and employee interfaces.
- Integrate a React frontend with a Spring Boot REST API.
- Persist application data in a MySQL relational database.

## ✨ Features

### 1. Authentication and Registration

- User registration and login.
- Authentication-related API endpoints.
- Frontend authentication state management.
- Role-specific navigation for administrators and employees.

### 2. Admin Dashboard

- Dashboard overview.
- Employee management.
- Leave request management.
- Leave approval and rejection.
- Attendance management.
- Department management.
- Holiday management.
- Payroll management.

### 3. Employee Dashboard

- Employee-specific dashboard.
- Employee leave management interface.
- Leave application interface.
- Employee attendance view.
- Holiday information.
- Payroll and payslip-related interface.

### 4. Leave Management

- Apply for leave.
- Retrieve all leave requests.
- Retrieve leave requests for a particular employee.
- Approve leave requests.
- Reject leave requests.
- Track leave request status using the application's leave status model.

### 5. Employee Management

- Create employee records.
- Retrieve all employees.
- Retrieve an employee by ID.
- Update employee information.
- Delete employee records.
- Maintain employee status and department information.

### 6. Attendance Management

- Mark employee attendance.
- Retrieve attendance records.
- Retrieve attendance records for a specific employee.
- Represent attendance using attendance-related models and status values.

### 7. Department Management

- Create departments.
- Retrieve departments.
- Update department information.
- Delete departments.

### 8. Holiday Management

- Add holidays.
- Retrieve holiday records.
- Delete holidays.
- Display holiday information in the employee interface.

### 9. Payroll Management

- Generate payroll records.
- Retrieve payroll records for a specific employee.
- Retrieve payroll records by month.
- Display payroll information and payslip-related UI components.

## 🛠️ Technology Stack

### Backend

| Technology | Purpose |
|---|---|
| Java 17 | Core backend programming language |
| Spring Boot 3.2.0 | Backend application framework |
| Spring Web | REST API development |
| Spring Security | Security configuration and authentication-related integration |
| Spring Data JPA | Database access and repository abstraction |
| Hibernate ORM | Object-relational mapping |
| JDBC | Relational database connectivity through the MySQL driver |
| MySQL | Relational database |
| Maven | Dependency management and backend build |
| Spring Boot DevTools | Development-time support |
| JUnit / Spring Boot Test | Backend testing dependencies |

### Frontend

| Technology | Purpose |
|---|---|
| React.js | User interface development |
| JavaScript | Frontend application logic |
| HTML5 | Web page structure |
| CSS3 | Styling |
| Bootstrap 5 | UI styling and responsive layouts |
| React Router DOM | Client-side routing |
| Axios | HTTP communication with backend APIs |
| Vite | Frontend development server and build tool |
| ESLint | JavaScript and React code linting |

**Note:** The supplied frontend dependencies include Bootstrap, but do not list Tailwind CSS. Add Tailwind CSS to the stack if it is installed and used in your current version of the project.

## 🏗️ Application Architecture

The application is organized into two major components.

```text
                 Role-Based Leave Management System
                              |
                 +------------+------------+
                 |                         |
             Frontend                   Backend
             React.js                 Spring Boot
                 |                         |
          React Router                  Controllers
                 |                         |
              Axios                      Services
                 |                         |
                 +---- REST APIs ---------+
                                           |
                                       Repositories
                                           |
                                      Spring Data JPA
                                           |
                                        Hibernate
                                           |
                                         MySQL
```

### Backend Architecture

The backend follows a layered architecture:

- **Controller Layer:** Handles incoming HTTP requests and returns API responses.
- **Service Layer:** Contains application business logic.
- **Repository Layer:** Provides database access using Spring Data JPA.
- **Model Layer:** Defines persistent entities and enums.
- **DTO Layer:** Transfers request and response data between the frontend and backend.
- **Configuration Layer:** Configures Spring Security and CORS.

### Frontend Architecture

The React application is organized into:

- `components/` — Reusable UI components, tables, cards, navigation, modals, and charts.
- `layouts/` — Shared layout components.
- `pages/` — Login, registration, dashboard, employee, leave, attendance, holiday, department, and payroll pages.
- `services/` — Axios-based API integration.
- `assets/` — Static frontend assets.
- `App.jsx` — Application routes and role-specific navigation.
- `main.jsx` — React application entry point.

## 📁 Project Structure

```text
aman_project/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/company/payroll/
│   │   │   │   ├── config/
│   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   └── EmployeePayrollApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
└── frontend/
    ├── public/
    ├── src/
    │   ├── assets/
    │   ├── components/
    │   ├── layouts/
    │   ├── pages/
    │   ├── services/
    │   │   └── api.js
    │   ├── App.jsx
    │   ├── App.css
    │   ├── index.css
    │   └── main.jsx
    ├── index.html
    ├── package.json
    └── vite.config.js
```

## ⚙️ Prerequisites

Install the following before running the project:

- [Java JDK 17](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/)
- [Node.js and npm](https://nodejs.org/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Git](https://git-scm.com/)
- An IDE such as IntelliJ IDEA, Eclipse, or Visual Studio Code

Postman is also useful for testing backend REST APIs independently of the frontend.

## 🚀 Getting Started

### Step 1: Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd aman_project
```

Replace `<YOUR_GITHUB_REPOSITORY_URL>` with your GitHub repository URL.

### Step 2: Configure MySQL

Start your MySQL server and create the database:

```sql
CREATE DATABASE employee_payroll_db;
```

The supplied `application.properties` uses port `3307` and the following default database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/${DB_NAME:employee_payroll_db}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

spring.datasource.username=${DB_USERNAME:employee_payroll_db}
spring.datasource.password=${DB_PASSWORD:employee_payroll_db}
```

**Important:** The default MySQL port is usually `3306`, but this project is configured for `3307`. If your MySQL server uses port `3306`, update the JDBC URL accordingly.

Set the environment variables `DB_NAME`, `DB_USERNAME`, and `DB_PASSWORD` to match your local database configuration. The username and password shown in the properties are fallback values, not guaranteed to be valid MySQL credentials.

For example, in PowerShell:

```powershell
$env:DB_NAME="employee_payroll_db"
$env:DB_USERNAME="your_mysql_username"
$env:DB_PASSWORD="your_mysql_password"
```

Use your actual local MySQL credentials. Do not commit passwords to GitHub.

### Step 3: Run the Spring Boot Backend

Open a terminal and navigate to the backend directory:

```bash
cd backend
```

Build the application:

```bash
mvn clean install
```

Start the application:

```bash
mvn spring-boot:run
```

Alternatively, from the backend directory, run the generated JAR:

```bash
java -jar target/employee-payroll-management-1.0.0.jar
```

The backend is configured to run on port `8080`.

**Backend URL:**

```text
http://localhost:8080
```

**API base URL:**

```text
http://localhost:8080/api
```

### Step 4: Run the React Frontend

Open a second terminal and navigate to the frontend directory:

```bash
cd frontend
```

Install the frontend dependencies:

```bash
npm install
```

Start the Vite development server:

```bash
npm run dev
```

Vite displays the local frontend URL in the terminal. With the default Vite configuration, it is usually:

```text
http://localhost:5173
```

Open the displayed URL in your browser.

### Step 5: Configure the Frontend API URL

The frontend uses Axios to communicate with the backend. The API client in `src/services/api.js` defaults to:

```javascript
http://localhost:8080/api
```

To configure a different backend URL, create a `.env` file inside `frontend/`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

Restart the Vite development server after changing environment variables.

## 🔗 Backend REST API Endpoints

The following endpoints are defined in the supplied backend controllers. All paths are relative to `http://localhost:8080`.

| Module | Method | Endpoint | Description |
|---|---|---|---|
| Authentication | POST | `/api/auth/register` | Register a user |
| Authentication | POST | `/api/auth/login` | Authenticate a user |
| Employees | POST | `/api/employees` | Create an employee |
| Employees | GET | `/api/employees` | Retrieve all employees |
| Employees | GET | `/api/employees/{id}` | Retrieve an employee by ID |
| Employees | PUT | `/api/employees/{id}` | Update an employee |
| Employees | DELETE | `/api/employees/{id}` | Delete an employee |
| Departments | POST | `/api/departments` | Create a department |
| Departments | GET | `/api/departments` | Retrieve all departments |
| Departments | PUT | `/api/departments/{id}` | Update a department |
| Departments | DELETE | `/api/departments/{id}` | Delete a department |
| Leaves | POST | `/api/leaves` | Apply for leave |
| Leaves | GET | `/api/leaves` | Retrieve all leave requests |
| Leaves | GET | `/api/leaves/employee/{employeeId}` | Retrieve leave requests for an employee |
| Leaves | PUT | `/api/leaves/{id}/approve` | Approve a leave request |
| Leaves | PUT | `/api/leaves/{id}/reject` | Reject a leave request |
| Attendance | POST | `/api/attendance` | Mark attendance |
| Attendance | GET | `/api/attendance` | Retrieve attendance records |
| Attendance | GET | `/api/attendance/employee/{employeeId}` | Retrieve attendance for an employee |
| Holidays | POST | `/api/holidays` | Add a holiday |
| Holidays | GET | `/api/holidays` | Retrieve all holidays |
| Holidays | DELETE | `/api/holidays/{id}` | Delete a holiday |
| Payroll | POST | `/api/payroll` | Generate a payroll record |
| Payroll | GET | `/api/payroll/employee/{employeeId}` | Retrieve payroll for an employee |
| Payroll | GET | `/api/payroll/month/{month}` | Retrieve payroll by month |

Replace `{id}` and `{employeeId}` with the relevant record IDs.

The API client contains additional frontend service methods for some operations that are not implemented in the listed controllers. Check the corresponding backend controller before relying on those methods.

## 🧩 Database and Domain Models

The backend includes models representing the application's main business entities.

| Model | Purpose |
|---|---|
| `User` | Stores user-related information |
| `UserRole` | Represents application roles |
| `Employee` | Represents employee records |
| `EmployeeStatus` | Represents employee status |
| `Department` | Stores department information |
| `LeaveRequest` | Represents employee leave requests |
| `LeaveStatus` | Represents leave request status |
| `LeaveType` | Represents leave categories |
| `Attendance` | Stores employee attendance |
| `AttendanceStatus` | Represents attendance status |
| `Holiday` | Represents holidays |
| `Payroll` | Represents payroll records |
| `BaseEntity` | Provides shared entity functionality |

The application uses Spring Data JPA and Hibernate to map Java entities to relational database tables.

The current configuration sets:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This allows Hibernate to update the database schema based on entity changes. For production environments, consider using managed database migrations instead.

## 🔐 Security and Role-Based Access

The project includes Spring Security configuration, an authentication controller, user roles, and role-specific frontend dashboards.

The React application uses the stored user role to select between the administrative interface and the employee interface.

**Security note:** In the supplied `SecurityConfig.java`, the `/api/auth/**` endpoints are public and `anyRequest().permitAll()` permits all other API requests as well. Therefore, the current backend does not enforce role-based authorization for protected API operations. Frontend route separation alone is not sufficient to secure sensitive employee, leave, attendance, or payroll data.

For a production-ready implementation, configure backend authorization rules, validate the authenticated user's role for each protected operation, and ensure that credentials and authentication tokens are handled securely.

## 🧪 Testing

### Backend

From the `backend` directory, run:

```bash
mvn test
```

The project includes Spring Boot and Spring Security test dependencies.

### Frontend

From the `frontend` directory, run:

```bash
npm run lint
```

Create and run additional automated tests as the application evolves.

### API Testing with Postman

You can test the REST endpoints by sending HTTP requests to `http://localhost:8080`.

For example, to retrieve employees:

```http
GET http://localhost:8080/api/employees
```

To retrieve all leave requests:

```http
GET http://localhost:8080/api/leaves
```

For POST and PUT requests, send JSON request bodies matching the corresponding DTO classes.

## 📦 Build for Production

### Backend

From `backend/`:

```bash
mvn clean package
```

The generated JAR is placed in the `target/` directory.

### Frontend

From `frontend/`:

```bash
npm run build
```

Vite generates the production frontend build in `frontend/dist/`.

## 🔮 Future Enhancements

- Enforce backend role-based authorization for every protected API.
- Implement secure token-based authentication and session management.
- Add comprehensive request validation and centralized exception handling.
- Improve leave balance calculation and tracking.
- Add leave cancellation and more advanced approval workflows.
- Introduce email or in-app notifications.
- Add payroll reports and exportable payslips.
- Improve automated test coverage.
- Add pagination, filtering, and sorting to large data tables.
- Deploy the frontend and backend with secure environment-specific configurations.

## 👨‍💻 Author

**Pabitra Khatri**
**Aman Verma**
**Raghabendra Kumar Shah**
**Pasupuleti Niranjan**

Java Full Stack Developer

**Technical Skills:** Java, Spring Boot, Spring Security, JDBC, Spring Data JPA, Hibernate, MySQL, React.js, JavaScript, HTML, CSS, Bootstrap, Axios, REST APIs, and Maven.

This project demonstrates full-stack development using a layered Java backend, relational database integration, REST APIs, and a React-based user interface.

---

⭐ If you find this project useful, feel free to explore the code, suggest improvements, and follow my full-stack development journey.

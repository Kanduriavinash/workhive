<p align="center">
  <img src="src/main/resources/static/images/logo.png" alt="WorkHive logo" width="180"/>
</p>

<h1 align="center">WorkHive</h1>

<p align="center">
  A role-based job recruitment platform built with Java, Spring Boot, Spring Security and Thymeleaf.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot 3.3.5"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6.x-blue?style=flat-square&logo=springsecurity" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.x-005F0F?style=flat-square&logo=thymeleaf" alt="Thymeleaf"/>
  <img src="https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square&logo=docker" alt="Docker"/>
</p>

<p align="center"><strong>Find opportunities. Manage applications. Hire better.</strong></p>

---

## Overview

**WorkHive** is a full-stack Java web application that connects job seekers and recruiters through a complete recruitment workflow.

Candidates can create professional profiles, manage resumes, discover jobs and track applications. Recruiters can manage company profiles, publish job openings, review applicants and update application stages.

The project demonstrates practical **Spring Boot architecture, relational data modeling, authentication and authorization, server-side rendering, validation, file handling and containerization**.

> **Status:** Functional academic/portfolio project. Core recruitment workflows are implemented; additional production hardening and UI refinement are planned.

---

## Key Features

### Job Seekers

- Create and manage a professional profile
- Store education and work-experience information
- Add skills, languages and career preferences
- Upload and manage a resume
- Search jobs by keyword and employment type
- View detailed job postings
- Apply with a cover letter
- Submit a tailored resume for an application
- Prevent duplicate applications
- Track application status

### Recruiters

- Create and manage company profiles
- Publish job openings
- Set salary, location, deadline, employment type and required skills
- Edit, activate/pause and delete job postings
- View applicants across company postings
- Review candidate profiles
- Access submitted resumes
- Update application status through the recruitment pipeline

### Authentication & Security

- Spring Security form-based authentication
- BCrypt password hashing
- Role-based access control
- Protected candidate and recruiter areas
- CSRF protection
- Session invalidation on logout
- Server-side authorization checks for recruiter operations

---

## Recruitment Workflow

### Candidate

\`\`\`
Register
   ↓
Build Profile
   ↓
Upload Resume
   ↓
Browse Jobs
   ↓
Apply
   ↓
Track Application
\`\`\`

### Recruiter

\`\`\`
Register
   ↓
Company Profile
   ↓
Post Job
   ↓
Receive Applications
   ↓
Review Candidates
   ↓
Update Status
\`\`\`

### Application Pipeline

| Status | Meaning |
|---|---|
| **Applied** | Candidate submitted an application |
| **Under Review** | Recruiter is reviewing the application |
| **Shortlisted** | Candidate progressed to the next stage |
| **Accepted** | Candidate was selected |
| **Rejected** | Application was declined |

---

## Architecture

WorkHive follows a layered Spring Boot architecture:

\`\`\`
┌──────────────────────────────┐
│        Browser / Client      │
└──────────────┬───────────────┘
               ↓
┌──────────────────────────────┐
│ Thymeleaf + Bootstrap UI     │
└──────────────┬───────────────┘
               ↓
┌──────────────────────────────┐
│ Spring MVC Controllers       │
└──────────────┬───────────────┘
               ↓
┌──────────────────────────────┐
│ Service Layer                │
│ Business & workflow logic    │
└──────────────┬───────────────┘
               ↓
┌──────────────────────────────┐
│ Spring Data JPA Repositories │
└──────────────┬───────────────┘
               ↓
┌──────────────────────────────┐
│ MySQL / H2                   │
└──────────────────────────────┘

       Spring Security
      protects the flow
\`\`\`

### Main Layers

| Layer | Responsibility |
|---|---|
| **Controller** | Handles HTTP requests and prepares view data |
| **Service** | Contains business and application logic |
| **Repository** | Provides database access through Spring Data JPA |
| **Model** | Represents persistent domain entities |
| **DTO** | Handles data transfer and validation |
| **Config** | Security, authentication and application initialization |
| **Templates** | Server-rendered Thymeleaf pages |
| **Static** | CSS, images and frontend assets |

---

## Technology Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Core programming language |
| **Spring Boot 3.3.5** | Application framework |
| **Spring MVC** | Web layer |
| **Spring Security 6** | Authentication and authorization |
| **Spring Data JPA / Hibernate** | Persistence and ORM |
| **Thymeleaf** | Server-side rendering |
| **Bootstrap 5.3** | Responsive UI |
| **Bootstrap Icons** | Interface icons |
| **MySQL 8** | Relational database option |
| **H2** | Embedded development database |
| **Maven** | Build and dependency management |
| **Docker** | Containerization |

---

## Project Structure

\`\`\`
workhive/
├── src/main/java/com/workhive/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── src/main/resources/
│   ├── static/
│   │   ├── css/
│   │   └── images/
│   ├── templates/
│   │   ├── fragments/
│   │   ├── recruiter/
│   │   └── seeker/
│   └── application.properties
│
├── src/test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
└── README.md
\`\`\`

---

## Core Domain Model

\`\`\`
User
 ├── JobSeekerProfile
 │      ├── Education
 │      ├── WorkExperience
 │      └── Applications
 │
 └── RecruiterProfile
        └── JobPosting
               └── Applications
\`\`\`

This structure keeps role-specific profile information separated while connecting jobs, candidates and applications through JPA relationships.

---

## Demo Accounts

The development initializer creates sample data when the database is empty.

| Role | Email | Password |
|---|---|---|
| Recruiter | \`recruiter@workhive.com\` | \`password123\` |
| Job Seeker | \`seeker@workhive.com\` | \`password123\` |

The seeded environment includes sample companies, job postings, candidate information and an application.

> **Security:** These credentials are for local/demo use only. Never use them for a production deployment.

---

## Getting Started

### Prerequisites

- JDK 21
- Git
- Docker Desktop *(optional)*

The repository includes the Maven Wrapper, so Maven does not need to be installed separately.

### Clone

\`\`\`bash
git clone https://github.com/Kanduriavinash/workhive.git
cd workhive
\`\`\`

### Run locally

**Windows**

\`\`\`powershell
.\mvnw.cmd spring-boot:run
\`\`\`

**Linux / macOS**

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

Open **http://localhost:8080**

### Build

**Windows**

\`\`\`powershell
.\mvnw.cmd clean package
\`\`\`

**Linux / macOS**

\`\`\`bash
./mvnw clean package
\`\`\`

### Test

\`\`\`bash
./mvnw test
\`\`\`

On Windows:

\`\`\`powershell
.\mvnw.cmd test
\`\`\`

---

## Database Configuration

The default configuration uses an embedded **H2 in-memory database**, allowing the application to start without a separate database server.

For MySQL, use environment-specific configuration or environment variables.

Example:

\`\`\`properties
spring.datasource.url=jdbc:mysql://localhost:3306/workhive
spring.datasource.username=root
spring.datasource.password=\${DB_PASSWORD}
\`\`\`

**Never commit real database credentials to source control.**

---

## Docker

The repository includes a multi-stage Dockerfile and Docker Compose configuration for running WorkHive with MySQL.

\`\`\`bash
docker compose up --build
\`\`\`

Open **http://localhost:8080**

Stop containers:

\`\`\`bash
docker compose down
\`\`\`

Remove the local database volume:

\`\`\`bash
docker compose down -v
\`\`\`

> The supplied Compose setup is intended for local development/demo use. Review credentials, secrets, networking and persistence before public production deployment.

---

## Resume Management

WorkHive supports resumes for candidate profiles and individual applications.

- Maximum multipart upload size: **10 MB**
- Files are stored under \`uploads/resumes/\`
- Stored filenames use generated UUIDs
- Candidates can submit a tailored resume for an application

For production use, stricter server-side file validation and fine-grained resume authorization should be added.

---

## Security Design

### Implemented

- BCrypt password hashing
- Form-based authentication
- Role-based route authorization
- Protected seeker and recruiter areas
- CSRF protection
- Session invalidation on logout
- Authorization checks for recruiter application operations

### Production Hardening Roadmap

- Environment-based secret management
- Strict resume MIME/content validation
- Fine-grained resume download authorization
- Production-specific security configuration
- Disable H2 console in production
- HTTPS and secure cookie configuration
- Expanded automated security tests

---

## UI & UX

The interface uses **Thymeleaf, Bootstrap 5 and Bootstrap Icons**.

Current UI areas include:

- Responsive navigation
- Light/dark theme support
- Job listing cards
- Job detail pages
- Candidate dashboard
- Candidate profile management
- Application tracking
- Recruiter dashboard
- Job management
- Applicant pipeline
- Resume upload interfaces
- Responsive layouts

The UI is designed around the recruitment workflow instead of a generic CRUD interface.

---

## Screenshots

Screenshots can be added here as the UI is finalized.

Recommended showcase screenshots:

1. Home / Job Discovery
2. Browse Jobs
3. Job Details
4. Candidate Dashboard
5. Candidate Profile
6. Recruiter Dashboard
7. Manage Jobs
8. Applicant Pipeline
9. Login / Registration

---

## Engineering Highlights

### Layered architecture

Controllers, services and repositories have clearly separated responsibilities.

### Role-based application design

Candidate and recruiter capabilities are separated through Spring Security authorities.

### Recruitment workflow

Applications connect users, profiles, jobs and recruiter decisions instead of functioning as isolated CRUD records.

### Resume handling

The application supports both profile resumes and job-specific resumes.

### Development-friendly database setup

H2 provides simple local development while MySQL and Docker Compose support a more realistic database environment.

---

## Roadmap

### Completed

- [x] Authentication and registration
- [x] Candidate profiles
- [x] Recruiter profiles
- [x] Job posting
- [x] Job search
- [x] Job applications
- [x] Application status management
- [x] Resume upload
- [x] Candidate dashboard
- [x] Recruiter dashboard
- [x] Docker configuration

### Planned

- [ ] Advanced job filters
- [ ] Fine-grained resume authorization
- [ ] Stronger file-type validation
- [ ] Expanded automated tests
- [ ] Production configuration profiles
- [ ] Email notifications
- [ ] Pagination for large datasets
- [ ] Accessibility improvements
- [ ] Production deployment with managed database/storage

---

## Why This Project?

WorkHive was built as a practical demonstration of developing a complete Java web application rather than only implementing isolated CRUD operations.

It combines:

**Backend**
- Spring Boot
- Spring MVC
- Spring Security
- JPA/Hibernate
- Service/repository architecture

**Database**
- Relational entity design
- JPA relationships
- Repository-based persistence
- H2 and MySQL support

**Web**
- Thymeleaf
- Bootstrap
- Responsive layouts
- Form validation
- Server-side rendering

**Software Engineering**
- Authentication and authorization
- File handling
- Layered architecture
- Dockerization
- Testing foundation

---

## Author

**Avinash Kanduri**

B.Tech Computer Science & Engineering

- GitHub: https://github.com/Kanduriavinash
- Portfolio: https://kanduriavinash.github.io/Portfolio

---

## License

This project is licensed under the MIT License.

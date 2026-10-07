<p align="center">
  <img src="src/main/resources/static/images/logo.png" alt="WorkHive logo" width="180"/>
</p>

<h1 align="center">WorkHive</h1>

<p align="center">
  <strong>Full-Stack Job Portal & Recruitment Management Platform</strong>
</p>

<p align="center">
  A Java and Spring Boot web application connecting job seekers and recruiters through profile management, job discovery, applications and applicant tracking.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot 3.3.5"/>
  <img src="https://img.shields.io/badge/Spring%20Security-6.x-blue?style=flat-square&logo=springsecurity" alt="Spring Security"/>
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?style=flat-square" alt="Spring Data JPA"/>
  <img src="https://img.shields.io/badge/Thymeleaf-3.x-005F0F?style=flat-square&logo=thymeleaf" alt="Thymeleaf"/>
  <img src="https://img.shields.io/badge/Bootstrap-5.3-7952B3?style=flat-square&logo=bootstrap" alt="Bootstrap"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql" alt="MySQL"/>
  <img src="https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square&logo=docker" alt="Docker"/>
</p>

---

## Overview

**WorkHive** is a full-stack recruitment platform built with **Java 21 and Spring Boot 3**.

It provides two role-based experiences:

- **Job Seekers** can create profiles, manage education and work experience, upload resumes, search for jobs, submit applications and track their application status.
- **Recruiters** can create company profiles, publish and manage job postings, review applicants, inspect candidate profiles and move applications through a recruitment pipeline.

The application uses a layered architecture with **Spring MVC, Spring Security, Spring Data JPA, Hibernate, Thymeleaf and a relational database**.

> **Project status:** Functional academic/portfolio project. The core recruitment workflow is implemented. Production hardening and additional automated test coverage remain future work.

---

## What WorkHive Does

WorkHive models the recruitment process as a connected workflow rather than a collection of independent CRUD screens.

\`\`\`
                  WORKHIVE RECRUITMENT FLOW

 Job Seeker                                      Recruiter
     │                                               │
     ▼                                               ▼
 Create Profile                              Create Company Profile
     │                                               │
     ├── Education                                  │
     ├── Experience                                 ▼
     └── Resume                                  Post Job
     │                                               │
     └──────────────► Browse Jobs ◄─────────────────┘
                            │
                            ▼
                       Apply for Job
                            │
                            ▼
                       Application
                            │
                            ▼
                    Recruiter Review
                            │
             ┌──────────────┼──────────────┐
             ▼              ▼              ▼
        Under Review   Shortlisted     Rejected
                            │
                            ▼
                         Accepted
\`\`\`

---

## Features

### Job Seeker Features

- User registration with the **Job Seeker** role
- Secure login using Spring Security
- Candidate profile management
- Personal and contact information
- Skills and known languages
- Desired job title and preferred industry
- Employment-type preference
- Education records
- Work-experience records
- Add and delete education entries
- Add and delete work-experience entries
- Resume upload
- Job browsing
- Keyword-based job search
- Employment-type filtering
- Detailed job pages
- Application submission
- Cover letter support
- Optional job-specific resume upload
- Duplicate application prevention
- Application history
- Application status tracking

### Recruiter Features

- User registration with the **Recruiter** role
- Recruiter/company profile management
- Company name, website, location and industry
- Company description
- Recruiter dashboard
- Job creation
- Job editing
- Job deletion
- Activate/deactivate job postings
- Salary and deadline fields
- Employment-type selection
- Required skills
- Job description and requirements
- View all company job postings
- View applicants for a specific job
- View all applicants
- View applicant profiles
- Review education and work experience
- Access submitted resumes
- Update application status

---

## Application Status Pipeline

WorkHive currently supports five application states:

| Status | Description |
|---|---|
| **Applied** | Application has been submitted |
| **Under Review** | Recruiter is reviewing the candidate |
| **Shortlisted** | Candidate has progressed to the next stage |
| **Accepted** | Candidate has been selected |
| **Rejected** | Application has been declined |

---

## Job Types

The application supports:

- Full Time
- Part Time
- Remote
- Internship
- Contract

---

## Job Search

The job search is implemented through a Spring Data JPA query.

A keyword can match:

- Job title
- Job location
- Required skills
- Recruiter/company name

The search can also be combined with a **job type** filter.

Only active job postings are returned by the public search.

---

## System Architecture

WorkHive follows a layered Spring Boot architecture:

\`\`\`
┌─────────────────────────────────────────────┐
│                  Browser                    │
│            HTML / CSS / Bootstrap           │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│              Thymeleaf Views                │
│        Server-side rendered templates       │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│            Spring MVC Controllers            │
│ Auth • Jobs • Seeker • Recruiter • Resume   │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│                Service Layer                │
│ Users • Jobs • Applications • Profiles      │
│                 File Storage                │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│          Spring Data JPA Repositories       │
└──────────────────────┬──────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────┐
│              H2 / MySQL Database            │
└─────────────────────────────────────────────┘

              Spring Security
       authentication + authorization
\`\`\`

### Architectural Responsibilities

| Layer | Responsibility |
|---|---|
| **Controller** | Handles HTTP requests and coordinates views |
| **Service** | Contains business rules and application logic |
| **Repository** | Database access through Spring Data JPA |
| **Model** | JPA entities representing application data |
| **DTO** | Form and data-transfer objects with validation |
| **Config** | Security, authentication and data initialization |
| **Templates** | Thymeleaf UI |
| **Static** | CSS and image assets |

---

## Security Architecture

Spring Security protects the application according to the authenticated user's role.

### Public routes

The following areas are publicly accessible:

- Home page
- Job listing
- Job details
- Registration
- Login
- Static CSS/JS/images
- H2 console in the current development configuration

### Job Seeker routes

\`\`\`
/seeker/**
/apply/**
\`\`\`

require:

\`\`\`
ROLE_JOB_SEEKER
\`\`\`

### Recruiter routes

\`\`\`
/recruiter/**
\`\`\`

require:

\`\`\`
ROLE_RECRUITER
\`\`\`

### Resume routes

\`\`\`
/resumes/**
\`\`\`

currently require an authenticated user.

### Security mechanisms used

- Spring Security 6
- BCrypt password hashing
- Form-based authentication
- Custom authentication success handling
- Role-based authorization
- CSRF protection
- Session invalidation on logout
- JSESSIONID deletion on logout
- Server-side ownership checks for recruiter job management
- Server-side ownership checks for application status updates
- Server-side ownership checks for candidate education and experience deletion

---

## Data Model

The main domain relationships are:

\`\`\`
                         User
                    ┌──────┴──────┐
                    │             │
                    ▼             ▼
          JobSeekerProfile   RecruiterProfile
             │       │              │
             │       ├── Education  │
             │       └── Experience │
             │                      │
             │                      └── JobPosting
             │                             │
             └────── Application ◄─────────┘
\`\`\`

### Important entities

| Entity | Purpose |
|---|---|
| **User** | Authentication, contact information and role |
| **JobSeekerProfile** | Candidate-specific information |
| **RecruiterProfile** | Company/recruiter information |
| **JobPosting** | Job listing and recruitment requirements |
| **Application** | Candidate application against a job |
| **Education** | Candidate education history |
| **WorkExperience** | Candidate professional experience |

### Application uniqueness

The database defines a unique constraint on:

\`\`\`
(job_posting_id, job_seeker_id)
\`\`\`

This prevents the same candidate from creating multiple application records for the same job.

---

## Resume Management

Candidates can upload a resume from their profile or provide a different resume when applying to a specific job.

### Current implementation

- Upload directory: \`uploads/resumes/\`
- Generated stored filename: UUID + original extension
- Maximum multipart request size: **10 MB**
- Original filename is retained as metadata
- Resume can be served inline through the resume controller
- Applications retain the resume associated with that application

### Important production note

The current implementation does **not** perform strict server-side MIME/content validation before storing an uploaded file. The UI suggests PDF/DOCX uploads, but production deployment should add backend file validation and stronger access control for individual resume resources.

---

## Technology Stack

### Backend

- **Java 21**
- **Spring Boot 3.3.5**
- Spring MVC
- Spring Security 6
- Spring Data JPA
- Hibernate
- Jakarta Validation

### Frontend

- Thymeleaf
- HTML5
- CSS3
- Bootstrap 5.3
- Bootstrap Icons
- JavaScript for UI interactions

### Database

- **H2** for the default development configuration
- **MySQL 8** supported through the MySQL Connector/J dependency

### Build & Deployment

- Maven
- Maven Wrapper
- Docker
- Docker Compose

---

## Project Structure

\`\`\`
workhive/
│
├── src/
│   ├── main/
│   │   ├── java/com/workhive/
│   │   │   ├── config/
│   │   │   │   ├── CustomAuthenticationSuccessHandler.java
│   │   │   │   ├── CustomUserDetails.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── DataInitializer.java
│   │   │   │   └── SecurityConfig.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── JobController.java
│   │   │   │   ├── JobSeekerController.java
│   │   │   │   ├── RecruiterController.java
│   │   │   │   └── ResumeDownloadController.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── images/
│   │       ├── templates/
│   │       │   ├── fragments/
│   │       │   ├── recruiter/
│   │       │   └── seeker/
│   │       └── application.properties
│   │
│   └── test/
│
├── .mvn/
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
\`\`\`

---

## Main Controllers

| Controller | Responsibility |
|---|---|
| **AuthController** | Home, login and registration |
| **JobController** | Public job search and job details |
| **JobSeekerController** | Candidate dashboard, profile, resume, education, experience and applications |
| **RecruiterController** | Recruiter dashboard, company profile, jobs and applicant management |
| **ResumeDownloadController** | Serves stored resume files |

---

## Main Services

| Service | Responsibility |
|---|---|
| **UserService** | Registration and user/profile initialization |
| **JobPostingService** | Job creation, editing, search, activation and deletion |
| **ApplicationService** | Application submission, retrieval and status updates |
| **JobSeekerService** | Candidate profile, resume, education and experience |
| **RecruiterService** | Recruiter/company profile management |
| **FileStorageService** | Resume file storage and retrieval |

---

## Getting Started

### Prerequisites

Install:

- **JDK 21**
- **Git**
- Docker Desktop *(optional)*

The project includes the Maven Wrapper, so Maven does not need to be installed separately.

### 1. Clone

\`\`\`bash
git clone https://github.com/Kanduriavinash/workhive.git
cd workhive
\`\`\`

### 2. Run the application

#### Windows

\`\`\`powershell
.\mvnw.cmd spring-boot:run
\`\`\`

#### Linux / macOS

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

The application runs on:

\`\`\`
http://localhost:8080
\`\`\`

### 3. Build

Windows:

\`\`\`powershell
.\mvnw.cmd clean package
\`\`\`

Linux / macOS:

\`\`\`bash
./mvnw clean package
\`\`\`

### 4. Run tests

Windows:

\`\`\`powershell
.\mvnw.cmd test
\`\`\`

Linux / macOS:

\`\`\`bash
./mvnw test
\`\`\`

The repository currently contains a Spring Boot context-loading test. Broader unit/integration/security test coverage is a planned improvement.

---

## Database Configuration

### Default: H2

The current \`application.properties\` uses:

- H2 database
- In-memory persistence
- H2 dialect
- Hibernate \`ddl-auto=update\`

This means the application can start without installing MySQL locally.

### MySQL

The project also includes the MySQL Connector/J dependency and commented MySQL configuration.

Example:

\`\`\`properties
spring.datasource.url=jdbc:mysql://localhost:3306/workhive
spring.datasource.username=root
spring.datasource.password=\${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
\`\`\`

Use environment variables or a secret-management solution for real credentials.

> **Important:** Never commit real database passwords to GitHub. Any previously exposed credential should be rotated.

---

## Docker

The repository includes:

- A multi-stage \`Dockerfile\`
- A \`docker-compose.yml\`
- Java 21 build/runtime images
- MySQL 8 service
- Persistent MySQL volume
- Application container on port 8080

### Start

\`\`\`bash
docker compose up --build
\`\`\`

### Stop

\`\`\`bash
docker compose down
\`\`\`

### Stop and remove database volume

\`\`\`bash
docker compose down -v
\`\`\`

> The included Compose credentials are intended for local development/demo use and should be replaced with secure secrets before any public deployment.

---

## Demo Data

On the first application startup, the development initializer creates sample recruitment data when the database is empty.

### Demo accounts

| Role | Email | Password |
|---|---|---|
| Recruiter | \`recruiter@workhive.com\` | \`password123\` |
| Job Seeker | \`seeker@workhive.com\` | \`password123\` |

The seed data includes:

- Sample recruiter/company profiles
- Multiple job postings
- Candidate profile information
- Education and work experience
- A sample resume file
- A pre-existing application

> These credentials are for local/demo purposes only.

---

## UI Pages

### Public

- Home
- Browse Jobs
- Job Details
- Login
- Registration

### Job Seeker

- Dashboard
- Profile
- Education management
- Work-experience management
- Resume management
- My Applications

### Recruiter

- Dashboard
- Company Profile
- Manage Jobs
- Create/Edit Job
- Applicants
- Applicant Profile

---

## Screenshots

For a strong portfolio presentation, add screenshots of:

| Screenshot | Purpose |
|---|---|
| **Home Page** | Overall WorkHive UI |
| **Job Search** | Search and filtering |
| **Job Details** | Job information and application flow |
| **Login/Register** | Authentication and role selection |
| **Job Seeker Dashboard** | Candidate experience |
| **Candidate Profile** | Resume, education and experience |
| **Applications** | Application tracking |
| **Recruiter Dashboard** | Recruiter overview |
| **Manage Jobs** | Job lifecycle management |
| **Applicant Pipeline** | Recruitment workflow |

---

## Engineering Highlights

### 1. Layered Spring architecture

The application separates controllers, services, repositories and entities, making business logic easier to maintain.

### 2. Role-based access control

Spring Security distinguishes between:

\`\`\`
ROLE_JOB_SEEKER
ROLE_RECRUITER
\`\`\`

and protects role-specific routes accordingly.

### 3. Ownership checks

Recruiter operations verify that the authenticated recruiter owns the relevant job before modifying or deleting it.

Candidate education and experience deletion also checks ownership.

### 4. Application integrity

A database unique constraint prevents duplicate applications for the same candidate/job combination.

### 5. Search implementation

Job search uses a JPQL query that supports keyword matching across multiple job/company fields plus job-type filtering.

### 6. Resume storage

Uploaded resumes receive generated UUID-based filenames, avoiding direct reliance on user-provided filenames for storage.

### 7. Development-friendly setup

H2 allows quick startup while MySQL and Docker Compose provide an alternative environment closer to a deployed application.

---

## Security Considerations

WorkHive includes several security mechanisms, but it should **not yet be described as production-hardened**.

### Current protections

- BCrypt password hashing
- Spring Security authentication
- Role-based route protection
- CSRF protection
- Logout session invalidation
- Recruiter ownership checks
- Candidate ownership checks
- Duplicate application constraint

### Recommended before production

- Strict backend resume type/content validation
- Fine-grained authorization for resume downloads
- Disable H2 console in production
- Move all credentials to environment variables/secrets
- HTTPS-only deployment
- Secure cookie configuration
- Add security-focused integration tests
- Add stronger path-boundary validation for stored files
- Add production-specific Spring profiles

---

## Testing

The project includes:

- Spring Boot Test
- Spring Security Test

Current test coverage includes an application context-loading test.

### Recommended expansion

\`\`\`
Authentication
   ├── Registration validation
   ├── Login success/failure
   └── Role authorization

Jobs
   ├── Create
   ├── Update
   ├── Delete
   └── Search

Applications
   ├── Submit
   ├── Duplicate prevention
   ├── Status updates
   └── Ownership checks

Files
   ├── Upload validation
   └── Access authorization
\`\`\`

---

## Future Enhancements

Planned improvements include:

- Advanced job filters
- Pagination for jobs and applicants
- Candidate profile completion indicators
- Recruiter analytics
- Email notifications
- Stronger resume file validation
- Fine-grained resume authorization
- Expanded unit and integration tests
- Production-specific configuration profiles
- Managed cloud database
- External object storage for resumes
- Improved accessibility
- Additional mobile UX refinements
- Production deployment and monitoring

---

## Project Roadmap

### Implemented

- [x] Role-based registration
- [x] Authentication
- [x] BCrypt password hashing
- [x] Candidate profiles
- [x] Recruiter/company profiles
- [x] Education management
- [x] Work-experience management
- [x] Resume upload
- [x] Job creation
- [x] Job editing
- [x] Job activation/deactivation
- [x] Job deletion
- [x] Job search
- [x] Job-type filtering
- [x] Job details
- [x] Job applications
- [x] Cover letters
- [x] Duplicate application prevention
- [x] Application tracking
- [x] Applicant review
- [x] Application status management
- [x] Docker configuration
- [x] H2 development database
- [x] MySQL support

### Planned

- [ ] Advanced filtering
- [ ] Pagination
- [ ] Production-grade resume authorization
- [ ] Stronger upload validation
- [ ] Expanded automated tests
- [ ] Notifications
- [ ] Recruiter analytics
- [ ] Production deployment

---

## Why WorkHive Is a Strong Portfolio Project

WorkHive demonstrates more than basic CRUD functionality.

It brings together:

**Backend Engineering**
- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Service/repository architecture

**Database Engineering**
- JPA/Hibernate
- Relational entity design
- Entity relationships
- JPQL search
- Unique constraints

**Web Development**
- Thymeleaf
- Bootstrap
- Responsive UI
- Form validation
- Role-specific dashboards

**Application Security**
- BCrypt
- Authentication
- Authorization
- CSRF protection
- Ownership validation

**Deployment**
- Maven Wrapper
- Docker
- Docker Compose
- H2/MySQL configuration

The result is a complete recruitment workflow implemented as a single integrated web application.

---

## Author

**Avinash Kanduri**

B.Tech Computer Science & Engineering

- GitHub: https://github.com/Kanduriavinash
- Portfolio: https://kanduriavinash.github.io/Portfolio

---

## Project Links

- **Repository:** https://github.com/Kanduriavinash/workhive
- **Portfolio:** https://kanduriavinash.github.io/Portfolio

---

## License

No license file is currently included in the repository.

If this project is intended to be publicly reusable, add an appropriate license file before describing it as an open-source licensed project.

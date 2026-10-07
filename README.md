<p align="center">
  <img src="src/main/resources/static/images/logo.png" alt="WorkHive Logo" width="220"/>
</p>

# 🐝 WorkHive — Enterprise Job Portal & Applicant Tracking System (ATS)

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot 3.3.5](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-blue.svg)](https://spring.io/projects/spring-security)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Docker Ready](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

A modern, production-grade recruitment and applicant tracking platform built with **Java 21, Spring Boot 3, Spring Data JPA, Spring Security, and Thymeleaf**. Designed with clean 3-tier architectural separation, BCrypt encryption, role-based access control (RBAC), and containerized cloud deployment readiness.

---

## 📌 Architectural Overview & System Design

```
                     ┌───────────────────────────────────────────────┐
                     │               Web Client / UI                │
                     │  (Bootstrap 5.3 + Responsive Thymeleaf Views) │
                     └───────────────────────┬───────────────────────┘
                                             │ HTTP / HTTPS
                                             ▼
                     ┌───────────────────────────────────────────────┐
                     │            Spring Security 6 (RBAC)           │
                     │   Form Login • BCrypt • Role Authorization    │
                     └───────────────────────┬───────────────────────┘
                                             │
             ┌───────────────────────────────┼───────────────────────────────┐
             ▼                               ▼                               ▼
    ┌─────────────────┐             ┌─────────────────┐             ┌─────────────────┐
    │ Auth & Public   │             │ Job Seeker Area │             │ Recruiter Area  │
    │ Controllers     │             │ Controllers     │             │ Controllers     │
    └────────┬────────┘             └────────┬────────┘             └────────┬────────┘
             │                               │                               │
             └───────────────────────┬───────┴───────────────────────────────┘
                                     ▼
                     ┌───────────────────────────────────────────────┐
                     │                 Service Layer                 │
                     │   UserService • JobPostingService • ATS       │
                     │   JobSeekerService • FileStorageService       │
                     └───────────────────────┬───────────────────────┘
                                             │
                                             ▼
                     ┌───────────────────────────────────────────────┐
                     │         Data Access Layer (Spring JPA)        │
                     │  UserRepository • JobPostingRepository        │
                     │  ApplicationRepository • SeekerProfileRepo    │
                     └───────────────────────┬───────────────────────┘
                                             │ JDBC / HikariCP
                                             ▼
                     ┌───────────────────────────────────────────────┐
                     │            Relational Database                │
                     │       MySQL 8.0 / H2 In-Memory Engine         │
                     └───────────────────────────────────────────────┘
```

---

## 🚀 Key Features

### 👤 For Candidates / Job Seekers
- **Unified Profile Dossier:** Fill and manage personal contact details, technical skill chips, languages, and career goals (desired title, industry, employment type).
- **Resume Management:** Upload PDF / DOCX resumes with secure UUID storage and preview/download capability.
- **Education & Career History:** Add degrees, institutions, graduation years, and CGPA metrics alongside detailed work experience logs.
- **Job Discovery Engine:** Search live openings by title, keywords, technical skills, or location, filtered by employment type (Full-time, Remote, Part-time, Internship).
- **One-Click Application:** Apply with attached profile resume or upload tailored resumes with custom cover notes.
- **Application Tracker:** Live tracker displaying candidate status across 5 pipeline stages (*Applied*, *Under Review*, *Shortlisted*, *Accepted*, *Rejected*).

### 🏢 For Recruiters / Employers
- **Company Branding:** Establish company headquarters, official website, industry tags, and culture summaries.
- **Job Lifecycle Management:** Post openings, define salary, deadlines, required technical skills, toggle active/paused status, edit, or remove listings.
- **Applicant Tracking Pipeline (ATS):** View candidate rosters per opening or company-wide.
- **Candidate Dossier Review:** Inspect candidate education credentials, employment history, cover letters, and download submitted resumes.
- **Live Status Progression:** Transition applicants between stages with instantaneous dropdown updates.

### 🛡️ Enterprise Security & Architecture
- **BCrypt Password Hashing:** Zero plaintext password storage.
- **Role-Based Authorization:** Strict route guarding separating candidate and employer workspaces.
- **Duplicate Prevention:** Database-level unique constraints preventing multiple applications to identical job listings.
- **Clean Relational Modeling:** Standardized JPA relationships (`@ManyToOne`, `@OneToOne`, `@OneToMany`) resolving all inheritance anti-patterns.

---

## ⚡ Quick Start (Run Locally in 60 Seconds)

### Prerequisites
- **Java 17+** installed (Java 21 or Java 24 supported)
- Git

### 1. Clone & Start
WorkHive includes the **Maven Wrapper (`mvnw`)**, so you do not need Maven pre-installed!

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

Once started, open your browser at **[http://localhost:8080](http://localhost:8080)**.

---

## 🔑 Pre-Loaded Demo Accounts

The application automatically seeds realistic sample data on first launch:

| Role | Email | Password | Pre-loaded Data |
| :--- | :--- | :--- | :--- |
| **Employer / Recruiter** | `recruiter@workhive.com` | `password123` | WorkHive Tech Innovations (2 Active Postings, Candidates Applied) |
| **Candidate / Seeker** | `seeker@workhive.com` | `password123` | Alex Johnson (Full CS Degree, Work History, Resume, Active Application) |

---

## 🗄️ Database Configuration

By default, WorkHive runs out-of-the-box using embedded **H2 file-based persistence** (zero database setup needed).

### Switching to MySQL:
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/workhive?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

---

## 🐳 Docker Deployment

Run the complete stack (WorkHive App + MySQL 8.0) with Docker Compose:

```bash
docker-compose up --build
```

---

## 📋 Suggested LinkedIn Project Showcase Post

```text
🚀 Excited to showcase my latest full-stack Java engineering project: WorkHive — an Enterprise Recruitment & Applicant Tracking System (ATS)!

Designed and engineered with Java 21, Spring Boot 3, Spring Data JPA, and Spring Security 6.

🔑 Key Architecture & Engineering Highlights:
• Scalable Multi-Tier Architecture: Clean separation of Concerns across Controllers, Services, and JPA Repositories.
• Role-Based Access Control (RBAC): Candidate and Recruiter workspaces with BCrypt credential encryption.
• Candidate Pipeline Engine: Full applicant tracking system with real-time status transitions (Applied ➔ Under Review ➔ Shortlisted ➔ Accepted).
• Document Ingestion: Secure PDF/DOCX resume upload, UUID path isolation, and inline document preview.
• Dynamic Search Engine: Multi-criteria job querying by skill stack, location, and employment type.
• Dockerized & Cloud Ready: Packaged with multi-stage Docker builds and automated database seeding.

💻 Tech Stack: Java 21 | Spring Boot 3 | Spring Security | Spring Data JPA | MySQL / H2 | Thymeleaf | Bootstrap 5 | Docker

Check out the GitHub repository below! 👇
#Java #SpringBoot #BackendDevelopment #FullStack #SoftwareEngineering #JavaDeveloper #WebDevelopment
```

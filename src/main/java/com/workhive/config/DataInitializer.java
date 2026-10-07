package com.workhive.config;

import com.workhive.model.*;
import com.workhive.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationRepository applicationRepository;
    private final EducationRepository educationRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RecruiterProfileRepository recruiterProfileRepository,
                           JobSeekerProfileRepository jobSeekerProfileRepository,
                           JobPostingRepository jobPostingRepository,
                           ApplicationRepository applicationRepository,
                           EducationRepository educationRepository,
                           WorkExperienceRepository workExperienceRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.applicationRepository = applicationRepository;
        this.educationRepository = educationRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Data already exists
        }

        // Create sample placeholder resume file
        String demoResumeFileName = "sample_resume_alex.pdf";
        try {
            Path resumeDir = Paths.get("uploads/resumes");
            Files.createDirectories(resumeDir);
            Path demoFile = resumeDir.resolve(demoResumeFileName);
            if (!Files.exists(demoFile)) {
                Files.writeString(demoFile, "%PDF-1.4 Sample Resume for Alex Johnson - Full Stack Java Engineer");
            }
        } catch (IOException ignored) {}

        // 1. Recruiter 1
        User recUser1 = new User("Sarah Jenkins", "recruiter@workhive.com", passwordEncoder.encode("password123"), "9876543210", Role.ROLE_RECRUITER);
        userRepository.save(recUser1);

        RecruiterProfile recProfile1 = new RecruiterProfile(recUser1, "WorkHive Tech Innovations");
        recProfile1.setCompanyLocation("San Francisco, CA / Remote");
        recProfile1.setCompanyWebsite("https://workhive.dev");
        recProfile1.setIndustry("Information Technology & Cloud");
        recProfile1.setAboutCompany("WorkHive Tech Innovations is a high-growth platform engineering distributed backend microservices and next-generation developer tooling.");
        recruiterProfileRepository.save(recProfile1);

        // 2. Recruiter 2
        User recUser2 = new User("David Miller", "talent@cloudscale.io", passwordEncoder.encode("password123"), "9876543211", Role.ROLE_RECRUITER);
        userRepository.save(recUser2);

        RecruiterProfile recProfile2 = new RecruiterProfile(recUser2, "CloudScale Solutions");
        recProfile2.setCompanyLocation("New York, NY / Hybrid");
        recProfile2.setCompanyWebsite("https://cloudscale.io");
        recProfile2.setIndustry("FinTech & Distributed Systems");
        recProfile2.setAboutCompany("CloudScale powers high-frequency transactional backends for financial institutions worldwide.");
        recruiterProfileRepository.save(recProfile2);

        // 3. Job Postings
        JobPosting job1 = new JobPosting(
                recProfile1,
                "Senior Java & Spring Boot Engineer",
                "We are seeking a senior backend engineer to lead our microservices infrastructure. You will design, develop, and scale REST APIs serving millions of requests.",
                "• 3+ years experience with Java & Spring Boot\n• Strong grasp of SQL, JPA, and relational DB architecture\n• Experience with Docker and CI/CD pipelines\n• Solid understanding of RESTful API design and security",
                "San Francisco, CA (Remote)",
                125000.0,
                JobType.REMOTE,
                LocalDate.now().plusDays(30),
                "Java, Spring Boot, Spring Data JPA, MySQL, Docker, REST APIs"
        );
        jobPostingRepository.save(job1);

        JobPosting job2 = new JobPosting(
                recProfile1,
                "Full Stack Web Developer",
                "Join our core product engineering team building responsive web applications using Java Spring Boot on the backend and modern reactive frontends.",
                "• Proficiency in Java, HTML5, CSS3, JavaScript\n• Experience with Thymeleaf, React, or modern templating\n• Good understanding of database normalization and Hibernate",
                "Austin, TX",
                95000.0,
                JobType.FULL_TIME,
                LocalDate.now().plusDays(25),
                "Java, Spring Boot, JavaScript, HTML5, CSS3, Git"
        );
        jobPostingRepository.save(job2);

        JobPosting job3 = new JobPosting(
                recProfile2,
                "Junior Software Engineer (Java Track)",
                "An exciting opportunity for recent graduates or early career engineers to hone their craft in high-throughput backend services and cloud deployment.",
                "• Strong foundational knowledge in Java & Object-Oriented Programming\n• Basic understanding of SQL and web fundamentals\n• Eagerness to learn cloud deployment and Agile best practices",
                "New York, NY (Hybrid)",
                75000.0,
                JobType.FULL_TIME,
                LocalDate.now().plusDays(45),
                "Java, OOP, SQL, Problem Solving, Spring Basics"
        );
        jobPostingRepository.save(job3);

        JobPosting job4 = new JobPosting(
                recProfile2,
                "DevOps & Cloud Infrastructure Intern",
                "Summer internship focused on container orchestration, cloud automation, and monitoring pipelines.",
                "• Enrolled in Computer Science or related degree\n• Familiarity with Linux shell, Docker, and GitHub Actions",
                "Remote",
                45000.0,
                JobType.INTERNSHIP,
                LocalDate.now().plusDays(15),
                "Linux, Docker, Bash, CI/CD, AWS"
        );
        jobPostingRepository.save(job4);

        // 4. Job Seeker
        User seekerUser = new User("Alex Johnson", "seeker@workhive.com", passwordEncoder.encode("password123"), "9876543212", Role.ROLE_JOB_SEEKER);
        userRepository.save(seekerUser);

        JobSeekerProfile seekerProfile = new JobSeekerProfile(seekerUser);
        seekerProfile.setFirstName("Alex");
        seekerProfile.setLastName("Johnson");
        seekerProfile.setDob(LocalDate.of(2001, 5, 14));
        seekerProfile.setGender("Male");
        seekerProfile.setCurrentAddress("Seattle, WA");
        seekerProfile.setPermanentAddress("Seattle, WA");
        seekerProfile.setNationality("American");
        seekerProfile.setSkills("Java, Spring Boot, Hibernate, MySQL, Docker, RESTful APIs, Git, Maven");
        seekerProfile.setKnownLanguages("English, Spanish");
        seekerProfile.setDesiredJobTitle("Backend Java Developer");
        seekerProfile.setPreferredIndustry("Software & Cloud Technology");
        seekerProfile.setEmploymentType("Full Time / Remote");
        seekerProfile.setResumeFileName(demoResumeFileName);
        seekerProfile.setResumeOriginalName("Alex_Johnson_Resume_2026.pdf");
        jobSeekerProfileRepository.save(seekerProfile);

        // Seeker Education
        Education edu1 = new Education(seekerProfile, "B.Tech", "Computer Science & Engineering", "State University of Technology", 2024, "3.85 / 4.0 CGPA");
        educationRepository.save(edu1);

        // Seeker Experience
        WorkExperience exp1 = new WorkExperience(seekerProfile, "Apex Innovations", "Software Developer Intern", "Jan 2024 - Dec 2024", 1, "Designed high-performance REST APIs in Spring Boot, optimized SQL query execution times by 35%, and wrote automated unit tests.");
        workExperienceRepository.save(exp1);

        // 5. Pre-seeded Application
        Application app1 = new Application(
                job1,
                seekerProfile,
                "Dear Hiring Team, I am enthusiastic about applying for the Senior Java & Spring Boot Engineer role at WorkHive. With solid practical experience building scalable Spring Boot backends and clean architectures, I am excited to contribute to your microservices roadmap.",
                demoResumeFileName,
                "Alex_Johnson_Resume_2026.pdf"
        );
        app1.setStatus(ApplicationStatus.UNDER_REVIEW);
        applicationRepository.save(app1);

        System.out.println(">>> WorkHive Demo Data Seeded Successfully!");
        System.out.println(">>> Recruiter Login: recruiter@workhive.com / password123");
        System.out.println(">>> Job Seeker Login: seeker@workhive.com / password123");
    }
}

package com.workhive.service;

import com.workhive.model.*;
import com.workhive.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingService jobPostingService;
    private final JobSeekerService jobSeekerService;
    private final RecruiterService recruiterService;
    private final FileStorageService fileStorageService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobPostingService jobPostingService,
                              JobSeekerService jobSeekerService,
                              RecruiterService recruiterService,
                              FileStorageService fileStorageService) {
        this.applicationRepository = applicationRepository;
        this.jobPostingService = jobPostingService;
        this.jobSeekerService = jobSeekerService;
        this.recruiterService = recruiterService;
        this.fileStorageService = fileStorageService;
    }

    public boolean hasApplied(User user, Long jobId) {
        JobPosting job = jobPostingService.getJobById(jobId);
        JobSeekerProfile seeker = jobSeekerService.getProfileByUser(user);
        return applicationRepository.existsByJobPostingAndJobSeeker(job, seeker);
    }

    @Transactional
    public Application applyToJob(User user, Long jobId, String coverLetter, MultipartFile customResume) {
        JobPosting job = jobPostingService.getJobById(jobId);
        JobSeekerProfile seeker = jobSeekerService.getProfileByUser(user);

        if (!job.isActive()) {
            throw new IllegalStateException("This job posting is currently closed for applications.");
        }

        if (applicationRepository.existsByJobPostingAndJobSeeker(job, seeker)) {
            throw new IllegalStateException("You have already applied for this job.");
        }

        String resumeFileName = seeker.getResumeFileName();
        String resumeOriginalName = seeker.getResumeOriginalName();

        // If applicant uploads a fresh resume specifically for this job, use it
        if (customResume != null && !customResume.isEmpty()) {
            resumeOriginalName = customResume.getOriginalFilename();
            resumeFileName = fileStorageService.storeFile(customResume);
        }

        if (resumeFileName == null || resumeFileName.isBlank()) {
            throw new IllegalArgumentException("Please upload your resume before submitting your application.");
        }

        Application application = new Application(job, seeker, coverLetter, resumeFileName, resumeOriginalName);
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsBySeeker(User user) {
        JobSeekerProfile seeker = jobSeekerService.getProfileByUser(user);
        return applicationRepository.findByJobSeekerOrderByAppliedDateDesc(seeker);
    }

    public List<Application> getApplicationsByRecruiter(User user) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        return applicationRepository.findByJobPostingRecruiterOrderByAppliedDateDesc(recruiter);
    }

    public List<Application> getApplicationsByJob(User user, Long jobId) {
        JobPosting job = jobPostingService.getJobById(jobId);
        if (!job.getRecruiter().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized to view applicants for this job");
        }
        return applicationRepository.findByJobPostingOrderByAppliedDateDesc(job);
    }

    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found with id: " + applicationId));
    }

    @Transactional
    public Application updateApplicationStatus(User recruiterUser, Long applicationId, ApplicationStatus newStatus) {
        Application application = getApplicationById(applicationId);
        if (!application.getJobPosting().getRecruiter().getUser().getId().equals(recruiterUser.getId())) {
            throw new SecurityException("Unauthorized to update application status");
        }
        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    public long countApplicationsByRecruiter(User user) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        return applicationRepository.countByJobPostingRecruiter(recruiter);
    }

    public long countApplicationsBySeeker(User user) {
        JobSeekerProfile seeker = jobSeekerService.getProfileByUser(user);
        return applicationRepository.countByJobSeeker(seeker);
    }
}

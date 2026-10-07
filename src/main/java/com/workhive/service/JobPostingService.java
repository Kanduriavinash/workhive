package com.workhive.service;

import com.workhive.dto.JobPostingDto;
import com.workhive.model.JobPosting;
import com.workhive.model.JobType;
import com.workhive.model.RecruiterProfile;
import com.workhive.model.User;
import com.workhive.repository.JobPostingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final RecruiterService recruiterService;

    public JobPostingService(JobPostingRepository jobPostingRepository, RecruiterService recruiterService) {
        this.jobPostingRepository = jobPostingRepository;
        this.recruiterService = recruiterService;
    }

    public List<JobPosting> getActiveJobs() {
        return jobPostingRepository.findByActiveTrueOrderByPostedDateDesc();
    }

    public List<JobPosting> searchJobs(String keyword, JobType jobType) {
        return jobPostingRepository.searchJobs(keyword, jobType);
    }

    public JobPosting getJobById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job Posting not found with id: " + id));
    }

    public List<JobPosting> getJobsByRecruiter(User user) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        return jobPostingRepository.findByRecruiterOrderByPostedDateDesc(recruiter);
    }

    public long countJobsByRecruiter(User user) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        return jobPostingRepository.countByRecruiter(recruiter);
    }

    @Transactional
    public JobPosting createJob(User user, JobPostingDto dto) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        JobPosting job = new JobPosting(
                recruiter,
                dto.getTitle(),
                dto.getDescription(),
                dto.getRequirements(),
                dto.getLocation(),
                dto.getSalary(),
                dto.getJobType(),
                dto.getDeadline(),
                dto.getSkillsRequired()
        );
        return jobPostingRepository.save(job);
    }

    @Transactional
    public JobPosting updateJob(User user, Long jobId, JobPostingDto dto) {
        JobPosting job = getJobById(jobId);
        if (!job.getRecruiter().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized to modify this job posting");
        }
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setRequirements(dto.getRequirements());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setJobType(dto.getJobType());
        job.setDeadline(dto.getDeadline());
        job.setSkillsRequired(dto.getSkillsRequired());
        job.setActive(dto.isActive());
        return jobPostingRepository.save(job);
    }

    @Transactional
    public void toggleJobStatus(User user, Long jobId) {
        JobPosting job = getJobById(jobId);
        if (!job.getRecruiter().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized to modify this job posting");
        }
        job.setActive(!job.isActive());
        jobPostingRepository.save(job);
    }

    @Transactional
    public void deleteJob(User user, Long jobId) {
        JobPosting job = getJobById(jobId);
        if (!job.getRecruiter().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized to delete this job posting");
        }
        jobPostingRepository.delete(job);
    }
}

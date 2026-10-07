package com.workhive.controller;

import com.workhive.config.CustomUserDetails;
import com.workhive.model.JobPosting;
import com.workhive.model.JobType;
import com.workhive.model.Role;
import com.workhive.service.ApplicationService;
import com.workhive.service.JobPostingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class JobController {

    private final JobPostingService jobPostingService;
    private final ApplicationService applicationService;

    public JobController(JobPostingService jobPostingService, ApplicationService applicationService) {
        this.jobPostingService = jobPostingService;
        this.applicationService = applicationService;
    }

    @GetMapping("/jobs")
    public String browseJobs(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "jobType", required = false) JobType jobType,
                             Model model) {
        List<JobPosting> jobs = jobPostingService.searchJobs(keyword, jobType);
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedJobType", jobType);
        model.addAttribute("jobTypes", JobType.values());
        return "jobs";
    }

    @GetMapping("/jobs/{id}")
    public String jobDetail(@PathVariable("id") Long id,
                            @AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {
        JobPosting job = jobPostingService.getJobById(id);
        model.addAttribute("job", job);

        boolean alreadyApplied = false;
        boolean isJobSeeker = false;

        if (userDetails != null && userDetails.getUser().getRole() == Role.ROLE_JOB_SEEKER) {
            isJobSeeker = true;
            alreadyApplied = applicationService.hasApplied(userDetails.getUser(), id);
        }

        model.addAttribute("isJobSeeker", isJobSeeker);
        model.addAttribute("alreadyApplied", alreadyApplied);
        return "job-detail";
    }
}

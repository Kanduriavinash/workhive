package com.workhive.controller;

import com.workhive.config.CustomUserDetails;
import com.workhive.dto.JobPostingDto;
import com.workhive.dto.RecruiterProfileDto;
import com.workhive.model.*;
import com.workhive.service.ApplicationService;
import com.workhive.service.JobPostingService;
import com.workhive.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    private final RecruiterService recruiterService;
    private final JobPostingService jobPostingService;
    private final ApplicationService applicationService;

    public RecruiterController(RecruiterService recruiterService,
                               JobPostingService jobPostingService,
                               ApplicationService applicationService) {
        this.recruiterService = recruiterService;
        this.jobPostingService = jobPostingService;
        this.applicationService = applicationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        RecruiterProfile recruiter = recruiterService.getProfileByUser(user);
        List<JobPosting> jobs = jobPostingService.getJobsByRecruiter(user);
        List<Application> recentApplications = applicationService.getApplicationsByRecruiter(user);

        long totalJobs = jobs.size();
        long activeJobs = jobs.stream().filter(JobPosting::isActive).count();
        long totalApplicants = recentApplications.size();

        model.addAttribute("recruiter", recruiter);
        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("activeJobs", activeJobs);
        model.addAttribute("totalApplicants", totalApplicants);
        model.addAttribute("jobs", jobs);
        model.addAttribute("recentApplications", recentApplications.size() > 5 ? recentApplications.subList(0, 5) : recentApplications);
        return "recruiter/dashboard";
    }

    @GetMapping("/company")
    public String companyProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        RecruiterProfile recruiter = recruiterService.getProfileByUser(userDetails.getUser());
        RecruiterProfileDto dto = new RecruiterProfileDto();
        dto.setCompanyName(recruiter.getCompanyName());
        dto.setCompanyWebsite(recruiter.getCompanyWebsite());
        dto.setCompanyLocation(recruiter.getCompanyLocation());
        dto.setIndustry(recruiter.getIndustry());
        dto.setAboutCompany(recruiter.getAboutCompany());

        model.addAttribute("recruiter", recruiter);
        model.addAttribute("dto", dto);
        return "recruiter/company";
    }

    @PostMapping("/company")
    public String updateCompanyProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @Valid @ModelAttribute("dto") RecruiterProfileDto dto,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recruiter/company";
        }
        recruiterService.updateProfile(userDetails.getUser(), dto);
        redirectAttributes.addFlashAttribute("successMessage", "Company profile updated successfully!");
        return "redirect:/recruiter/company";
    }

    @GetMapping("/jobs")
    public String myJobs(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<JobPosting> jobs = jobPostingService.getJobsByRecruiter(userDetails.getUser());
        model.addAttribute("jobs", jobs);
        return "recruiter/jobs";
    }

    @GetMapping("/jobs/new")
    public String newJobForm(Model model) {
        model.addAttribute("jobDto", new JobPostingDto());
        model.addAttribute("jobTypes", JobType.values());
        return "recruiter/job-form";
    }

    @PostMapping("/jobs/new")
    public String saveJob(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @Valid @ModelAttribute("jobDto") JobPostingDto jobDto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("jobTypes", JobType.values());
            return "recruiter/job-form";
        }
        jobPostingService.createJob(userDetails.getUser(), jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job posted successfully!");
        return "redirect:/recruiter/jobs";
    }

    @GetMapping("/jobs/edit/{id}")
    public String editJobForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable("id") Long id,
                              Model model) {
        JobPosting job = jobPostingService.getJobById(id);
        JobPostingDto dto = new JobPostingDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequirements(job.getRequirements());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        dto.setJobType(job.getJobType());
        dto.setDeadline(job.getDeadline());
        dto.setSkillsRequired(job.getSkillsRequired());
        dto.setActive(job.isActive());

        model.addAttribute("jobDto", dto);
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("isEdit", true);
        return "recruiter/job-form";
    }

    @PostMapping("/jobs/edit/{id}")
    public String updateJob(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable("id") Long id,
                            @Valid @ModelAttribute("jobDto") JobPostingDto jobDto,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("jobTypes", JobType.values());
            model.addAttribute("isEdit", true);
            return "recruiter/job-form";
        }
        jobPostingService.updateJob(userDetails.getUser(), id, jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
        return "redirect:/recruiter/jobs";
    }

    @PostMapping("/jobs/{id}/toggle")
    public String toggleJobStatus(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes) {
        jobPostingService.toggleJobStatus(userDetails.getUser(), id);
        redirectAttributes.addFlashAttribute("successMessage", "Job status updated!");
        return "redirect:/recruiter/jobs";
    }

    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable("id") Long id,
                            RedirectAttributes redirectAttributes) {
        jobPostingService.deleteJob(userDetails.getUser(), id);
        redirectAttributes.addFlashAttribute("successMessage", "Job deleted successfully!");
        return "redirect:/recruiter/jobs";
    }

    @GetMapping("/jobs/{id}/applicants")
    public String viewApplicantsForJob(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable("id") Long id,
                                       Model model) {
        JobPosting job = jobPostingService.getJobById(id);
        List<Application> applications = applicationService.getApplicationsByJob(userDetails.getUser(), id);
        model.addAttribute("job", job);
        model.addAttribute("applications", applications);
        model.addAttribute("statuses", ApplicationStatus.values());
        return "recruiter/applicants";
    }

    @GetMapping("/applicants")
    public String viewAllApplicants(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<Application> applications = applicationService.getApplicationsByRecruiter(userDetails.getUser());
        model.addAttribute("applications", applications);
        model.addAttribute("statuses", ApplicationStatus.values());
        return "recruiter/applicants";
    }

    @PostMapping("/applications/{id}/status")
    public String updateApplicationStatus(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable("id") Long id,
                                          @RequestParam("status") ApplicationStatus status,
                                          RedirectAttributes redirectAttributes) {
        applicationService.updateApplicationStatus(userDetails.getUser(), id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Applicant status updated to " + status.getLabel() + "!");
        return "redirect:/recruiter/applicants";
    }

    @GetMapping("/applicants/{id}/profile")
    public String viewApplicantProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable("id") Long applicationId,
                                       Model model) {
        Application application = applicationService.getApplicationById(applicationId);
        model.addAttribute("application", application);
        model.addAttribute("seeker", application.getJobSeeker());
        model.addAttribute("educations", application.getJobSeeker().getEducationList());
        model.addAttribute("experiences", application.getJobSeeker().getExperienceList());
        return "recruiter/applicant-detail";
    }
}

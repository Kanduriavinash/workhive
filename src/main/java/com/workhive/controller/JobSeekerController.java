package com.workhive.controller;

import com.workhive.config.CustomUserDetails;
import com.workhive.dto.EducationDto;
import com.workhive.dto.JobSeekerProfileDto;
import com.workhive.dto.WorkExperienceDto;
import com.workhive.model.Application;
import com.workhive.model.JobPosting;
import com.workhive.model.JobSeekerProfile;
import com.workhive.model.User;
import com.workhive.service.ApplicationService;
import com.workhive.service.JobPostingService;
import com.workhive.service.JobSeekerService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;
    private final ApplicationService applicationService;
    private final JobPostingService jobPostingService;

    public JobSeekerController(JobSeekerService jobSeekerService,
                               ApplicationService applicationService,
                               JobPostingService jobPostingService) {
        this.jobSeekerService = jobSeekerService;
        this.applicationService = applicationService;
        this.jobPostingService = jobPostingService;
    }

    @GetMapping("/seeker/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        JobSeekerProfile profile = jobSeekerService.getProfileByUser(user);
        List<Application> recentApplications = applicationService.getApplicationsBySeeker(user);
        List<JobPosting> latestJobs = jobPostingService.getActiveJobs();
        if (latestJobs.size() > 4) {
            latestJobs = latestJobs.subList(0, 4);
        }

        long appliedCount = applicationService.countApplicationsBySeeker(user);

        model.addAttribute("profile", profile);
        model.addAttribute("applications", recentApplications);
        model.addAttribute("appliedCount", appliedCount);
        model.addAttribute("latestJobs", latestJobs);
        return "seeker/dashboard";
    }

    @GetMapping("/seeker/profile")
    public String profileView(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        JobSeekerProfile profile = jobSeekerService.getProfileByUser(user);

        JobSeekerProfileDto profileDto = new JobSeekerProfileDto();
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setDob(profile.getDob());
        profileDto.setGender(profile.getGender());
        profileDto.setCurrentAddress(profile.getCurrentAddress());
        profileDto.setPermanentAddress(profile.getPermanentAddress());
        profileDto.setNationality(profile.getNationality());
        profileDto.setSkills(profile.getSkills());
        profileDto.setKnownLanguages(profile.getKnownLanguages());
        profileDto.setDesiredJobTitle(profile.getDesiredJobTitle());
        profileDto.setPreferredIndustry(profile.getPreferredIndustry());
        profileDto.setEmploymentType(profile.getEmploymentType());

        model.addAttribute("profile", profile);
        model.addAttribute("profileDto", profileDto);
        model.addAttribute("educationDto", new EducationDto());
        model.addAttribute("experienceDto", new WorkExperienceDto());
        model.addAttribute("educations", jobSeekerService.getEducations(profile));
        model.addAttribute("experiences", jobSeekerService.getExperiences(profile));
        return "seeker/profile";
    }

    @PostMapping("/seeker/profile")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @ModelAttribute("profileDto") JobSeekerProfileDto profileDto,
                                RedirectAttributes redirectAttributes) {
        jobSeekerService.updateProfile(userDetails.getUser(), profileDto);
        redirectAttributes.addFlashAttribute("successMessage", "Profile details updated successfully!");
        return "redirect:/seeker/profile";
    }

    @PostMapping("/seeker/resume")
    public String uploadResume(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam("resumeFile") MultipartFile resumeFile,
                               RedirectAttributes redirectAttributes) {
        try {
            jobSeekerService.uploadResume(userDetails.getUser(), resumeFile);
            redirectAttributes.addFlashAttribute("successMessage", "Resume uploaded successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/seeker/profile";
    }

    @PostMapping("/seeker/education")
    public String addEducation(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @Valid @ModelAttribute("educationDto") EducationDto educationDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please provide all required education fields.");
            return "redirect:/seeker/profile";
        }
        jobSeekerService.addEducation(userDetails.getUser(), educationDto);
        redirectAttributes.addFlashAttribute("successMessage", "Education added successfully!");
        return "redirect:/seeker/profile";
    }

    @PostMapping("/seeker/education/{id}/delete")
    public String deleteEducation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            jobSeekerService.deleteEducation(userDetails.getUser(), id);
            redirectAttributes.addFlashAttribute("successMessage", "Education record deleted.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/seeker/profile";
    }

    @PostMapping("/seeker/experience")
    public String addExperience(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @Valid @ModelAttribute("experienceDto") WorkExperienceDto experienceDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please provide all required experience fields.");
            return "redirect:/seeker/profile";
        }
        jobSeekerService.addWorkExperience(userDetails.getUser(), experienceDto);
        redirectAttributes.addFlashAttribute("successMessage", "Work experience added successfully!");
        return "redirect:/seeker/profile";
    }

    @PostMapping("/seeker/experience/{id}/delete")
    public String deleteExperience(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            jobSeekerService.deleteWorkExperience(userDetails.getUser(), id);
            redirectAttributes.addFlashAttribute("successMessage", "Experience record deleted.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/seeker/profile";
    }

    @GetMapping("/seeker/applications")
    public String myApplications(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<Application> applications = applicationService.getApplicationsBySeeker(userDetails.getUser());
        model.addAttribute("applications", applications);
        return "seeker/applications";
    }

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable("jobId") Long jobId,
                              @RequestParam(value = "coverLetter", required = false) String coverLetter,
                              @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile,
                              RedirectAttributes redirectAttributes) {
        try {
            applicationService.applyToJob(userDetails.getUser(), jobId, coverLetter, resumeFile);
            redirectAttributes.addFlashAttribute("successMessage", "Congratulations! Your application was submitted successfully.");
            return "redirect:/seeker/applications";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/jobs/" + jobId;
        }
    }
}

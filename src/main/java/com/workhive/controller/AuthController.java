package com.workhive.controller;

import com.workhive.dto.UserRegistrationDto;
import com.workhive.model.JobPosting;
import com.workhive.model.User;
import com.workhive.service.JobPostingService;
import com.workhive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;
    private final JobPostingService jobPostingService;

    public AuthController(UserService userService, JobPostingService jobPostingService) {
        this.userService = userService;
        this.jobPostingService = jobPostingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<JobPosting> featuredJobs = jobPostingService.getActiveJobs();
        if (featuredJobs.size() > 6) {
            featuredJobs = featuredJobs.subList(0, 6);
        }
        model.addAttribute("featuredJobs", featuredJobs);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        if (registered != null) {
            model.addAttribute("successMessage", "Account created successfully! You can now log in.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserRegistrationDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (!userDto.isPasswordMatching()) {
            bindingResult.rejectValue("confirmPassword", "error.userDto", "Passwords do not match");
        }

        if (userService.emailExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userDto", "This email address is already in use");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login?registered=true";
        } catch (Exception ex) {
            bindingResult.reject("globalError", "Registration failed: " + ex.getMessage());
            return "register";
        }
    }
}

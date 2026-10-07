package com.workhive.service;

import com.workhive.dto.UserRegistrationDto;
import com.workhive.model.JobSeekerProfile;
import com.workhive.model.RecruiterProfile;
import com.workhive.model.Role;
import com.workhive.model.User;
import com.workhive.repository.JobSeekerProfileRepository;
import com.workhive.repository.RecruiterProfileRepository;
import com.workhive.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       JobSeekerProfileRepository jobSeekerProfileRepository,
                       RecruiterProfileRepository recruiterProfileRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered: " + dto.getEmail());
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        // Automatically initialize profile based on role
        if (dto.getRole() == Role.ROLE_JOB_SEEKER) {
            JobSeekerProfile profile = new JobSeekerProfile(savedUser);
            // Split name into first and last if possible
            String[] parts = dto.getFullName().split(" ", 2);
            profile.setFirstName(parts[0]);
            if (parts.length > 1) {
                profile.setLastName(parts[1]);
            }
            jobSeekerProfileRepository.save(profile);
        } else if (dto.getRole() == Role.ROLE_RECRUITER) {
            RecruiterProfile profile = new RecruiterProfile(savedUser, dto.getFullName() + "'s Organization");
            recruiterProfileRepository.save(profile);
        }

        return savedUser;
    }
}

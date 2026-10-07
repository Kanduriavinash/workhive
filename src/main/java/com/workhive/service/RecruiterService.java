package com.workhive.service;

import com.workhive.dto.RecruiterProfileDto;
import com.workhive.model.RecruiterProfile;
import com.workhive.model.User;
import com.workhive.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruiterService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public RecruiterProfile getProfileByUser(User user) {
        return recruiterProfileRepository.findByUser(user)
                .orElseGet(() -> recruiterProfileRepository.save(new RecruiterProfile(user, user.getFullName() + "'s Organization")));
    }

    public RecruiterProfile getProfileById(Long id) {
        return recruiterProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recruiter profile not found with id: " + id));
    }

    @Transactional
    public RecruiterProfile updateProfile(User user, RecruiterProfileDto dto) {
        RecruiterProfile profile = getProfileByUser(user);
        profile.setCompanyName(dto.getCompanyName());
        profile.setCompanyWebsite(dto.getCompanyWebsite());
        profile.setCompanyLocation(dto.getCompanyLocation());
        profile.setIndustry(dto.getIndustry());
        profile.setAboutCompany(dto.getAboutCompany());
        return recruiterProfileRepository.save(profile);
    }
}

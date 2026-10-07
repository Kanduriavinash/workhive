package com.workhive.service;

import com.workhive.dto.EducationDto;
import com.workhive.dto.JobSeekerProfileDto;
import com.workhive.dto.WorkExperienceDto;
import com.workhive.model.Education;
import com.workhive.model.JobSeekerProfile;
import com.workhive.model.User;
import com.workhive.model.WorkExperience;
import com.workhive.repository.EducationRepository;
import com.workhive.repository.JobSeekerProfileRepository;
import com.workhive.repository.WorkExperienceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class JobSeekerService {

    private final JobSeekerProfileRepository profileRepository;
    private final EducationRepository educationRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final FileStorageService fileStorageService;

    public JobSeekerService(JobSeekerProfileRepository profileRepository,
                            EducationRepository educationRepository,
                            WorkExperienceRepository workExperienceRepository,
                            FileStorageService fileStorageService) {
        this.profileRepository = profileRepository;
        this.educationRepository = educationRepository;
        this.workExperienceRepository = workExperienceRepository;
        this.fileStorageService = fileStorageService;
    }

    public JobSeekerProfile getProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseGet(() -> profileRepository.save(new JobSeekerProfile(user)));
    }

    public JobSeekerProfile getProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job Seeker Profile not found with id: " + id));
    }

    @Transactional
    public JobSeekerProfile updateProfile(User user, JobSeekerProfileDto dto) {
        JobSeekerProfile profile = getProfileByUser(user);
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setDob(dto.getDob());
        profile.setGender(dto.getGender());
        profile.setCurrentAddress(dto.getCurrentAddress());
        profile.setPermanentAddress(dto.getPermanentAddress());
        profile.setNationality(dto.getNationality());
        profile.setSkills(dto.getSkills());
        profile.setKnownLanguages(dto.getKnownLanguages());
        profile.setDesiredJobTitle(dto.getDesiredJobTitle());
        profile.setPreferredIndustry(dto.getPreferredIndustry());
        profile.setEmploymentType(dto.getEmploymentType());
        return profileRepository.save(profile);
    }

    @Transactional
    public void uploadResume(User user, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please select a file to upload.");
        }
        String originalName = file.getOriginalFilename();
        String storedName = fileStorageService.storeFile(file);

        JobSeekerProfile profile = getProfileByUser(user);
        profile.setResumeFileName(storedName);
        profile.setResumeOriginalName(originalName);
        profileRepository.save(profile);
    }

    @Transactional
    public void addEducation(User user, EducationDto dto) {
        JobSeekerProfile profile = getProfileByUser(user);
        Education education = new Education(
                profile,
                dto.getQualification(),
                dto.getSpecialization(),
                dto.getInstitution(),
                dto.getYearOfPassing(),
                dto.getGradeOrCgpa()
        );
        educationRepository.save(education);
    }

    @Transactional
    public void deleteEducation(User user, Long educationId) {
        Education edu = educationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalArgumentException("Education entry not found"));
        if (!edu.getJobSeeker().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to delete education record");
        }
        educationRepository.delete(edu);
    }

    @Transactional
    public void addWorkExperience(User user, WorkExperienceDto dto) {
        JobSeekerProfile profile = getProfileByUser(user);
        WorkExperience experience = new WorkExperience(
                profile,
                dto.getCompanyName(),
                dto.getJobTitle(),
                dto.getJobDuration(),
                dto.getYearsOfExperience(),
                dto.getDescription()
        );
        workExperienceRepository.save(experience);
    }

    @Transactional
    public void deleteWorkExperience(User user, Long experienceId) {
        WorkExperience exp = workExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new IllegalArgumentException("Experience entry not found"));
        if (!exp.getJobSeeker().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to delete experience record");
        }
        workExperienceRepository.delete(exp);
    }

    public List<Education> getEducations(JobSeekerProfile profile) {
        return educationRepository.findByJobSeekerOrderByYearOfPassingDesc(profile);
    }

    public List<WorkExperience> getExperiences(JobSeekerProfile profile) {
        return workExperienceRepository.findByJobSeekerOrderByIdDesc(profile);
    }
}

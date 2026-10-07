package com.workhive.repository;

import com.workhive.model.JobSeekerProfile;
import com.workhive.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByJobSeekerOrderByIdDesc(JobSeekerProfile jobSeeker);
}

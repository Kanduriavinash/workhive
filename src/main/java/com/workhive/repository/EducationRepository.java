package com.workhive.repository;

import com.workhive.model.Education;
import com.workhive.model.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByJobSeekerOrderByYearOfPassingDesc(JobSeekerProfile jobSeeker);
}

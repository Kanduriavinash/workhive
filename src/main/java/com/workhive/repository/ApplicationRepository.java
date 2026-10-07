package com.workhive.repository;

import com.workhive.model.Application;
import com.workhive.model.JobPosting;
import com.workhive.model.JobSeekerProfile;
import com.workhive.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByJobSeekerOrderByAppliedDateDesc(JobSeekerProfile jobSeeker);

    List<Application> findByJobPostingOrderByAppliedDateDesc(JobPosting jobPosting);

    List<Application> findByJobPostingRecruiterOrderByAppliedDateDesc(RecruiterProfile recruiter);

    boolean existsByJobPostingAndJobSeeker(JobPosting jobPosting, JobSeekerProfile jobSeeker);

    Optional<Application> findByJobPostingAndJobSeeker(JobPosting jobPosting, JobSeekerProfile jobSeeker);

    long countByJobPostingRecruiter(RecruiterProfile recruiter);

    long countByJobSeeker(JobSeekerProfile jobSeeker);
}

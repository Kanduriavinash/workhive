package com.workhive.repository;

import com.workhive.model.JobPosting;
import com.workhive.model.JobType;
import com.workhive.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByActiveTrueOrderByPostedDateDesc();

    List<JobPosting> findByRecruiterOrderByPostedDateDesc(RecruiterProfile recruiter);

    long countByRecruiter(RecruiterProfile recruiter);

    @Query("SELECT j FROM JobPosting j WHERE j.active = true " +
           "AND (:keyword IS NULL OR :keyword = '' OR " +
           "     LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "     LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "     LOWER(j.skillsRequired) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "     LOWER(j.recruiter.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:jobType IS NULL OR j.jobType = :jobType) " +
           "ORDER BY j.postedDate DESC")
    List<JobPosting> searchJobs(@Param("keyword") String keyword, @Param("jobType") JobType jobType);
}

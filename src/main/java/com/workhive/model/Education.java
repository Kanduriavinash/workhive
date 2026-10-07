package com.workhive.model;

import jakarta.persistence.*;

@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeekerProfile jobSeeker;

    @Column(nullable = false)
    private String qualification;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private Integer yearOfPassing;

    @Column(nullable = false)
    private String gradeOrCgpa;

    public Education() {
    }

    public Education(JobSeekerProfile jobSeeker, String qualification, String specialization, String institution, Integer yearOfPassing, String gradeOrCgpa) {
        this.jobSeeker = jobSeeker;
        this.qualification = qualification;
        this.specialization = specialization;
        this.institution = institution;
        this.yearOfPassing = yearOfPassing;
        this.gradeOrCgpa = gradeOrCgpa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobSeekerProfile getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekerProfile jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public String getGradeOrCgpa() {
        return gradeOrCgpa;
    }

    public void setGradeOrCgpa(String gradeOrCgpa) {
        this.gradeOrCgpa = gradeOrCgpa;
    }
}

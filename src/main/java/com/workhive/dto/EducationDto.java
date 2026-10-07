package com.workhive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EducationDto {

    private Long id;

    @NotBlank(message = "Qualification is required (e.g., B.Tech, B.Sc)")
    private String qualification;

    @NotBlank(message = "Specialization is required (e.g., Computer Science)")
    private String specialization;

    @NotBlank(message = "Institution name is required")
    private String institution;

    @NotNull(message = "Passing year is required")
    private Integer yearOfPassing;

    @NotBlank(message = "Grade or CGPA is required")
    private String gradeOrCgpa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package com.workhive.model;

public enum Role {
    ROLE_JOB_SEEKER("Job Seeker"),
    ROLE_RECRUITER("Recruiter");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

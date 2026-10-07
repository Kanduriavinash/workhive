package com.workhive.model;

public enum JobType {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    REMOTE("Remote"),
    INTERNSHIP("Internship"),
    CONTRACT("Contract");

    private final String label;

    JobType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

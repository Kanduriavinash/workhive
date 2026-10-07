package com.workhive.model;

public enum ApplicationStatus {
    APPLIED("Applied", "primary"),
    UNDER_REVIEW("Under Review", "info"),
    SHORTLISTED("Shortlisted", "warning"),
    ACCEPTED("Accepted", "success"),
    REJECTED("Rejected", "danger");

    private final String label;
    private final String badgeColor;

    ApplicationStatus(String label, String badgeColor) {
        this.label = label;
        this.badgeColor = badgeColor;
    }

    public String getLabel() {
        return label;
    }

    public String getBadgeColor() {
        return badgeColor;
    }
}

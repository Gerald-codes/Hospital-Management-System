package org.groupJ.models.enums;

public enum  TriageLevel {
    PRIORITY_1_CRITICAL("LIFE-THREATENING"),
    PRIORITY_2_MAJOR_EMERGENCIES("MAJOR EMERGENCIES (NON-AMBULATORY)"), // For serious conditions that are not immediately life-threatening but require urgent care
    PRIORITY_3_MINOR_EMERGENCIES("MINOR EMERGENCIES (AMBULATORY)"), // For non-life-threatening conditions that can be managed with less urgency
    PRIORITY_4_NON_EMERGENCY("NON-EMERGENCY (ROUTINE)");

    private final String description;

    // Constructor to assign description to each triage level
    TriageLevel(String description) {
        this.description = description;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }
}


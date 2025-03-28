package org.groupJ.models.enums;

// Enum to track the current status of a patient in the emergency system
public enum PatientStatus {
    DISCHARGED,
    ADMITTED,
    REFERRED,
    ONGOING,
    WAITING,
    DONE,
    DISPENSED,
    ONDISPATCHED; // Added WAITING as initial status
}
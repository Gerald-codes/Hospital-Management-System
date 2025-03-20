package org.lucas.Emergency.enums;

// Enum to track the current status of a patient in the emergency system
public enum PatientStatus {
    DISCHARGED,
    ADMITTED,
    TRANSFERRED,
    WAITING,
    ONDISPATCHED; // Added WAITING as initial status
}
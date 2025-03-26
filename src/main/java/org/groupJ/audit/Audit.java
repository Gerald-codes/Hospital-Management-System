package org.groupJ.audit;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry that records actions performed by healthcare professionals.
 * This class captures details such as the user performing the action, the nature of the action,
 * the target entity affected, the time of the action, the outcome, and the device used.
 */
public class Audit {
    private final String auditId;               // Unique ID for the audit entry
    private final String userId;                // ID of the healthcare professional
    private final String action;                // Description of the action (e.g., "Accessed CDSS", "Viewed Patient Record")
    private final String targetEntity;          // Entity that was accessed/modified (e.g., Patient ID, EHR)
    private final LocalDateTime timestamp;      // Timestamp of when the action occurred
    private final String outcome;               // Result of the action (e.g., "Success", "Failed", "Alert Triggered")
    private final String device;                // Device from which the action was performed
    private String userRole;                    // Role of the user performing the action (e.g., Doctor, Nurse)

    /**
     * Constructs an Audit log entry with the specified details.
     *
     * @param auditId       Unique identifier for the audit log.
     * @param userId        Identifier of the healthcare professional performing the action.
     * @param action        Description of the action performed.
     * @param targetEntity  The entity that was accessed or modified.
     * @param timestamp     The time when the action occurred.
     * @param outcome       The result of the action.
     */
    public Audit(String auditId, String userId, String action, String targetEntity, LocalDateTime timestamp, String outcome) {
        this.auditId = auditId;
        this.userId = userId;
        this.action = action;
        this.targetEntity = targetEntity;
        this.timestamp = timestamp;
        this.outcome = outcome;
        this.device = "PC1";  // Default device name, can be modified if needed
    }

    /**
     * Returns the unique identifier of the audit entry.
     *
     * @return the audit ID as a String
     */
    public String getAuditId() { return auditId; }

    /**
     * Returns the identifier of the user associated with the audit entry.
     *
     * @return the user ID as a String
     */
    public String getUserId() { return userId; }

    /**
     * Returns the action that was performed in the audit entry.
     *
     * @return the action as a String
     */
    public String getAction() { return action; }

    /**
     * Returns the target entity that was affected by the action in the audit entry.
     *
     * @return the target entity as a String
     */
    public String getTargetEntity() { return targetEntity; }

    /**
     * Returns the timestamp indicating when the action was performed.
     *
     * @return the timestamp as a {@link java.time.LocalDateTime}
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Returns the outcome of the action, indicating success or failure.
     *
     * @return the outcome as a String
     */
    public String getOutcome() { return outcome; }

    /**
     * Returns the device from which the action was performed.
     *
     * @return the device as a String
     */
    public String getDevice() { return device; }

    /**
     * Determines the role of the user based on the userId.
     * If the userId contains 'D', the role is Doctor.
     * If the userId contains 'N', the role is Nurse.
     * Otherwise, role is NIL.
     *
     * @return The role of the user.
     */
    public String getUserRole() {
        if (auditId.contains("D")) {
            return "Doctor";
        } else if (auditId.contains("N")) {
            return "Nurse";
        } else {
            return "NIL";
        }
    }

    /**
     * Sets the user role for the audit entry. This method allows updating or setting
     * the role of the user who performed the action recorded in the audit log.
     *
     * @param userRole The role of the user to be set (e.g., Doctor, Nurse, Admin).
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Returns a string representation of the audit log entry for logging purposes.
     *
     * @return A formatted string containing audit details.
     */
    @Override
    public String toString() {
        return "Audit Log - [AuditID: " + auditId + ", UserID: " + userId + ", Action: " + action +
                ", Target: " + targetEntity + ", Timestamp: " + timestamp + ", Outcome: " + outcome + ", Device: " + device + ", Role: " + userRole + "]";
    }
}

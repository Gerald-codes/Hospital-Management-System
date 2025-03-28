package org.groupJ.audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * The AuditManager class handles the creation, storage, and persistence of audit logs.
 * It generates unique audit IDs, logs actions performed by users, and saves these logs to a file.
 */
public class AuditManager {

    //Singleton Instance
    private static AuditManager instance;
    // Remove the static final variable and replace with a method

    // List to hold audit log entries in memory
    private final List<Audit> auditLogs;

    // Formatter to generate unique IDs with timestamp up to milliseconds
    private static final DateTimeFormatter ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    // Formatter for the file name
    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // Atomic counter to ensure unique IDs even within the same millisecond
    private static final AtomicInteger counter = new AtomicInteger(0);

    // Constructor initializes the list of audit logs
    public AuditManager() {
        this.auditLogs = new ArrayList<>();
    }

    /**
     * Gets the current day's log file name.
     * The file name is formatted as "yyyyMMdd.txt" based on the current date.
     *
     * @return The current day's log file name.
     */
    private String getAuditLogFileName() {
        return LocalDateTime.now().format(FILE_DATE_FORMATTER) + ".txt";
    }

    /**
     * Gets the singleton instance of AuditManager
     * @return The AuditManager instance
     */
    public static AuditManager getInstance() {
        if (instance == null) {
            instance = new AuditManager();
        }
        return instance;
    }

    /**
     * Generates a unique audit ID based on the current timestamp and an incrementing counter.
     * The ID format is "yyyyMMddHHmmssSSSNNN", where NNN is a unique number padded with zeros.
     *
     * @return A unique audit ID.
     */
    private String generateAuditId() {
        String timestamp = LocalDateTime.now().format(ID_FORMATTER);  // Get current timestamp in specified format
        int uniqueNumber = counter.incrementAndGet();  // Increment counter to ensure uniqueness
        return timestamp + String.format("%03d", uniqueNumber);  // Combine timestamp with counter, padded to 3 digits
    }

    /**
     * Logs an action performed by a user. This method creates an audit log entry,
     * adds it to the in-memory list, and saves it to a file.
     *
     * @param userId       The ID of the user performing the action.
     * @param action       The action performed by the user.
     * @param targetEntity The entity affected by the action.
     * @param outcome      The outcome of the action.
     * @param userRole     The role of the user performing the action.
     */
    public void logAction(String userId, String action, String targetEntity, String outcome, String userRole) {
        String auditId = generateAuditId();  // Generate a unique ID for the audit
        Audit auditEntry = new Audit(auditId, userId, action, targetEntity, LocalDateTime.now(), outcome);  // Create new audit entry
        auditEntry.setUserRole(userRole);
        auditLogs.add(auditEntry);  // Add the audit entry to the in-memory list
        saveToFile(auditEntry);  // Save the audit entry to the file
    }

    /**
     * Saves a single audit entry to the log file. The file is named based on the current date.
     *
     * @param auditEntry The audit entry to be saved.
     */
    private void saveToFile(Audit auditEntry) {
        String currentFileName = getAuditLogFileName();  // Get the current day's log file name
        try (FileWriter writer = new FileWriter(currentFileName, true)) {  // Open file in append mode
            writer.write(auditEntry.toString() + "\n");  // Write audit entry to the file followed by a newline
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());  // Print error message if writing fails
        }
    }

    /**
     * Retrieves all audit logs currently stored in memory.
     *
     * @return A list of {@link Audit} objects representing the audit logs.
     */
    public List<Audit> getAuditLogs() {
        return auditLogs;
    }
}
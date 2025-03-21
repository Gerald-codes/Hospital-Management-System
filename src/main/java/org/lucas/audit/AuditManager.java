package org.lucas.audit;

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

    // Get the current day's log file name
    private String getAuditLogFileName() {
        return LocalDateTime.now().format(FILE_DATE_FORMATTER) + ".txt";
    }

    // Generate a unique audit ID based on the current timestamp and an incrementing counter
    private String generateAuditId() {
        String timestamp = LocalDateTime.now().format(ID_FORMATTER);  // Get current timestamp in specified format
        int uniqueNumber = counter.incrementAndGet();  // Increment counter to ensure uniqueness
        return timestamp + String.format("%03d", uniqueNumber);  // Combine timestamp with counter, padded to 3 digits
    }

    // Method to log an audit entry with specified details
    public void logAction(String userId, String action, String targetEntity, String outcome, String userRole) {
        String auditId = generateAuditId();  // Generate a unique ID for the audit
        Audit auditEntry = new Audit(auditId, userId, action, targetEntity, LocalDateTime.now(), outcome);  // Create new audit entry
        auditEntry.setUserRole(userRole);
        auditLogs.add(auditEntry);  // Add the audit entry to the in-memory list
        saveToFile(auditEntry);  // Save the audit entry to the file
    }

    // Save a single audit entry to the file
    private void saveToFile(Audit auditEntry) {
        String currentFileName = getAuditLogFileName();  // Get the current day's log file name
        try (FileWriter writer = new FileWriter(currentFileName, true)) {  // Open file in append mode
            writer.write(auditEntry.toString() + "\n");  // Write audit entry to the file followed by a newline
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());  // Print error message if writing fails
        }
    }

    // Retrieve all audit logs stored in memory
    public List<Audit> getAuditLogs() {
        return auditLogs;
    }
}
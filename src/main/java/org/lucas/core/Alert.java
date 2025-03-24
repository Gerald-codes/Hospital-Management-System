package org.lucas.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Alert class represents an alert generated by a Clinical Decision Support System (CDSS).
 * It encapsulates details such as the alert's unique identifier, associated guideline, name, severity level,
 * type, timestamp, and any override reason provided by clinicians.
 * Alerts can be displayed in two formats: a patient-friendly summary using {@link #displayAlertForPatient()}
 * and a detailed view via {@link #displayAlertDetails()}. The severity level typically ranges from 1 to 5,
 * indicating the urgency of the alert.
 */

public class Alert {
    // Private fields for storing alert details
    private String guidelineId;       // Identifier for the guideline associated with the alert
    private String alertName;         // Name or description of the alert
    private String alertID;           // Unique identifier for the alert
    private int severityLevel;        // Severity level of the alert (e.g., 1-5)
    private String alertType;         // Type or category of the alert
    private Date dateTime;            // Date and time when the alert was generated
    private String overrideReason;    // Reason for overriding the alert, if any


    /**
     * Constructs a new {@code Alert} object with the specified details.
     *
     * @param guidelineId    The ID of the guideline associated with the alert.
     * @param alertID        The unique identifier for the alert.
     * @param alertName      The name or description of the alert.
     * @param severityLevel  The severity level of the alert (e.g., 1-5).
     * @param alertType      The type or category of the alert.
     * @param dateTime       The date and time when the alert was generated.
     * @param overrideReason The reason for overriding the alert, if any.
     */
    // Constructor to initialize Alert object with provided values
    public Alert(String guidelineId, String alertID, String alertName, int severityLevel, String alertType, Date dateTime, String overrideReason) {
        this.guidelineId = guidelineId;
        this.alertName = alertName;
        this.alertID = alertID;
        this.severityLevel = severityLevel;
        this.alertType = alertType;
        this.dateTime = dateTime;
        this.overrideReason = overrideReason;
    }

    /**
     * Gets the alert ID.
     *
     * @return the alert ID as a String.
     */
    public String getAlertID() {
        return alertID;
    }

    /**
     * Sets the alert ID.
     *
     * @param alertID the alert ID to set as a String.
     */
    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    /**
     * Sets the date and time of the alert.
     *
     * @param dateTime the date and time to set as a Date object.
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the reason for overriding.
     *
     * @return the override reason as a String
     */
    public String getOverrideReason() {
        return overrideReason;
    }

    /**
     * Sets the reason for overriding.
     *
     * @param overrideReason the reason for override to set
     */
    public void setOverrideReason(String overrideReason) {
        this.overrideReason = overrideReason;
    }

    /**
     * Gets the ID of the guideline.
     *
     * @return the guideline ID as a String
     */
    public String getGuidelineId() {
        return this.guidelineId;
    }

    /**
     * Sets the ID of the guideline.
     *
     * @param guidelineId the guideline ID to set
     */
    public void setGuidelineId(String guidelineId) {
        this.guidelineId = guidelineId;
    }

    /**
     * Gets the name of the alert.
     *
     * @return the alert name as a String
     */
    public String getAlertName() {
        return this.alertName;
    }

    /**
     * Sets the name of the alert.
     *
     * @param alertName the alert name to set
     */
    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    /**
     * Gets the severity level of the alert.
     *
     * @return the severity level as an integer
     */
    public int getSeverityLevel() {
        return this.severityLevel;
    }

    /**
     * Sets the severity level of the alert.
     *
     * @param severityLevel the severity level to set
     */
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * Gets the type of the alert.
     *
     * @return the alert type as a String
     */
    public String getAlertType() {
        return this.alertType;
    }

    /**
     * Sets the type of the alert.
     *
     * @param alertType the alert type to set
     */
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    /**
     * Gets the date and time of the alert.
     *
     * @return the date and time as a Date object
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * Displays a patient-friendly summary of the alert, including the alert name, severity level, type,
     * date and time, and override reason (if applicable).
     */
    // Method to display alert information for a patient
    public void displayAlertForPatient(){
        System.out.println("\n\u26A0\uFE0F ALERTS for this medication:");
        System.out.println("\ud83d\udd14 Alert: " + alertName);
        System.out.println("\u26A0\uFE0F Severity Level: " + severityLevel);
        System.out.println("\ud83d\udce2 Alert Type: " + alertType);
        System.out.println("\ud83d\uddd3 Date of Alert: " + dateTime);
        // Display override reason if it's not empty
        if(!overrideReason.isEmpty()) {
            System.out.println("Reason for overriding: " + overrideReason);
        }
        System.out.println("---------------------------------");
    }
    /**
     * Displays detailed information about the alert, including all attributes such as the alert name,
     * ID, severity level, type, date and time, and override reason.
     */
    // Method to display detailed alert information
    public void displayAlertDetails() {
        System.out.println("Alert details\n-------------");
        System.out.println("Alert Name: " + alertName);
        System.out.println("Alert ID: " + alertID);
        System.out.println("Severity Level: " + severityLevel);
        System.out.println("Alert Type: " + alertType);
        System.out.println("Date and Time of Alert: " + dateTime);
        System.out.println("Override Reason: " + overrideReason);
    }
    public static List<Alert> generateAlert(){
        List<Alert> alert = new ArrayList<>();
        alert.add(new Alert(
                "C003","A001","Please take Patient BP",3,"Administer",new Date(),"")
        );
        return alert;
    }
}

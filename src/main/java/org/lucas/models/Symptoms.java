package org.lucas.models;


/**
 * The Symptoms class represents a possible symptom a patient might face.
 * It stores name of alert, unique identifier of symptom, severity level an d
 * It also manages alerts, medications, and other patient-specific data.
 */
public class Symptoms {

    // Private fields for storing symptom details
    private String symptomName;         // Name of the symptom
    private String symptomId;           // Unique ID for the symptom
    private int severity;               // Severity level of the symptom (e.g., 1-10 scale)
    private int duration;               // Duration of the symptom in days
    private String doctorNotes;      // Notes from the clinician regarding the symptom
    private static int count = 0;

    /**
     * Default constructor initializing fields with default values.
     */
    public Symptoms() {
        this.symptomName = "";
        this.symptomId = "";
        this.severity = 0;
        this.duration = 0;
        this.doctorNotes = "";
    }

    /**
     * Parameterized constructor to initialize a Symptoms object with provided values.
     *
     * @param symptomName Name of the symptom.
     * @param severity Severity level of the symptom (1-10 scale).
     * @param duration Duration of the symptom in days.
     * @param doctorNotes Clinician notes regarding the symptom.
     */
    public Symptoms(String symptomName, int severity, int duration, String doctorNotes) {
        this.symptomName = symptomName.toLowerCase();  // Store symptom name in lowercase
        this.symptomId = String.format("SYM-%03d", ++count);
        this.severity = severity;
        this.duration = duration;
        this.doctorNotes = doctorNotes;
    }
    /**
     * Gets the name of the symptom.
     *
     * @return the name of the symptom in lowercase.
     */
    public String getSymptomName() {
        return symptomName;
    }

    /**
     * Sets the name of the symptom.
     * Converts the provided name to lowercase for consistency.
     *
     * @param symptomName the name of the symptom.
     */
    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName.toLowerCase();  // Convert to lowercase for consistency
    }

    /**
     * Gets the unique identifier of the symptom.
     *
     * @return the symptom ID.
     */
    public String getSymptomId() {
        return symptomId;
    }

    /**
     * Sets the unique identifier of the symptom.
     *
     * @param symptomId the unique identifier for the symptom.
     */
    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    /**
     * Gets the severity level of the symptom.
     *
     * @return the severity level as an integer.
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Sets the severity level of the symptom.
     *
     * @param severity the severity level as an integer.
     */
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    /**
     * Gets the duration of the symptom in days.
     *
     * @return the duration in days.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the symptom in days.
     *
     * @param duration the duration in days.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the clinician's notes regarding the symptom.
     *
     * @return the clinician's notes.
     */
    public String getDoctorNotes() {
        return doctorNotes;
    }

    /**
     * Sets the clinician's notes regarding the symptom.
     *
     * @param doctorNotes the clinician's notes.
     */
    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    /**
     * Provides a formatted string representation of the Symptoms object.
     *
     * @return A formatted string displaying symptom details.
     */    @Override
    public String toString() {
        return String.format(
                "  - Symptom Name       : %s\n" +
                        "  - Symptom ID         : %s\n" +
                        "  - Severity Level     : %d\n" +
                        "  - Duration (days)    : %d\n" +
                        "  - Clinician Notes    : %s",
                symptomName, symptomId, severity, duration, doctorNotes
        );
    }
}

package org.lucas.models;


import org.lucas.core.Alert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Patient class represents a patient in the healthcare system.
 * It stores personal information, medical details, and the patient's Electronic Health Record (EHR).
 * It also manages alerts, medications, and other patient-specific data.
 */
public class Patient extends User{
    // Private fields for storing patient information
    private List<Alert> alertHistory;              // List of alerts related to the patient
    private String patientID;                     // Unique identifier for the patient
    //private String patientName;                   // Full name of the patient
    private String dateOfBirth;                // Date of birth of the patient
    //private String gender;                          // Gender of the patient
    private ElectronicHealthRecord ElectronicHealthRecord; // Electronic Health Record of the patient
    private String patientSpecificFactor;         // Specific factors like allergies or conditions
    private String assignedNurse;                 // Name of the assigned nurse
    private String assignedDoctor;                // Name of the assigned doctor
    private double height;                        // Height of the patient in cm
    private double weight;                        // Weight of the patient in kg
    private String bloodType;                     // Blood type of the patient
    private String houseAddress;                  // Home address of the patient
    private int emergencyContactNumber;           // Emergency contact number
    private String occupation;                    // Patient's occupation
    private String ethnicity;                     // Ethnicity of the patient
    //private String email;                         // Email address of the patient
    private String healthcareDepartment;          // Healthcare department handling the patient
    private PatientConsent patientConsent;

    /**
     * Constructor to initialize a Patient object with all attributes, including alert history.
     *
     * @param patientID              The unique identifier for the patient.
     * @param userName               The full name of the patient.
     * @param dateOfBirth            The date of birth of the patient.
     * @param gender                 The gender of the patient.
     * @param patientSpecificFactor  Specific factors like allergies or conditions.
     * @param assignedNurse          The name of the assigned nurse.
     * @param assignedDoctor         The name of the assigned doctor.
     * @param alertHistory           The list of alerts related to the patient.
     * @param height                 The height of the patient in cm.
     * @param weight                 The weight of the patient in kg.
     * @param bloodType              The blood type of the patient.
     * @param houseAddress           The home address of the patient.
     * @param emergencyContactNumber The emergency contact number.
     * @param occupation             The patient's occupation.
     * @param ethnicity              The ethnicity of the patient.
     * @param email                  The email address of the patient.
     * @param healthcareDepartment   The healthcare department handling the patient.
     */
    public Patient(String userID, String userName, String name, String password, String email, String gender, String phoneNumber, String patientID, String dateOfBirth, String patientSpecificFactor,
                   String assignedNurse, String assignedDoctor, List<Alert> alertHistory, double height,
                   double weight, String bloodType, String houseAddress, int emergencyContactNumber,
                   String occupation, String ethnicity, String healthcareDepartment) {
        super(userID, userName, name, password, email, gender, phoneNumber);
        this.patientID = patientID;
        this.dateOfBirth = dateOfBirth;
        this.ElectronicHealthRecord = new ElectronicHealthRecord();
        this.patientSpecificFactor = patientSpecificFactor;
        this.assignedDoctor = assignedDoctor;
        this.assignedNurse = assignedNurse;
        this.alertHistory = alertHistory != null ? alertHistory : new ArrayList<>();
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.houseAddress = houseAddress;
        this.emergencyContactNumber = emergencyContactNumber;
        this.occupation = occupation;
        this.ethnicity = ethnicity;
        this.healthcareDepartment = healthcareDepartment;
        this.patientConsent = new PatientConsent(false,"");
    }
    public void setAlertHistory(List<Alert> alertHistory) {
        this.alertHistory = alertHistory;
    }

    public ElectronicHealthRecord getElectronicHealthRecord() {
        return ElectronicHealthRecord;
    }

    public void setElectronicHealthRecord(ElectronicHealthRecord electronicHealthRecord) {
        ElectronicHealthRecord = electronicHealthRecord;
    }

    /**
     *  Constructor to initialize a Patient object without alert history.
     *
     * @param patientID              The unique identifier for the patient.
     * @param patientName            The full name of the patient.
     * @param dateOfBirth            The date of birth of the patient.
     * @param gender                 The gender of the patient.
     * @param patientSpecificFactor  Specific factors like allergies or conditions.
     * @param assignedNurse          The name of the assigned nurse.
     * @param assignedDoctor         The name of the assigned doctor.
     * @param height                 The height of the patient in cm.
     * @param weight                 The weight of the patient in kg.
     * @param bloodType              The blood type of the patient.
     * @param houseAddress           The home address of the patient.
     * @param emergencyContactNumber The emergency contact number.
     * @param occupation             The patient's occupation.
     * @param ethnicity              The ethnicity of the patient.
     * @param email                  The email address of the patient.
     * @param healthcareDepartment   The healthcare department handling the patient.
     */
//    public Patient(String patientID, String patientName, LocalDate dateOfBirth, char gender, String patientSpecificFactor,
//                   String assignedNurse, String assignedDoctor, double height,
//                   double weight, String bloodType, String houseAddress, int emergencyContactNumber,
//                   String occupation, String ethnicity, String email, String healthcareDepartment) {
//        this(patientID, patientName, dateOfBirth, gender, patientSpecificFactor, assignedNurse, assignedDoctor,
//                null, height, weight, bloodType, houseAddress, emergencyContactNumber,
//                occupation, ethnicity, email, healthcareDepartment);
//    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public int getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(int emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getHealthcareDepartment() {
        return healthcareDepartment;
    }

    public void setHealthcareDepartment(String healthcareDepartment) {
        this.healthcareDepartment = healthcareDepartment;
    }

    // Getters and Setters for all attributes
    /**
     * Retrieves the history of alerts associated with the patient.
     *
     * @return a list of Alert objects representing the alert history.
     */
    public List<Alert> getAlertHistory() { return alertHistory; }

    /**
     * Adds a new alert to the patient's alert history.
     *
     * @param alert the Alert object to be added to the history.
     */
    public void setAlertHistory(Alert alert) { this.alertHistory.add(alert); }

    /**
     * Retrieves the electronic health record (EHR) of the patient.
     *
     * @return the ElectronicHealthRecord object associated with the patient.
     */
    public ElectronicHealthRecord getEHR() { return this.ElectronicHealthRecord; }

    /**
     * Sets the electronic health record (EHR) for the patient.
     *
     * @param ElectronicHealthRecord the ElectronicHealthRecord object to be set.
     */
    public void setEHR(ElectronicHealthRecord ElectronicHealthRecord) { this.ElectronicHealthRecord = ElectronicHealthRecord; }

    /**
     * Retrieves the unique identifier of the patient.
     *
     * @return the patient ID as a String.
     */
    public String getPatientID() { return this.patientID; }

    /**
     * Sets the unique identifier for the patient.
     *
     * @param patientID the patient ID to be set.
     */
    public void setPatientID(String patientID) { this.patientID = patientID; }

    /**
     * Retrieves the date of birth of the patient.
     *
     * @return the patient's date of birth as a LocalDate.
     */
    public String getDateOfBirth() { return this.dateOfBirth; }

    /**
     * Sets the date of birth for the patient.
     *
     * @param dateOfBirth the LocalDate representing the patient's date of birth.
     */
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    /**
     * Retrieves the name of the doctor assigned to the patient.
     *
     * @return the assigned doctor's name as a String.
     */
    public String getAssignedDoctor() { return this.assignedDoctor; }

    /**
     * Sets the name of the doctor assigned to the patient.
     *
     * @param assignedDoctor the name of the assigned doctor to be set.
     */
    public void setAssignedDoctor(String assignedDoctor) { this.assignedDoctor = assignedDoctor; }

    /**
     * Retrieves the name of the nurse assigned to the patient.
     *
     * @return the assigned nurse's name as a String.
     */
    public String getAssignedNurse() { return this.assignedNurse; }

    /**
     * Sets the name of the nurse assigned to the patient.
     *
     * @param assignedNurse the name of the assigned nurse to be set.
     */
    public void setAssignedNurse(String assignedNurse) { this.assignedNurse = assignedNurse; }

    /**
     * Retrieves patient-specific factors that might influence care.
     *
     * @return the patient-specific factors as a String.
     */
    public String getPatientSpecificFactor() { return this.patientSpecificFactor; }

    /**
     * Sets patient-specific factors that might influence care.
     *
     * @param patientSpecificFactor the patient-specific factors to be set.
     */
    public void setPatientSpecificFactor(String patientSpecificFactor) { this.patientSpecificFactor = patientSpecificFactor; }


    /**
     * Displays all alerts for the patient.
     */
    public void displayPatientAlerts() {
        for (Alert alert : alertHistory) {
            alert.displayAlertDetails();
        }
    }

    /**
     * Displays information about the patient
     */
    public void displayPatientInfo(Patient patient) {

        System.out.println("=======================================");
        System.out.println("          PATIENT INFORMATION          ");
        System.out.println("=======================================");
        System.out.printf("%-20s: %s%n", "Patient ID", this.patientID);
        System.out.printf("%-20s: %s%n", "Name", patient.getName());
        System.out.printf("%-20s: %s%n", "Gender", patient.getGender());
        System.out.printf("%-20s: %s%n", "Date of Birth", this.dateOfBirth);
        System.out.printf("%-20s: %s%n", "Height", this.height + " cm");
        System.out.printf("%-20s: %s%n", "Weight", this.weight + " kg");
        System.out.printf("%-20s: %s%n", "Blood Type", this.bloodType);
        System.out.printf("%-20s: %s%n", "House Address", this.houseAddress);
        System.out.printf("%-20s: %s%n", "Emergency Contact", this.emergencyContactNumber);
        System.out.printf("%-20s: %s%n", "Occupation", this.occupation);
        System.out.printf("%-20s: %s%n", "Ethnicity", this.ethnicity);
        System.out.printf("%-20s: %s%n", "Email", patient.getEmail());
        System.out.printf("%-20s: %s%n", "Healthcare Dept.", this.healthcareDepartment);
        System.out.printf("%-20s: %s%n", "Consent", this.patientConsent.isConsentGiven());

        System.out.println("\n---------------------------------------");
        System.out.println("          ELECTRONIC HEALTH RECORD     ");
        System.out.println("---------------------------------------");
        System.out.printf("%-20s: %s%n", "Allergies", formatList(this.ElectronicHealthRecord.getAllergies()));
        System.out.printf("%-20s: %s%n", "Medical History", formatList(this.ElectronicHealthRecord.getMedicalHistory()));

        System.out.println("\nCurrent Medications:");
        for (Medication med : this.ElectronicHealthRecord.getCurrentMedications()) {
            System.out.println("  - " + med.getMedicationName());
        }

        System.out.printf("%-20s: %s%n", "\nVital Signs", this.ElectronicHealthRecord.getVitalSigns());

        System.out.println("\nPast Surgeries:");
        for (String surgery : this.ElectronicHealthRecord.getPastSurgeries()) {
            System.out.println("  - " + surgery);
        }

        System.out.println("\nVaccination Record:");
        for (String vaccine : this.ElectronicHealthRecord.getVaccinationRecord()) {
            System.out.println("  - " + vaccine);
        }

        System.out.println("\nLab Results:");
        for (String result : this.ElectronicHealthRecord.getLabResults()) {
            System.out.println("  - " + result);
        }

        System.out.println("\nImaging Records:");
        for (String image : this.ElectronicHealthRecord.getImagingRecords()) {
            System.out.println("  - " + image);
        }

        if (this.ElectronicHealthRecord.getSymptoms().isEmpty()) {
            System.out.printf("%-20s: %s%n", "\nSymptoms", "Nil");
        } else {
            System.out.printf("%-20s:%n", "\nSymptoms"); // Print the label first
            for (Symptoms symptom : this.ElectronicHealthRecord.getSymptoms()) {
                System.out.printf("  - %-18s: %s%n", "Symptom Name", symptom.getSymptomName());
                System.out.printf("    %-18s: %s%n", "Symptom ID", symptom.getSymptomId());
                System.out.printf("    %-18s: %d%n", "Severity Level", symptom.getSeverity());
                System.out.printf("    %-18s: %d%n", "Duration (days)", symptom.getDuration());
                System.out.printf("    %-18s: %s%n", "Doctor Notes", symptom.getDoctorNotes());
                System.out.println(); // Add space between symptoms
            }
        }

        System.out.printf("%-20s: %s%n", "Diagnosis", this.ElectronicHealthRecord.getDiagnosis());
        System.out.printf("%-20s: %s%n", "Clinical Notes", this.ElectronicHealthRecord.getClinicalNotes());

        if (!this.getEHR().getOutcomeMonitoringRecords().isEmpty()) {
            System.out.println("\nOutcome Monitoring Records:");
            for (String record : this.getEHR().getOutcomeMonitoringRecords()) {
                System.out.println("  - " + record);
            }
        }

        System.out.println("=======================================");
    }

    /**
     * Returns the list of current medications for the patient.
     *
     * @return A list of Medication objects for patient's current medications.
     */
    public List<Medication> getMedications() {
        return this.ElectronicHealthRecord.getCurrentMedications();
    }

    /**
     * Helper method to format a list for display.
     *
     * @param list The list to be formatted.
     * @return A string representation of the list, or "None" if the list is empty or null.
     */
    private static String formatList(List<String> list) {
        return list != null && !list.isEmpty() ? String.join(", ", list) : "None";
    }

    public PatientConsent getPatientConsent() {
        return this.patientConsent;
    }

    public void setPatientConsent(PatientConsent patientConsent) {
        PatientConsent patientconsent = new PatientConsent(true,"");
    }
}
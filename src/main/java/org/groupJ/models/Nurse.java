package org.groupJ.models;

import org.groupJ.core.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nurse extends User{
    private String department;
    private String role;
    private String assignedWard;
    private boolean canAdministerMedication;
    private String nursingLicenseNumber;
    private final List<Alert> alertList;
    /**
     * Constructs a {@code Nurse} object with specified attributes.
     *
     * @param id                     The nurse's unique ID.
     * @param loginName              The nurse's login name.
     * @param name                   The nurse's full name.
     * @param password               The login password for the nurse.
     * @param email                  The nurse's email address.
     * @param gender                 The nurse's gender.
     * @param phoneNumber            The nurse's phone number.
     * @param department             The department the nurse belongs to.
     * @param role                   The specific role of the nurse (e.g., Head Nurse, Staff Nurse).
     * @param assignedWard           The ward assigned to the nurse.
     * @param canAdministerMedication Indicates whether the nurse is authorized to administer medication.
     * @param nursingLicenseNumber   The nurse's license number.
     */
    public Nurse(String id, String loginName, String name, String password, String email, String gender, String phoneNumber,String department, String role, String assignedWard, boolean canAdministerMedication, String nursingLicenseNumber) {
        super(id, loginName, name, password, email, gender, phoneNumber);
        this.department = department;
        this.role = role;
        this.assignedWard = assignedWard;
        this.canAdministerMedication = canAdministerMedication;
        this.nursingLicenseNumber = nursingLicenseNumber;
        this.alertList = new ArrayList<>();
    }

    /// GETTER & SETTER METHODS
    /**
     * Retrieves the nurse's role.
     *
     * @return The role of the nurse.
     */
    public String getRole() { return role; }

    /**
     * Retrieves the nurse's department.
     *
     * @return The department of the nurse.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the nurse's role.
     *
     * @param role The role to assign to the nurse.
     */
    public void setRole(String role) { this.role = role; }

    /**
     * Retrieves the ward assigned to the nurse.
     *
     * @return The assigned ward.
     */
    public String getAssignedWard() { return assignedWard; }

    /**
     * Sets the ward assigned to the nurse.
     *
     * @param assignedWard The ward to assign to the nurse.
     */
    public void setAssignedWard(String assignedWard) { this.assignedWard = assignedWard; }

    /**
     * Checks if the nurse is authorized to administer medication.
     *
     * @return {@code true} if the nurse can administer medication; {@code false} otherwise.
     */
    public boolean canAdministerMedication() { return canAdministerMedication; }

    /**
     * Sets the nurse's permission to administer medication.
     *
     * @param canAdministerMedication {@code true} to grant permission; {@code false} to revoke.
     */
    public void setCanAdministerMedication(boolean canAdministerMedication) {
        this.canAdministerMedication = canAdministerMedication;
    }

    /**
     * Retrieves the nurse's license number.
     *
     * @return The nursing license number.
     */
    public String getNursingLicenseNumber() { return nursingLicenseNumber; }

    /**
     * Sets the nurse's license number.
     *
     * @param nursingLicenseNumber The license number to assign.
     */
    public void setNursingLicenseNumber(String nursingLicenseNumber) {
        this.nursingLicenseNumber = nursingLicenseNumber;
    }

    /**
     * PATIENT MANAGEMENT METHODS
     */

    /**
     * Assigns a list of patients to the nurse by filtering patients based on the nurse's ID.
     *
     */
//    @Override
//    public void setPatientList(List<Patient> patients) {
//        super.setPatientList(patients, patient -> this.getId().equals(patient.getAssignedNurse()));
//    }
    public List<Alert> getAlertList() { return new ArrayList<>(this.alertList); }

    public void setAlertList(List<Alert> alerts) {
        this.alertList.clear();
        this.alertList.addAll(alerts);
    }

    public void addAlert(Alert alert) {
        this.alertList.add(alert);
    }
    /**
     * Displays the medication alert history for a given patient.
     *
     * @param patient The patient whose alert history is being displayed.
     */
    //@Override
    public static void showMedicationAlertHistory(Patient patient) {
        System.out.println("Displaying medication alert history...");
        if (patient.getAlertHistory() != null) {
            patient.displayPatientAlerts();
        }else{
            System.out.println("No medication alerts found for this patient.");
        }
    }

    /**
     * Updates the patient's blood pressure in their Electronic Health Record (EHR).
     *
     * @param patient     The patient whose blood pressure is being updated.
     * @param systolicBP  The systolic blood pressure reading.
     * @param diastolicBP The diastolic blood pressure reading.
     */
    public void updatePatientBloodPressure(Patient patient, int systolicBP, int diastolicBP) {
        ElectronicHealthRecord patientElectronicHealthRecord = patient.getEHR();
        patientElectronicHealthRecord.updateBloodPressure(systolicBP, diastolicBP);
        System.out.println("Blood pressure updated successfully.");
    }

    /**
     * Throws an exception as nurses are not authorized to update patient symptoms.
     *
     * @param symptoms The symptoms to set.
     * @param patient  The patient whose symptoms are being updated.
     * @throws UnsupportedOperationException if the method is called.
     */
    //@Override
    public void setPatientSymptoms(Symptoms symptoms, Patient patient) {
        throw new UnsupportedOperationException("Nurses cannot update patient symptoms.");
    }

    /**
     * Updates the patient's alert history with a new alert.
     *
     * @param patient The patient whose alert history is being updated.
     * @param alert   The alert to add to the patient's history.
     */
    public void setPatientAlertHistory(Patient patient, Alert alert) {
        patient.setAlertHistory(new Alert(alert.getGuidelineId(), alert.getAlertID(), alert.getAlertName(),
                alert.getSeverityLevel(), alert.getAlertType(), new Date(), alert.getOverrideReason()));
    }

    /**
     * Throws an exception as nurses are not authorized to update patient symptoms.
     *
     * @param patient The patient whose symptoms are being updated.
     * @throws UnsupportedOperationException if the method is called.
     */
    public void updatePatientSymptoms(Patient patient) {
        throw new UnsupportedOperationException("Nurses cannot update patient symptoms.");
    }

    /**
     * Updates the outcome monitoring data for a patient's medication in their EHR.
     *
     * @param patient        The patient whose outcome is being monitored.
     * @param medicationName The name of the medication being monitored.
     * @param outcome        The outcome to record.
     */
    public void updateOutcomeMonitoring(Patient patient, String medicationName, String outcome) {
        ElectronicHealthRecord patientElectronicHealthRecord = patient.getEHR();
        patientElectronicHealthRecord.addOutcomeMonitoringHistory(medicationName, outcome);
        System.out.println("Outcome monitoring updated successfully for " + medicationName);
    }

    /**
     * DISPLAY METHODS
     */

    /**
     * Displays detailed information about the nurse, including role, assigned ward,
     * and permissions for administering medication.
     */
//    public void displayNurseInfo() {
//        System.out.println("Nurse ID: " + User.getId());
//        System.out.println("Name: " + getName());
//        System.out.println("Age: " + getAge());
//        System.out.println("Department: " + getDepartment());
//        System.out.println("Role: " + role);
//        System.out.println("Assigned Ward: " + assignedWard);
//        System.out.println("Can Administer Medication: " + (canAdministerMedication ? "Yes" : "No"));
//        System.out.println("Nursing License Number: " + nursingLicenseNumber);
//    }

    @Override
    public String toString() {
        return super.toString() + "\nNurse {" +
                "\n  Department               : '" + department + '\'' +
                ",\n  Role                    : '" + role + '\'' +
                ",\n  Assigned Ward           : '" + assignedWard + '\'' +
                ",\n  Can Administer Medication: " + (canAdministerMedication ? "Yes" : "No") +
                ",\n  Nursing License Number  : '" + nursingLicenseNumber + '\'' +
                ",\n  Alerts                  : " + alertList.size() +
                "\n}";
    }

}


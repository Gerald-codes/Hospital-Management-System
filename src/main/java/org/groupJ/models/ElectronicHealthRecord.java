<<<<<<< HEAD:src/main/java/org/groupJ/models/ElectronicHealthRecord.java
package org.groupJ.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The ElectronicHealthRecord class represents a patient's electronic health record (EHR).
 * It stores and manages various aspects of a patient's health information, including
 * allergies, medications, medical history, vital signs, lab results, imaging records,
 * clinical notes, symptoms, diagnosis, outcome monitoring records, past surgeries,
 * and vaccination records.
 */
public class ElectronicHealthRecord {
    // Private fields for storing patient health information
    private List<String> allergies;                    // List of patient allergies
    private List<Medication> currentMedications;      // List of current medications the patient is taking
    private List<String> medicalHistory;              // List of past medical conditions
    private VitalSigns vitalSigns;                    // Current vital signs of the patient
    private List<String> labResults;                  // List of lab test results
    private List<String> imagingRecords;              // List of imaging test results (e.g., X-rays, MRIs)
    private String clinicalNotes;                     // Notes from clinicians regarding the patient
    private List<Symptoms> symptoms;                  // Current symptoms reported by the patient
    private String diagnosis;                         // Current diagnosis for the patient
    private List<String> outcomeMonitoringRecords;    // Records of outcomes from treatments or medications
    private List<String> pastSurgeries;               // List of surgeries the patient has undergone
    private List<String> vaccinationRecord;           // Record of vaccinations the patient has received

    /**
     * Default constructor initializing all fields to default values.
     * All lists are initialized as empty ArrayLists, and strings are set to "Nil".
     */
    public ElectronicHealthRecord() {
        this.allergies = new ArrayList<>();
        this.medicalHistory = new ArrayList<>();
        this.currentMedications = new ArrayList<>();
        this.vitalSigns = new VitalSigns();
        this.labResults = new ArrayList<>();
        this.imagingRecords = new ArrayList<>();
        this.clinicalNotes = "Nil";
        this.symptoms = new ArrayList<>();
        this.diagnosis = "Nil";
        this.outcomeMonitoringRecords = new ArrayList<>();
        this.pastSurgeries = new ArrayList<>();
        this.vaccinationRecord = new ArrayList<>();
    }


    /**
     * Retrieves the list of allergies.
     *
     * @return a list of allergy names
     */
    public List<String> getAllergies() { return allergies; }

    /**
     * Sets the list of allergies.
     *
     * @param allergies a list of allergy names
     */
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }

    /**
     * Retrieves the list of current medications.
     *
     * @return a list of Medication objects representing current medications
     */
    public List<Medication> getCurrentMedications() { return currentMedications; }

    /**
     * Sets the list of current medications.
     *
     * @param currentMedications a list of Medication objects
     */
    public void setCurrentMedications(List<Medication> currentMedications) { this.currentMedications = currentMedications; }

    /**
     * Retrieves the medical history.
     *
     * @return a list of medical history records
     */
    public List<String> getMedicalHistory() { return medicalHistory; }

    /**
     * Sets the medical history.
     *
     * @param medicalHistory a list of medical history records
     */
    public void setMedicalHistory(List<String> medicalHistory) { this.medicalHistory = medicalHistory; }

    /**
     * Retrieves the vital signs.
     *
     * @return a VitalSigns object representing the patient's vital signs
     */
    public VitalSigns getVitalSigns() { return vitalSigns; }

    /**
     * Sets the vital signs.
     *
     * @param vitalSigns a VitalSigns object representing the patient's vital signs
     */
    public void setVitalSigns(VitalSigns vitalSigns) { this.vitalSigns = vitalSigns; }

    /**
     * Retrieves the lab results.
     *
     * @return a list of lab results
     */
    public List<String> getLabResults() { return labResults; }

    /**
     * Sets the lab results.
     *
     * @param labResults a list of lab results
     */
    public void setLabResults(List<String> labResults) { this.labResults = labResults; }

    /**
     * Retrieves the imaging records.
     *
     * @return a list of imaging records
     */
    public List<String> getImagingRecords() { return imagingRecords; }

    /**
     * Sets the imaging records.
     *
     * @param imagingRecords a list of imaging records
     */
    public void setImagingRecords(List<String> imagingRecords) { this.imagingRecords = imagingRecords; }

    /**
     * Retrieves the clinical notes.
     *
     * @return a string containing clinical notes
     */
    public String getClinicalNotes() { return clinicalNotes; }

    /**
     * Sets the clinical notes.
     *
     * @param clinicalNotes a string containing clinical notes
     */
    public void setClinicalNotes(String clinicalNotes) { this.clinicalNotes = clinicalNotes; }

    /**
     * Retrieves the list of symptoms.
     *
     * @return a list of Symptoms objects
     */
    public List<Symptoms> getSymptoms() { return symptoms; }

    /**
     * Sets the list of symptoms.
     *
     * @param symptoms a list of Symptoms objects
     */
    public void setSymptoms(List<Symptoms> symptoms) { this.symptoms = symptoms; }

    /**
     * Retrieves the diagnosis.
     *
     * @return a string representing the diagnosis
     */
    public String getDiagnosis() { return diagnosis; }

    /**
     * Sets the diagnosis.
     *
     * @param diagnosis a string representing the diagnosis
     */
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    /**
     * Retrieves the outcome monitoring records.
     *
     * @return a list of outcome monitoring records
     */
    public List<String> getOutcomeMonitoringRecords() { return outcomeMonitoringRecords; }

    /**
     * Sets the outcome monitoring records.
     *
     * @param outcomeMonitoringRecords a list of outcome monitoring records
     */
    public void setOutcomeMonitoringRecords(List<String> outcomeMonitoringRecords) { this.outcomeMonitoringRecords = outcomeMonitoringRecords; }

    /**
     * Retrieves the list of past surgeries.
     *
     * @return a list of past surgeries
     */
    public List<String> getPastSurgeries() { return pastSurgeries; }

    /**
     * Sets the list of past surgeries.
     *
     * @param pastSurgeries a list of past surgeries
     */
    public void setPastSurgeries(List<String> pastSurgeries) { this.pastSurgeries = pastSurgeries; }

    /**
     * Retrieves the vaccination record.
     *
     * @return a list of vaccination records
     */
    public List<String> getVaccinationRecord() { return vaccinationRecord; }

    /**
     * Sets the vaccination record.
     *
     * @param vaccinationRecord a list of vaccination records
     */
    public void setVaccinationRecord(List<String> vaccinationRecord) { this.vaccinationRecord = vaccinationRecord; }

    /**
     * Adds a medication to the list of current medications.
     *
     * @param medication The Medication object to be added.
     */
    public void addCurrentMedications(Medication medication) {
        this.currentMedications.add(medication);
    }

    /**
     * Updates the patient's blood pressure readings in the vital signs.
     *
     * @param systolic  The systolic blood pressure value.
     * @param diastolic The diastolic blood pressure value.
     */
    public void updateBloodPressure(int systolic, int diastolic) {
        VitalSigns vitalSigns = this.getVitalSigns();
        vitalSigns.setBloodPressureSystolic(systolic);
        vitalSigns.setBloodPressureDiastolic(diastolic);
    }

    public void updateVitalSigns(double temperature, int heartRate, int systolic, int diastolic, int respiratoryRate) {
        VitalSigns VitalSigns = this.getVitalSigns();
        VitalSigns.setTemperature(temperature);
        VitalSigns.setHeartRate(heartRate);
        VitalSigns.setBloodPressureSystolic(systolic);
        VitalSigns.setBloodPressureDiastolic(diastolic);
        VitalSigns.setRespiratoryRate(respiratoryRate);
    }

    /**
     * Adds an outcome monitoring record for a medication.
     *
     * @param medication The name of the medication.
     * @param outcome    The outcome observed from the medication.
     */
    public void addOutcomeMonitoringHistory(String medication, String outcome) {
        String record = "Medication: " + medication + " | Outcome: " + outcome;
        outcomeMonitoringRecords.add(record);
        System.out.println("Outcome recorded in EHR: " + record);
    }

    /**
     * Displays all outcome monitoring records stored in the EHR.
     */
    public void displayOutcomeMonitoring() {
        if (outcomeMonitoringRecords.isEmpty()) {
            System.out.println("No outcome monitoring records available in EHR.");
            return;
        }
        System.out.println("\nOutcome Monitoring Records in EHR:");
        for (String record : outcomeMonitoringRecords) {
            System.out.println(" " + record);
        }
    }

    /**
     * Adds a symptom to the list of symptoms reported by the patient.
     *
     * @param symptoms The Symptoms object to be added.
     */
    public void addSymptom(Symptoms symptoms) {
        this.symptoms.add(symptoms);
    }

    /**
     * Get a string representation of the ElectronicHealthRecord object.
     *
     * @return A string containing all the patient's health information.
     */
    @Override
    public String toString() {
        return "EHR{" +
                "allergies=" + allergies +
                ", currentMedications=" + currentMedications +
                ", medicalHistory=" + medicalHistory +
                ", vitalSigns=" + (vitalSigns != null ? vitalSigns.toString() : "null") +
                ", labResults=" + labResults +
                ", imagingRecords=" + imagingRecords +
                ", clinicalNotes='" + clinicalNotes + '\'' +
                ", symptoms=" + symptoms +
                ", diagnosis=" + diagnosis + '\'' +
                ", outcomeMonitoringRecords=" + outcomeMonitoringRecords +
                ", pastSurgeries=" + pastSurgeries +
                ", vaccinationRecord=" + vaccinationRecord +
                '}';
    }
}
=======
package org.lucas.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The ElectronicHealthRecord class represents a patient's electronic health record (EHR).
 * It stores and manages various aspects of a patient's health information, including
 * allergies, medications, medical history, vital signs, lab results, imaging records,
 * clinical notes, symptoms, diagnosis, outcome monitoring records, past surgeries,
 * and vaccination records.
 */
public class ElectronicHealthRecord {
    // Private fields for storing patient health information
    private List<String> allergies;                    // List of patient allergies
    private List<Medication> currentMedications;      // List of current medications the patient is taking
    private List<String> medicalHistory;              // List of past medical conditions
    private VitalSigns vitalSigns;                    // Current vital signs of the patient
    private List<String> labResults;                  // List of lab test results
    private List<String> imagingRecords;              // List of imaging test results (e.g., X-rays, MRIs)
    private String clinicalNotes;                     // Notes from clinicians regarding the patient
    private List<Symptoms> symptoms;                  // Current symptoms reported by the patient
    private String diagnosis;                         // Current diagnosis for the patient
    private List<String> outcomeMonitoringRecords;    // Records of outcomes from treatments or medications
    private List<String> pastSurgeries;               // List of surgeries the patient has undergone
    private List<String> vaccinationRecord;           // Record of vaccinations the patient has received

    /**
     * Default constructor initializing all fields to default values.
     * All lists are initialized as empty ArrayLists, and strings are set to "Nil".
     */
    public ElectronicHealthRecord() {
        this.allergies = new ArrayList<>();
        this.medicalHistory = new ArrayList<>();
        this.currentMedications = new ArrayList<>();
        this.vitalSigns = new VitalSigns();
        this.labResults = new ArrayList<>();
        this.imagingRecords = new ArrayList<>();
        this.clinicalNotes = "Nil";
        this.symptoms = new ArrayList<>();
        this.diagnosis = "Nil";
        this.outcomeMonitoringRecords = new ArrayList<>();
        this.pastSurgeries = new ArrayList<>();
        this.vaccinationRecord = new ArrayList<>();
    }


    /**
     * Retrieves the list of allergies.
     *
     * @return a list of allergy names
     */
    public List<String> getAllergies() { return allergies; }

    /**
     * Sets the list of allergies.
     *
     * @param allergies a list of allergy names
     */
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }

    /**
     * Retrieves the list of current medications.
     *
     * @return a list of Medication objects representing current medications
     */
    public List<Medication> getCurrentMedications() { return currentMedications; }

    /**
     * Sets the list of current medications.
     *
     * @param currentMedications a list of Medication objects
     */
    public void setCurrentMedications(List<Medication> currentMedications) { this.currentMedications = currentMedications; }

    /**
     * Retrieves the medical history.
     *
     * @return a list of medical history records
     */
    public List<String> getMedicalHistory() { return medicalHistory; }

    /**
     * Sets the medical history.
     *
     * @param medicalHistory a list of medical history records
     */
    public void setMedicalHistory(List<String> medicalHistory) { this.medicalHistory = medicalHistory; }

    /**
     * Retrieves the vital signs.
     *
     * @return a VitalSigns object representing the patient's vital signs
     */
    public VitalSigns getVitalSigns() { return vitalSigns; }

    /**
     * Sets the vital signs.
     *
     * @param vitalSigns a VitalSigns object representing the patient's vital signs
     */
    public void setVitalSigns(VitalSigns vitalSigns) { this.vitalSigns = vitalSigns; }

    /**
     * Retrieves the lab results.
     *
     * @return a list of lab results
     */
    public List<String> getLabResults() { return labResults; }

    /**
     * Sets the lab results.
     *
     * @param labResults a list of lab results
     */
    public void setLabResults(List<String> labResults) { this.labResults = labResults; }

    /**
     * Retrieves the imaging records.
     *
     * @return a list of imaging records
     */
    public List<String> getImagingRecords() { return imagingRecords; }

    /**
     * Sets the imaging records.
     *
     * @param imagingRecords a list of imaging records
     */
    public void setImagingRecords(List<String> imagingRecords) { this.imagingRecords = imagingRecords; }

    /**
     * Retrieves the clinical notes.
     *
     * @return a string containing clinical notes
     */
    public String getClinicalNotes() { return clinicalNotes; }

    /**
     * Sets the clinical notes.
     *
     * @param clinicalNotes a string containing clinical notes
     */
    public void setClinicalNotes(String clinicalNotes) { this.clinicalNotes = clinicalNotes; }

    /**
     * Retrieves the list of symptoms.
     *
     * @return a list of Symptoms objects
     */
    public List<Symptoms> getSymptoms() { return symptoms; }

    /**
     * Sets the list of symptoms.
     *
     * @param symptoms a list of Symptoms objects
     */
    public void setSymptoms(List<Symptoms> symptoms) { this.symptoms = symptoms; }

    /**
     * Retrieves the diagnosis.
     *
     * @return a string representing the diagnosis
     */
    public String getDiagnosis() { return diagnosis; }

    /**
     * Sets the diagnosis.
     *
     * @param diagnosis a string representing the diagnosis
     */
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    /**
     * Retrieves the outcome monitoring records.
     *
     * @return a list of outcome monitoring records
     */
    public List<String> getOutcomeMonitoringRecords() { return outcomeMonitoringRecords; }

    /**
     * Retrieves the list of past surgeries.
     *
     * @return a list of past surgeries
     */
    public List<String> getPastSurgeries() { return pastSurgeries; }

    /**
     * Sets the list of past surgeries.
     *
     * @param pastSurgeries a list of past surgeries
     */
    public void setPastSurgeries(List<String> pastSurgeries) { this.pastSurgeries = pastSurgeries; }

    /**
     * Retrieves the vaccination record.
     *
     * @return a list of vaccination records
     */
    public List<String> getVaccinationRecord() { return vaccinationRecord; }

    /**
     * Sets the vaccination record.
     *
     * @param vaccinationRecord a list of vaccination records
     */
    public void setVaccinationRecord(List<String> vaccinationRecord) { this.vaccinationRecord = vaccinationRecord; }
    /**
     * Updates the patient's blood pressure readings in the vital signs.
     *
     * @param systolic  The systolic blood pressure value.
     * @param diastolic The diastolic blood pressure value.
     */
    public void updateBloodPressure(int systolic, int diastolic) {
        VitalSigns vitalSigns = this.getVitalSigns();
        vitalSigns.setBloodPressureSystolic(systolic);
        vitalSigns.setBloodPressureDiastolic(diastolic);
    }

    public void updateVitalSigns(double temperature, int heartRate, int systolic, int diastolic, int respiratoryRate) {
        VitalSigns VitalSigns = this.getVitalSigns();
        VitalSigns.setTemperature(temperature);
        VitalSigns.setHeartRate(heartRate);
        VitalSigns.setBloodPressureSystolic(systolic);
        VitalSigns.setBloodPressureDiastolic(diastolic);
        VitalSigns.setRespiratoryRate(respiratoryRate);
    }

    /**
     * Adds an outcome monitoring record for a medication.
     *
     * @param medication The name of the medication.
     * @param outcome    The outcome observed from the medication.
     */
    public void addOutcomeMonitoringHistory(String medication, String outcome) {
        String record = "Medication: " + medication + " | Outcome: " + outcome;
        outcomeMonitoringRecords.add(record);
        System.out.println("Outcome recorded in EHR: " + record);
    }
    /**
     * Get a string representation of the ElectronicHealthRecord object.
     *
     * @return A string containing all the patient's health information.
     */
    @Override
    public String toString() {
        return "EHR{" +
                "allergies=" + allergies +
                ", currentMedications=" + currentMedications +
                ", medicalHistory=" + medicalHistory +
                ", vitalSigns=" + (vitalSigns != null ? vitalSigns.toString() : "null") +
                ", labResults=" + labResults +
                ", imagingRecords=" + imagingRecords +
                ", clinicalNotes='" + clinicalNotes + '\'' +
                ", symptoms=" + symptoms +
                ", diagnosis=" + diagnosis + '\'' +
                ", outcomeMonitoringRecords=" + outcomeMonitoringRecords +
                ", pastSurgeries=" + pastSurgeries +
                ", vaccinationRecord=" + vaccinationRecord +
                '}';
    }
}
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/models/ElectronicHealthRecord.java

package org.lucas.pages.nurse;

import org.lucas.Globals;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.MedicationController;
import org.lucas.controllers.UserController;
import org.lucas.core.*;
import org.lucas.models.*;
import org.lucas.pages.doctor.PatientInfoPage;
import org.lucas.ui.framework.*;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.InputValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the patient actions page for nurses in the Telemedicine Integration System.
 * This page allows nurses to perform various actions on a selected patient,
 * such as administering medication, viewing clinical guidelines, and updating patient information.
 */
public class NursePatientActionsPage extends UiBase {
    private static Patient patient;
    private ListView listView;
    private static List<ClinicalGuideline> clinicalGuidelines = List.copyOf(ClinicalGuideline.generateClinicalGuideLine());
    final private static List<Alert> alertList = List.copyOf(Alert.generateAlert());
    private AuditManager auditManager = new AuditManager();
    public static void setPatient(Patient p) {
        patient = p;
    }
    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.BLUE);
        listView.setTitleHeader("Patient Actions");
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;
        displayPatientInfo(lv);
        Nurse nurse = UserController.getActiveNurse();
        // Attach user input handlers for each action
        lv.attachUserInput("Administer Medication", str -> {
            showPatientMedicationHistory(nurse, patient, clinicalGuidelines, alertList, auditManager);
            refreshUi();
        });

        lv.attachUserInput("Check Medication Alert History", str -> {
            Nurse.showMedicationAlertHistory(patient);
        });

        lv.attachUserInput("Enter Outcome Monitoring", str -> {
            enterOutcomeMonitoring(nurse, patient, auditManager);
            refreshUi();
            canvas.setRequireRedraw(true);
        });

        lv.attachUserInput("End Administration", str -> {
            ToPage(Globals.nurseMenuPage);
        });


        canvas.setRequireRedraw(true);
    }

    private void displayPatientInfo(ListView lv) {
        if (patient == null) {
            lv.addItem(new TextView(this.canvas, "No patient selected", Color.RED));
            return;
        }

        lv.addItem(new TextView(this.canvas, "Patient ID: " + patient.getPatientID(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Name: " + patient.getName(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Gender: " + patient.getGender(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Date of Birth: " + patient.getDateOfBirth(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Height: " + patient.getHeight() + " cm", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Weight: " + patient.getWeight() + " kg", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Blood Type: " + patient.getBloodType(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "House Address: " + patient.getHouseAddress(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Emergency Contact: " + patient.getEmergencyContactNumber(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Occupation: " + patient.getOccupation(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Ethnicity: " + patient.getEthnicity(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Email: " + patient.getEmail(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Healthcare Dept.: " + patient.getHealthcareDepartment(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Patient Consent: " + patient.getPatientConsent().isConsentGiven(), Color.GREEN));

        // EHR Section
        lv.addItem(new TextView(this.canvas, "\n----------------------------------------------------------------------------", Color.YELLOW));
        lv.addItem(new TextView(this.canvas, "                             ELECTRONIC HEALTH RECORD                       ", Color.BLUE));
        lv.addItem(new TextView(this.canvas, "----------------------------------------------------------------------------", Color.YELLOW));

        lv.addItem(new TextView(this.canvas, "Allergies: " + formatList(patient.getEHR().getAllergies()), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Medical History: " + formatList(patient.getEHR().getMedicalHistory()), Color.GREEN));

        // Medications
        lv.addItem(new TextView(this.canvas, "\nCurrent Medications:", Color.CYAN));
        for (Medication med : patient.getEHR().getCurrentMedications()) {
            lv.addItem(new TextView(this.canvas, "  - " + med.getMedicationName(), Color.GREEN));
        }

        lv.addItem(new TextView(this.canvas, "\nVital Signs: ", Color.CYAN));
        lv.addItem(new TextView(this.canvas, "" + patient.getEHR().getVitalSigns(), Color.GREEN));

        // Past Surgeries
        lv.addItem(new TextView(this.canvas, "\nPast Surgeries:", Color.CYAN));
        for (String surgery : patient.getEHR().getPastSurgeries()) {
            lv.addItem(new TextView(this.canvas, "  - " + surgery, Color.GREEN));
        }

        // Vaccination Record
        lv.addItem(new TextView(this.canvas, "\nVaccination Record:", Color.CYAN));
        for (String vaccine : patient.getEHR().getVaccinationRecord()) {
            lv.addItem(new TextView(this.canvas, "  - " + vaccine, Color.GREEN));
        }

        // Lab Results
        lv.addItem(new TextView(this.canvas, "\nLab Results:", Color.CYAN));
        for (String result : patient.getEHR().getLabResults()) {
            lv.addItem(new TextView(this.canvas, "  - " + result, Color.GREEN));
        }

        // Imaging Records
        lv.addItem(new TextView(this.canvas, "\nImaging Records:", Color.CYAN));
        for (String image : patient.getEHR().getImagingRecords()) {
            lv.addItem(new TextView(this.canvas, "  - " + image, Color.GREEN));
        }

        // Symptoms
        if (patient.getEHR().getSymptoms().isEmpty()) {
            lv.addItem(new TextView(this.canvas, "\nSymptoms: Nil", Color.GREEN));
        } else {
            lv.addItem(new TextView(this.canvas, "\nSymptoms:", Color.CYAN));
            for (Symptoms symptom : patient.getEHR().getSymptoms()) {
                lv.addItem(new TextView(this.canvas, "  - Symptom Name: " + symptom.getSymptomName(), Color.GREEN));
                lv.addItem(new TextView(this.canvas, "    Symptom ID: " + symptom.getSymptomId(), Color.GREEN));
                lv.addItem(new TextView(this.canvas, "    Severity Level: " + symptom.getSeverity(), Color.GREEN));
                lv.addItem(new TextView(this.canvas, "    Duration (days): " + symptom.getDuration(), Color.GREEN));
                lv.addItem(new TextView(this.canvas, "    Doctor Notes: " + symptom.getDoctorNotes(), Color.GREEN));
                lv.addItem(new TextView(this.canvas, "", Color.GREEN)); // Space between symptoms
            }
        }

        // Diagnosis and Clinical Notes
        lv.addItem(new TextView(this.canvas, "Diagnosis: " + patient.getEHR().getDiagnosis(), Color.CYAN));
        lv.addItem(new TextView(this.canvas, "Clinical Notes: " + patient.getEHR().getClinicalNotes(), Color.CYAN));

        // Outcome Monitoring Records
        if (!patient.getEHR().getOutcomeMonitoringRecords().isEmpty()) {
            lv.addItem(new TextView(this.canvas, "\nOutcome Monitoring Records:", Color.CYAN));
            for (String record : patient.getEHR().getOutcomeMonitoringRecords()) {
                lv.addItem(new TextView(this.canvas, "  - " + record, Color.GREEN));
            }
        }
    }

    private String formatList(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "None";
        }
        return String.join(", ", items);
    }

    @Override
    public void OnBackPressed(){
        super.OnBackPressed();
        PatientInfoPage.patient = null;
    }

    public static void administerMedication(Nurse nurse, Alert alert,
                                            ClinicalGuideline clinicalGuideline, Patient patient,
                                            AuditManager auditManager) {
        alert.displayAlertForPatient();  // Display any alerts before administration
        String outcomeResponse;
        auditManager.logAction(nurse.getId(), "ADMINISTER MEDICATION", "Patient: " + patient.getPatientID(), "OVERRIDDEN", "NURSE");
        while (true) {
            int systolicBP = InputValidator.getValidIntInput("Please enter Systolic BP: ");
            if (systolicBP <= clinicalGuideline.getBloodPressureSystolicThreshHold()) {
                // Prompt for override if systolic BP is too low
                if (InputValidator.getValidStringInput("Systolic BP is too low, would you like to override? (Yes/No): ")
                        .equalsIgnoreCase("Yes")) {
                    systolicBP = InputValidator.getValidIntInput("Please enter Systolic BP again: ");
                    outcomeResponse = InputValidator.getValidStringWithSpaceInput("Please enter override reason: ");
                    alert.setOverrideReason(outcomeResponse);
                    auditManager.logAction(nurse.getId(), "USER ENTERED:" + outcomeResponse, "Override reason", "SUCCESS", "NURSE");
                } else {
                    return;  // Abort medication administration if override is not approved
                }
            }

            int diastolicBP = InputValidator.getValidIntInput("Please enter Diastolic BP before proceeding: ");
            nurse.updatePatientBloodPressure(patient, systolicBP, diastolicBP);  // Update patient's blood pressure

            return;  // Exit after successful medication administration
        }
    }
    public static void enterOutcomeMonitoring(Nurse nurse, Patient patient, AuditManager auditManager) {
        System.out.println("\nEnter outcome monitoring for patient: " + patient.getName());

        List<Medication> medicationList = patient.getMedications();  // Get the list of prescribed medications
        if (medicationList.isEmpty()) {
            System.out.println("No medications found for this patient.");
            return;
        }

        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicationName());  // Display medications
        }

        int medicationChoice = InputValidator.getValidRangeIntInput(
                "Enter the medication number for which you want to enter outcome monitoring: ", medicationList.size());
        Medication selectedMedication = medicationList.get(medicationChoice - 1);  // Select medication

        String outcomeResponse = InputValidator.getValidStringWithSpaceInput(
                "Enter patient's response after medication (e.g., Improved, Unchanged, Side Effects): ");
        nurse.updateOutcomeMonitoring(patient, selectedMedication.getMedicationName(), outcomeResponse);  // Update outcome

        auditManager.logAction(nurse.getId(), "ENTER OUTCOME MONITORING", "Patient: " + patient.getPatientID() + "'s outcome monitoring", "SUCCESS", "NURSE");
        auditManager.logAction(nurse.getId(), "USER ENTERED:" + outcomeResponse, "Patient: " + patient.getPatientID() + "'s outcome monitoring", "SUCCESS", "NURSE");
    }

    public static void showPatientMedicationHistory(Nurse nurse, Patient patient,
                                                    List<ClinicalGuideline> clinicalGuidelines, List<Alert> alerts,
                                                    AuditManager auditManager) {
        List<Medication> medicationHistory = patient.getMedications();  // Get medication history
        auditManager.logAction(nurse.getId(), "VIEW MEDICATION HISTORY", "Patient: " + patient.getPatientID(), "SUCCESS", "NURSE");

        for (Medication med : medicationHistory) {
            med.displayPatientMedication();  // Display each medication

            ClinicalGuideline matchedGuideline = ClinicalGuideline.findGuidelineById(clinicalGuidelines, med.getGuidelineId());
            if (matchedGuideline != null) {
                matchedGuideline.displayGuidelineForPatient();  // Display associated guideline if available
            } else {
                System.out.println("No clinical guideline found for this medication.");
            }

            // Check for medication alerts
            Alert matchedAlert = SharedMethod.findAlertByID(alerts, med.getGuidelineId());
            if (matchedAlert != null && matchedGuideline != null) {
                administerMedication(nurse, matchedAlert, matchedGuideline, patient, auditManager);  // Administer medication
                nurse.setPatientAlertHistory(patient, matchedAlert);  // Log the alert history
            }
            askNurseMedicationConfirmation();  // Confirm medication administration
        }
    }
    public static void askNurseMedicationConfirmation() {
        while (true) {
            String response = InputValidator.getValidStringInput("\nHas this medication been administered? (yes/no): ");
            switch (response.toLowerCase()) {
                case "yes" -> {
                    System.out.println("✅ Medication has been administered.");
                    return;
                }
                case "no" -> {
                    System.out.println("❌ Medication not administered. Moving to the next one.");
                    return;
                }
                default -> System.out.println("❌ Invalid input! Please enter a valid option (yes/no).");
            }
        }
    }
    private void refreshUi() {
        listView.clear();
        displayPatientInfo(listView);
        canvas.setRequireRedraw(true);
    }

}
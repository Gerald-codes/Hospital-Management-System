package org.lucas.pages.doctor;

import org.lucas.controllers.UserController;
import org.lucas.models.Medication;
import org.lucas.models.Patient;
import org.lucas.models.Symptoms;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.InputHelper;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;

/**
 * Dual purpose page, can ask user to select multiple patients, or display a single patient
 */
public class PatientInfoPage extends UiBase {
    public static UserController userController = new UserController();
    public static Patient patient;
    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.GREEN);
    }

    @Override
    public void OnViewCreated(View parentView) {

        ListView lv = (ListView) parentView;
        if(patient != null){
            displayPatient(patient, lv, false);
            return;
        }
        lv.setTitleHeader("List of Patients");
        List<Patient> patients = userController.getPatients();
        int index = 0;
        lv.addItem(new TextView(this.canvas, index + ". Display All Patients", Color.GREEN));
        index += 1;

         //Show list of patient
        for (Patient patient : patients) {
            lv.addItem(new TextView(this.canvas, index + ". " + patient.getName(), Color.GREEN));
            index += 1;
        }

        // When selecting "Select Patient Index"
        lv.attachUserInput("Select Patient Index", str -> {
            int selectedIndex = InputHelper.getValidIndex("Select Patient index", patients);
                if(selectedIndex == 0) {
                    lv.clear();
                    displayAllPatients(patients, lv);
                    canvas.setRequireRedraw(true);
                }
                else{
                    patient = patients.get(selectedIndex -1);
                    try {
                        lv.clear();  // Clear the ListView first
                        lv.setTitleHeader("Current Patient");
                        lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
                        lv.addItem(new TextView(this.canvas, "                               PATIENT INFORMATION                         ", Color.BLUE));
                        lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
                        displayPatient(patient, lv, false);
                        canvas.setRequireRedraw(true);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw e;
                    }
                }
        });
    }

    private void displayPatient(Patient patient, ListView lv, boolean isPartOfList) {
        // Patient header (only if part of a list)
        if (isPartOfList) {
            lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
            lv.addItem(new TextView(this.canvas, "                               PATIENT INFORMATION                         ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
        }

        // Basic patient information
        lv.addItem(new TextView(this.canvas, "Patient ID: " + patient.getId(), Color.GREEN));
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

        lv.addItem(new TextView(this.canvas, "Vital Signs: " + patient.getEHR().getVitalSigns(), Color.GREEN));

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
        lv.addItem(new TextView(this.canvas, "Diagnosis: " + patient.getEHR().getDiagnosis(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Clinical Notes: " + patient.getEHR().getClinicalNotes(), Color.GREEN));

        // Outcome Monitoring Records
        if (!patient.getEHR().getOutcomeMonitoringRecords().isEmpty()) {
            lv.addItem(new TextView(this.canvas, "\nOutcome Monitoring Records:", Color.CYAN));
            for (String record : patient.getEHR().getOutcomeMonitoringRecords()) {
                lv.addItem(new TextView(this.canvas, "  - " + record, Color.GREEN));
            }
        }

        // Footer (only if part of a list)
        if (isPartOfList) {
            lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
        }
    }
    private void displayAllPatients(List<Patient> patients, ListView lv) {
        lv.clear();
        lv.setTitleHeader("All Patients Information");
        for (Patient patient : patients) {
            displayPatient(patient, lv, true);
        }
        // Request UI redraw
        canvas.setRequireRedraw(true);
    }
    // Helper method to format lists for display
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
}
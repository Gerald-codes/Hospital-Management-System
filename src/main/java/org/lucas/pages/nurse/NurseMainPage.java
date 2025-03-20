package org.lucas.pages.nurse;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.models.Medication;
import org.lucas.models.Patient;
import org.lucas.models.Symptoms;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.InputHelper;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseMainPage extends UiBase {
    public static UserController userController = new UserController();
    public static Patient patient;

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        return lv;
    }

    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to Telemedicine Integration System | Nurse Portal"); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information and perform actions ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Feedback Mechanism ", Color.GREEN));

        List<Patient> patients = userController.getPatients();

        lv.addItem(new TextView(this.canvas, "Now Displaying All Patients", Color.GREEN));
        int index = 0;
        for (Patient patient : patients) {
            ListView rowView = new ListView(this.canvas, Color.GREEN, ListViewOrientation.HORIZONTAL);

            rowView.addItem(new TextView(this.canvas, String.format("%-3d", index), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getId(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getName(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getDateOfBirth(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getGender(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getPatientSpecificFactor(), Color.GREEN));

            lv.addItem(rowView);
            index += 1;
        }

        // When selecting "Select Patient Index"
        lv.attachUserInput("Select Patient Index", str -> {
            int selectedIndex = InputHelper.getValidIndex("Select Patient index", patients);
            patient = patients.get(selectedIndex);

            // Show options for the selected patient
            System.out.println("\nSelected Patient: " + patient.getName());
            System.out.println("1. Perform Patient Actions");
            int choice = InputHelper.getValidIndex("Select an option", 1, 1);
            if (choice == 1) {
                // Navigate to patient actions page
                NursePatientActionsPage.setPatient(patient);
                ToPage(Globals.nursePatientActionsPage);
            }
        });
        canvas.setRequireRedraw(true);
    }

    private void displayPatient(Patient patient, ListView lv) {
        // Basic patient information
        lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
        lv.addItem(new TextView(this.canvas, "                               PATIENT INFORMATION                         ", Color.BLUE));
        lv.addItem(new TextView(this.canvas, "===========================================================================", Color.RED));
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
        lv.addItem(new TextView(this.canvas, "\n----------------------------------------------------------------------------", Color.RED));
        lv.addItem(new TextView(this.canvas, "                             ELECTRONIC HEALTH RECORD                       ", Color.BLUE));
        lv.addItem(new TextView(this.canvas, "----------------------------------------------------------------------------", Color.RED));

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
    }

    private String formatList(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "None";
        }
        return String.join(", ", items);
    }



}
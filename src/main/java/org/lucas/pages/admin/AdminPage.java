package org.lucas.pages.admin;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.controllers.UserController;
import org.lucas.models.Patient;
import org.lucas.pages.doctor.FeedbackPage;
import org.lucas.pages.nurse.NurseMainPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Admin Main Page");
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
        lv.setTitleHeader("Welcome to Telemedicine Integration System | Welcome Back " + UserController.getActiveAdmin().getName() +
                "\n\"=========== Emergency Case Management ==========="); // Set the title header of the list view

        lv.addItem(new TextView(this.canvas, "1. Create New Emergency Case", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Update Emergency Case Status", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. Update Patient Location", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. View Active Emergency Cases", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "5. Back", Color.GREEN));

        lv.attachUserInput("\nCreate New Emergency Case", str -> System.out.println("KAJSDKLJASJDAJS"));
        lv.attachUserInput("\nCreate New Emergency Case", str -> createNewEmergencyCase());

        canvas.setRequireRedraw(true);
    }

    private static void createNewEmergencyCase() {
        // Use EmergencyCase class to create new case
        // Collect: CaseID, PatientID, ChiefComplaint, ArrivalMode, etc.
        EmergencySystem ECsystem = new EmergencySystem();
        System.out.println("\n=========== Register New Emergency Case ===========");
        int caseID = ECsystem.setCaseID(); // CaseId is auto incremented
        List<Patient> allPatients = UserController.getAvailablePatients();
        boolean continueChecking = true;
        boolean useExistingPatient = false;
        String existPatientName = "";
        String patientID = "";
        do {
            String enteredPatientID = InputValidator.getValidStringInput("Enter Patient ID: ");
            // Check if patient ID already exists
            if (allPatients.stream().anyMatch(p -> p.getId().equals(enteredPatientID))) {
                for (Patient patientX : allPatients) {
                    if (patientX.getId().equals(enteredPatientID)) {
                        existPatientName = patientX.getName();
                        break;
                    }
                }

                System.out.println("Patient ID already exists.\nExisting patient found --> [ ID: " + enteredPatientID
                        + " | Name: " + existPatientName + " ]");
                System.out.println("\nPlease select to use the existing patient or enter a new patient ID.");
                int choice = InputValidator.getValidRangeIntInput("1. Use existing patient \n2. Enter new patient ID \nChoice: ", 2);
                if (choice == 1) { // Use existing patient
                    patientID = enteredPatientID;
                    continueChecking = false;
                    useExistingPatient = true;
                } // Else continue checking for new patient ID
            } else { // No existing patient found
                patientID = enteredPatientID; // Set patient ID
                continueChecking = false;
            }
        } while (continueChecking);

        String patientName = "";
        if (useExistingPatient) {
            patientName = existPatientName;
            System.out.println("Using existing patient: " + patientName);
        } else {
            patientName = InputValidator.getValidStringInput("Enter patient name: ").trim();
        }
        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            chiefComplaint = InputValidator.getValidStringInput("Enter reason of patient's visit (Chief Complaint): ").trim();
        }
        String arrivalMode = "Walk-In"; // Default arrival mode
        LocalDateTime arrivalDateTime = LocalDateTime.now(); // Ambulance/Helicopter

        // If patent exists, return existing patient
        Patient patient = Patient.checkOrCreatePatient(allPatients, patientID);
        EmergencyCase newCase = new EmergencyCase(caseID, patient, chiefComplaint, arrivalMode, arrivalDateTime);
        ECsystem.addEmergencyCase(newCase);
        System.out.println(
                "______________________________________________________________________________________________");
        System.out.println("New Walk In Case | Case ID: " + caseID + " | Patient Name: " + patientName
                + " | Registered successfully!\n");
        System.out.println("<--- Back");
    }


}

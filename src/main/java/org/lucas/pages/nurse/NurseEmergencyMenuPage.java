package org.lucas.pages.nurse;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Patient;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.util.InputValidator;

import java.time.LocalDateTime;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseEmergencyMenuPage extends UiBase {

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("NurseEmergencyMenuPage");
        ESController.loadEmergencyCaseFromFile();
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Emergency Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Case ", str -> createNewEmergencyCase());
        lv.attachUserInput("Location ", str -> ToPage(new NurseLocationPage()));
        lv.attachUserInput("View All Emergency Cases ", str -> viewAllEmergencyCases());
        lv.attachUserInput("View Dispatch Menu ", str -> ToPage(new NurseDispatchMenuPage()));
        canvas.setRequireRedraw(true);
    }

    private void createNewEmergencyCase() {
        EmergencySystem ECsystem = new EmergencySystem();
        System.out.println("\n=========== Register New Emergency Case ===========");
        int caseID = ECsystem.setCaseID(); // Auto-incremented CaseId

        String enteredPatientID = InputValidator.getValidStringInput("Enter Patient ID: ");
        Patient patient = UserController.checkOrCreatePatient(enteredPatientID);

        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            chiefComplaint = InputValidator.getValidStringInput("Enter reason of patient's visit (Chief Complaint): ").trim();
        }

        boolean isUrgent;
        while (true) {
            String urgency = InputValidator.getValidStringInput("Require Urgent Treatment (YES or NO): ");
            if (urgency.equalsIgnoreCase("YES")){
                isUrgent = true;
                break;
            } else if (urgency.equalsIgnoreCase("NO")) {
                isUrgent = false;
                break;
            }
        }

        EmergencyCase newCase = new EmergencyCase(caseID, patient, chiefComplaint, "Walk-In", LocalDateTime.now(), isUrgent);
        ESController.addEmergencyCases(newCase);

        System.out.println("\nNew Case Registered | Case ID: " + caseID + " | Patient: " + patient.getName());
        ESController.saveEmergencyCasesToFile();

        // After creating the emergency case, navigate back to the NurseEmergencyMenuPage
        ToPage(new NurseEmergencyMenuPage());
    }

    private void viewAllEmergencyCases(){
        ESController.printAllEmergencyCase();
//        listView.addItem(new TextView(this.canvas, doctorNotes + "\n", Color.GREEN));
        ToPage(new NurseEmergencyMenuPage());
    }

}
package org.lucas.pages.nurse;

import org.lucas.Emergency.DispatchInfo;
import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Nurse;
import org.lucas.models.Patient;
import org.lucas.models.User;
import org.lucas.pages.doctor.FeedbackPage;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseEmergencyMenuPage extends UiBase {

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("NurseEmergencyMenuPage");
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
        lv.setTitleHeader("Nurse Emergency Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Case\n", str -> createNewEmergencyCase());
        lv.attachUserInput("Location\n", str -> ToPage(new FeedbackPage()));
        lv.attachUserInput("View All Emergency Cases\n", str -> ToPage(new FeedbackPage()));
        lv.attachUserInput("View Dispatch Menu\n", str -> ToPage(new NurseDispatchMenuPage()));

        canvas.setRequireRedraw(true);
    }

    private static void createNewEmergencyCase() {
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
        System.out.println(newCase.toString());
        ECsystem.addEmergencyCase(newCase);

        System.out.println("\nNew Case Registered | Case ID: " + caseID + " | Patient: " + patient.getName());
        ESController.saveCasesToFile(ECsystem);
        new NurseMainPage();
    }




}
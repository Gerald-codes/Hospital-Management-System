package org.lucas.pages.nurse;

import org.lucas.Emergency.DispatchInfo;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;

import org.lucas.models.Nurse;
import org.lucas.models.Patient;

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
public class NurseDispatchMenuPage extends UiBase {

    /**
     * Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     *
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override
     */

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("NurseDispatchMenuPage");
        lv.addItem(new TextView(this.canvas, "1. Create New Emergency Dispatch Case", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Update Active Dispatch Case Status", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. View Dispatch Cases", Color.GREEN));
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Emergency Dispatch Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Dispatch Case", str -> createNewDispatchCase());
        lv.attachUserInput("Update Active Dispatch Case Status", str -> updateDispatchStatus());
        lv.attachUserInput("View Dispatch Cases", str -> viewDispatchCases());

        canvas.setRequireRedraw(true);
    }

    private void createNewDispatchCase() {
        System.out.println("\n=========== Register New Emergency Case ===========");
        int caseID = ESController.setCaseID();  // Auto-incremented CaseId

        //Patient ID
        String enteredPatientID = InputValidator.getValidStringInput("Enter Patient ID: ");
        Patient patient = UserController.checkOrCreatePatient(enteredPatientID);

        //Cheif Complaint
        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            chiefComplaint = InputValidator.getValidStringInput("Enter reason of patient's visit (Chief Complaint): ").trim();
        }

        //Is case urgent
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


        //Select dispatch vehicle type
        System.out.print(
                "___- Select dispatch vehicle -___\n (1. Ambulance) \n (2. Helicopter) \n");
        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        String arrivalMode;
        switch (choice) {
            case 1:
                arrivalMode = "Ambulance";
                break;
            case 2:
                arrivalMode = "Helicopter";
                break;
            default:
                arrivalMode = "Error";
                break;
        }
        PatientStatus patientStatus = PatientStatus.ONDISPATCHED;

        //Get Vehicle ID
        int ambulanceID = 0;
        while (ambulanceID == 0) {
            ambulanceID = InputValidator.getValidIntInput("Enter Vehicle ID: ");
        }

        //Dispatched paramedic nurse
        List<Nurse> paramedicNurses = new ArrayList<Nurse>();
        String nurseID = InputValidator.getValidStringInput("Enter dispatched paramedic nurse ID: ");
        Nurse paramedicNurse = UserController.checkParamedicNurse(nurseID);
        paramedicNurses.add(paramedicNurse);

        //Add more staff members
        boolean addMoreStaff = true;
        while (addMoreStaff) {
            System.out.print("___- Select Option -___\n (1. Add more member)\n (2. End)\n");
            choice = InputValidator.getValidIntInput("Enter your choice: ");

            if (choice == 2) {
                addMoreStaff = false;
            } else {
                // Get staff ID with validation
                nurseID = InputValidator.getValidStringInput("Enter dispatched paramedic nurse ID: ");
                paramedicNurse= UserController.checkParamedicNurse(nurseID);
                paramedicNurses.add(paramedicNurse);
            }
        }

        //Add Equipment
        System.out.print(
                "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n");
        choice = InputValidator.getValidIntInput("Enter your choice: ");

        List<String> equipmentList = new ArrayList<>();
        boolean addEquipment = false;
        if (choice == 1)
            addEquipment = true;

        while (addEquipment) {
            String equipment = InputValidator.getValidStringInput("Enter special treatment: ");
            equipmentList.add(equipment);
            System.out.print(
                    "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n");
            choice = InputValidator.getValidIntInput("Enter your choice: ");
            if (choice == 2)
                addEquipment = false;
        }

        String dispatchLocation = InputValidator.getValidStringInput("Please enter the location for dispatch: ");

        DispatchInfo dispatchInfo = new DispatchInfo(ambulanceID, paramedicNurses, equipmentList, dispatchLocation);
        EmergencyCase_Dispatch newDispatchCase = new EmergencyCase_Dispatch(caseID, patient, chiefComplaint, arrivalMode, patientStatus, dispatchInfo, isUrgent);
        ESController.addEmergencyCaseDispatch(newDispatchCase);

        System.out.println("New Dispatch Case | Case ID: " + caseID + " | Patient Name: " + patient.getName()
                + " | Registered successfully!\n");
        ESController.saveEmergencyDispatchCasesToFile();

        ToPage(new NurseDispatchMenuPage());
    }


    private void updateDispatchStatus() {
        ESController.printAllDispatchCases();

        int caseID = InputValidator.getValidIntInput("Enter case ID: ");
        List<EmergencyCase_Dispatch> dispatchCases = ESController.getAllDispatchCases();
        boolean found = false;
        for (EmergencyCase_Dispatch dc : dispatchCases) {
            if (dc.getCaseID() == caseID){
                if (dc.getPatientStatus() == PatientStatus.DONE) {
                    System.out.println("Dispatch case already resolved.");
                }
                else{
                    System.out.print(
                            "___- Select Option -___\n (0. Back\n1. Set Dispatch Team status to arrived to patient location)\n " +
                                    "(2. Set Dispatch Team status to arrived to hospital)\n");
                    int choice = InputValidator.getValidIntInput("Enter your choice: ");
                    switch(choice){
                        case 0:
                            return;
                        case 1:
                            arrivedAtLocation(dc);
                            break;

                        case 2:
                            arrivedAtHospital(dc);
                            ESController.addResolvedCases(dc);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            return;
                    }
                }
                break;
                }
            }
        }

//    private void updateDispatchStatus() {
//        int caseID = InputValidator.getValidIntInput("Enter case ID: ");
//        List<EmergencyCase_Dispatch> dispatchCases = ESController.getAllDispatchCases();
//        boolean found = false;
//        for (EmergencyCase_Dispatch dc : dispatchCases) {
//            if (dc.getCaseID() == caseID) { found = true;
//                if (dc.getPatientStatus() == PatientStatus.DONE) {
//                    System.out.println("Dispatch case already resolved.");
//                }
//                else {
//                    System.out.print( "- Select Option -\n (0. Back)\n (1. Set Dispatch Team status to arrived to patient location)\n " + "(2. Set Dispatch Team status to arrived to hospital)\n");
//                    int choice = InputValidator.getValidIntInput("Enter your choice: ");
//                    switch(choice) {
//                        case 0:
//                            return;
//                        case 1:
//                            arrivedAtLocation(dc);
//                            break;
//                        case 2:
//                            arrivedAtHospital(dc);
//                            ESController.mergeBothCases();
//                            break;
//                        default:
//                            System.out.println("Invalid choice.");
//                            return;
//                    }
//                }
//                break;
//                }
//        }
//        if (!found) {
//            System.out.println("Invalid case ID");
//        }
//    }


    private void arrivedAtLocation(EmergencyCase_Dispatch dispatchCase){
        // update dispatch arrival time
        dispatchCase.setDispatchTeamArrivalTime(LocalDateTime.now());
        ESController.nurseInitialScreening(dispatchCase);
        //didnt include the staff member selection part because the paramedic nurse for each dispatch case is already a staff member
        dispatchCase.setPatientStatus(PatientStatus.ONDISPATCHED);
        //save the case
        ESController.saveEmergencyDispatchCasesToFile();
        ToPage(new NurseDispatchMenuPage());
    }

    private void arrivedAtHospital(EmergencyCase_Dispatch dispatchCase){
        dispatchCase.setPatientStatusToArrived();
        dispatchCase.setLocation(PatientLocation.EMERGENCY_ROOM_WAITING_ROOM);
        System.out.println(dispatchCase.getResponseDetails());
        //save the case
        ESController.saveEmergencyDispatchCasesToFile();
    }


    private void viewDispatchCases(){
        System.out.println("0. Back\n1. View Active Dispatch Case\n2. View ALl Dispatch Cases\n");
        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        switch(choice){
            case 0:
                return;
            case 1:
                ESController.printActiveDispatch();
            case 2:
                ESController.printAllDispatchCases();
        }
        ToPage(new NurseDispatchMenuPage());
    }
}
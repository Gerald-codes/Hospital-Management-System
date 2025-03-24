package org.lucas.pages.nurse;

import org.lucas.Emergency.DispatchInfo;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.ElectronicHealthRecord;
import org.lucas.models.Nurse;
import org.lucas.models.Patient;
import org.lucas.models.VitalSigns;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.util.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Emergency Dispatch Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Dispatch Case\n", str -> createNewDispatchCase());
        lv.attachUserInput("Update Active Dispatch Case Status\n", str -> updateDispatchStatus());
        lv.attachUserInput("View Dispatch Cases\n", str -> viewDispatchCases());

        canvas.setRequireRedraw(true);
    }

    private static EmergencySystem ECsystem = new EmergencySystem();
    private static void createNewDispatchCase() {
//      EmergencySystem ECsystem = new EmergencySystem();
        System.out.println("\n=========== Register New Emergency Case ===========");
        int caseID = ECsystem.setCaseID(); // Auto-incremented CaseId

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
        ESController.saveEmergencyCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "NEW EMERGENCY DISPATCH CASE CREATED", "Case ID: " + caseID, "EMERGENCY DISPATCH", "NURSE");
        new NurseDispatchMenuPage();
    }


    private static void updateDispatchStatus() {
//        EmergencySystem ECsystem = new EmergencySystem();
        int caseID = InputValidator.getValidIntInput("Enter case ID: ");

        for (EmergencyCase_Dispatch dispatchCase : ECsystem.getEmergencyCaseDispatch()) { //this part should load the existing emergency dispatch cases
            if ((dispatchCase.getCaseID() == caseID) && (dispatchCase.getPatientStatus().equals("DONE"))) {
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
                        arrivedAtLocation(dispatchCase);
                    case 2:
                        arrivedAtHospital(dispatchCase);
                    }
                }
            }
        }


    private static void arrivedAtLocation(EmergencyCase_Dispatch dispatchCase){
        // update dispatch arrival time
        dispatchCase.setDispatchTeamArrivalTime(LocalDateTime.now());
//        //do initial screening
//        String triageLevelInput = InputValidator.getValidStringInput("Enter triage level: ");
//        dispatchCase.setTriageLevel(triageLevelInput);
        Patient patient = dispatchCase.getPatient();
        ElectronicHealthRecord EHR = patient.getElectronicHealthRecord();
        // ask for vital signs
        double temperature = InputValidator.getValidDoubleInput("Enter patient's temperature: ");
        int heartRate = InputValidator.getValidIntInput("Enter patient's heart rate: ");
        int bloodPressureSystolic = InputValidator.getValidIntInput("Enter patient's systolic blood pressure: ");
        int bloodPressureDiastolic = InputValidator.getValidIntInput("Enter patient's diastolic blood pressure: ");
        int respiratoryRate = InputValidator.getValidIntInput("Enter patient's respiratory rate: ");
        VitalSigns vitalSigns = new VitalSigns(temperature, heartRate, bloodPressureSystolic, bloodPressureDiastolic, respiratoryRate);
        EHR.setVitalSigns(vitalSigns);
        // ask for allergies
        String input = InputValidator.getValidStringInput("Enter patient's allergies (separate by commas): ");
        List<String> allergies = Arrays.asList(input.split("\\s*,\\s*"));
        EHR.setAllergies(allergies);
        //didnt include the staff member selection part because the paramedic nurse for each dispatch case is already a staff member
        dispatchCase.setPatientStatus(PatientStatus.ONDISPATCHED);
        //save the case
    }

    private static void arrivedAtHospital(EmergencyCase_Dispatch dispatchCase){
        dispatchCase.setPatientStatusToArrived();
        dispatchCase.setLocation(PatientLocation.EMERGENCY_ROOM_WAITING_ROOM);
        System.out.println(dispatchCase.getResponseDetails());
        //save the case
    }


    private static void viewDispatchCases(){
        System.out.println("0. Back\n1. View Past Dispatch Case\n2.View Active Dispatch Case\n3. View ALl Dispatch Cases\n");
        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        switch(choice){
            case 0:
                return;
            case 1:
                int caseID = InputValidator.getValidIntInput("Enter the case ID of the dispatch case you want to view: ");
                ECsystem.printCaseInfo(caseID,"Dispatch");
            case 2:
                ECsystem.printActiveDispatch();
            case 3:
                ECsystem.printAllDispatch();
        }

    }
}
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
package org.groupJ.pages.paramedic;

import org.groupJ.Emergency.DispatchInfo;
import org.groupJ.Emergency.EmergencyCase_Dispatch;
import org.groupJ.Emergency.enums.PatientLocation;
import org.groupJ.Emergency.enums.PatientStatus;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Nurse;
import org.groupJ.models.Patient;

import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;
========
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
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
public class ParamedicMenuPage extends UiBase {
    private ListView listView;
========
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
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java

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
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
        int caseID = ESController.setCaseID();  // Auto-incremented CaseId
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "CREATE NEW DISPATCH CASE", String.valueOf(caseID), "ONGOING", "PARAMEDIC");

        //Patient ID
        Patient patient = UserController.checkOrCreatePatient(UserController.getActiveParamedic());
========
        int caseID = ECsystem.setCaseID(); // Auto-incremented CaseId

        //Patient ID
        String enteredPatientID = InputValidator.getValidStringInput("Enter Patient ID: ");
        Patient patient = UserController.checkOrCreatePatient(enteredPatientID);
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java

        //Chief Complaint
        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            chiefComplaint = InputValidator.getValidStringInput("Enter reason of patient's visit (Chief Complaint): ").trim();
        }
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTER REASON OF PATIENT'S VISIT", chiefComplaint, "SUCCESS", "PARAMEDIC");


        //Is case urgent
        boolean isUrgent;
        while (true) {
            String urgency = InputValidator.getValidStringInput("Require Urgent Treatment (YES or NO): ");
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "REQUIRE URGENT TREATMENT", urgency, "SUCCESS", "PARAMEDIC");
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
                "------ Select dispatch vehicle ------\n 1. Ambulance \n 2. Helicopter \n");

        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING VEHICLE CHOICE",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");


        String arrivalMode = switch (choice) {
            case 1 -> "Ambulance";
            case 2 -> "Helicopter";
            default -> "Error";
        };
        PatientStatus patientStatus = PatientStatus.ONDISPATCHED;

        //Get Vehicle ID
        int ambulanceID;
        while (true) {
            ambulanceID = InputValidator.getValidIntInput("Enter Vehicle ID: ");
            int length = String.valueOf(ambulanceID).length();
            if (length == 4){
                break;
            }
        }
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTER VEHICLE ID",String.valueOf(ambulanceID), "SUCCESS", "PARAMEDIC");


        //Dispatched paramedic nurse
        List<Nurse> paramedicNurses = new ArrayList<>();
        String nurseID = InputValidator.getValidStringInput("Enter dispatched paramedic nurse ID: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTER PARAMEDIC ID",nurseID, "SUCCESS", "PARAMEDIC");

        Nurse paramedicNurse = UserController.checkParamedicNurse(nurseID);
        paramedicNurses.add(paramedicNurse);
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "CREATE NEW DISPATCH CASE", patient.getId(), "SUCCESS", "PARAMEDIC");

        //Add more staff members
        boolean addMoreStaff = true;
        while (addMoreStaff) {
            System.out.print("___- Select Option -___\n (1. Add more member)\n (2. End)\n");
            choice = InputValidator.getValidIntInput("Enter your choice: ");
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");

            if (choice == 2) {
                addMoreStaff = false;
            } else {
                // Get staff ID with validation
                nurseID = InputValidator.getValidStringInput("Enter dispatched paramedic nurse ID: ");
                AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTER PARAMEDIC ID",nurseID, "SUCCESS", "PARAMEDIC");
                paramedicNurse= UserController.checkParamedicNurse(nurseID);
                paramedicNurses.add(paramedicNurse);
            }
        }

        //Add Equipment
        System.out.print(
                "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n");
        choice = InputValidator.getValidIntInput("Enter your choice: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");

        List<String> equipmentList = new ArrayList<>();
        boolean addEquipment = choice == 1;

        while (addEquipment) {
            String equipment = InputValidator.getValidStringInput("Enter special treatment: ");
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTERING TREATMENT",equipment, "SUCCESS", "PARAMEDIC");
            equipmentList.add(equipment);
            System.out.print(
                    "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n");
            choice = InputValidator.getValidIntInput("Enter your choice: ");
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");
            if (choice == 2)
                addEquipment = false;
        }

        String dispatchLocation = InputValidator.getValidStringInput("Please enter the location for dispatch: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTERING DISPATCH LOCATION",dispatchLocation, "SUCCESS", "PARAMEDIC");


        DispatchInfo dispatchInfo = new DispatchInfo(ambulanceID, paramedicNurses, equipmentList, dispatchLocation);

        EmergencyCase_Dispatch newDispatchCase = new EmergencyCase_Dispatch(caseID, patient, chiefComplaint, arrivalMode, patientStatus, dispatchInfo, isUrgent);
        ESController.addEmergencyCaseDispatch(newDispatchCase);
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
        String string = ("New Dispatch Case | Case ID: " + caseID + " | Patient Name: " + patient.getName()
                + " | Registered successfully!\n");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "CREATE NEW DISPATCH CASE", String.valueOf(caseID), "COMPLETED", "PARAMEDIC");

        ESController.saveEmergencyDispatchCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SAVE NEW DISPATCH CASE TO FILE", String.valueOf(caseID), "SUCCESS", "PARAMEDIC");

        refreshUi(string);
    }


    private void updateDispatchStatus() {
        ESController.printAllDispatchCases();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "VIEW ALL DISPATCH CASE", "DISPATCH CASE", "SUCCESS", "PARAMEDIC");

        int caseID = InputValidator.getValidIntInput("Enter case ID: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTER CASE ID", String.valueOf(caseID), "SUCCESS", "PARAMEDIC");


        List<EmergencyCase_Dispatch> dispatchCases = ESController.getAllDispatchCases();
        for (EmergencyCase_Dispatch dc : dispatchCases) {
            if (dc.getCaseID() == caseID){
                if (dc.getPatientStatus() == PatientStatus.DONE) {
                    System.out.println("Dispatch case already resolved.");
                    refreshUi("");
                }
                else{
                    System.out.print(
                            "____Select Option____\n 0. Back\n 1. Set Dispatch Team status to arrived to patient location\n 2. Set Dispatch Team status to arrived to hospital\n");
                    int choice = InputValidator.getValidIntInput("Enter your choice: ");
                    AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");
                    switch(choice){
                        case 0:
                            refreshUi("");
                            break;
                        case 1:
                            arrivedAtLocation(dc);
                            refreshUi("Status updated to arrived at location.");
                            break;

                        case 2:
                            arrivedAtHospital(dc);
                            ESController.addResolvedCases(dc);
                            refreshUi("Dispatch case completed, added to emergency case.");
                            break;
                        default:
                            refreshUi("Invalid choice.");
                            break;
                    }
                }
                break;
            }refreshUi("Invalid Case ID");
========
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
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java
        }


    private static void arrivedAtLocation(EmergencyCase_Dispatch dispatchCase){
        // update dispatch arrival time
        dispatchCase.setDispatchTeamArrivalTime(LocalDateTime.now());
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE ARRIVAL TIME" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        ESController.nurseInitialScreening(dispatchCase);

        //didn't include the staff member selection part because the paramedic nurse for each dispatch case is already a staff member
========
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
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java
        dispatchCase.setPatientStatus(PatientStatus.ONDISPATCHED);
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE PATIENT STATUS TO ONDISPATCHED" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
        //save the case
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
        ESController.saveEmergencyDispatchCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SAVE DISPATCH CASE TO FILE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
========
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java
    }

    private static void arrivedAtHospital(EmergencyCase_Dispatch dispatchCase){
        dispatchCase.setPatientStatusToArrived();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE ARRIVE DATETIME" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE PATIENT STATUS TO DONE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        dispatchCase.setLocation(PatientLocation.EMERGENCY_ROOM_WAITING_ROOM);
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE LOCATION TO WAITING ROOM" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        System.out.println(dispatchCase.getResponseDetails());

        //save the case
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
        ESController.saveEmergencyDispatchCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SAVE DISPATCH CASE TO FILE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
    }


    private void viewDispatchCases(){
        System.out.println("0. Back\n1. View ALl Dispatch Cases\n");
========
    }


    private static void viewDispatchCases(){
        System.out.println("0. Back\n1. View Past Dispatch Case\n2.View Active Dispatch Case\n3. View ALl Dispatch Cases\n");
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java
        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(choice), "SUCCESS", "PARAMEDIC");

        switch(choice){
            case 0:
                return;
            case 1:
<<<<<<<< HEAD:src/main/java/org/groupJ/pages/paramedic/ParamedicMenuPage.java
                ESController.printAllDispatchCases();
                AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "VIEW ALL DISPATCH CASE" , "DISPATCH CASE","SUCCESS", "PARAMEDIC");
========
                int caseID = InputValidator.getValidIntInput("Enter the case ID of the dispatch case you want to view: ");
                ECsystem.printCaseInfo(caseID,"Dispatch");
            case 2:
                ECsystem.printActiveDispatch();
            case 3:
                ECsystem.printAllDispatch();
>>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/groupJ/pages/nurse/NurseDispatchMenuPage.java
        }

    }
}
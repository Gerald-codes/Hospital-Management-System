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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParamedicMenuPage extends UiBase {
    private ListView listView;

    @Override
    public View OnCreateView() {
        listView= new ListView(this.canvas, Color.GREEN);
        listView.addItem(new TextView(this.canvas, "1. Create New Emergency Dispatch Case", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Update Active Dispatch Case Status", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. View Dispatch Menu", Color.GREEN));
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Paramedic Emergency Dispatch Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Dispatch Case", str -> createNewDispatchCase());
        lv.attachUserInput("Update Active Dispatch Case Status", str -> updateDispatchStatus());
        lv.attachUserInput("View Dispatch Cases", str -> viewDispatchCases());

        canvas.setRequireRedraw(true);
    }
    private void createNewDispatchCase() {
        System.out.println("\n=========== Register New Emergency Case ===========");
        int caseID = ESController.setCaseID();  // Auto-incremented CaseId
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "CREATE NEW DISPATCH CASE", String.valueOf(caseID), "ONGOING", "PARAMEDIC");

        //Patient ID
        Patient patient = UserController.checkOrCreatePatient(UserController.getActiveParamedic());

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
            System.out.print("------------ Select Option ------------\n 1. Add more member\n 2. End\n");
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
                "------------ Select Option ------------\n 1. Add special equipment\n 2. End\n");
        choice = InputValidator.getValidIntInput("Enter your choice: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(caseID), "SUCCESS", "PARAMEDIC");

        List<String> equipmentList = new ArrayList<>();
        boolean addEquipment = choice == 1;

        while (addEquipment) {
            String equipment = InputValidator.getValidStringInput("Enter special treatment: ");
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "ENTERING TREATMENT",equipment, "SUCCESS", "PARAMEDIC");
            equipmentList.add(equipment);
            System.out.print(
                    "------------ Select Option ------------\n 1. Add special equipment\n 2. End\n");
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
        }
    }


    private void arrivedAtLocation(EmergencyCase_Dispatch dispatchCase){
        // update dispatch arrival time
        dispatchCase.setDispatchTeamArrivalTime(LocalDateTime.now());
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE ARRIVAL TIME" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        ESController.nurseInitialScreening(dispatchCase);

        //didn't include the staff member selection part because the paramedic nurse for each dispatch case is already a staff member
        dispatchCase.setPatientStatus(PatientStatus.ONDISPATCHED);
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE PATIENT STATUS TO ONDISPATCHED" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
        //save the case
        ESController.saveEmergencyDispatchCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SAVE DISPATCH CASE TO FILE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
    }

    private void arrivedAtHospital(EmergencyCase_Dispatch dispatchCase){
        dispatchCase.setPatientStatusToArrived();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE ARRIVE DATETIME" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE PATIENT STATUS TO DONE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        dispatchCase.setLocation(PatientLocation.EMERGENCY_ROOM_WAITING_ROOM);
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "UPDATE DISPATCH CASE LOCATION TO WAITING ROOM" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");

        System.out.println(dispatchCase.getResponseDetails());

        //save the case
        ESController.saveEmergencyDispatchCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SAVE DISPATCH CASE TO FILE" , String.valueOf(dispatchCase.getCaseID()),"SUCCESS", "PARAMEDIC");
    }


    private void viewDispatchCases(){
        System.out.println("0. Back\n1. View ALl Dispatch Cases\n");
        int choice = InputValidator.getValidIntInput("Enter your choice: ");
        AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "SELECTING MORE CHOICES",String.valueOf(choice), "SUCCESS", "PARAMEDIC");

        switch(choice){
            case 0:
                return;
            case 1:
                ESController.printAllDispatchCases();
                AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "VIEW ALL DISPATCH CASE" , "DISPATCH CASE","SUCCESS", "PARAMEDIC");
        }
        refreshUi("");
    }

    public void refreshUi(String string){
        listView.clear();
        listView.setTitleHeader("Paramedic Emergency Dispatch Menu");
        listView.addItem(new TextView(this.canvas, "1. Create New Emergency Dispatch Case", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Update Active Dispatch Case Status", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. View Dispatch Menu", Color.GREEN));
        listView.addItem(new TextView(this.canvas, string, Color.GREEN));

        canvas.setRequireRedraw(true);
    }
}

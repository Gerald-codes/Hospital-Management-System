package org.groupJ.pages.nurse;

<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java
import org.groupJ.Emergency.EmergencyCase;
import org.groupJ.Emergency.EmergencyCase_Dispatch;
import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Nurse;
import org.groupJ.models.Patient;

import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.TextStyle;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;
=======
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
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java

import java.time.LocalDateTime;

public class NurseEmergencyMenuPage extends UiBase {

    @Override
    public View OnCreateView() {
<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java
        listView= new ListView(this.canvas, Color.GREEN);
        listView.addItem(new TextView(this.canvas, "1. Create New Emergency Case - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Locations - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. View All Emergency Cases", Color.GREEN));
        return listView;
=======
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("NurseEmergencyMenuPage");
        ESController.loadEmergencyCaseFromFile();
        return lv;
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Emergency Menu"); // Set the title header of the list view
<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java
        lv.attachUserInput("Create New Emergency Case ", str -> createNewEmergencyCase());
        lv.attachUserInput("Location ", str -> ToPage(Globals.nurseLocationPage));
        lv.attachUserInput("View All Emergency Cases ", str -> viewAllEmergencyCases());
=======
        lv.attachUserInput("Create New Emergency Case\n", str -> createNewEmergencyCase());
        lv.attachUserInput("Location\n", str -> ToPage(new NurseLocationPage()));
        lv.attachUserInput("View All Emergency Cases\n", str -> viewAllEmergencyCases());
        lv.attachUserInput("View Dispatch Menu\n", str -> ToPage(new NurseDispatchMenuPage()));
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java
        canvas.setRequireRedraw(true);
    }

    private void createNewEmergencyCase() {
        EmergencySystem ECsystem = new EmergencySystem();
        System.out.println("\n=========== Register New Emergency Case ===========");
<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java

        int caseID = ESController.setCaseID(); // Auto-incremented CaseId
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "CREATE NEW EMERGENCY CASE", String.valueOf(caseID), "ONGOING", "NURSE");

        Patient patient = UserController.checkOrCreatePatient(UserController.getActiveNurse());
=======
        int caseID = ECsystem.setCaseID(); // Auto-incremented CaseId

        String enteredPatientID = InputValidator.getValidStringInput("Enter Patient ID: ");
        Patient patient = UserController.checkOrCreatePatient(enteredPatientID);
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java

        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            chiefComplaint = InputValidator.getValidStringInput("Enter reason of patient's visit (Chief Complaint): ").trim();
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "ENTER REASON OF PATIENT'S VISIT", String.valueOf(caseID), "SUCCESS", "NURSE");
        }

        boolean isUrgent;
        while (true) {
            String urgency = InputValidator.getValidStringInput("Require Urgent Treatment (YES or NO): ");
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "ENTER REQUIREMENT FOR URGENT TREATMENT", String.valueOf(caseID), "SUCCESS", "NURSE");
            if (urgency.equalsIgnoreCase("YES")){
                isUrgent = true;
                break;
            } else if (urgency.equalsIgnoreCase("NO")) {
                isUrgent = false;
                break;
            }
        }

        EmergencyCase newCase = new EmergencyCase(caseID, patient, chiefComplaint, "Walk-In", LocalDateTime.now(), isUrgent);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "CREATE NEW EMERGENCY CASE", patient.getId(), "COMPLETED", "NURSE");
        ESController.addEmergencyCases(newCase);

        System.out.println("\nNew Case Registered | Case ID: " + caseID + " | Patient: " + patient.getName());
        ESController.saveEmergencyCasesToFile();
<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "SAVE EMERGENCY CASE TO FILE", patient.getId(), "SUCCESS", "NURSE");
        refreshUi(string);
=======
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java

        // After creating the emergency case, navigate back to the NurseEmergencyMenuPage
        ToPage(new NurseEmergencyMenuPage());
    }

<<<<<<< HEAD:src/main/java/org/groupJ/pages/nurse/NurseEmergencyMenuPage.java
    private void viewAllEmergencyCases() {
        List<EmergencyCase> allCases = ESController.getAllCases();
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "VIEW ALL EMERGENCY CASE", "EMERGENCY CASE" , "SUCCESS", "NURSE");
        listView.clear();
        listView.addItem(new TextView(this.canvas, "============ ALL EMERGENCY CASE ============ ", Color.CYAN, TextStyle.BOLD));

        for (EmergencyCase ec : allCases) {
            listView.addItem(new TextView(this.canvas, "---------------------------------", Color.CYAN, TextStyle.BOLD));
            listView.addItem(new TextView(this.canvas, "Case ID: " + ec.getCaseID(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Patient Name: " + ec.getPatient().getName(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Location: " + ec.getLocation(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Chief Complaint: " + ec.getChiefComplaint(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Arrival Mode: " + ec.getArrivalMode(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Arrival Date & Time: " + ec.getArrivalDateTime(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Triage Level: " + ec.getTriageLevel(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Patient Status: " + ec.getPatientStatus(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Urgent: " + (ec.isUrgent() ? "YES" : "NO"), (ec.isUrgent() ? Color.RED : Color.CYAN), TextStyle.BOLD));

            if (ec instanceof EmergencyCase_Dispatch dispatchCase) {
                listView.addItem(new TextView(this.canvas, "====== Dispatch Info ======", Color.BLUE, TextStyle.BOLD));
                listView.addItem(new TextView(this.canvas, "Vehicle ID: " + dispatchCase.getDispatchInfo().getVehicleId(), Color.BLUE));

                listView.addItem(new TextView(this.canvas, "Medevac Members:", Color.BLUE));
                for (Nurse nurse : dispatchCase.getDispatchInfo().getMedivacMembers()) {
                    listView.addItem(new TextView(this.canvas, "  - " + nurse.getName(), Color.CYAN));
                }

                listView.addItem(new TextView(this.canvas, "Equipment:", Color.BLUE));
                for (String equipment : dispatchCase.getDispatchInfo().getEquipment()) {
                    listView.addItem(new TextView(this.canvas, "  - " + equipment, Color.CYAN));
                }

                listView.addItem(new TextView(this.canvas, "Dispatch Location: " + dispatchCase.getDispatchInfo().getDispatchLocation(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Dispatch Arrival Time: " + dispatchCase.getDispatchArrivalTime(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Time of Call: " + dispatchCase.getTimeOfCall(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Response Time: " + dispatchCase.getResponseTime().toMinutes() + " minutes", Color.BLUE));
            }

            listView.addItem(new TextView(this.canvas, "---------------------------------\n", Color.CYAN, TextStyle.BOLD));
        }
        canvas.setRequireRedraw(true);

    }

    public void refreshUi(String string){
        listView.clear();
        listView.setTitleHeader("Nurse Emergency Menu");
        listView.addItem(new TextView(this.canvas, "1. Create New Emergency Case - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Locations - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. View All Emergency Cases", Color.GREEN));
        listView.addItem(new TextView(this.canvas, string, Color.GREEN));

        canvas.setRequireRedraw(true);
=======
    private void viewAllEmergencyCases(){
        ESController.printAllEmergencyCase();
//        listView.addItem(new TextView(this.canvas, doctorNotes + "\n", Color.GREEN));
        ToPage(new NurseEmergencyMenuPage());
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/nurse/NurseEmergencyMenuPage.java
    }

}
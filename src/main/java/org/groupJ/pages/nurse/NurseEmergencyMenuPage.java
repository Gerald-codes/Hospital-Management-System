package org.groupJ.pages.nurse;

import org.groupJ.models.EmergencyCase;
import org.groupJ.models.EmergencyCase_Dispatch;
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

import java.time.LocalDateTime;
import java.util.List;

public class NurseEmergencyMenuPage extends UiBase {
    private ListView listView;

    @Override
    public View OnCreateView() {
        listView= new ListView(this.canvas, Color.GREEN);
        listView.addItem(new TextView(this.canvas, "1. Create New Emergency Case - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Locations - To enter and proceed with Action  ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. View All Emergency Cases", Color.GREEN));
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Emergency Menu"); // Set the title header of the list view
        lv.attachUserInput("Create New Emergency Case ", str -> createNewEmergencyCase());
        lv.attachUserInput("Location ", str -> ToPage(Globals.nurseLocationPage));
        lv.attachUserInput("View All Emergency Cases ", str -> viewAllEmergencyCases());
        canvas.setRequireRedraw(true);
    }

    private void createNewEmergencyCase() {
        System.out.println("\n=========== Register New Emergency Case ===========");

        int caseID = ESController.setCaseID(); // Auto-incremented CaseId
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "CREATE NEW EMERGENCY CASE", String.valueOf(caseID), "ONGOING", "NURSE");

        Patient patient = UserController.checkOrCreatePatient(UserController.getActiveNurse());

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

        String string = "\nNew Case Registered | Case ID: " + caseID + " | Patient: " + patient.getName();
        ESController.saveEmergencyCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "SAVE EMERGENCY CASE TO FILE", patient.getId(), "SUCCESS", "NURSE");
        refreshUi(string);

    }

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
    }

}
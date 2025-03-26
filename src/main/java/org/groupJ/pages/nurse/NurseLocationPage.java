package org.groupJ.pages.nurse;

import org.groupJ.Emergency.EmergencyCase;
import org.groupJ.Emergency.enums.PatientLocation;
import org.groupJ.Emergency.enums.PatientStatus;
import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Nurse;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;


public class NurseLocationPage extends UiBase {
    private ListView listView;

    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        listView.addItem(new TextView(this.canvas, "1. Triage Room - Proceed With Initial Screening", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Observation Room - Proceed With Patient Monitoring & Care Tasks", Color.GREEN));
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Location Menu"); // Set the title header of the list view
        lv.attachUserInput("Triage Room", str -> proceedWithInitialScreening() );
        lv.attachUserInput("Observation Room", str -> proceedToObservationRoom());

        canvas.setRequireRedraw(true);
    }

    public void proceedWithInitialScreening() {

        EmergencyCase selectedCase = null;
        do {
            ESController.printAllEmergencyCaseInWaitingRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");

            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "ENTER CASE ID", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");
            selectedCase = ESController.selectCase(caseId);
        } while(selectedCase == null);

        Nurse nurse = UserController.getActiveNurse();
        selectedCase.setInitialScreeningNurse(nurse);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE INITIAL SCREENING NURSE", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_TRIAGE_ROOM);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE LOCATION TO TRIAGE ROOM", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        selectedCase.setPatientStatus(PatientStatus.ONGOING);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE PATIENT STATUS TO ONGOING", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        ESController.nurseInitialScreening(selectedCase);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE PATIENT STATUS TO DONE", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        ESController.saveEmergencyCasesToFile();
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "SAVE EMERGENCY CASE TO FILE", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        refreshUi("Patient Initial Screening Completed!");
    }

    public void proceedToObservationRoom(){
        EmergencyCase selectedCase = null;
        do {
            ESController.printAllEmergencyCaseInObservationRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");

            selectedCase = ESController.selectCase(caseId);
        } while(selectedCase == null);

        Nurse nurse = UserController.getActiveNurse();
        selectedCase.setInitialScreeningNurse(nurse);

        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_OBSERVATION_ROOM);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE LOCATION TO OBSERVATION ROOM", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        selectedCase.setPatientStatus(PatientStatus.ADMITTED);
        AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UPDATE PATIENT STATUS TO ONGOING", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "NURSE");

        NursePatientActionsPage.setPatient(selectedCase.getPatient());
        NursePatientActionsPage.setEmergencyCase(selectedCase);
        ToPage(Globals.nursePatientActionsPage);

        refreshUi("Patient Discharged!");
    }

    public void refreshUi(String string){
        listView.clear();
        listView.setTitleHeader("Nurse Location Menu");
        listView.addItem(new TextView(this.canvas, "1. Triage Room - Proceed With Initial Screening", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Observation Room - Proceed With Patient Monitoring & Care Tasks", Color.GREEN));
        listView.addItem(new TextView(this.canvas, string, Color.GREEN));

        canvas.setRequireRedraw(true);
    }
}

package org.lucas.pages.nurse;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Nurse;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.InputValidator;


public class NurseLocationPage extends UiBase {
    private ListView listView;

    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        listView.setTitleHeader("Nurse Location Menu");
        listView.addItem(new TextView(this.canvas, "1. Triage Room - Proceed With Initial Screening", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Observation Room - Linked to CDSS", Color.GREEN));
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Location Menu1"); // Set the title header of the list view
        lv.attachUserInput("Triage Room", str -> proceedWithInitialScreening() );
        lv.attachUserInput("Observation Room", str -> ToPage(new NurseMainPage()));
    }

    public void proceedWithInitialScreening() {

        EmergencyCase selectedCase = null;
        do {
            ESController.printAllEmergencyCaseInWaitingRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
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

    public void refreshUi(String string){
        listView.clear();
        listView.setTitleHeader("Nurse Location Menu");
        listView.addItem(new TextView(this.canvas, "1. Triage Room - Proceed With Initial Screening", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Observation Room - Linked to CDSS", Color.GREEN));
        listView.addItem(new TextView(this.canvas, string, Color.GREEN));

        canvas.setRequireRedraw(true);
    }
}

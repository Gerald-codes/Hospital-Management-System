package org.lucas.pages.nurse;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
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
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Nurse Location Menu");
        lv.addItem(new TextView(this.canvas, "1. Triage Room - Proceed With Initial Screening", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Observation Room - Linked to CDSS", Color.GREEN));
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Nurse Location Menu"); // Set the title header of the list view
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
        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_TRIAGE_ROOM);
        selectedCase.setPatientStatus(PatientStatus.ONGOING);
        ESController.nurseInitialScreening(selectedCase);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        ESController.saveEmergencyCasesToFile();
        ToPage( new NurseLocationPage());
    }


}

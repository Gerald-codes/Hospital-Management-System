package org.lucas.pages.doctor;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Doctor;
import org.lucas.pages.nurse.NurseLocationPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.util.InputValidator;

public class DoctorLocationPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Doctor Location Menu");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Doctor Location Menu"); // Set the title header of the list view
        lv.attachUserInput("Examination Room - Proceed With Doctor Screening\n", str -> proceedWithDoctorScreening() );
        lv.attachUserInput("Trauma Room - Proceed With Immediate Response\n", str -> proceedWithImmediateResponse());
        lv.attachUserInput("Back\n", str -> ToPage(new DoctorMainPage()));
    }

    public void proceedWithDoctorScreening() {
        EmergencyCase selectedCase = null;
        do {
            ESController.printAllDoneEmergencyCaseInTriageRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
            System.out.println("Entered caseID: " + caseId);
            selectedCase = ESController.selectCase(caseId);
        } while(selectedCase == null);

        Doctor doctor = UserController.getActiveDoctor();
        selectedCase.setScreeningDoctor(doctor);
        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_EXAMINATION_ROOM);
        selectedCase.setPatientStatus(PatientStatus.ONGOING);
//        ESController.doctorScreening(selectedCase);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        ESController.saveEmergencyCasesToFile();
        ToPage( new DoctorLocationPage());
    }

    public void proceedWithImmediateResponse(){

    }
}

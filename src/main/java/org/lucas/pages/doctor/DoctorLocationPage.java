package org.lucas.pages.doctor;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Doctor;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;
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
        AuditManager manager = new AuditManager();
        lv.addItem(new TextView(this.canvas, "1. Examination Room - Proceed With Doctor Screening ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Trauma Room - Proceed With Immediate Response ", Color.GREEN));

        lv.setTitleHeader("Doctor Location Menu"); // Set the title header of the list view
        lv.attachUserInput("Examination Room", str -> proceedWithDoctorScreening(manager) );
        lv.attachUserInput("Trauma Room", str -> proceedWithImmediateResponse(manager));
    }

    public void proceedWithDoctorScreening(AuditManager manager) {
        EmergencyCase selectedCase = null;
        do {
            ESController.printAllDoneEmergencyCaseInTriageRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
            selectedCase = ESController.selectCase(caseId);
        } while(selectedCase == null);

        Doctor doctor = UserController.getActiveDoctor();
        selectedCase.setAssignedDoctor(doctor);
        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_EXAMINATION_ROOM);
        selectedCase.setPatientStatus(PatientStatus.ONGOING);
        ESController.doctorScreening(selectedCase,manager);
        System.out.println("======= Update Patient's Status =======");

        // Only showing first 3 statuses — you can extend this to show all
        for (int i = 0; i < 3 && i < PatientStatus.values().length; i++) {
            System.out.printf("%d. %s\n", i + 1, PatientStatus.values()[i]);
        }

        int statusChoice = InputValidator.getValidRangeIntInput(
                "Enter the number corresponding to the Patient status: ",
                3
        );

        PatientStatus selectedPatientStatus = PatientStatus.values()[statusChoice - 1];
        System.out.println("Selected Patient Status: " + selectedPatientStatus);
        selectedCase.setPatientStatus(selectedPatientStatus);
        ESController.saveEmergencyCasesToFile();

        ToPage( new DoctorLocationPage());
    }

    public void proceedWithImmediateResponse(AuditManager manager){
        EmergencyCase selectedCase = null;
        do {
            ESController.printAllEmergencyCaseInTraumaRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
            selectedCase = ESController.selectCase(caseId);

        } while(selectedCase == null);

        Doctor doctor = UserController.getActiveDoctor();
        selectedCase.setAssignedDoctor(doctor);
        selectedCase.setPatientStatus(PatientStatus.ONGOING);
        ESController.immediateResponse(selectedCase,manager);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        ESController.saveEmergencyCasesToFile();

        ToPage( new DoctorLocationPage());
    }

}

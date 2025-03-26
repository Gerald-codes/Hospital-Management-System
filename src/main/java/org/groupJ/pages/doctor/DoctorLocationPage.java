package org.groupJ.pages.doctor;

<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorLocationPage.java
import org.groupJ.Emergency.EmergencyCase;
import org.groupJ.Emergency.enums.PatientLocation;
import org.groupJ.Emergency.enums.PatientStatus;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Doctor;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;
=======
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
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorLocationPage.java

public class DoctorLocationPage extends UiBase {
    private ListView listView;
    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorLocationPage.java
        lv.addItem(new TextView(this.canvas, "1. Examination Room - Proceed With Doctor Screening ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Trauma Room - Proceed With Immediate Response ", Color.GREEN));

=======
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorLocationPage.java
        lv.setTitleHeader("Doctor Location Menu"); // Set the title header of the list view
        lv.attachUserInput("Examination Room - Proceed With Doctor Screening\n", str -> proceedWithDoctorScreening() );
        lv.attachUserInput("Trauma Room - Proceed With Immediate Response\n", str -> proceedWithImmediateResponse());
        lv.attachUserInput("Back\n", str -> ToPage(new DoctorMainPage()));
    }

    public void proceedWithDoctorScreening() {

        EmergencyCase selectedCase;
        do {
            ESController.printAllDoneEmergencyCaseInTriageRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
            System.out.println("Entered caseID: " + caseId);
            selectedCase = ESController.selectCase(caseId);
        } while(selectedCase == null);

        Doctor doctor = UserController.getActiveDoctor();
<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorLocationPage.java
        selectedCase.setAssignedDoctor(doctor);
        AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "UPDATE ASSIGNED DOCTOR", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "DOCTOR");

=======
        selectedCase.setScreeningDoctor(doctor);
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorLocationPage.java
        selectedCase.setLocation(PatientLocation.EMERGENCY_ROOM_EXAMINATION_ROOM);
        AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "UPDATE PATIENT LOCATION TO EXAMINATION ROOM", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "DOCTOR");

        selectedCase.setPatientStatus(PatientStatus.ONGOING);
<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorLocationPage.java
        AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "UPDATE PATIENT STATUS TO ONGOING", String.valueOf(selectedCase.getCaseID()), "SUCCESS", "DOCTOR");

        ESController.doctorScreening(selectedCase);
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
        selectedCase.setPatientStatus(selectedPatientStatus);
        ESController.saveEmergencyCasesToFile();

        refreshUi("Selected Patient Status: " + selectedPatientStatus);
    }

    public void proceedWithImmediateResponse(){
        Doctor doctor = UserController.getActiveDoctor();
        if (!doctor.isSurgicalApproved()){
            refreshUi("Doctor is not surgical approve, find another doctor");
        }
        EmergencyCase selectedCase;
        do {
            ESController.printAllEmergencyCaseInTraumaRoom();
            int caseId = InputValidator.getValidIntInput("Enter Case ID : ");
            selectedCase = ESController.selectCase(caseId);

        } while(selectedCase == null);

        selectedCase.setAssignedDoctor(doctor);
        selectedCase.setPatientStatus(PatientStatus.ONGOING);
        ESController.immediateResponse(selectedCase);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        ESController.saveEmergencyCasesToFile();

        refreshUi("Patient Treated and Emergency Procedures Completed.");
    }

    public void refreshUi(String string){
        listView.clear();
        listView.setTitleHeader("Doctor Location Menu"); // Set the title header of the list view
        listView.addItem(new TextView(this.canvas, "1. Examination Room - Proceed With Doctor Screening ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Trauma Room - Proceed With Immediate Response ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, string, Color.GREEN));

        canvas.setRequireRedraw(true);
=======
//        ESController.doctorScreening(selectedCase);
        selectedCase.setPatientStatus(PatientStatus.DONE);
        ESController.saveEmergencyCasesToFile();
        ToPage( new DoctorLocationPage());
    }

    public void proceedWithImmediateResponse(){

>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorLocationPage.java
    }
}

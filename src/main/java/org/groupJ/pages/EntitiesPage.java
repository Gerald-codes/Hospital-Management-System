package org.groupJ.pages;

import org.groupJ.Emergency.EmergencyCase;
import org.groupJ.Emergency.EmergencyCase_Dispatch;
import org.groupJ.Globals;
import org.groupJ.controllers.AppointmentController;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.MedicationController;
import org.groupJ.controllers.UserController;
import org.groupJ.core.ClinicalGuideline;
import org.groupJ.models.*;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;

import java.util.List;


public class EntitiesPage extends UiBase {
    private ListView listView;
    public static UserController userController = new UserController();


    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        return listView; // Return the list view
    }

    @Override
    public void OnViewCreated(View parentView) {
        ESController.loadEmergencyCaseFromFile();
        ESController.loadEmergencyDispatchCaseFromFile();
        listView = (ListView) parentView; // Cast the parent view to a list view
        listView.setTitleHeader("Welcome to our application"); // Set the title header of the list view
        listView.addItem(new TextView(this.canvas, "1. Display all Patients (Include EHR)", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Display all Doctors", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. Display all Nurses ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "4. Display all Paramedics ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "5. Display all Medications ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "6. Display all Clinical Guidelines ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "7. Display all Appointments ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "8. Display all Emergency Cases ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "9. Display all Dispatch Cases ", Color.GREEN));


        listView.attachUserInput("Display all Patients (Include EHR)", str -> displayAllPatients());
        listView.attachUserInput("Display all Doctors", str -> displayAllDoctors());
        listView.attachUserInput("Display all Nurses", str -> displayAllNurse());
        listView.attachUserInput("Display all Paramedics", str -> displayAllParamedics());
        listView.attachUserInput("Display all Medications", str -> displayAllMedications());
        listView.attachUserInput("Display all Clinical Guidelines", str -> displayAllClinicalGuidelines());
        listView.attachUserInput("Display all Appointments", str -> displayAllAppointments());
        listView.attachUserInput("Display all Emergency Cases", str -> displayAllEmergencyCases());
        listView.attachUserInput("Display all Dispatch Cases", str -> displayAllDispatchCases());

        listView.attachUserInput("Display Entities", str -> ToPage(Globals.entitiesPage));
        canvas.setRequireRedraw(true);
    }



    private void displayAllPatients(){

        System.out.println("======================= Displaying Patients =======================\n");
        for (Patient patient : UserController.getAvailablePatients()) {
            patient.displayPatientInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllDoctors(){

        System.out.println("======================= Displaying Doctors =======================\n");
        for (Doctor doctor : UserController.getAvailableDoctors()) {
            doctor.displayDoctorInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllNurse(){
        System.out.println("======================= Displaying Nurse =======================\n");
        for (Nurse nurse : UserController.getAvailableNurses()) {
            nurse.displayNurseInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllParamedics(){

        System.out.println("======================= Displaying Paramedics =======================\n");
        for (Paramedic paramedic : UserController.getAvailableParamedics()) {
            paramedic.displayParamedicInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllMedications(){
        System.out.println("======================= Displaying Medications =======================");
        for (Medication medication : MedicationController.getAvailableMedications()) {
            medication.displayMedicationInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllClinicalGuidelines() {
        System.out.println("======================= Displaying Clinical Guidelines =======================");
        for (ClinicalGuideline clinicalGuideline : List.copyOf(ClinicalGuideline.generateClinicalGuideLine())) {
            clinicalGuideline.displayClinicalGuidelineInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllAppointments(){
        System.out.println("======================= Displaying Appointments =======================");
        for (Appointment appointment : AppointmentController.getAppointments()) {
            appointment.displayAppointmentInfo();
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllEmergencyCases(){
        System.out.println("======================= Displaying Emergency Cases =======================");
        for (EmergencyCase emergencyCase : ESController.getAllCases()) {
            emergencyCase.displayEmergencyCaseInfo();
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void displayAllDispatchCases(){
        System.out.println("======================= Displaying Dispatch Cases =======================");
        for (EmergencyCase_Dispatch dispatch : ESController.getAllDispatchCases()) {
            dispatch.displayDispatchEmergencyCaseInfo();
            System.out.println();
        }
        InputValidator.getValidStringInput("Enter 0 to go back: ");
        refreshUi();
    }

    private void refreshUi(){
        listView.clear();
        listView.addItem(new TextView(this.canvas, "1. Display all Patients (Include EHR)", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Display all Doctors", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. Display all Nurses ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "4. Display all Paramedics ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "5. Display all Medications ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "6. Display all Clinical Guidelines ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "7. Display all Appointments ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "8. Display all Emergency Case ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "9. Display all Dispatch Case ", Color.GREEN));

        canvas.setRequireRedraw(true);

    }
}

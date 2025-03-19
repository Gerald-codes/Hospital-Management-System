package org.lucas;

import org.lucas.controllers.*;
import org.lucas.models.Medication;
import org.lucas.models.Patient;
import org.lucas.ui.framework.ApplicationContext;
import org.lucas.ui.framework.Canvas;


public class Main {

    public static void main(String[] args) {
        //UserController.getAvailablePatients();
        //UserController.printAllPatients();
        //UserController.getAvailableDoctors();
        //UserController.printAvailableDoctors();
        //Globals.appointmentController.generateRandomAppointmentData(8);
        // initialise medicines
        //create a new canvas
        var canvas = new Canvas();
        ApplicationContext applicationContext = new ApplicationContext(canvas);
        //Initializations
        // Initialize Medicine: This line needs to stay uncommented
        MedicationController.getAvailableMedications();

        // Uncomment these to generate dummy users
        //MedicationController.getAvailableMedications();
        //UserController.getAvailablePatients();
        //UserController.generateDummyUsers();

        //MedicationController.populateExampleMedication();

        //UserController.generateDummyUsers();
        //UserController.getAvailablePatients();
        //UserController.printAllPatients();
        //UserController.getAvailableNurses();
        //UserController.generateDummyNurses();
        applicationContext.startApplication(Globals.landingPage);
    }
}
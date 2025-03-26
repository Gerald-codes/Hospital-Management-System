package org.groupJ;

import org.groupJ.controllers.*;
import org.groupJ.ui.framework.ApplicationContext;
import org.groupJ.ui.framework.Canvas;


public class Main {

    /**
     * Main method to start the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        //create a new canvas
        var canvas = new Canvas();
        ApplicationContext applicationContext = new ApplicationContext(canvas);

        //Initialize Medicine: This line needs to stay uncommented
        MedicationController.getAvailableMedications();

        // Uncomment these to generate dummy medications
        //MedicationController.getAvailableMedications();
        //MedicationController.populateExampleMedication();

        // Uncomment these to generate dummy Patients (getAvailableMedications needs to be uncommented)
        //UserController.getAvailablePatients();
        //UserController.generateDummyUsers();

        //Uncomment this to generate dummy appointments (getAvailableMedications & getAvailablePatients needs to be uncommented)
        //Globals.appointmentController.generateRandomAppointmentData(12);

        applicationContext.startApplication(Globals.landingPage);
    }
}
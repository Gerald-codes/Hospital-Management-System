package org.groupJ.pages;

import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.enums.UserType;
import org.groupJ.pages.paramedic.ParamedicMenuPage;
import org.groupJ.pages.patient.PatientMainPage;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.util.InputValidator;

public class LoginPage extends UiBase { // This is the class that represents the login page

    public static UserController userController = new UserController();

    private String loginMessage;

    public LoginPage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    @Override
    public View OnCreateView() { // This is the method that is called when the view is created
        // Create a new list view with the canvas and color
        return new ListView(this.canvas, Color.GREEN); // Return the list view
    }

    @Override
    public void OnViewCreated(View parentView) { // This is the method that is called when the view is created

        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to the Hospital Management System "); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, loginMessage, Color.GREEN));
        lv.addItem(new TextView(this.canvas, "To use our system, please kindly login by pressing 1", Color.GREEN)); // Create a new text view with the message
        ESController.loadEmergencyCaseFromFile();
        ESController.loadEmergencyDispatchCaseFromFile();
        lv.attachUserInput("Login ", x -> { // Attach the user input to the list view
            String username = InputValidator.getValidStringInput("Enter your username: ");
            String password = InputValidator.getValidStringInput("Enter your password: ");

            //Check credential for Doctors / Patient
            if (UserType.DOCTOR == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(Globals.doctorMainPage);
            } else if (UserType.NURSE == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(Globals.nurseMenuPage);
            } else if (UserType.PATIENT == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(Globals.patientMainPage);
            } else if (UserType.PARAMEDIC == userController.authenticate(username, password)){
                System.out.println("Login successful");
                ToPage(Globals.paramedicMenuPage);
            }else { System.out.println("Invalid username or password!"); }

            canvas.setRequireRedraw(true);
            });
        canvas.setRequireRedraw(true);
    }

}

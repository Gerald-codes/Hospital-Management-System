package org.lucas.pages;

import org.lucas.controllers.UserController;
import org.lucas.models.enums.UserType;
import org.lucas.pages.doctor.DoctorMainPage;
import org.lucas.pages.nurse.NurseMenuPage;
import org.lucas.pages.patient.PatientMainPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.Scanner;

/**
 * Represents the login page of the Telemedicine Integration System.
 * This class extends {@link UiBase} and provides the UI elements and logic for user authentication.*/
public class LoginPage extends UiBase { // This is the class that represents the login page

    /**
     * The {@link UserController} instance used for user authentication.
     * This field is static, meaning there's only one UserController shared across all LoginPage instances.*/
    public static UserController userController = new UserController();

    /**
     * Called when the login page's view is created.
     * Creates a {@link ListView} to hold the login page's UI elements.
     * @return A new {@link ListView} instance representing the login page's view.
     * @Override*/
    @Override
    public View OnCreateView() { // This is the method that is called when the view is created
        ListView lv = new ListView(this.canvas, Color.GREEN); // Create a new list view with the canvas and color
        return lv; // Return the list view
    }

    /**
     * Called after the view has been created and attached to the UI.
     * Populates the view with UI elements such as the title header and login prompt with user input handling for the login process.
     * @param parentView The parent {@link View} to which the login page's UI elements are added.  This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) { // This is the method that is called when the view is created

        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to the Hospital Management System "); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "To use our system, please kindly login by pressing 1", Color.GREEN)); // Create a new text view with the message

        lv.attachUserInput("Login ", x -> { // Attach the user input to the list view
            Scanner scanner = new Scanner(System.in); // Create a new scanner object
            System.out.println("Enter your username: ");
            String username = scanner.nextLine(); // Get the username from the user
            System.out.println("Enter your password: ");
            String password = scanner.nextLine(); // Get the password from the user

             //Check credential for Doctors / Patient
            if (UserType.DOCTOR == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(new DoctorMainPage());
            } else if (UserType.NURSE == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(new NurseMenuPage());

            } else if (UserType.PATIENT == userController.authenticate(username, password)) {
                System.out.println("Login successful!");
                ToPage(new PatientMainPage());
            } else { System.out.println("Invalid username or password!"); }

            canvas.setRequireRedraw(true);
            });
        canvas.setRequireRedraw(true);
    }

}

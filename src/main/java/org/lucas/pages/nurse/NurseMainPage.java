package org.lucas.pages.nurse;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.models.Medication;
import org.lucas.models.Patient;
import org.lucas.models.Symptoms;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.InputHelper;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseMainPage extends UiBase {
    public static UserController userController = new UserController();
    public static Patient patient;

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        return lv;
    }

    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to the Hospital Management System | Nurse Portal"); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information and perform actions ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Feedback Mechanism ", Color.GREEN));

        List<Patient> patients = userController.getPatients();

        lv.addItem(new TextView(this.canvas, "Now Displaying All Patients", Color.GREEN));
        int index = 0;
        for (Patient patient : patients) {
            ListView rowView = new ListView(this.canvas, Color.GREEN, ListViewOrientation.HORIZONTAL);

            rowView.addItem(new TextView(this.canvas, String.format("%-3d", index), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getId(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getName(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getDateOfBirth(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getGender(), Color.GREEN));
            rowView.addItem(new TextView(this.canvas, patient.getPatientSpecificFactor(), Color.GREEN));

            lv.addItem(rowView);
            index += 1;
        }

        // When selecting "Select Patient Index"
        lv.attachUserInput("Select Patient Index", str -> {
            int selectedIndex = InputHelper.getValidIndex("Select Patient index", patients);
            patient = patients.get(selectedIndex);

            // Show options for the selected patient
            System.out.println("\nSelected Patient: " + patient.getName());
            System.out.println("1. Perform Patient Actions");
            int choice = InputHelper.getValidIndex("Select an option", 1, 1);
            if (choice == 1) {
                // Navigate to patient actions page
                NursePatientActionsPage.setPatient(patient);
                ToPage(Globals.nursePatientActionsPage);
            }
        });

        lv.attachUserInput("Feedback Mechanism", str -> {
            ToPage(Globals.feedbackPage);});
        canvas.setRequireRedraw(true);
    }
}
package org.groupJ.pages.nurse;

import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.UserController;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

import java.util.List;
import java.util.Objects;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseMenuPage extends UiBase {
    private String errorMessage = "";
    private ListView listView;

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        this.listView = lv;
        return lv;
    }

    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) {
        listView.clear();
        listView = (ListView) parentView; // Cast the parent view to a list view
        listView.setTitleHeader("Welcome to the Hospital Management System | Welcome Back " + UserController.getActiveNurse().getName());
        listView.addItem(new TextView(this.canvas, "1. Pharmacy", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Emergency ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. Feedback Mechanism ", Color.GREEN));

        listView.attachUserInput("Pharmacy", str -> {
            if (!Objects.equals(UserController.getActiveNurse().getDepartment(), "Pharmacy")) {
                setErrorMessage("Current Nurse is not AUTHORIZED to access this page");
                AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UNAUTHORIZED ACCESS TO PHARMACY", "SYSTEM", "FAILED", "NURSE");
            }
            else{
                ToPage(Globals.pharmacyPage);
            }
        });
        listView.attachUserInput("Emergency", str -> {
            if (!Objects.equals(UserController.getActiveNurse().getDepartment(), "Emergency")) {
                setErrorMessage("Current Nurse is not AUTHORIZED to access this page");
                AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "UNAUTHORIZED ACCESS TO EMERGENCY", "SYSTEM", "FAILED", "NURSE");
            }
            else{
                ToPage(Globals.nurseEmergencyMenuPage);
            }
        });
        listView.attachUserInput("Feedback Mechanism", str -> ToPage(Globals.feedbackPage));
    }

    /**
     * Sets the error message and refreshes the UI.
     *
     * @param message The error message to display
     */
    private void setErrorMessage(String message) {
        this.errorMessage = message;
        refreshUI();
    }

    /**
     * Refreshes the UI by clearing and rebuilding the list view.
     */
    private void refreshUI() {
        listView.clear();
        listView.setTitleHeader("Welcome to the Hospital Management System | Welcome Back " + UserController.getActiveNurse().getName());
        listView.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "2. Feedback Mechanism ", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "3. Pharmacy", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "4. Emergency ", Color.GREEN));

        // Only add error message if it's not empty
        if (!errorMessage.isEmpty()) {
            listView.addItem(new TextView(this.canvas, errorMessage, Color.RED));
        }

        canvas.setRequireRedraw(true);
    }
}
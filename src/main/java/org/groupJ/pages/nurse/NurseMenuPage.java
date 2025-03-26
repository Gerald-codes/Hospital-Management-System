package org.groupJ.pages.nurse;

import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.UserController;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

import java.util.Objects;

public class NurseMenuPage extends UiBase {
    private String errorMessage = "";
    private ListView listView;
    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        listView = (ListView) parentView; // Cast the parent view to a list view
        listView.setTitleHeader("Welcome to the Hospital Management System | Welcome Back " + UserController.getActiveNurse().getName()); // Set the title header of the list view

        listView.attachUserInput("View List of Patient", str -> ToPage(Globals.nurseMainPage));
        listView.attachUserInput("Feedback Mechanism", str -> ToPage(Globals.feedbackPage));
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

        canvas.setRequireRedraw(true);
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
package org.lucas.pages.nurse;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.pages.doctor.FeedbackPage;

import org.lucas.pages.pharmacy.PharmacyPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class NurseMenuPage extends UiBase {

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
        lv.setTitleHeader("Welcome to the Hospital Management System | Welcome Back " + UserController.getActiveNurse().getName()); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Feedback Mechanism ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. Pharmacy", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. Emergency ", Color.GREEN));

        lv.attachUserInput("View List of Patient", str -> ToPage(Globals.nurseMainPage));
        lv.attachUserInput("Feedback Mechanism", str -> ToPage(Globals.feedbackPage));
        lv.attachUserInput("Pharmacy ", str -> ToPage(Globals.pharmacyPage));
        lv.attachUserInput("Emergency", str -> ToPage(Globals.nurseEmergencyMenuPage));

        canvas.setRequireRedraw(true);
    }

}
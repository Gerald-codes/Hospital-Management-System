package org.lucas.pages.admin;

import org.lucas.controllers.UserController;
import org.lucas.pages.doctor.FeedbackPage;
import org.lucas.pages.nurse.NurseMainPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

public class AdminPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Admin Main Page");
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
        lv.setTitleHeader("Welcome to Telemedicine Integration System | Welcome Back " + UserController.getActiveAdmin().getName()); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. Walk-In Emergency", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Dispatch Call", Color.GREEN));

        lv.attachUserInput(" Walk-In Emergency", str -> ToPage(new NurseMainPage()));
        lv.attachUserInput("Dispatch Call", str -> ToPage(new FeedbackPage()));

        canvas.setRequireRedraw(true);
    }
}

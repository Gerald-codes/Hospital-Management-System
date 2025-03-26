package org.groupJ.pages.nurse;

import org.groupJ.Globals;
import org.groupJ.controllers.UserController;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

public class NurseMenuPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        return lv;
    }

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
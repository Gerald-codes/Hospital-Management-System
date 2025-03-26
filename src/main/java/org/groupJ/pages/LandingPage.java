package org.groupJ.pages;

import org.groupJ.Globals;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

/**
 * Represents the login page of the Hospital Management System.
 * This class extends {@link UiBase} and provides the UI elements and logic for user authentication.*/
public class LandingPage extends UiBase {

    @Override
    public View OnCreateView() { // This is the method that is called when the view is created
        // Create a new list view with the canvas and color
        return new ListView(this.canvas, Color.GREEN); // Return the list view
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to our application"); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. Pharmacy", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Teleconsultation", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. CDSS ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. Emergency Services ", Color.GREEN));

//        lv.attachUserInput("Pharmacy", str -> ToPage(Globals.loginPage));
//        lv.attachUserInput("Teleconsultation", str -> ToPage(Globals.loginPage));
//        lv.attachUserInput("CDSS", str -> ToPage(Globals.loginPage));
//        lv.attachUserInput("Emergency Services", str -> ToPage(Globals.loginPage));
        lv.attachUserInput("Pharmacy", str -> ToPage(new LoginPage("Logging into Pharmacy")));
        lv.attachUserInput("Teleconsultation", str -> ToPage(new LoginPage("Logging into Teleconsultation")));
        lv.attachUserInput("CDSS", str -> ToPage(new LoginPage("Logging into CDSS")));
        lv.attachUserInput("Emergency Services", str -> ToPage(new LoginPage("Logging into Emergency Services")));
        canvas.setRequireRedraw(true);
    }
}

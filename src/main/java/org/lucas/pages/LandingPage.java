package org.lucas.pages;
import org.lucas.pages.doctor.*;
import org.lucas.pages.patient.*;
import org.lucas.pages.pharmacy.*;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

/**
 * Represents the login page of the Telemedicine Integration System.
 * This class extends {@link UiBase} and provides the UI elements and logic for user authentication.*/
public class LandingPage extends UiBase {

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
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to our application"); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. Pharmacy", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Teleconsultation", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. CDSS ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. Emergency Services ", Color.GREEN));
        lv.attachUserInput("Pharmacy", str -> ToPage(new LoginPage()));
        lv.attachUserInput("Teleconsultation", str -> ToPage(new LoginPage()));
        lv.attachUserInput("CDSS", str -> ToPage(new LoginPage()));
        lv.attachUserInput("Emergency Services", str -> ToPage(new LoginPage()));
        canvas.setRequireRedraw(true);
    }
}

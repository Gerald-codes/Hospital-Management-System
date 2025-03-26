package org.lucas.pages.pharmacy;

import org.lucas.Globals;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

public class PharmacyPage extends UiBase {

    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.GREEN); // Return the list view
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to the Pharmacy"); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. Dispensary - View Outstanding Prescription to settle. ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Medication - View list of Medication. ", Color.GREEN));

        lv.attachUserInput("Dispensary", str -> ToPage(Globals.dispensaryPage));
        lv.attachUserInput("Medication", str -> ToPage(Globals.medicationPage));
        canvas.setRequireRedraw(true);
    }
}

package org.groupJ.pages.pharmacy;

import org.groupJ.Globals;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

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

package org.lucas.pages.admin;

import org.lucas.controllers.UserController;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

public class AdminWalkInPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Admin Walk In Page");
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
        lv.setTitleHeader("=========== Emergency Case Management ==========="); // Set the title header of the list view

        lv.addItem(new TextView(this.canvas, "1. Create New Emergency Case", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. Update Emergency Case Status", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. Update Patient Location", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. View Active Emergency Cases", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "5. Back to Admin Menu", Color.GREEN));


        lv.attachUserInput(" Create New Emergency Case", str -> ToPage(new AdminWalkInPage()));
        lv.attachUserInput(" Back to Admin Menu", str -> ToPage(new AdminPage()));
//        lv.attachUserInput("Dispatch Call", str -> ToPage(new AdminDispatchPage()));
//
        canvas.setRequireRedraw(true);
//        System.out.println("\n=== Emergency Case Management ===");
//        System.out.println("1. Register New Emergency Case");
//        System.out.println("2. Update Emergency Case Status");
//        System.out.println("3. Update Screening by Doctor");
//        System.out.println("4. Add Emergency Procedures");
//        System.out.println("5. Update Patient Location");
//        System.out.println("6. View Active Emergency Cases");
//        System.out.println("7. Back to Main Menu");
//        System.out.print("Enter your choice: ");
    }
}

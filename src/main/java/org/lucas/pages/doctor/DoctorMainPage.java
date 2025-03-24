package org.lucas.pages.doctor;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.models.Nurse;
import org.lucas.pages.nurse.NurseDispatchMenuPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class DoctorMainPage extends UiBase {

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/
    private ListView listView;
    @Override
    public View OnCreateView() {
        listView = new ListView(this.canvas, Color.GREEN);
        listView.setTitleHeader("Main");
        return listView;
    }

    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to Telemedicine Integration System | Welcome Back " + UserController.getActiveDoctor().getName()); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. View Appointment - To view new / scheduled appointments for teleconsultation ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. Feedback Mechanism - Provide your feedback on Clinical Guidelines  ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. Locations - To enter and proceed with Action  ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. View All Emergency Cases", Color.GREEN));


        lv.attachUserInput("View List of Patient", str -> ToPage(new PatientInfoPage()));
        lv.attachUserInput("View Appointment", str -> ToPage(new ViewAppointmentsPage()));
        lv.attachUserInput("Feedback Mechanism", str -> ToPage(new FeedbackPage()));
        lv.attachUserInput("Locations", str -> ToPage(new DoctorLocationPage()));
        lv.attachUserInput("View All Emergency Cases ", str -> viewAllEmergencyCases());


        canvas.setRequireRedraw(true);
    }

    private void viewAllEmergencyCases() {
        List<EmergencyCase> allcases = ESController.getAllCases();
        listView.clear();
        listView.addItem(new TextView(this.canvas, "============ ALL EMERGENCY CASE ============ ", Color.CYAN, TextStyle.BOLD));

        for (EmergencyCase ec : allcases) {
            listView.addItem(new TextView(this.canvas, "---------------------------------", Color.CYAN, TextStyle.BOLD));
            listView.addItem(new TextView(this.canvas, "Case ID: " + ec.getCaseID(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Patient Name: " + ec.getPatient().getName(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Location: " + ec.getLocation(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Chief Complaint: " + ec.getChiefComplaint(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Arrival Mode: " + ec.getArrivalMode(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Arrival Date & Time: " + ec.getArrivalDateTime(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Triage Level: " + ec.getTriageLevel(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Patient Status: " + ec.getPatientStatus(), Color.CYAN));
            listView.addItem(new TextView(this.canvas, "Urgent: " + (ec.isUrgent() ? "YES" : "NO"), (ec.isUrgent() ? Color.RED : Color.CYAN), TextStyle.BOLD));

            if (ec instanceof EmergencyCase_Dispatch dispatchCase) {
                listView.addItem(new TextView(this.canvas, "====== Dispatch Info ======", Color.BLUE, TextStyle.BOLD));
                listView.addItem(new TextView(this.canvas, "Vehicle ID: " + dispatchCase.getDispatchInfo().getVehicleId(), Color.BLUE));

                listView.addItem(new TextView(this.canvas, "Medivac Members:", Color.BLUE));
                for (Nurse nurse : dispatchCase.getDispatchInfo().getMedivacMembers()) {
                    listView.addItem(new TextView(this.canvas, "  - " + nurse.getName(), Color.CYAN));
                }

                listView.addItem(new TextView(this.canvas, "Equipment:", Color.BLUE));
                for (String equipment : dispatchCase.getDispatchInfo().getEquipment()) {
                    listView.addItem(new TextView(this.canvas, "  - " + equipment, Color.CYAN));
                }

                listView.addItem(new TextView(this.canvas, "Dispatch Location: " + dispatchCase.getDispatchInfo().getDispatchLocation(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Dispatch Arrival Time: " + dispatchCase.getDispatchArrivalTime(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Time of Call: " + dispatchCase.getTimeOfCall(), Color.BLUE));
                listView.addItem(new TextView(this.canvas, "Response Time: " + dispatchCase.getResponseTime().toMinutes() + " minutes", Color.BLUE));
            }

            listView.addItem(new TextView(this.canvas, "---------------------------------\n", Color.CYAN, TextStyle.BOLD));
        }

        canvas.setRequireRedraw(true);

    }
}
package org.groupJ.pages.doctor;

<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorMainPage.java
import org.groupJ.Emergency.EmergencyCase;
import org.groupJ.Emergency.EmergencyCase_Dispatch;
import org.groupJ.Globals;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Nurse;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.TextStyle;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;

import java.util.List;

public class DoctorMainPage extends UiBase {

    private ListView listView;
=======
import org.lucas.controllers.UserController;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class DoctorMainPage extends UiBase {

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/

>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorMainPage.java
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to Telemedicine Integration System | Welcome Back " + UserController.getActiveDoctor().getName()); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. View List of Patient - To view patient information ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. View Appointment - To view new / scheduled appointments for teleconsultation ", Color.GREEN));
<<<<<<< HEAD:src/main/java/org/groupJ/pages/doctor/DoctorMainPage.java
        lv.addItem(new TextView(this.canvas, "3. Locations - To enter and proceed with Action  ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. View All Emergency Cases", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "5. Feedback Mechanism - Provide your feedback on Clinical Guidelines  ", Color.GREEN));

        lv.attachUserInput("View List of Patient", str -> ToPage(Globals.patientInfoPage));
        lv.attachUserInput("View Appointment", str -> ToPage(Globals.viewAppointmentsPage));
        lv.attachUserInput("Locations", str -> ToPage(Globals.doctorLocationPage));
        lv.attachUserInput("View All Emergency Cases ", str -> viewAllEmergencyCases());
        lv.attachUserInput("Feedback Mechanism", str -> ToPage(Globals.feedbackPage));

        canvas.setRequireRedraw(true);
    }

    private void viewAllEmergencyCases() {
        List<EmergencyCase> allCases = ESController.getAllCases();
        listView.clear();
        listView.addItem(new TextView(this.canvas, "============ ALL EMERGENCY CASE ============ ", Color.CYAN, TextStyle.BOLD));

        for (EmergencyCase ec : allCases) {
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
=======
        lv.addItem(new TextView(this.canvas, "3. Feedback Mechanism - Provide your feedback on Clinical Guidelines  ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "4. Locations - View the locations of doctor ", Color.GREEN));

        lv.attachUserInput("View List of Patient", str -> ToPage(new PatientInfoPage()));
        lv.attachUserInput("View Appointment", str -> ToPage(new ViewAppointmentsPage()));
        lv.attachUserInput("Feedback Mechanism", str -> ToPage(new FeedbackPage()));
        lv.attachUserInput("Locations", str -> ToPage(new DoctorLocationPage()));

>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/pages/doctor/DoctorMainPage.java
        canvas.setRequireRedraw(true);
    }
}
package org.groupJ.pages.patient;

import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Appointment;
import org.groupJ.models.enums.AppointmentStatus;
import org.groupJ.ui.framework.*;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.ListViewOrientation;
import org.groupJ.ui.framework.views.TextView;

import java.util.List;

public class BillingPage extends UiBase {
    public static List<Appointment> appointments;
    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.BLUE);
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView listView = (ListView) parentView;
        listView.attachUserInput("Select Appointment Index", str->{

            int selectedIndex = InputHelper.getValidIndex("Select Appointment index", appointments);
            Appointment selectedAppointment = appointments.get(selectedIndex);

            System.out.println("Select options: 1. Pay Bill (simulated) | 2. View Appointment Summary | 3. Generate Itemised Bill");
            int selectedIndex1 = InputHelper.getValidIndex("Select Option", 1, 3);

            switch(selectedIndex1) {
                case 1:
                    System.out.println("Processing payment (simulated)...");
                    selectedAppointment.getBilling().setPaid(true);
                    Globals.appointmentController.saveAppointmentsToFile();
                    AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER PAID BILL", "SYSTEM", "SUCCESS", "PATIENT");
                    System.out.println("Payment success!!");
                    System.out.println("Press Enter to exit...");
                    refreshUi(listView);
                    break;
                case 2:
                    System.out.println("Viewing appointment summary...");
                    ViewAppointmentSummaryPage.setAppointment(selectedAppointment);
                    AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER VIEWED APPOINTMENT SUMMARY", "SYSTEM", "SUCCESS", "PATIENT");
                    ToPage(new ViewAppointmentSummaryPage());
                    break;
                case 3:
                    System.out.println("Generating Itemised Bill...");
                    String bill = selectedAppointment.getBilling().generateBillString();
                    AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER GENERATED BILL", "SYSTEM", "SUCCESS", "PATIENT");
                    System.out.println(bill);
                    System.out.println("Press Enter to exit...");
                    break;
            }
        });
        refreshUi(listView);
    }

    private void refreshUi(ListView listView) {
        listView.clear();
        // If appointments is null, retrieve the concluded appointments for the active patient
        appointments = Globals.appointmentController.getAppointments()
                .stream()
                .filter(appointment -> appointment.getPatient().getId().equals(UserController.getActivePatient().getId()))
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED || appointment.getAppointmentStatus() == AppointmentStatus.DISPENSED)
                .toList();

        if (appointments.isEmpty()) {
            listView.addItem(new TextView(this.canvas, "No concluded appointments found for this patient.", Color.RED, TextStyle.BOLD));
        } else {
                listView.setTitleHeader("Billing Details");
                for (int i = 0; i < appointments.size(); i++) {
                    Appointment appointment = appointments.get(i);
                    if (appointment.getBilling() == null || appointment.getBilling().isPaid()) {
                        continue;
                    }
                    Color color = getItemColor(appointment.getBilling().isPaid());
                    ListView rowView = new ListView(this.canvas, Color.BLUE, ListViewOrientation.HORIZONTAL);

                    // Display billing details for each appointment
                    rowView.addItem(new TextView(this.canvas,
                            i + ". | Appointment Date: " + appointment.getAppointmentTime().toLocalDate(),
                            color));

                    rowView.addItem(new TextView(this.canvas,
                            " | Amount Due: $" + String.format("%.2f",
                                    appointment.getBilling().getBillAmount()),
                            color, TextStyle.BOLD));

                    rowView.addItem(new TextView(this.canvas,
                            " | Payment Status: " + (appointment.getBilling().isPaid() ? "Paid" : "Unpaid"),
                            color, TextStyle.BOLD));
                    listView.addItem(rowView);
                }
        }
        canvas.setRequireRedraw(true);
    }

    private Color getItemColor(boolean status) {
        return status ? Color.GREEN : Color.RED;
    }

    @Override
    public void OnBackPressed() {
        super.OnBackPressed();
        appointments = null;
    }
}

package org.lucas.pages.pharmacy;

import org.lucas.Globals;
import org.lucas.controllers.MedicationController;
import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.Billing;
import org.lucas.models.Medication;
import org.lucas.models.Prescription;
import org.lucas.models.enums.AppointmentStatus;
import org.lucas.models.enums.UserType;
import org.lucas.ui.framework.*;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.views.TextView;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DispensaryPage extends UiBase {
    ListView listView;
    List<Appointment> appointments;

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.GREEN
        );
        if (UserController.getActiveUserType() != UserType.NURSE) {
            this.OnBackPressed();
            return lv;
        }
        lv.setTitleHeader("Dispense Medication for Patients | " + UserController.getActiveNurse().getName());
        this.listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView listView = (ListView) parentView;
        appointments = Globals.appointmentController.getAppointments()
                .stream()
                .filter(x -> x.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                .toList();

        listView.attachUserInput("Select index ", str-> selectAppointmentIndex(appointments));
        refreshUi();
        canvas.setRequireRedraw(true);
    }

    private void refreshUi() {
        listView.clear();
        // loop through the appointments and display them
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientName = appointment.getPatient().getName();
            List<String> medicationNames = new ArrayList<>();
            for (Medication med : appointment.getBilling().getPrescription().getMedication()) {
                medicationNames.add(med.getMedicationName());
            }
            String medicationsString = String.join(", ", medicationNames);
            //String formattedTime = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String formattedDate = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Boolean isPaid = appointment.getBilling().isPaid();

            // error handling for dirty json data
            if(appointment.getAppointmentStatus() == null){
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
            }
            final int indexWidth = 3;
            final int nameWidth = 10;
            final int medicationsWidth = 35;
            final int dateWidth = 10;
            //final int timeWidth = 5;
            final int isPaidWidth = 5;


            String formattedIndex = String.format("%-" + indexWidth + "d", i);
            String formattedName = String.format("%-" + nameWidth + "s", patientName.length() > nameWidth ?
                    patientName.substring(0, nameWidth - 3) + "..." :
                    patientName);
            String formattedmedicationsString = String.format("%-" + medicationsWidth + "s", medicationsString.length() > medicationsWidth ?
                    medicationsString.substring(0, medicationsWidth - 3) + "..." :
                    medicationsString);
            String formattedDateColumn = String.format("%-" + dateWidth + "s", formattedDate);
            //String formattedTimeColumn = String.format("%-" + timeWidth + "s", formattedTime);
            String formattedisPaid = String.format("%-" + isPaidWidth + "s", isPaid);

            String status = appointment.getAppointmentStatus().toString();

            String displayText = String.format("%s | %s | %s | %s | %s |",
                    formattedIndex, formattedName, formattedmedicationsString, formattedDateColumn, formattedisPaid);

            Color itemColor = getItemColor(appointment.getAppointmentStatus());

            // add row view for horizontal separation, use two text views for two different statuses.
            ListView rowView = new ListView(this.canvas, itemColor, ListViewOrientation.HORIZONTAL);
            rowView.addItem(new TextView(this.canvas, displayText, itemColor));
            rowView.addItem(new TextView(this.canvas, status, itemColor, TextStyle.BOLD));
            listView.addItem(rowView);
        }
        this.canvas.setRequireRedraw(true);
    }

    private void selectAppointmentIndex(List<Appointment> appointments) {
        int selectedIndex = InputHelper.getValidIndex("Select Appointment index", appointments);
        Appointment selectedAppointment = appointments.get(selectedIndex);

        int selectedIndex1;
        System.out.println("| 1. Dispense Medication | 2. Go Back |");
        selectedIndex1 = InputHelper.getValidIndex("Select An Option", 1, 2);


        switch (selectedIndex1){
            case 1:
                processBilling(selectedAppointment);
                break;
            case 2:
                refreshUi();
                break;
        }
//        refreshUi();
    }

    public void processBilling(Appointment appointment) {
        // Retrieve the Billing object from the Appointment.
        Billing billing = appointment.getBilling();
        if (billing == null) {
            System.out.println("No billing information available.");
            return;
        }

        // Retrieve the Prescription from the Billing.
        Prescription prescription = billing.getPrescription();
        if (prescription == null) {
            System.out.println("No prescription found in billing.");
            return;
        }

        // Process each medication in the prescription.
        for (Medication requiredMed : prescription.getMedication()) {
            // Look up the medication by its ID using MedicationController.
            System.out.println(requiredMed.getMedicationName());
            Medication availableMed = MedicationController.findAvailableMedicationByName(requiredMed.getMedicationName());
            if (availableMed == null) {
                System.out.println("Medication " + requiredMed.getMedicationName() + " not found in inventory.");
                continue;
            }

            // Check if there is stock available.
            if (availableMed.getStockAvailable() > 0) {
                // Deduct (or the required amount) from the stock.
                int newStock = availableMed.getStockAvailable() - requiredMed.getStockAvailable();;
                availableMed.setStockAvailable(newStock);
                System.out.println("Dispensed: " + availableMed.getMedicationName());
            } else {
                System.out.println("Out of stock: " + availableMed.getMedicationName());
            }
        }

        // Save the updated stock information.
        MedicationController.saveMedicationToFile();

        // Mark the billing as processed.
        System.out.println("Billing processed: medications have been dispensed.");
        System.out.println("Press 0 to continue");
        appointment.setAppointmentStatus(AppointmentStatus.DISPENSED);
        //Uncomment this once ready
        //Globals.appointmentController.saveAppointmentsToFile();
    }

    private Color getItemColor(AppointmentStatus status) {
        switch (status) {
            case DECLINED:
                return Color.RED;
            case PENDING:
                return Color.CYAN;
            default:
                return Color.GREEN;
        }
    }
}
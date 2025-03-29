package org.groupJ.pages.pharmacy;

import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.controllers.ESController;
import org.groupJ.controllers.MedicationController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.*;
import org.groupJ.models.enums.AppointmentStatus;
import org.groupJ.models.enums.PatientStatus;
import org.groupJ.models.enums.UserType;
import org.groupJ.ui.framework.*;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.ListViewOrientation;
import org.groupJ.ui.framework.views.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DispensaryPage extends UiBase {
    ListView listView;
    List<Appointment> appointments;
    List<EmergencyCase> emergencyCases;

    // Column width constants for consistent display
    private static final int INDEX_WIDTH = 5;
    private static final int NAME_WIDTH = 15;
    private static final int MEDICATIONS_WIDTH = 35;
    private static final int DATE_WIDTH = 10;
    private static final int IS_PAID_WIDTH = 5;


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
        lv.setTitleHeader("Dispense Medication for Patients | GREEN for Appointments & RED for Emergency");

        this.listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView listView = (ListView) parentView;
        ESController.loadEmergencyCaseFromFile();
        appointments = Globals.appointmentController.getAppointments()
                .stream()
                .filter(x -> x.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                .toList();
        emergencyCases = ESController.getAllCases()
                .stream()
                .filter(x -> x.getPatientStatus() == PatientStatus.DISCHARGED)
                .toList();
        if (emergencyCases == null) {
            emergencyCases = new ArrayList<>();
        }
        listView.attachUserInput("Select index ", str -> selectCaseOrAppointmentIndex());
        refreshUi();
        canvas.setRequireRedraw(true);
    }
    //Prompts the user to select an appointment or emergency case index and processes the selection
    private void selectCaseOrAppointmentIndex() {
        System.out.println("Select 1 for Appointments, 2 for Emergency Cases");
        int option = InputHelper.getValidIndex("Select an option", 1, 2);
        if (option == 1) {
            selectAppointmentIndex();
        } else if (option == 2) {
            selectEmergencyCaseIndex();
        }
    }
    //Prompts the user to select an appointment index and processes the selection
    private void selectAppointmentIndex() {
        int selectedIndex = InputHelper.getValidIndex("Select Appointment index", appointments);
        Appointment selectedAppointment = appointments.get(selectedIndex);

        System.out.println("| 1. Dispense Medication | 2. Go Back |");
        int selectedIndex1 = InputHelper.getValidIndex("Select An Option", 1, 2);

        switch (selectedIndex1) {
            case 1:
                processBilling(selectedAppointment);
                break;
            case 2:
                refreshUi();
                break;
        }
    }
    //Prompts the user to select an emergency case index and processes the selection
    private void selectEmergencyCaseIndex() {
        int selectedIndex = InputHelper.getValidIndex("Select Emergency Case index", emergencyCases);
        EmergencyCase selectedCase = emergencyCases.get(selectedIndex);

        System.out.println("| 1. Dispense Medication | 2. Go Back |");
        int selectedIndex1 = InputHelper.getValidIndex("Select An Option", 1, 2);

        switch (selectedIndex1) {
            case 1:
                processEmergencyCaseBilling(selectedCase);
                break;
            case 2:
                refreshUi();
                break;
        }
    }
    //Processes the billing for the selected appointment by dispensing
    public void processBilling(Appointment appointment) {
        // Retrieve the Billing object from the Appointment.
        Billing billing = appointment.getBilling();
        if (billing == null) {
            System.out.println("No billing information available.");
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "USER RETRIEVED", appointment.getPatient().getId() + "'s BILLING", "FAILED", "NURSE");
            return;
        }

        // Retrieve the Prescription from the Billing.
        Prescription prescription = billing.getPrescription();
        if (prescription == null) {
            System.out.println("No prescription found in billing.");
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "USER RETRIEVED", appointment.getPatient().getId() + "'s PRESCRIPTION", "FAILED", "NURSE");
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
                int newStock = availableMed.getStockAvailable() - requiredMed.getStockAvailable();
                availableMed.setStockAvailable(newStock);
                //Debugging
                String logMessage = String.format(
                        "DISPENSED - Medication: %s, Quantity: %d",
                        requiredMed.getMedicationName(),
                        requiredMed.getStockAvailable()
                );
                System.out.println(logMessage);
                AuditManager.getInstance().logAction(
                        UserController.getActiveNurse().getId(),
                        logMessage,
                        appointment.getPatient().getId(),
                        "SUCCESS",
                        "NURSE"
                );
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
    }
    //Processes the billing for the selected emergency case by dispensing
    private void processEmergencyCaseBilling(EmergencyCase emergencyCase) {
        // Retrieve the list of prescribed medications from the emergency case.
        List<Medication> prescribedMedications = emergencyCase.getPrescribedMedications();

        if (prescribedMedications == null || prescribedMedications.isEmpty()) {
            System.out.println("No prescribed medications found for this emergency case.");
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "USER RETRIEVED", emergencyCase.getPatient().getId() + "'s PRESCRIPTIONS", "FAILED", "NURSE");
            return;
        }

        // Process each medication in the prescription.
        for (Medication requiredMed : prescribedMedications) {
            // Look up the medication by its name using MedicationController.
            System.out.println(requiredMed.getMedicationName());
            Medication availableMed = MedicationController.findAvailableMedicationByName(requiredMed.getMedicationName());
            if (availableMed == null) {
                System.out.println("Medication " + requiredMed.getMedicationName() + " not found in inventory.");
                continue;
            }

            // Check if there is stock available.
            if (availableMed.getStockAvailable() > 0) {
                // Deduct the required amount from the stock.
                int newStock = availableMed.getStockAvailable() - requiredMed.getStockAvailable();
                availableMed.setStockAvailable(newStock);
                //Debugging
                String logMessage = String.format(
                        "DISPENSED - Medication: %s, Quantity: %d",
                        requiredMed.getMedicationName(),
                        requiredMed.getStockAvailable()
                );
                System.out.println(logMessage);
                AuditManager.getInstance().logAction(
                        UserController.getActiveNurse().getId(),
                        logMessage,
                        emergencyCase.getPatient().getId(),
                        "SUCCESS",
                        "NURSE"
                );
            } else {
                System.out.println("Out of stock: " + availableMed.getMedicationName());
            }
        }

        // Save the updated stock information.
        MedicationController.saveMedicationToFile();

        // Mark the emergency case as medications dispensed.
        System.out.println("Medications have been dispensed.");
        emergencyCase.setPatientStatus(PatientStatus.DISPENSED);
        ESController.saveEmergencyCasesToFile();
    }
    //Refreshes the UI
    private void refreshUi() {
        listView.clear();
        // Add headers
        displayHeaders();
        displayAppointments();
        displayEmergencyCases();
        this.canvas.setRequireRedraw(true);
    }

    private void displayHeaders() {
        String headers = String.format("%-" + INDEX_WIDTH + "s | %-" + NAME_WIDTH + "s | %-" + MEDICATIONS_WIDTH + "s | %-" + DATE_WIDTH + "s | %-" + IS_PAID_WIDTH + "s |",
                "Index", "Patient Name", "Prescribed Medications", "Date", "Paid");

        ListView headerRow = new ListView(this.canvas, Color.CYAN, ListViewOrientation.HORIZONTAL);
        headerRow.addItem(new TextView(this.canvas, headers, Color.CYAN, TextStyle.BOLD));
        headerRow.addItem(new TextView(this.canvas, "Status", Color.CYAN, TextStyle.BOLD));
        listView.addItem(headerRow);
    }

    //Display Appointments
    private void displayAppointments() {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientName = appointment.getPatient().getName();
            List<String> medicationNames = new ArrayList<>();
            for (Medication med : appointment.getBilling().getPrescription().getMedication()) {
                medicationNames.add(med.getMedicationName());
            }
            String medicationsString = String.join(", ", medicationNames);
            String formattedDate = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Boolean isPaid = appointment.getBilling().isPaid();

            // error handling for dirty json data
            if (appointment.getAppointmentStatus() == null) {
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
            }
            String formattedIndex = String.format("%-" + INDEX_WIDTH + "d", i);
            String formattedName = String.format("%-" + NAME_WIDTH + "s", patientName.length() > NAME_WIDTH ?
                    patientName.substring(0, NAME_WIDTH - 3) + "..." :
                    patientName);
            String formattedmedicationsString = String.format("%-" + MEDICATIONS_WIDTH + "s", medicationsString.length() > MEDICATIONS_WIDTH ?
                    medicationsString.substring(0, MEDICATIONS_WIDTH - 3) + "..." :
                    medicationsString);
            String formattedDateColumn = String.format("%-" + DATE_WIDTH + "s", formattedDate);
            String formattedPaid = String.format("%-" + IS_PAID_WIDTH + "s", isPaid);

            String status = appointment.getAppointmentStatus().toString();

            String displayText = String.format("%s | %s | %s | %s | %s |",
                    formattedIndex, formattedName, formattedmedicationsString, formattedDateColumn, formattedPaid);

            Color itemColor = getItemColor(appointment.getAppointmentStatus());

            // add row view for horizontal separation, use two text views for two different statuses.
            ListView rowView = new ListView(this.canvas, itemColor, ListViewOrientation.HORIZONTAL);
            rowView.addItem(new TextView(this.canvas, displayText, itemColor));
            rowView.addItem(new TextView(this.canvas, status, itemColor, TextStyle.BOLD));
            listView.addItem(rowView);
        }
    }

    private void displayEmergencyCases() {
        for (int i = 0; i < emergencyCases.size(); i++) {
            EmergencyCase emergencyCase = emergencyCases.get(i);
            String patientName = emergencyCase.getPatient().getName();
            List<String> medicationNames = new ArrayList<>();
            for (Medication med : emergencyCase.getPrescribedMedications()) {
                medicationNames.add(med.getMedicationName());
            }
            String medicationsString = String.join(", ", medicationNames);
            String formattedDate = emergencyCase.getArrivalDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            String formattedIndex = String.format("%-" + INDEX_WIDTH + "d", i);
            String formattedName = String.format("%-" + NAME_WIDTH + "s", patientName.length() > NAME_WIDTH ?
                    patientName.substring(0, NAME_WIDTH - 3) + "..." :
                    patientName);
            String formattedmedicationsString = String.format("%-" + MEDICATIONS_WIDTH + "s", medicationsString.length() > MEDICATIONS_WIDTH ?
                    medicationsString.substring(0, MEDICATIONS_WIDTH - 3) + "..." :
                    medicationsString);
            String formattedDateColumn = String.format("%-" + DATE_WIDTH + "s", formattedDate);
            String formattedPaid = String.format("%-" + IS_PAID_WIDTH + "s", "false");
            String status = emergencyCase.getPatientStatus().toString();

            String displayText = String.format("%s | %s | %s | %s | %s |",
                    formattedIndex, formattedName, formattedmedicationsString, formattedDateColumn, formattedPaid);

            Color itemColor = getItemColor(emergencyCase.getPatientStatus());

            // add row view for horizontal separation, use two text views for two different statuses.
            ListView rowView = new ListView(this.canvas, itemColor, ListViewOrientation.HORIZONTAL);
            rowView.addItem(new TextView(this.canvas, displayText, itemColor));
            rowView.addItem(new TextView(this.canvas, status, itemColor, TextStyle.BOLD));
            listView.addItem(rowView);
        }
    }

    // Helper method to get item color for the appointment status
    private Color getItemColor(AppointmentStatus status) {
        return switch (status) {
            default -> Color.GREEN;
        };
    }

    // Overloaded helper method to get item color for the patient status
    private Color getItemColor(PatientStatus status) {
        return switch (status) {
            default -> Color.RED;
        };
    }
}
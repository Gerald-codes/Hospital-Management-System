package org.groupJ.pages.patient;

import org.groupJ.controllers.UserController;
import org.groupJ.models.Appointment;
import org.groupJ.models.PatientConsent;
import org.groupJ.models.enums.AppointmentStatus;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.InputHelper;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.audit.*;
import org.groupJ.util.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.groupJ.Globals.appointmentController;

public class PatientMainPage extends UiBase {
    ListView listView;

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader(" Teleconsultation | Welcome back! " + UserController.getActivePatient().getName());
        lv.addItem(new TextView(this.canvas, "1. Book Appointment - To schedule appointment to see doctors ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. View Billing - To view due teleconsult bills ", Color.GREEN));

        lv.attachUserInput("Book Appointment", str -> bookAppointmentPrompt());

        lv.attachUserInput("View Billing", str -> {
            BillingPage.appointments = appointmentController.getAppointments();
            ToPage(new BillingPage());
        });


        canvas.setRequireRedraw(true);
    }

    private void bookAppointmentPrompt(){
        appointmentController.getAppointments();
        String reason = InputValidator.getValidStringInput("Enter reason to consult: ");
        AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER ENTERED: " + reason, "Patient: " + UserController.getActivePatient().getId() + "'s REASON FOR CONSULTATION", "SUCCESS", "PATIENT");
        String history = InputValidator.getValidStringInput("Do you have any Medical History?: ");
        AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER ENTERED: " + history, "Patient: " + UserController.getActivePatient().getId() + "'s MEDICAL HISTORY", "SUCCESS", "PATIENT");

        LocalDate date = null; // safe to initialise as null, as it will never be null after the prompt.
        boolean validDate = false;
        String appointmentDate;
        while (!validDate) {
            appointmentDate = InputValidator.getAllStringInput("Select your appointment date in this format (DD-MM-YYYY): ");
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(appointmentDate, dateFormatter);

                System.out.println(appointmentDate);

                // Check if the date is in the future, don't want past appointments.
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("The appointment date must be in the future. Please enter a valid date (DD-MM-YYYY):");
                    continue;
                }
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format (DD-MM-YYYY):");
            }
        }

        Dictionary<Integer, LocalDateTime> dateTimeDictionary = new Hashtable<>();
        // start the timeslot at 8:00am
        LocalDateTime startDate = date.atStartOfDay().withHour(8).withMinute(0).withSecond(0).withNano(0);

        // Define the time slots and their corresponding integer keys
        dateTimeDictionary.put(1, startDate);
        dateTimeDictionary.put(2, startDate.plusHours(1));
        dateTimeDictionary.put(3, startDate.plusHours(2));
        dateTimeDictionary.put(4, startDate.plusHours(3));
        dateTimeDictionary.put(5, startDate.plusHours(4));
        dateTimeDictionary.put(6, startDate.plusHours(5));
        dateTimeDictionary.put(7, startDate.plusHours(6));
        dateTimeDictionary.put(8, startDate.plusHours(7));
        dateTimeDictionary.put(9, startDate.plusHours(8));

        // Display available time slots (using StringBuilder)
        StringBuilder sb = new StringBuilder("Available timeslots: [");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 1; i <= dateTimeDictionary.size(); i++) {
            // display for the user in a nice fashion "1. 8:00 AM
            LocalDateTime time = dateTimeDictionary.get(i);
            String formattedTime = formatter.format(time);
            sb.append(i).append(". ").append(formattedTime);
            if (i < dateTimeDictionary.size()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb); // Convert StringBuilder to String and print


        System.out.print("Select your appointment timeslot (1-" + dateTimeDictionary.size() + "): ");

        int selectedSlot = InputHelper.getValidIndex("Select your appointment timeslot", 1, dateTimeDictionary.size());

        LocalDateTime selectedDateTime = dateTimeDictionary.get(selectedSlot);
        AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER REQUESTED APPOINTMENT ON" + formatter.format(selectedDateTime) , "SYSTEM", "SUCCESS", "PATIENT");
        System.out.println("You have requested for an appointment on " + formatter.format(selectedDateTime) + " at index " + selectedSlot);
        Appointment appointment = new Appointment(UserController.getActivePatient(), reason, selectedDateTime, AppointmentStatus.PENDING, "");
        // check if consent is already given before asking.
        if(UserController.getActivePatient().getPatientConsent() == null || !UserController.getActivePatient().getPatientConsent().isConsentGiven()) {
            String consentString = "Telemedicine Consent Form\n\n" +
                    "Purpose: This telemedicine session is for a general checkup.\n\n" +
                    "Procedure:  This session will use live video and audio to connect you with the provider.  You may be asked to share information about your health, and the provider may provide advice or recommendations.\n\n" +
                    "Recording: This session will not be recorded. If this session is recorded for any purpose, it will be made known to you and separate verbal consent will be required during the call.\n\n" +
                    "Confidentiality: Your personal health information is protected by Singapore privacy laws.  We will take reasonable steps to protect your privacy.\n\n" +
                    "Risks and Limitations: Telemedicine is not a substitute for in-person care.  Some conditions cannot be diagnosed or treated remotely.  Technical issues (e.g., poor internet connection) may affect the quality of the session.  In case of an emergency, please call 911 or go to the nearest emergency room.\n\n" +
                    "Alternatives: You have the option to schedule an in-person appointment instead of using telemedicine.\n\n" +
                    "Rights: You have the right to refuse or withdraw consent at any time. You have the right to ask questions about this session and your health information.\n\n" +
                    "By Agreeing, you confirm that you have read, understood, and agree to the terms of this telemedicine consent (Y/N). \n\n" +
                    "Technical requirements: A laptop or mobile device (such as phone or tablet) with Zoom Meetings app installed";
            System.out.println(consentString);
            boolean validInput = false;
            while (!validInput) {
                String s = InputValidator.getValidStringInput("Do you wish to proceed with this appointment? (Y/N) ");
                if (s.equalsIgnoreCase("Y")) {
                    validInput = true;
                    AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER CONSENT ACCEPTED", "SYSTEM", "SUCCESS", "PATIENT");

                } else if (s.equalsIgnoreCase("N")) {
                    System.out.println("Consent not recieved, terminating session. Your information will not be saved.");
                    AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER CONSENT REJECTED", "SYSTEM", "FAILED", "PATIENT");
                    canvas.setRequireRedraw(true);
                    return;
                }
            }
            // set the consent.
            appointment.getPatient().setPatientConsent(new PatientConsent(true, consentString));
        }
        appointment.setHistory(history);
        appointmentController.addAppointment(appointment);
        AuditManager.getInstance().logAction(UserController.getActivePatient().getId(), "USER APPOINTMENT BOOKED", "SYSTEM", "SUCCESS", "PATIENT");
        appointmentController.saveAppointmentsToFile();
        canvas.setRequireRedraw(true);
    }
}


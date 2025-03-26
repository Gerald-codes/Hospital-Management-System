package org.lucas.pages.patient;

import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.Medication;
import org.lucas.models.Prescription;
import org.lucas.models.Symptoms;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAppointmentSummaryPage extends UiBase {
    private static Appointment appointment;
    private ListView listView;

    public static void setAppointment(Appointment appointment) {
        ViewAppointmentSummaryPage.appointment = appointment;
    }

    @Override
    public View OnCreateView() {
        listView = new ListView(
                this.canvas,
                Color.CYAN
        );
        listView.setTitleHeader("Appointment Summary for " + UserController.getActivePatient().getName());
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        refreshUi();
    }

    private void refreshUi () {
        listView.clear();
        listView.addItem(new TextView(this.canvas, "Attending Doctor Name: " + appointment.getDoctor().getName(), Color.BLUE, TextStyle.BOLD));
        listView.addItem(new TextView(this.canvas, "Patient Name: " + appointment.getPatient().getName(), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Appointment Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Reason: " + appointment.getReason() + "\n", Color.GREEN, TextStyle.ITALIC));

        String MCString = "Medical Certificate not yet given.";

        // separate the editable items with different colours
        if (appointment.getMc() != null) {
            MCString = "Medical Certificate: from: " +
                    appointment.getMc().getStartDate().format(DateTimeFormatter.ofPattern("dd/MM")) + " to: " +
                    appointment.getMc().getEndDate().format(DateTimeFormatter.ofPattern("dd/MM"));
        }
        listView.addItem(new TextView(this.canvas,
                MCString,
                Color.CYAN,
                TextStyle.ITALIC));

        List<Symptoms> symptomsList = appointment.getPatient().getEHR().getSymptoms();
        String doctorNotes = "";
        if (!symptomsList.isEmpty()) {
            doctorNotes = symptomsList.stream()
                    .map(Symptoms::getDoctorNotes)
                    .filter(notes -> notes != null && !notes.isEmpty())
                    .collect(Collectors.joining(", "));

            // Else set to doctor notes that is already set
            if (doctorNotes.isEmpty()) {
                doctorNotes = "Not yet set";
            }
        }
        listView.addItem(new TextView(this.canvas, "Doctor Notes/Follow up: " + doctorNotes, Color.CYAN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Diagnosis: " + appointment.getDiagnosis(), Color.CYAN, TextStyle.ITALIC));

        // Add prescription details
        Prescription prescription = appointment.getBilling().getPrescription();
        if (prescription != null) {
            List<Medication> medication = prescription.getMedication();
            if (medication != null && !medication.isEmpty()) {
                listView.addItem(new TextView(this.canvas, "Prescription:", Color.BLUE, TextStyle.BOLD));
                for (Medication medications : medication) {
                    listView.addItem(new TextView(this.canvas, "  - " + medications.getMedicationName() + " x " + medications.getStockAvailable() + " | Dosage: " + medications.getDosage(), Color.GREEN));
                }
            }
        }
        canvas.setRequireRedraw(true);
    }
}

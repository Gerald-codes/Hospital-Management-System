package org.lucas.pages.doctor;

import org.lucas.Emergency.EmergencyCase;
import org.lucas.Globals;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.AppointmentController;
import org.lucas.controllers.ESController;
import org.lucas.controllers.MedicationController;
import org.lucas.core.ClinicalGuideline;
import org.lucas.models.*;
import org.lucas.util.InputValidator;
import org.lucas.ui.framework.*;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
/**
 * Represents the teleconsultation page in the UI.
 * This class handles the creation and management of the teleconsultation page, including user inputs and UI updates.
 */
public class TeleconsultPage extends UiBase {
    private static Appointment appointment;
    private static Doctor doctor;
    private ListView listView;

    public static void setAppointment(Appointment appointment) {
        TeleconsultPage.appointment = appointment;
    }

    @Override
    public View OnCreateView() {
        listView = new ListView(
                this.canvas,
                Color.CYAN
        );
        listView.setTitleHeader("Teleconsult");
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;
        lv.attachUserInput("Set Doctor Notes", str -> {
            Globals.appointmentController.setDoctorNotes(appointment);
            refreshUi();
        });

        lv.attachUserInput("Edit Prescription", str -> {
            System.out.println("Prescription Options:");
            System.out.println("1. Add New Prescription");
            System.out.println("2. Edit Existing Prescription");
            System.out.println("3. Remove Prescription");
            System.out.println("4. Cancel");
            int prescriptionchoice = InputHelper.getValidIndex("Select option:", 1, 4);

            switch (prescriptionchoice) {
                case 1: // Add a New Medication (In Database or Not, the function will handle it)
                    //setPrescribeMeds handles the auditing of the action as well
                    Globals.appointmentController.setPrescribeMeds(-1, appointment);
                    refreshUi();
                    break;
                case 2: // Edit Existing Medication
                    int editIndex = selectMedicationIndexPrompt();
                    if (editIndex != -1) {
                        Globals.appointmentController.setPrescribeMeds(editIndex, appointment);
                        refreshUi();
                    }
                    AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "USER EDITED MEDICATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
                    break;
                case 3: // Remove Medication
                    int removeIndex = selectMedicationIndexPrompt();
                    if (removeIndex != -1) {
                        appointment.getBilling().getPrescription().removeMedicationAtIndex(removeIndex);
                    }
                    AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "USER DELETED MEDICATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
                    break;
                case 4: // Cancel
                    // If doctor accidentally clicked this, they can cancel
                    break;
            }

            refreshUi();
        });
        lv.attachUserInput("Edit Medical Certificate", str -> {
            // Check if medical certificate already exists
            boolean mcExists = appointment.getMc() != null;

            System.out.println("Medical Certificate Options:");
            System.out.println("1. " + (mcExists ? "Edit" : "Add") + " Medical Certificate");
            if (mcExists) {
                System.out.println("2. Remove Medical Certificate");
            }
            System.out.println((mcExists ? "3" : "2") + ". Cancel");

            int maxOption = mcExists ? 3 : 2;
            int choice = InputHelper.getValidIndex("Select option:", 1, maxOption);

            if (choice == 1) {
                // Add or Edit Medical Certificate - using your existing code
                Scanner scanner;
                // according to official government policy, MOM allocates mandatory 14 days MC. Above that, most companies consider it
                // hospitalisation leave. Therefore, telemedicine should not usually provide more than a few days of MC
                // 14 is a very reasonable upper limit to set here. Anymore and they should be inpatient/physical consult.
                int days = InputHelper.getValidIndex("Medical Certificate No. Days * incl today:", 1, 14);

                scanner = new Scanner(System.in);
                System.out.println("Medical Certificate Remarks: ");
                String remarks = scanner.nextLine();

                LocalDate today = LocalDate.now();
                LocalDate endDay = today.plusDays(days - 1);
                MedicalCertificate mc = new MedicalCertificate(today.atStartOfDay(), endDay.atTime(23, 59), remarks);
                appointment.setMedicalCertificate(mc);
                AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "USER GAVE: "+ remarks + "DAY(S) MC", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
            } else if (choice == 2 && mcExists) {
                appointment.setMedicalCertificate(null);
                AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "USER REMOVED MC", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");

            }
            refreshUi();
        });
        lv.attachUserInput("Diagnose Patient", str -> {
            Globals.appointmentController.diagnosePatient(appointment);
            refreshUi();
        });
        lv.attachUserInput("Finish Consultation", str -> {
            System.out.println("Finishing consultation...");
            System.out.println("Calculating Billing for patient...");
            appointment.finishAppointment(appointment.getDoctorNotes());
            AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "FINISHED CONSULTATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
            Globals.appointmentController.saveAppointmentsToFile();
            canvas.previousPage();
            this.OnBackPressed();
        });
        lv.attachUserInput("Refer Patient to Emergency", str -> {
            System.out.println("Refering...");
            appointment.referEmergency(appointment.getDoctorNotes());
            Globals.appointmentController.saveAppointmentsToFile();
            int caseID = 1;
            EmergencyCase newCase = new EmergencyCase(caseID,appointment.getPatient(),appointment.getDoctorNotes(),"Referred",LocalDateTime.now(),true);
            ESController.addEmergencyCases(newCase);
            System.out.println("\nNew Case Registered | Case ID: " + caseID + " | Patient: " + appointment.getPatient().getName());
            AuditManager.getInstance().logAction(appointment.getDoctor().getId(), "REFERRED PATIENT TO EMERGENCY", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
            ESController.saveEmergencyCasesToFile();
            canvas.previousPage();
            this.OnBackPressed();
        });
        refreshUi();
    }
    /**
     * Prompts the user to select a medication index for editing.
     *
     * @return the selected medication index, or -1 if no medication is available
     */
    private int selectMedicationIndexPrompt() {
        List<Medication> medication = appointment.getBilling().getPrescription().getMedication();
        if (medication == null || medication.isEmpty()) {
            System.out.println("No medication have been added yet, returning...");
            refreshUi();
            return -1;
        }
        return InputHelper.getValidIndex("Select medication index to edit", medication);
    }
    /**
     * Refreshes the UI by clearing and updating the list view with the latest appointment details.
     */
    private void refreshUi() {
        listView.clear();
        listView.addItem(new TextView(this.canvas, "Patient Name: " + appointment.getPatient().getName(), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Appointment Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Zoom link: " + appointment.getSession().getZoomLink(), Color.GREEN, TextStyle.ITALIC));
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

        // "null" looks ugly haha
        //String doctorNotes = appointment.getDoctorNotes() == null ? "Not yet set" : appointment.getDoctorNotes();
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
        listView.addItem(new TextView(this.canvas, "Doctor Notes/Follow up: ", Color.CYAN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, doctorNotes + "\n", Color.GREEN));
        listView.addItem(new TextView(this.canvas, "Diagnosis: " + appointment.getDiagnosis(), Color.CYAN, TextStyle.ITALIC));

        // Add prescription details
        Prescription prescription = appointment.getBilling().getPrescription();
        if (prescription != null) {
            List<Medication> medication = prescription.getMedication();
            if (medication != null && !medication.isEmpty()) {
                listView.addItem(new TextView(this.canvas, "Prescription:", Color.BLUE, TextStyle.BOLD));
                for (int i = 0; i < medication.size(); i++) {
                    Medication medications = medication.get(i);
                    listView.addItem(new TextView(this.canvas, i + ".  - " + medications.getMedicationName() + " x " + medications.getStockAvailable() + " | Dosage: " + medications.getDosage(), Color.GREEN));
                }
            }
        }
        canvas.setRequireRedraw(true);
    }
}

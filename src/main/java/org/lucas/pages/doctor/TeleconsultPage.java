package org.lucas.pages.doctor;

import org.lucas.Globals;
import org.lucas.audit.AuditManager;
import org.lucas.controllers.MedicationController;
import org.lucas.core.ClinicalGuideline;
import org.lucas.models.*;
import org.lucas.util.InputValidator;
import org.lucas.ui.framework.*;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TeleconsultPage extends UiBase {
    private static Appointment appointment;
    private static Doctor doctor;
    private ListView listView;
    private static List<ClinicalGuideline> clinicalGuidelines = List.copyOf(ClinicalGuideline.generateClinicalGuideLine());
    AuditManager auditManager = new AuditManager();

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
            setDoctorNotes();
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
                    auditManager.logAction(appointment.getDoctor().getId(), "PRESCRIBED MEDICATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
                    setPrescribeMeds(-1);
                    break;
                case 2: // Edit Existing Medication
                    int editIndex = selectMedicationIndexPrompt();
                    if (editIndex != -1) {
                        setPrescribeMeds(editIndex);
                    }
                    auditManager.logAction(appointment.getDoctor().getId(), "EDITED MEDICATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
                    break;
                case 3: // Remove Medication
                    int removeIndex = selectMedicationIndexPrompt();
                    if (removeIndex != -1) {
                        appointment.getBilling().getPrescription().removeMedicationAtIndex(removeIndex);
                    }
                    auditManager.logAction(appointment.getDoctor().getId(), "DELETED MEDICATION", "Patient: " + appointment.getPatient().getId(), "SUCCESS", "DOCTOR");
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
            } else if (choice == 2 && mcExists) {
                appointment.setMedicalCertificate(null);
            }
            refreshUi();
        });
        lv.attachUserInput("Diagnose Patient", str -> {
            diagnosePatient();
            refreshUi();
        });
        lv.attachUserInput("Finish Consultation", str -> {
            System.out.println("Finishing consultation...");
            System.out.println("Calculating Billing for patient...");
            appointment.finishAppointment(appointment.getDoctorNotes());
            Globals.appointmentController.saveAppointmentsToFile();
            canvas.previousPage();
            this.OnBackPressed();
        });
        lv.attachUserInput("Refer Patient to Emergency", str -> {
            System.out.println("Refering...");
            appointment.referEmergency(appointment.getDoctorNotes());
            Globals.appointmentController.saveAppointmentsToFile();
            canvas.previousPage();
            this.OnBackPressed();
        });
        refreshUi();
    }

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
     * Sets the medicine, either at the index or add a new medicine when index = -1
     *
     * @param index -1 when adding, otherwise, specifying index will add a new medicine
     */
    private void setPrescribeMeds(int index) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the drug name: ");
        // always use upper case
        String medicationName = scanner.nextLine().trim().toUpperCase();

        System.out.println("Enter the amount: ");
        int medicineAmount = scanner.nextInt();
        // somehow creating a new scanner prevents errors where it may just skip the scanner.
        scanner = new Scanner(System.in);
        Prescription prescription = appointment.getBilling().getPrescription();

        // if the medicine is in the database, automatically fill in the dosage
        // otherwise prompt for the dosage and instructions.
        if (MedicationController.findAvailableMedicationByName(medicationName) != null) {
            if (index == -1) {
                prescription.addMedication(medicationName, medicineAmount, "");
                auditManager.logAction(appointment.getDoctor().getId(), "DOCTOR PRESCRIBED: x" + medicineAmount + " - " + medicationName, "MEDICINE(s) TO" + appointment.getPatient().getPatientID(), "SUCCESS", "DOCTOR");
            } else {
                prescription.setMedicationAtIndex(medicationName, medicineAmount, "", index);
                auditManager.logAction(appointment.getDoctor().getId(), "DOCTOR PRESCRIBED: x" + medicineAmount + " - " + medicationName, "MEDICINE(s) TO" + appointment.getPatient().getPatientID(), "SUCCESS", "DOCTOR");
            }
        } else {
            System.out.println("Enter the dosage/instructions: ");
            String dosage = scanner.nextLine();

            if (index == -1) {
                prescription.addMedication(medicationName, medicineAmount, dosage);
                auditManager.logAction(appointment.getDoctor().getId(), "DOCTOR PRESCRIBED: x" + medicineAmount + " - " + medicationName, "MEDICINE(s) TO" + appointment.getPatient().getPatientID(), "SUCCESS", "DOCTOR");
            } else {
                prescription.setMedicationAtIndex(medicationName, medicineAmount, dosage, index);
                auditManager.logAction(appointment.getDoctor().getId(), "DOCTOR PRESCRIBED: x" + medicineAmount + " - " + medicationName, "MEDICINE(s) TO" + appointment.getPatient().getPatientID(), "SUCCESS", "DOCTOR");
            }
        }

        refreshUi();
    }
    private void diagnosePatient(){
        System.out.println("Diagnose patient in progress");
        List<Symptoms> symptoms = appointment.getPatient().getEHR().getSymptoms();
        if (symptoms == null || symptoms.isEmpty()) {
            System.out.println("No symptoms recorded for this patient.");
            return;
        }
        // Get the last symptom
        Symptoms lastSymptom = symptoms.get(symptoms.size() - 1);
        List<String> cdssDiagnosis = cdssAnalyzeSymptoms(lastSymptom);
        System.out.println("CDSS Suggests: ");
        String outcome = "SUCCESS";
        for (int i = 0; i < cdssDiagnosis.size(); i++) {
            System.out.println((i+1) + ". " + cdssDiagnosis.get(i));
            if (i == cdssDiagnosis.size()-1){
                while (true){
                    String doctorConfirmation = InputValidator.getValidStringInput("Doctor " + appointment.getDoctor().getName() +
                            ", do you agree with the CDSS diagnosis? (yes/no): ");

                    auditManager.logAction(appointment.getDoctor().getId(), "USER ENTERED: "+ doctorConfirmation, "CDSS diagnosis", "SUCCESS", "DOCTOR");
                    if (doctorConfirmation.equalsIgnoreCase("no")) {
                        System.out.println("===== Override CDSS Diagnosis =====");
                        String diagnosis = InputValidator.getValidStringWithSpaceInput("Enter your diagnosis: ");
                        appointment.setDiagnosis(diagnosis);
                        outcome = "OVERRIDDEN";
                        auditManager.logAction(appointment.getDoctor().getId(), "DIAGNOSE PATIENT", "Patient: " + appointment.getPatient().getId(), outcome,"DOCTOR");
                        auditManager.logAction(appointment.getDoctor().getId(), "USER ENTERED: "+ diagnosis, "Patient: " + appointment.getPatient().getId() + "'s override diagnosis", "SUCCESS", "DOCTOR");
                        break;
                    } else if (doctorConfirmation.equalsIgnoreCase("yes")) {
                        int choice = InputValidator.getValidRangeIntInput("Enter choice of Diagnosis: ", cdssDiagnosis.size());
                        appointment.setDiagnosis(cdssDiagnosis.get(choice-1));  // Accept CDSS diagnosis
                        auditManager.logAction(appointment.getDoctor().getId(), "DIAGNOSE PATIENT", "Patient: " + appointment.getPatient().getId(), outcome,"DOCTOR");
                        break;
                    }else{
                        System.out.println("Invalid Input!\n");
                    }
                }
            }
        }
    }
    private static List<String> cdssAnalyzeSymptoms(Symptoms symptom) {
        List<String> CDSSDiagnosis = new ArrayList<>();
        for (ClinicalGuideline guidelineSymptom : clinicalGuidelines) {
            if (guidelineSymptom.getGuideLineType().equals("Symptom") &&
                    guidelineSymptom.getSupportingEvidence().contains(symptom.getSymptomName())) {
                CDSSDiagnosis.add(guidelineSymptom.getGuideDescription());
            }
        }
        if (CDSSDiagnosis.isEmpty()) {
            CDSSDiagnosis.add("Diagnosis Unclear - Further Tests Required");
        }
        // Return possible Diagnosis
        return CDSSDiagnosis;
    }
    private void setDoctorNotes() {
        System.out.println("Enter Doctor Notes :");
        Scanner scanner = new Scanner(System.in);
        String newNotes = scanner.nextLine();

        // Set the doctor notes at the appointment level
        appointment.setDoctorNotes(newNotes);

        // Clear any existing doctor notes from symptoms
        List<Symptoms> symptomsList = appointment.getPatient().getEHR().getSymptoms();
        if (!symptomsList.isEmpty()) {
            // Only set the notes on the first symptom, leave others empty
            symptomsList.get(0).setDoctorNotes(newNotes);

            // Clear notes from other symptoms to avoid duplication
            for (int i = 1; i < symptomsList.size(); i++) {
                symptomsList.get(i).setDoctorNotes("");
            }
        }

        refreshUi();
    }

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

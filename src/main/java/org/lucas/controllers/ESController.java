package org.lucas.controllers;

import com.google.gson.reflect.TypeToken;
import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.Emergency.enums.PatientLocation;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.Globals;
import org.lucas.audit.AuditManager;
import org.lucas.core.ClinicalGuideline;
import org.lucas.models.*;
import org.lucas.models.enums.TriageLevel;
import org.lucas.util.InputValidator;
import org.lucas.util.JarLocation;
import org.lucas.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ESController {
    private static List<EmergencyCase> allCases = new ArrayList<>();
    private static List<EmergencyCase_Dispatch> allDispatchCases = new ArrayList<>();
    private static final String fileName = "emergency_cases.txt";
    private static final String fileNameDispatch = "emergency_dispatch_cases.txt";
    private static List<ClinicalGuideline> clinicalGuidelines = List.copyOf(ClinicalGuideline.generateClinicalGuideLine());

    public static List<EmergencyCase_Dispatch> getAllDispatchCases() {
        return allDispatchCases;
    }

    public static void printDispatchCaseInfo(int caseID) {
        for (EmergencyCase_Dispatch ec : allDispatchCases) {
            if (ec.getCaseID() == caseID) {
                System.out.println("Emergency Dispatch Case found: " + ec.printIncidentReport()); /*
                 * print the
                 * dispatch case
                 * info if found
                 */
                break; /* Exit the loop once we find the dispatch case */
            }
        }
        System.out.println("Emergency Dispatch Case with ID " + caseID + " not found.");
    }

    public static void printAllDispatchCases() {
        if (allDispatchCases.isEmpty()) {
            System.out.println("No emergency dispatch cases in the system.");
            return;
        }
        for (EmergencyCase c : allDispatchCases) {
            System.out.println(c.printIncidentReport());
        }
    }

    /**
     * Adds an emergency case to the list of all cases.
     *
     * @param emergencyCase The emergency case to be added.
     */
    public static void addEmergencyCases(EmergencyCase emergencyCase){
        allCases.add(emergencyCase);
    }

//    Maybe can sort by triage level future improvement
//    public void sortEmergencyCase(){
//        emergencyCases.sort(): //sort by triage level
//    }

    public static List<EmergencyCase> getAllCases() {
        return allCases;
    }

    public static void addEmergencyCaseDispatch(EmergencyCase_Dispatch newDispatchCase) {
//        if (!allDispatchCases.contains(newDispatchCase)) {
//            System.out.println("Adding case with ID: " + newDispatchCase.getCaseID());
//            allDispatchCases.add(newDispatchCase); // add the new case dispatch
//            System.out.println("Case added successfully. Total cases: " + allDispatchCases.size() + "HEYING") ;
//
//        } else {
//            System.out.println("Duplicate emergency case dispatch-not added: " + newDispatchCase); // print the error
//            // message if the
//           // case dispatch
//           // already exists
//       }
        allDispatchCases.add(newDispatchCase);
    }

    /**
     * Prints all emergency cases that are currently in the emergency room waiting room.
     */
    public static void printAllEmergencyCaseInWaitingRoom() {
        // Filter and print all emergency cases in the Waiting Room
        for (EmergencyCase emergencyCase : allCases) {
            if (PatientLocation.EMERGENCY_ROOM_WAITING_ROOM.equals(emergencyCase.getLocation())) {
                emergencyCase.displayCase();
            }
        }
    }

    /**
     * Prints all emergency cases that have been completed (status DONE) and are in the triage room.
     */
    public static void printAllDoneEmergencyCaseInTriageRoom() {
        // Filter and print all emergency cases in the Triage Room
        for (EmergencyCase emergencyCase : allCases) {
            if (PatientLocation.EMERGENCY_ROOM_WAITING_ROOM.equals(emergencyCase.getLocation()) &&
                    emergencyCase.getPatientStatus().equals(PatientStatus.DONE)) {
                emergencyCase.displayCase();
            }
        }
    }

    /**
     * Prints all emergency cases that are currently in the examination room.
     */
    public static void printAllEmergencyCaseInTraumaRoom() {
        // Filter and print all emergency cases in the Trauma Room
        for (EmergencyCase emergencyCase : allCases) {
            if (PatientLocation.EMERGENCY_ROOM_TRAUMA_ROOM.equals(emergencyCase.getLocation()) &&
                    emergencyCase.getPatientStatus().equals(PatientStatus.WAITING)) {
                emergencyCase.displayCase();
            }
        }
    }

    /**
     * Prints all emergency cases, regardless of their location or status.
     */
    public static void printAllEmergencyCase() {
        if (allCases.isEmpty()) {
            System.out.println("No emergency cases available.");
            return;
        }

        System.out.println("\n===== Emergency Cases =====");

        // Iterate over all cases and print them
        for (EmergencyCase emergencyCase : allCases) {
            emergencyCase.displayCase();
        }
    }

    /**
     * Loads emergency cases from a file.
     */
    public static void  loadEmergencyCaseFromFile() {
        allCases.clear();
        StringBuilder sb = new StringBuilder();
        String basePath = "";

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            Type listType = new TypeToken<List<EmergencyCase>>() {
            }.getType();
            allCases = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * Saves all emergency cases to a file.
     */
    public static void saveEmergencyCasesToFile() {
        String basePath = "";

        // Get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String path = Paths.get(basePath, fileName).toString();

        // Serialize the combined data to JSON (using allCases now)
        String json = Globals.gsonPrettyPrint.toJson(allCases); // Serialize all cases, not just emergencyCases

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            if (allCases.isEmpty()) {
                writer.write("[]");
            } else {
                // Write the JSON representation of all cases
                writer.write(json);
            }
            System.out.println("Emergency Cases saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadEmergencyDispatchCaseFromFile() {
        allDispatchCases.clear();
        StringBuilder sb = new StringBuilder();
        String basePath = "";

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileNameDispatch))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            Type listType = new TypeToken<List<EmergencyCase_Dispatch>>() {
            }.getType();
            allDispatchCases = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public static void saveEmergencyDispatchCasesToFile() {
        String basePath = "";

        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String path = Paths.get(basePath, fileNameDispatch).toString();

        if (allDispatchCases == null) {
            System.out.println("allDispatchCases is null!");
            return;
        }

        System.out.println("Number of dispatch cases: " + allDispatchCases.size());

        String json = "";
        try {
            json = Globals.gsonPrettyPrint.toJson(allDispatchCases);
            System.out.println("Serialized JSON:\n" + json);
        } catch (Exception e) {
            System.out.println("Serialization error: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            if (allDispatchCases.isEmpty()) {
                writer.write("[]");
            } else {
                writer.write(json);
            }
            System.out.println("Emergency Cases saved to " + fileNameDispatch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects an emergency case by its ID.
     *
     * @param caseID The ID of the emergency case to select.
     * @return The selected emergency case, or null if no case with the given ID is found.
     */
    public static EmergencyCase selectCase(int caseID){
        for (EmergencyCase emergencyCase : allCases) {

            if (caseID == emergencyCase.getCaseID()) {
                return emergencyCase;
            }
        }
        return null;
    }

    /**
     * Performs the initial screening for an emergency case, typically done by a nurse.
     * This involves collecting the patient's triage level, vital signs, and allergies.
     * The method prompts for user input and allows for editing of the entered information.
     *
     * @param emergencyCase The emergency case to be screened.
     */
    public static void nurseInitialScreening(EmergencyCase emergencyCase) {
        System.out.println("----------------------------------------------");
        System.out.println("                 CASE DETAILS                 ");

        //Display Case Detail
        emergencyCase.displayCase();

        // Print all TriageLevel enum values and prompt the user to choose one
        System.out.println("======= Select Patient's Triage Level =======");

        for (int i = 0; i < TriageLevel.values().length; i++) {
            // Printing each TriageLevel enum value with an index
            System.out.printf("%d. %s\n", i + 1, TriageLevel.values()[i]);
        }

        // Ask the user to choose a TriageLevel
        int triageChoice = InputValidator.getValidRangeIntInput("Enter the number corresponding to the patient's triage level: ", TriageLevel.values().length);

        // Get the selected TriageLevel
        TriageLevel selectedTriageLevel = TriageLevel.values()[triageChoice - 1];
        System.out.println("Selected Triage Level: " + selectedTriageLevel);

        // Ask for vital signs
        System.out.println("\n======= Enter Patient's Vital Signs =======");
        double temperature = InputValidator.getValidDoubleInput("Enter body temperature (°C): ");
        int heartRate = InputValidator.getValidRangeIntInput("Enter heart rate (beats per minute): ", 200);
        int bloodPressureSystolic = InputValidator.getValidRangeIntInput("Enter systolic blood pressure (mmHg): ", 200);
        int bloodPressureDiastolic = InputValidator.getValidRangeIntInput("Enter diastolic blood pressure (mmHg): ", 120);
        int respiratoryRate = InputValidator.getValidRangeIntInput("Enter respiratory rate (breaths per minute): ", 40); // Typical max RR is 40int respiratoryRate = InputValidator.getValidRangeIntInput("Enter diastolic blood pressure (mmHg): ", 120);

        // Ask for allergies
        String allergies = InputValidator.getValidStringInput("Enter patient's allergies (if any): ");

        // Print summary of entered data for verification
        System.out.println("\nPatient Initial Screening Details:");
        System.out.printf("Triage Level: %s\n", selectedTriageLevel);
        System.out.printf("Temperature: %.2f°C\n", temperature);
        System.out.printf("Heart Rate: %d bpm\n", heartRate);
        System.out.printf("Blood Pressure: %d/%d mmHg\n", bloodPressureSystolic, bloodPressureDiastolic);
        System.out.printf("Respiratory Rate: %d breaths per minute\n", respiratoryRate);

        System.out.printf("Allergies: %s\n", allergies);

        // Ask for confirmation if the details are correct
        String confirmation = InputValidator.getValidStringInput("\nAre the details correct? (YES/NO): ");

        if (confirmation.equalsIgnoreCase("NO")) {
            // Allow the user to edit the details
            boolean editing = true;

            while (editing) {
                System.out.println("\nWhat would you like to edit?");
                System.out.println("1. Triage Level");
                System.out.println("2. Temperature");
                System.out.println("3. Heart Rate");
                System.out.println("4. Blood Pressure");
                System.out.println("5. Respiratory Rate");
                System.out.println("6. Allergies");
                System.out.println("7. Finish Editing");

                int editChoice = InputValidator.getValidRangeIntInput("Enter the number corresponding to the field you want to edit: ", 6);

                switch (editChoice) {
                    case 1:
                        // Edit Triage Level
                        triageChoice = InputValidator.getValidRangeIntInput("Enter the number corresponding to the patient's triage level: ", TriageLevel.values().length);
                        selectedTriageLevel = TriageLevel.values()[triageChoice - 1];
                        System.out.println("Updated Triage Level: " + selectedTriageLevel);
                        break;
                    case 2:
                        // Edit Temperature
                        temperature = InputValidator.getValidDoubleInput("Enter body temperature (°C): ");
                        break;
                    case 3:
                        // Edit Heart Rate
                        heartRate = InputValidator.getValidRangeIntInput("Enter Heart rate (beats per minute): ", 200);
                        break;
                    case 4:
                        // Edit Blood Pressure
                        bloodPressureSystolic = InputValidator.getValidRangeIntInput("Enter systolic blood pressure (mmHg): ", 200);
                        bloodPressureDiastolic = InputValidator.getValidRangeIntInput("Enter diastolic blood pressure (mmHg): ", 120);
                        break;
                    case 5:
                        // Edit Respiratory Rate
                        respiratoryRate = InputValidator.getValidRangeIntInput("Enter respiratory rate (breaths per minute): ", 40);  // 40 breaths/min max
                        break;
                    case 6:
                        // Edit Allergies
                        allergies = InputValidator.getValidStringInput("Enter patient's allergies (if any): ");
                        break;
                    case 7:
                        // Finish Editing
                        editing = false;
                        System.out.println("Editing finished. Final details:");
                        break;
                }

                // Print the updated details after editing
                System.out.println("\nUpdated Patient Initial Screening Details:");
                System.out.printf("Triage Level: %s\n", selectedTriageLevel);
                System.out.printf("Temperature: %.2f°C\n", temperature);
                System.out.printf("Heart Rate: %d bpm\n", heartRate);
                System.out.printf("Blood Pressure: %d/%d mmHg\n", bloodPressureSystolic, bloodPressureDiastolic);
                System.out.printf("Respiratory Rate: %d breaths per minute\n", respiratoryRate);
                System.out.printf("Allergies: %s\n", allergies);
            }
        } else {
            emergencyCase.setTriageLevel(selectedTriageLevel);
            Patient patient = emergencyCase.getPatient();
            patient.updatePatientVitalSigns(temperature, heartRate, bloodPressureSystolic, bloodPressureDiastolic, respiratoryRate);
            System.out.println("\nPatient Initial Screening Completed!");
        }
    }

    public static void doctorScreening(EmergencyCase emergencyCase) {
        Patient patient = emergencyCase.getPatient();
        Doctor doctor = emergencyCase.getScreeningDoctor();

        // Assign the doctor to the patient
        patient.setAssignedDoctor(doctor.getId());

        while (true) {
            System.out.println("----------------------------------------------");
            System.out.println("                 CASE DETAILS                 ");

            // Display case and patient info
            emergencyCase.displayCase();
            patient.displayPatientInfo();

            // Show doctor options
            showDoctorPatientOption();
            int doctorInput = InputValidator.getValidIntInput("Enter your choice: ");

            switch (doctorInput) {
                case 0:
                    return; // Done screening, go back to patient list

                case 1:
                    updatePatientVitalSigns(patient, doctor);  // Update vital signs
                    break;

                case 2:
                    updatePatientSymptoms(patient, doctor);    // Update symptoms
                    break;

                case 3:
                    diagnosePatient(patient, doctor);          // Diagnose patient
                    break;

                case 4:
                    prescribeMedications(patient, doctor); // Uncomment if implemented
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void showDoctorPatientOption() {
        System.out.println("\n========== Doctor Options ==========");
        System.out.println("1. Update Patient Vital Signs");
        System.out.println("2. Update Patient Symptoms");
        System.out.println("3. Diagnose Patient");
        System.out.println("4. Prescribe Medications");
        System.out.println("0. End Consultation");
    }

    /**
     * Updates the vital signs of the selected patient.
     *
     * @param patient      The patient whose vital signs are being updated.
     * @param doctor       The doctor performing the update.
     */

    private static void updatePatientVitalSigns(Patient patient, Doctor doctor) {
        System.out.println("========== Update Patient Vital Signs ==========");
        System.out.println("Printing " + patient.getName() + "'s current vital signs...");
        System.out.println(patient.getEHR().getVitalSigns().toString());  // Display current vital signs
        AuditManager.getInstance().logAction(doctor.getId(), "UPDATE PATIENT RECORD", "Patient: " + patient.getId(), "SUCCESS", "DOCTOR");

        // Prompt for new vital sign values
        double temperature = InputValidator.getValidDoubleInput("Please enter the temperature: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + temperature, "Patient: " + patient.getId() + "'s temperature", "SUCCESS", "DOCTOR");
        int hr = InputValidator.getValidIntInput("Please enter the heart rate: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + hr, "Patient: " + patient.getId() + "'s heart rate", "SUCCESS", "DOCTOR");
        int sysBloodPressure = InputValidator.getValidIntInput("Please enter the systolic blood pressure: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + sysBloodPressure, "Patient: " + patient.getId() + "'s systolic blood pressure", "SUCCESS", "DOCTOR");
        int diaBloodPressure = InputValidator.getValidIntInput("Please enter the diastolic blood pressure: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + diaBloodPressure, "Patient: " + patient.getId() + "'s diastolic blood pressure", "SUCCESS", "DOCTOR");
        int respiratory = InputValidator.getValidIntInput("Please enter the respiratory rate: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + respiratory, "Patient: " + patient.getId() + "'s respiratory rate", "SUCCESS", "DOCTOR");

        // Update patient's vital signs
        patient.getEHR().setVitalSigns(new VitalSigns(temperature, hr, sysBloodPressure, diaBloodPressure, respiratory));

        System.out.println(patient.getName() + "'s vital signs have been updated.");

    }

    /**
     * Updates the symptoms of the selected patient.
     *
     * @param patient      The patient whose symptoms are being updated.
     * @param doctor       The doctor performing the update.
     */
    private static void updatePatientSymptoms(Patient patient, Doctor doctor) {
        String symptomName = InputValidator.getValidStringInput("Enter patient's symptoms: ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + symptomName, "Patient: " + patient.getId() + "'s symptoms", "SUCCESS", "DOCTOR");
        int severity = InputValidator.getValidRangeIntInput("Enter symptom's severity (0-10): ", 10);
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + severity, "symptom's severity", "SUCCESS", "DOCTOR");
        int duration = InputValidator.getValidRangeIntInput("Enter duration of symptoms (days): ", 50);
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + duration, "duration of symptoms", "SUCCESS", "DOCTOR");
        String clinicianNotes = InputValidator.getValidStringWithSpaceInput("Doctor's notes:  ");
        AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + clinicianNotes, "doctor notes", "SUCCESS", "DOCTOR");

        Symptoms newSymptom = new Symptoms(symptomName, severity, duration, clinicianNotes);

        doctor.setPatientSymptoms(newSymptom, patient);  // Update patient's symptoms
        AuditManager.getInstance().logAction(doctor.getId(), "UPDATE SYMPTOMS", "Patient: " + patient.getId(), "SUCCESS", "DOCTOR");
    }

    /**
     * Prescribes medication to the selected patient.
     *
     * @param patient      The patient receiving the medication.
     * @param doctor       The doctor prescribing the medication.
     */

    public static void diagnosePatient(Patient patient, Doctor doctor) {
        // Get CDSS suggestions based on latest symptoms
        List<String> cdssDiagnosis = cdssAnalyzeSymptoms(patient.getEHR().getSymptoms().getLast());
        String outcome = "SUCCESS";

        System.out.println("======= CDSS Suggestions =======");
        for (int i = 0; i < cdssDiagnosis.size(); i++) {
            System.out.println((i + 1) + ". " + cdssDiagnosis.get(i));
        }

        while (true) {
            String doctorConfirmation = InputValidator.getValidStringInput(
                    "Doctor " + doctor.getName() + ", do you agree with the CDSS diagnosis? (yes/no): ");

            AuditManager.getInstance().logAction(
                    doctor.getId(),
                    "USER ENTERED: " + doctorConfirmation,
                    "CDSS diagnosis",
                    "SUCCESS",
                    "DOCTOR"
            );

            if (doctorConfirmation.equalsIgnoreCase("no")) {
                System.out.println("======== Override CDSS Diagnosis ========");
                String diagnosis = InputValidator.getValidStringWithSpaceInput("Enter your diagnosis: ");

                doctor.diagnosePatient(patient, diagnosis);
                outcome = "OVERRIDDEN";

                AuditManager.getInstance().logAction(doctor.getId(), "DIAGNOSE PATIENT", "Patient: " + patient.getId(), outcome, "DOCTOR");
                AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + diagnosis, "Override diagnosis", "SUCCESS", "DOCTOR");
                break;

            } else if (doctorConfirmation.equalsIgnoreCase("yes")) {
                int choice = InputValidator.getValidRangeIntInput(
                        "Enter choice of Diagnosis (1-" + cdssDiagnosis.size() + "): ",
                        cdssDiagnosis.size());

                String selectedDiagnosis = cdssDiagnosis.get(choice - 1);
                doctor.diagnosePatient(patient, selectedDiagnosis);

                AuditManager.getInstance().logAction(doctor.getId(), "DIAGNOSE PATIENT", "Patient: " + patient.getId(), outcome, "DOCTOR");
                break;

            } else {
                System.out.println("Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }

    /**
     * Analyzes the provided symptom using the Clinical Decision Support System (CDSS)
     * guidelines associated with the healthcare professional. It returns a list of
     * possible diagnoses based on matching clinical guidelines.
     *
     * <p>The method checks if the symptom matches any clinical guideline categorized
     * under "Symptom" and includes supporting evidence that corresponds to the provided
     * symptom's name. If no matches are found, it returns a default message indicating
     * that further tests are required.</p>
     *
     * @param symptom The {@link Symptoms} object representing the patient's symptom to be analyzed.
     * @return A {@link List} of possible diagnoses. If no matches are found, the list will contain
     * a default message: "Diagnosis Unclear - Further Tests Required".
     * @see ClinicalGuideline
     * @see Symptoms
     * @see Doctor
     */

    // Method to Analyze Symptoms and Return Diagnosis
    private static List<String> cdssAnalyzeSymptoms(Symptoms symptom) {
        List<String> CDSSDiagnosis = new ArrayList<>();

        for (ClinicalGuideline guidelineSymptom : clinicalGuidelines) {
            if (guidelineSymptom.getGuideLineType().equals("Symptom") &&
                    guidelineSymptom.getSupportingEvidence().contains(symptom.getSymptomName())) {
                CDSSDiagnosis.add(guidelineSymptom.getSymptomName());
            }
        }

        if (CDSSDiagnosis.isEmpty()) {
            CDSSDiagnosis.add("Diagnosis Unclear - Further Tests Required");
        }

        // Return possible Diagnosis
        return CDSSDiagnosis;
    }

    private static void prescribeMedications(Patient patient, Doctor doctor) {
        String diagnosis = patient.getEHR().getDiagnosis();

        if (diagnosis == null || diagnosis.isEmpty()) {
            System.out.println("⚠ Cannot prescribe medications without a diagnosis.");
            return;
        }

        System.out.println("Diagnosis: " + diagnosis);

        // Get CDSS recommendations
        List<Medication> cdssRecommendations = cdssAnalyzeDiagnosis(diagnosis);
        if (cdssRecommendations.isEmpty()) {
            System.out.println("No recommendations found from CDSS for: " + diagnosis);
            return;
        }

        System.out.println("======= CDSS Medication Suggestions =======");
        for (int i = 0; i < cdssRecommendations.size(); i++) {
            Medication med = cdssRecommendations.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, med.getMedicationName(), med.getDosage());
        }

        String outcome = "SUCCESS";

        while (true) {
            String confirm = InputValidator.getValidStringInput("Doctor " + doctor.getName() +
                    ", do you want to accept a CDSS suggestion? (yes/no): ");

            AuditManager.getInstance().logAction(doctor.getId(), "USER ENTERED: " + confirm, "CDSS prescription suggestion", "SUCCESS", "DOCTOR");

            if (confirm.equalsIgnoreCase("yes")) {
                int choice = InputValidator.getValidRangeIntInput("Select medication to prescribe: ", cdssRecommendations.size());
                Medication selectedMed = cdssRecommendations.get(choice - 1);

                doctor.prescribeMedication(patient, selectedMed);

                AuditManager.getInstance().logAction(doctor.getId(), "PRESCRIBED MEDICATION", selectedMed.getMedicationName(), outcome, "DOCTOR");
                System.out.println("Medication prescribed: " + selectedMed.getMedicationName());
                break;

            } else if (confirm.equalsIgnoreCase("no")) {
                System.out.println("======== Manual Prescription Entry ========");

                String medName = InputValidator.getValidStringWithSpaceInput("Enter medication name: ");

                // Try to fetch from available medications
                Medication availableMed = MedicationController.findAvailableMedicationByName(medName);
                Medication customMed;

                if (availableMed != null) {
                    System.out.println("Medication found: " + availableMed.getMedicationName() + " (" + availableMed.getDosage() + ")");

                    String override = InputValidator.getValidStringInput("Use specific dosage/frequency? (yes/no): ");
                    if (override.equalsIgnoreCase("yes")) {
                        String dosage = InputValidator.getValidStringInput("Enter dosage (e.g. 500mg): ");
                        String frequency = InputValidator.getValidStringInput("Enter frequency (e.g. once daily): ");
                        String combined = dosage + ", " + frequency;

                        // Create new med object with custom dosage/frequency
                        customMed = new Medication(
                                availableMed.getMedicationId(),
                                availableMed.getMedicationName(),
                                combined
                        );
                    } else {
                        customMed = availableMed;
                    }

                } else {
                    // Full manual entry
                    System.out.println("Medication not found in system. Creating a custom medication record.");
                    String dosage = InputValidator.getValidStringInput("Enter dosage (e.g. 500mg): ");
                    String frequency = InputValidator.getValidStringInput("Enter frequency (e.g. once daily): ");
                    String combined = dosage + ", " + frequency;

                    customMed = new Medication(null, medName, combined);
                }

                doctor.prescribeMedication(patient, customMed);
                AuditManager.getInstance().logAction(doctor.getId(), "PRESCRIBED MEDICATION", customMed.getMedicationName(), "OVERRIDDEN", "DOCTOR");

                System.out.println("Medication manually prescribed: " + customMed.getMedicationName());
                break;
            } else {
                System.out.println("Invalid input! Please type 'yes' or 'no'.");
            }
        }
    }

    // Method to Analyze Diagnosis and Return Medication Suggestion
    private static List<Medication> cdssAnalyzeDiagnosis(String diagnosis) {
        List<Medication> CDSSPrescription = new ArrayList<>();
        List<Medication> medicationList = MedicationController.getAvailableMedications();
        String formattedDiagnosis = diagnosis.substring(0, 1).toUpperCase() + diagnosis.substring(1).toLowerCase();

        for (ClinicalGuideline guidelineMedication : clinicalGuidelines) {
            if (guidelineMedication.getGuideLineType().equalsIgnoreCase("Medication") &&
                    (guidelineMedication.getSupportingEvidence().toLowerCase().contains(diagnosis.toLowerCase()) ||
                            guidelineMedication.getSupportingEvidence().contains(formattedDiagnosis))) {

                System.out.println(); // Optional — can remove or replace with a label

                for (Medication med : medicationList) {
                    if (guidelineMedication.getMedicationName() != null &&
                            guidelineMedication.getMedicationName().equalsIgnoreCase(med.getMedicationName())) {
                        CDSSPrescription.add(med);
                    }
                }
            }
        }

        // Return possible Diagnosis
        return CDSSPrescription;
    }

    public static void immediateResponse(EmergencyCase emergencyCase) {
        System.out.println("=== Immediate Response: Emergency Procedures ===");

        while (true) {
            String procedure = InputValidator.getValidStringWithSpaceInput(
                    "Enter emergency procedure (type 'done' to finish): ");

            if (procedure.equalsIgnoreCase("done")) {
                break;
            }

            emergencyCase.addEmergencyProcedure(procedure); // Add to the case

            AuditManager.getInstance().logAction("SYSTEM", "ADDED PROCEDURE", procedure, "SUCCESS", "SYSTEM");

            System.out.println("Procedure added: " + procedure);
        }

        System.out.println("\nAll procedures recorded for Case ID: " + emergencyCase.getCaseID());
    }

    public static int setCaseID() { // Finds the largest existing case ID and returns the next available ID
        int highestID = 0; // initialize the highest ID

        if (!allCases.isEmpty()) {
            for (EmergencyCase x : allCases) {
                if (x.getCaseID() > highestID)
                    highestID = x.getCaseID(); // update the highest ID
            }
        }

        if (!allDispatchCases.isEmpty()) {
            for (EmergencyCase_Dispatch x : allDispatchCases) {
                if (x.getCaseID() > highestID)
                    highestID = x.getCaseID(); // update the highest ID
            }
        }
        return highestID + 1; // return the next available ID
    }



    public static void addResolvedCases(EmergencyCase_Dispatch dc) {

        int caseID;
        caseID = ESController.setCaseID();
        dc.setCaseID(caseID);
        dc.setPatientStatus(PatientStatus.WAITING);
        allCases.add(dc);
        saveEmergencyCasesToFile();

    }

}


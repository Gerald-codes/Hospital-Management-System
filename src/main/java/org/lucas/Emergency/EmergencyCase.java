package org.lucas.Emergency;

import CDSS.Patient.Patient;
import CDSS.User.HealthcareProfessional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * EmergencyCase class represents a single emergency case in the emergency department.
 * It stores information about the patient, with other recordable data and emergency procedures performed on the patient.
 * Methods provided to update the patient's status, location, triage level, and treatment,
 * as well as to add emergency procedures and print a comprehensive incident report.
 */

public class EmergencyCase {
    // Enum to track the current status of a patient in the emergency system
    public enum PatientStatus {
        DISCHARGED,
        ADMITTED,
        TRANSFERRED,
        WAITING,
        ONDISPATCHED; // Added WAITING as initial status
    }

    // attributes;
    private int caseID;
    private Patient patient; // storing a single Patient
    private String chiefComplaint;
    private String arrivalMode; // Ambulance, Helicopter, Walk-in
    private LocalDateTime arrivalDateTime;
    private String triageLevel; // Enum or String based on severity
    private List<HealthcareProfessional> initialScreeningNurse; // list of either nurse, doctor, paramedic etc // Connect to staff
    // class - store
    private List<HealthcareProfessional> screeningdoctor; // staff member ID
    private LocalDateTime dateAndTimeOfScreening;
    private String location; // Current location of the patient (e.g., Waiting Room, Treatment Room)
    private PatientStatus patientStatus; // Admitted, Discharged, etc.
    private boolean isUrgentTreatment; // True False
    private List<String> emergencyProcedures; // List of emergency procedures done on patient

    // standard triage levels used in the emergency department
    private static final String[] TRIAGE_LEVELS = {
            "PRIORITY 1: CRITICALLY-ILL", // Life-threatening
            "PRIORITY 2: MAJOR EMERGENCIES (NON-AMBULANT)", // Urgent but not immediately life-threatening
            "PRIORITY 3: MINOR EMERGENCIES (AMBULANT)", // Minor emergencies
            "PRIORITY 4: NON-EMERGENCY" // Not serious
    };

    // helper method to check if the triage level is valid
    public static boolean isValidTriageLevel(String triageLevel) {
        for (String validLevel : TRIAGE_LEVELS) {
            if (validLevel.equals(triageLevel)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Constructor for EmergencyCase
     * @param caseID - unique identifier for the case
     * @param patient - patient object
     * @param chiefComplaint - reason for patient's visit
     * @param arrivalMode - mode of arrival (e.g., Ambulance, Helicopter, Walk-in)
     * @param arrivalDateTime - date and time of arrival
     */
    // Constructor //registration
    public EmergencyCase(int caseID, Patient patient, String chiefComplaint, String arrivalMode,
                         LocalDateTime arrivalDateTime) {
        this.caseID = caseID;
        // this.patient = patient;
        this.patient = patient;
        this.chiefComplaint = chiefComplaint; // reason for patient's visit
        this.arrivalMode = arrivalMode;
        // Only set to now if arrivalDateTime is null (e.g., for new cases, not loaded
        // ones)
        this.arrivalDateTime = (arrivalDateTime == null) ? LocalDateTime.now() : arrivalDateTime;

        this.emergencyProcedures = new ArrayList<>();
        this.patientStatus = PatientStatus.WAITING;
        this.location = "Emergency room - Waiting Room"; // Set default location
        this.isUrgentTreatment = false; // Default to non-urgent
        this.triageLevel = TRIAGE_LEVELS[3]; // Default triage level

    }
    /**
     * Constructor for EmergencyCase
     * @param caseID - unique identifier for the case
     *  @param patient - patient object
     * @param chiefComplaint - reason for patient's visit
     * @param arrivalMode - mode of arrival (e.g., Ambulance, Helicopter, Walk-in)
     * @param patientStatus - current status of the patient
     */
    // Dont want the set arrivaldateTime to be in the constructor
    public EmergencyCase(int caseID, Patient patient, String chiefComplaint, String arrivalMode,
                         PatientStatus patientStatus) {
        this.caseID = caseID;
        // this.patient = patient;
        this.patient = patient;
        this.chiefComplaint = chiefComplaint; // reason for patient's visit
        this.arrivalMode = arrivalMode;
        this.patientStatus = patientStatus;

        if (patientStatus == PatientStatus.ONDISPATCHED)
            this.location = "Dispatch In Progress";

        this.emergencyProcedures = new ArrayList<>();
        this.isUrgentTreatment = false;
        this.triageLevel = TRIAGE_LEVELS[3];
    }
    /**
     * Set Method to set the arrival date time for the patient
     * @param time
     */
    public void SetArrivalDateTime(LocalDateTime time) {
        arrivalDateTime = time;

    }
    /**
     * Get Method to get the arrival date time for the patient
     */
    public LocalDateTime GetArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Get Method to get the case ID for the patient
     */
    public int getCaseID() {
        return caseID;
    }

    /**
     * Get Method to get the triage level for the patient
     */
    public String getTriageLevel() {
        return this.triageLevel;
    }

    /**
     * Set Method to set the location of the patient
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * Set method for the triage level for the patient and update the patient's location
     * and urgency status based on the triage level
     * @param triageLevelInput
     */
    public void setTriageLevel(String triageLevelInput) {
        // check if the triage level is valid
        if (isValidTriageLevel(triageLevelInput)) {
            this.triageLevel = triageLevelInput;
            // For Priority 1 (Critically-ill) or Priority 2 (Major emergencies)
            if (triageLevel == TRIAGE_LEVELS[0] || triageLevel == TRIAGE_LEVELS[1]) {
                // set location to trauma room
                location = "Emergency room - Trauma Room";
                // set isUrgentTreatment to true
                isUrgentTreatment = true;

            } else if (triageLevel == TRIAGE_LEVELS[2]) {
                // set location to observation unit
                location = "Emergency room - Observation Unit";
                // set isUrgentTreatment to false
                isUrgentTreatment = false;
            } else {
                // set location to waiting room
                location = "Emergency room - Waiting Room";
                // set isUrgentTreatment to false
                isUrgentTreatment = false;
            }
        } else {
            // if the triage level is not valid, throw an exception
            throw new IllegalArgumentException(
                    "Invalid triage level. Valid levels are: " + String.join(", ", TRIAGE_LEVELS));
        }
    }

    /**
     * Get method for the date and time of the screening
     */
    public LocalDateTime getScreeningTime() {
        return this.dateAndTimeOfScreening;
    }

    /**
     * Get method for the status (WAITING, DISCHARGE, etc) of the patient
     */
    public PatientStatus getPatientStatus() {
        return this.patientStatus;
    }

    /**
     * Get method for the location of the patient
     */
    public String getCurrentLocation() {
        return this.location;
    }

    /**
     * Get method for the emergency procedures performed on the patient
     */
    public List<String> getEmergencyProcedures() {
        return this.emergencyProcedures;
    }

    /**
     * Get method to get the patient object
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * Get method to get the chief complaint of the patient
     */
    public String getChiefComplaint() {
        return this.chiefComplaint;
    }

    /**
     * Get method for the arrival mode of the patient (e.g., Ambulance, Helicopter, Walk-in)
     */
    public String getArrivalMode() {
        return this.arrivalMode;
    }

    /**
     * Get method for the arrival date and time of the patient
     */
    public LocalDateTime getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    /**
     * Set method for the treatment of the patient and update the patient's location,
     * screening doctor, triage level, and emergency procedures based on the urgency of the treatment
     * @param isUrgent  - boolean value to indicate if the treatment is urgent
     * @param staffMember - staff member object
     * @param procedures - emergency procedures performed on the patient
     */
    public void setTreatment(boolean isUrgent, HealthcareProfessional staffMember, String procedures) {
        this.isUrgentTreatment = isUrgent;
        if (isUrgent) { // true
            location = "Emergency room - Trauma Room"; // update patient location
            screeningdoctor = new ArrayList<>(); // Initialize if null
            screeningdoctor.add(staffMember);
            triageLevel = TRIAGE_LEVELS[0]; // update triage level
            emergencyProcedures.add(procedures); // add emergency procedures
        } else {
            location = "Emergency room - Observation Unit";
            initialScreeningNurse = new ArrayList<>(); // Initialize if null
            initialScreeningNurse.add(staffMember);
            triageLevel = TRIAGE_LEVELS[3];
            emergencyProcedures.add(procedures);
        }
        this.dateAndTimeOfScreening = LocalDateTime.now();
    }

    /**
     * Get method for the triage levels used in the emergency department
     * @return an array of triage levels
     */
    public static String[] getTriageLevels() {
        return TRIAGE_LEVELS.clone(); // Return a copy to prevent modification
    }

    /**
     * Get method for the urgency of the treatment
     * @return boolean value indicating if the treatment is urgent
     */
    public boolean isUrgent() {
        return this.isUrgentTreatment;
    }

    /**
     * Get method for the urgency of the treatment
     * @return boolean value indicating if the treatment is urgent
     */
    public boolean isUrgentTreatment() {
        return "PRIORITY 1: CRITICALLY-ILL".equals(this.triageLevel);
    }

    /**
     * Update the initial screening of the patient by a staff member
     * @param staffMember - staff member object
     * @param triageLevel - triage level of the patient
     * @param vitalSigns - vital signs of the patient
     * @throws IllegalArgumentException if the triage level is invalid
     */
    public void updateInitialScreening(HealthcareProfessional staffMember, String triageLevel, String vitalSigns) {
        if (!isValidTriageLevel(triageLevel)) {
            throw new IllegalArgumentException(
                    "Invalid triage level. Valid levels are: " + String.join(", ", TRIAGE_LEVELS));
        }
        setTriageLevel(triageLevel); // This will automatically update location
//        this.patient.setEHR(.vitalSigns);
        ;
        this.initialScreeningNurse = new ArrayList<>();
        this.initialScreeningNurse.add(staffMember);
        this.dateAndTimeOfScreening = LocalDateTime.now();
        this.isUrgentTreatment = triageLevel.equals("PRIORITY 1: CRITICALLY-ILL");

        System.out.println("Initial Screening Completed:");
        System.out.println("Staff Member: " + staffMember.getName());
        System.out.println("Triage Level: " + triageLevel);
        System.out.println("Vital Signs: " + vitalSigns);
        System.out.println("Patient Location: " + location);
    }

//    /**
//     * Update the initial screening of the patient by a staff member
//     * @param HealthcareProfessional - staff member object
//     * @param triageLevel - triage level of the patient
//     * @param vitalSigns - vital signs of the patient
//     * @param location - location of the patient
//     * @throws IllegalArgumentException if the triage level is invalid
//     */
    public void setInitialScreeningNurses(List<HealthcareProfessional> nurses) {
        this.initialScreeningNurse = nurses;
    }

    /**
     * Get the initial screening nurses
     * @return a list of staff members who performed the initial screening
     */
    public List<HealthcareProfessional> getInitialScreeningNurses() {
        return this.initialScreeningNurse;
    }

    /**
     * Set the screening doctors
     * @param doctors
     */
    public void setScreeningDoctors(List<HealthcareProfessional> doctors) {
        this.screeningdoctor = doctors;
    }
    /**
     * Get the screening doctors
     * @return a list of staff members who performed the screening
     */
    public List<HealthcareProfessional> getScreeningDoctors() {
        return this.screeningdoctor;
    }

    /**
     * Set the date and time of the screening
     * @param dateTime
     */
    public void setDateAndTimeOfScreening(LocalDateTime dateTime) {
        this.dateAndTimeOfScreening = dateTime;
    }

    /**
     * Get the date and time of the screening
     * @return the date and time of the screening
     */
    public LocalDateTime getDateAndTimeOfScreening() {
        return this.dateAndTimeOfScreening;
    }

    /**
     * Update the doctor screening of the patient
     * @param doctor - staff member object
     * @param updatedLocation - updated location of the patient
     */
    public void updateDoctorScreening(HealthcareProfessional doctor, String updatedLocation) {
        this.screeningdoctor = new ArrayList<>(); //initialize a new list for screening doctor
        this.screeningdoctor.add(doctor); //add the doctor to the list
        this.location = updatedLocation; //update the location
        this.dateAndTimeOfScreening = LocalDateTime.now(); //update the date and time of screening
        // Set urgent treatment flag based on triage level
        // Only Priority 1 cases are marked as urgent
        this.isUrgentTreatment = triageLevel.equals("PRIORITY 1: CRITICALLY-ILL");

        // print the results
        System.out.println("Doctor Screening Completed:");
        System.out.println("Attending Physician: " + doctor.getName());
        System.out.println("Updated Patient Location: " + updatedLocation);

    }

    /**
     * Add emergency procedures performed on the patient
     * @param procedure - emergency procedure performed on the patient
     */
    public void addEmergencyProcedure(String procedure) { // write any procedures performed on patients
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedProcedure = timestamp.format(formatter) + " - " + procedure;
        emergencyProcedures.add(formattedProcedure);
        System.out.println("Emergency Procedure Recorded: " + formattedProcedure);
    }

    /**
     * Update the status of the patient and the location based on the current status
     * @param currentStatus - current status of the patient
     */
    public void updatePatientStatus(PatientStatus currentStatus) {
        // Avoid redundant updates (prevents multiple WAITING entries)
        if (this.patientStatus == currentStatus) {
            return; // No need to update if the status is already the same
        }
        // update the patient status
        this.patientStatus = currentStatus;
        // switch case to update the location based on the current status
        switch (currentStatus) {
            case DISCHARGED:
                this.location = "Discharge Area"; // patient discharged
                break;
            case ADMITTED:
                this.location = "Hospital Ward"; // patient admitted
                break;
            case TRANSFERRED:
                this.location = "Transferred to Another Facility"; // patient transferred
                break;
            case ONDISPATCHED:
                this.location = "Dispatch In Progress"; // patient on dispatched
                break;
            default:
                this.location = "Emergency Department"; // default location
                break;
        }

        // print the results
        System.out.println("Patient Status Updated: " + patientStatus);
        System.out.println("New Location: " + location);
    }

    /**
     * Print the emergency procedures performed on the patient
     */
    public void printPatientInfo() {
        String output = "Patient ID: " + patient.getPatientID() + "\n";
        output += "Patient Name: " + patient.getPatientName() + "\n";
        // output += "Patient Age: " + patient.getPatientAge() + "\n";
        System.out.println(output);

    }

    /**
     * Print the emergency procedures performed on the patient
     */
    public String printIncidentReport() {
        // create a new string builder
        StringBuilder report = new StringBuilder();
        report.append("\n=== Emergency Case Report ===\n"); // print the header
        report.append("Case ID: ").append(caseID).append("\n"); // print the case ID
        report.append("Patient ID: ").append(patient.getPatientID()).append("\n"); // print the patient ID
        report.append("Patient Name: ").append(patient.getPatientName()).append("\n"); // print the patient name
        if (arrivalDateTime != null)
            report.append("Date and Time of Incident: ").append(arrivalDateTime.format(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).append("\n"); // print the date and time of incident
        else
            report.append("Date and Time of Incident: Dispatch In Progress\n"); // print the date and time of incident
        report.append("Arrival Mode: ").append(arrivalMode).append("\n"); // print the arrival mode
        report.append("Chief Complaint: ").append(chiefComplaint).append("\n"); // print the chief complaint
        report.append("Triage Level: ").append(triageLevel).append("\n"); // print the triage level

        /**
         * Handle initial screening nurse
         * If the initial screening nurse is not null and not empty, print the nurse's name and ID
         */
        if (initialScreeningNurse != null && !initialScreeningNurse.isEmpty()) {
            HealthcareProfessional nurse = initialScreeningNurse.get(0);
            report.append("Initial Screening Nurse: ")
                    .append(nurse.getName())
                    .append(" (ID: ").append(nurse.getId()).append(")\n"); // print the initial screening nurse
        }

        /**
         * Handle screening doctor
         * If the screening doctor is not null and not empty, print the doctor's name and ID
         */
        if (dateAndTimeOfScreening != null) {
            report.append("Screening Time: ")
                    .append(dateAndTimeOfScreening.format(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .append("\n"); // print the screening time
        }

        /**
         * Handle screening doctor
         * If the screening doctor is not null and not empty, print the doctor's name and ID
         */
//        if (patient.getVitalSignsHistory() != null && !patient.getVitalSignsHistory().isEmpty()) {
//            report.append("Vital Signs: ").append(patient.getVitalSignsHistory().get(0)).append("\n"); // print the vital signs
//        }

        /**
         * Handle screening doctor
         * If the screening doctor is not null and not empty, print the doctor's name and ID
         */
        if (screeningdoctor != null && !screeningdoctor.isEmpty()) {
            HealthcareProfessional doctor = screeningdoctor.get(0);
            report.append("Attending Doctor: ")
                    .append(doctor.getName())
                    .append(" (ID: ").append(doctor.getId()).append(")\n"); // print the attending doctor
        }

        /**
         * Handle emergency procedures
         * If the emergency procedures list is not null and not empty, print the emergency procedures
         */
        if (emergencyProcedures != null && !emergencyProcedures.isEmpty()) {
            report.append("Emergency Procedures:\n"); // print the emergency procedures
            for (String procedure : emergencyProcedures) {
                report.append("- ").append(procedure).append("\n"); // print the emergency procedures
            }
        }

        /**
         * Handle urgent treatment
         * If the treatment is urgent, print the urgent treatment details
         */
        report.append("\nCurrent Status: ").append(patientStatus).append("\n"); // print the current status
        report.append("Current Location: ").append(location).append("\n"); // print the current location

        return report.toString();
    }

//    public static void main(String[] args) {
//        // Create a test patient
//        Patient testPatient = new Patient(1001, "John Doe");
//
//        // Create some staff members
//        StaffMember nurse = new StaffMember("Sarah Smith", 1234567, "Nurse");
//        StaffMember doctor = new StaffMember("Dr. James Wilson", 7654321, "Doctor");
//
//        // Create an emergency case
//        EmergencyCase emergencyCase = new EmergencyCase(
//                1,
//                testPatient,
//                "Chest Pain",
//                "Ambulance",
//                LocalDateTime.now());
//
//        // Test initial screening by nurse
//        System.out.println("\n=== Testing Initial Screening ===");
//        emergencyCase.updateInitialScreening(
//                nurse,
//                TRIAGE_LEVELS[2], // PRIORITY 2: MAJOR EMERGENCIES
//                "BP: 150/90, HR: 95, Temp: 37.5");
//
//        // Test adding emergency procedures
//        System.out.println("\n=== Testing Emergency Procedures ===");
//        emergencyCase.addEmergencyProcedure("ECG performed");
//        emergencyCase.addEmergencyProcedure("Blood samples taken");
//
//        // Test urgent treatment scenario
//        System.out.println("\n=== Testing Urgent Treatment ===");
//        emergencyCase.setTreatment(true, doctor, "Administered nitroglycerin");
//
//        // Test updating patient status
//        System.out.println("\n=== Testing Status Update ===");
//        emergencyCase.updatePatientStatus(PatientStatus.ADMITTED);
//
//        // Test doctor screening
//        System.out.println("\n=== Testing Doctor Screening ===");
//        emergencyCase.updateDoctorScreening(doctor, "Cardiac Unit");
//
//        // Print final comprehensive report
//        System.out.println("\n=== Final Emergency Case Report ===");
//        System.out.println(emergencyCase.printIncidentReport());
//
//        // Print emergency procedures
//        System.out.println("\n=== Emergency Procedures List ===");
//        // emergencyCase.printEmergencyProcedures();
//
//        // Test invalid triage level (should throw exception)
//        try {
//            System.out.println("\n=== Testing Invalid Triage Level ===");
//            emergencyCase.updateInitialScreening(
//                    nurse,
//                    "INVALID LEVEL",
//                    "BP: 120/80");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Caught expected exception: " + e.getMessage());
//        }
//    }

}

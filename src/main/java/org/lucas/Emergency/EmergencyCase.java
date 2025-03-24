package org.lucas.Emergency;

import org.lucas.models.*;
import org.lucas.Emergency.enums.*;
import org.lucas.models.enums.TriageLevel;

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

    private int caseID;
    private Patient patient; // storing a single Patient
    private String chiefComplaint;
    private String arrivalMode; // Ambulance, Helicopter, Walk-in
    private LocalDateTime arrivalDateTime;
    private TriageLevel triageLevel; // Enum or String based on severity
    private Nurse initialScreeningNurse; // list of either nurse, doctor, paramedic etc // Connect to staff
    private Doctor asssignedDoctor; // staff member ID
    private PatientLocation location; // Current location of the patient (e.g., Waiting Room, Treatment Room)

    private PatientStatus patientStatus; // Admitted, Discharged, etc.
    private List<String> emergencyProcedures; // List of emergency procedures done on patient
    private LocalDateTime dateAndTimeOfScreening;
    private boolean isUrgent;
    // standard triage levels used in the emergency department

    // helper method to check if the triage level is valid
    public static boolean isValidTriageLevel(String triageLevel) {
        for (TriageLevel level : TriageLevel.values()) {
            // Compare using the enum's name or description as needed
            if (level.name().equalsIgnoreCase(triageLevel) || level.getDescription().equalsIgnoreCase(triageLevel)) {
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

    public EmergencyCase(int caseID, Patient patient, String chiefComplaint, String arrivalMode,
                         LocalDateTime arrivalDateTime,boolean isUrgent) {
        this.caseID = caseID;
        this.patient = patient;
        this.chiefComplaint = chiefComplaint; // reason for patient's visit
        this.arrivalMode = arrivalMode;
        // Only set to now if arrivalDateTime is null (e.g., for new cases, not loaded
        // ones)
        this.arrivalDateTime = (arrivalDateTime == null) ? LocalDateTime.now() : arrivalDateTime;
        this.emergencyProcedures = new ArrayList<>();
        this.patientStatus = PatientStatus.WAITING;
        this.location = isUrgent ? PatientLocation.EMERGENCY_ROOM_TRAUMA_ROOM : PatientLocation.EMERGENCY_ROOM_WAITING_ROOM ; // Set default location
        this.triageLevel = isUrgent ? TriageLevel.PRIORITY_1_CRITICAL : TriageLevel.PRIORITY_4_NON_EMERGENCY;
        this.isUrgent = isUrgent;

    }
    /**
     * Constructor for EmergencyCase
     * @param caseID - unique identifier for the case
     *  @param patient - patient object
     * @param chiefComplaint - reason for patient's visit
     * @param arrivalMode - mode of arrival (e.g., Ambulance, Helicopter, Walk-in)
     * @param patientStatus - current status of the patient
     */

    // Dont want the set ArrivalTimeDate to be in the constructor
    // FOR DISPATCH
    public EmergencyCase(int caseID, Patient patient, String chiefComplaint, String arrivalMode,
                         PatientStatus patientStatus, boolean isUrgent) {
        this.caseID = caseID;
        // this.patient = patient;
        this.patient = patient;
        this.chiefComplaint = chiefComplaint; // reason for patient's visit
        this.arrivalMode = arrivalMode;
        this.patientStatus = patientStatus;

//        if (patientStatus == PatientStatus.ONDISPATCHED)
//            this.location = "Dispatch In Progress";

        this.emergencyProcedures = new ArrayList<>();
        this.triageLevel = isUrgent ? TriageLevel.PRIORITY_1_CRITICAL : TriageLevel.PRIORITY_4_NON_EMERGENCY;
        this.isUrgent = isUrgent;


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

    public void setPatientStatus(PatientStatus status){
        this.patientStatus = status;
    }

    /**
     * Get Method to get the case ID for the patient
     */
    public int getCaseID() {
        return caseID;
    }


    public LocalDateTime getScreeningTime() {
        return this.dateAndTimeOfScreening;
    }

    public void setTriageLevel(TriageLevel triageLevel){
        this.triageLevel = triageLevel;
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
    public PatientLocation getLocation() {
        return this.location;
    }

    public TriageLevel getTriageLevel(){
        return this.triageLevel;
    }
    public void setLocation(PatientLocation location) {
        this.location = location;
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
//    public void setTreatment(boolean isUrgent, User staffMember, String procedures) {
//        this.isUrgent = isUrgent;
//        if (isUrgent) { // true
//            location = "Emergency room - Trauma Room"; // update patient location
//            screeningdoctor = new ArrayList<>(); // Initialize if null
//            screeningdoctor.add(staffMember);
////            triageLevel = TRIAGE_LEVELS[0]; // update triage level
//            emergencyProcedures.add(procedures); // add emergency procedures
//        } else {
//            location = "Emergency room - Observation Unit";
//            initialScreeningNurse = new ArrayList<>(); // Initialize if null
//            initialScreeningNurse.add(staffMember);
////            triageLevel = TRIAGE_LEVELS[3];
//            emergencyProcedures.add(procedures);
//        }
//        this.dateAndTimeOfScreening = LocalDateTime.now();
//    }

    /**
     * Get method for the urgency of the treatment
     * @return boolean value indicating if the treatment is urgent
     */
    public boolean isUrgent() {
        return this.isUrgent;
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
//    public void updateInitialScreening(User staffMember, String triageLevel, String vitalSigns) {
//        if (!isValidTriageLevel(triageLevel)) {
//            throw new IllegalArgumentException(
//                    "Invalid triage level. Valid levels are: " + String.join(", ", TRIAGE_LEVELS));
//        }
//        setTriageLevel(triageLevel); // This will automatically update location
////        this.patient.setEHR(.vitalSigns);
//        ;
//        this.initialScreeningNurse = new ArrayList<>();
//        this.initialScreeningNurse.add(staffMember);
//        this.dateAndTimeOfScreening = LocalDateTime.now();
//        this.isUrgentTreatment = triageLevel.equals("PRIORITY 1: CRITICALLY-ILL");
//
//        System.out.println("Initial Screening Completed:");
//        System.out.println("Staff Member: " + staffMember.getName());
//        System.out.println("Triage Level: " + triageLevel);
//        System.out.println("Vital Signs: " + vitalSigns);
//        System.out.println("Patient Location: " + location);
//    }
//
//
//    public void setInitialScreeningNurses(List<User> nurses) {
//        this.initialScreeningNurse = nurses;
//    }

    /**
     * Get the initial screening nurses
     * @return a list of staff members who performed the initial screening
     */
    public Nurse getInitialScreeningNurses() {
        return this.initialScreeningNurse;
    }

    public void setInitialScreeningNurse(Nurse nurse) {
        this.initialScreeningNurse = nurse;
    }

    public void setAssignedDoctor(Doctor doctor) {
        this.asssignedDoctor = doctor;
    }
    /**
     * Get the screening doctors
     * @return a list of staff members who performed the screening
     */
    public Doctor getScreeningDoctor() {
        return this.asssignedDoctor;
    }

    /**
     * Set the date and time of the screening
     * @param dateTime
     */
    public void setDateAndTimeOfScreening(LocalDateTime dateTime) {
        this.dateAndTimeOfScreening = dateTime;
    }

    public void setArrivalDateTime(LocalDateTime dateTime) {
        this.arrivalDateTime = dateTime;
    }

    /**
     * Get the date and time of the screening
     * @return the date and time of the screening
     */
    public LocalDateTime getDateAndTimeOfScreening() {
        return this.dateAndTimeOfScreening;
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
     * Print the emergency procedures performed on the patient
     */
    public String printIncidentReport() {
        // create a new string builder
        StringBuilder report = new StringBuilder();
        report.append("\n=== Emergency Case Report ===\n"); // print the header
        report.append("Case ID: ").append(caseID).append("\n"); // print the case ID
        report.append("Patient ID: ").append(patient.getId()).append("\n"); // print the patient ID
        report.append("Patient Name: ").append(patient.getName()).append("\n"); // print the patient name
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
        if (initialScreeningNurse != null) {
            report.append("Initial Screening Nurse: ")
                    .append(initialScreeningNurse.getName())
                    .append(" (ID: ").append(initialScreeningNurse.getId()).append(")\n"); // print the initial screening nurse
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
        if (asssignedDoctor != null ) {
            report.append("Attending Doctor: ")
                    .append(asssignedDoctor.getName())
                    .append(" (ID: ").append(asssignedDoctor.getId()).append(")\n"); // print the attending doctor
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

    public String toString() {
        return "EmergencyCase{" +
                "caseID=" + caseID +
                ", patient=" + patient +
                ", chiefComplaint='" + chiefComplaint + '\'' +
                ", arrivalMode='" + arrivalMode + '\'' +
                ", arrivalDateTime=" + arrivalDateTime +
                ", triageLevel='" + triageLevel + '\'' +
                ", initialScreeningNurse=" + initialScreeningNurse +
                ", assignedDoctor=" + asssignedDoctor +
                ", location='" + location + '\'' +
                ", patientStatus=" + patientStatus +
                ", emergencyProcedures=" + emergencyProcedures +
                ", dateAndTimeOfScreening=" + dateAndTimeOfScreening +
                ", isUrgentTreatment=" + isUrgent +
                '}';
    }

    public void displayCase(){
        System.out.println("----------------------------------------------");

        // Print each emergency case's details in a readable format
        System.out.printf("Case ID: %d\n", this.getCaseID());
        System.out.printf("Patient Name: %s\n", this.getPatient().getName());
        System.out.printf("Location: %s\n", this.getLocation());
        System.out.printf("Chief Complaint: %s\n", this.getChiefComplaint());
        System.out.printf("Arrival Mode: %s\n", this.getArrivalMode());
        System.out.printf("Arrival Date & Time: %s\n", this.getArrivalDateTime());
        System.out.printf("Triage Level: %s\n", this.getTriageLevel());
        System.out.printf("Patient Status: %s\n", this.getPatientStatus());
        System.out.printf("Urgent: %s\n", this.isUrgent() ? "YES" : "NO");

        // If it's a dispatch case, print additional details
        if (this instanceof EmergencyCase_Dispatch dispatchCase) {
            // Print Dispatch Info
            System.out.println("=== Dispatch Info ===");
            System.out.printf("Vehicle ID: %d\n", dispatchCase.getDispatchInfo().getVehicleId());
            System.out.println("Medivac Members:");
            for (Nurse nurse : dispatchCase.getDispatchInfo().getMedivacMembers()) {
                System.out.println("  - " + nurse.getName()); // Assuming Nurse has a getName() method
            }

            System.out.println("Equipment:");
            for (String equipment : dispatchCase.getDispatchInfo().getEquipment()) {
                System.out.println("  - " + equipment);
            }

            System.out.printf("Dispatch Location: %s\n", dispatchCase.getDispatchInfo().getDispatchLocation());
            System.out.printf("Dispatch Arrival Time: %s\n", dispatchCase.getDispatchArrivalTime());
            System.out.printf("Time of Call: %s\n", dispatchCase.getTimeOfCall());
            System.out.printf("Response Time: %s\n", dispatchCase.getResponseTime().toMinutes() + " minutes");
        }

        System.out.println("---------------------------------");
    }
}

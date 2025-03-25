package org.lucas.Emergency;

import org.lucas.models.Nurse;
import org.lucas.models.Patient;
import org.lucas.controllers.UserController;
import org.lucas.models.enums.TriageLevel;
import org.lucas.util.InputValidator;
import org.lucas.Emergency.enums.PatientStatus;
import org.lucas.models.Patient;
import org.lucas.Emergency.EmergencyCase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class that inherits from base EmergencyCase class.
 * Used for creating emergency cases that requires a medivac team to be dispatched to a patient's location.
 */
public class EmergencyCase_Dispatch extends EmergencyCase {
    /**
     * Constructor for EmergencyCase
     *
     * @param caseID          - unique identifier for the case
     * @param patient         - patient object
     * @param chiefComplaint  - reason for patient's visit
     * @param arrivalMode     - mode of arrival (e.g., Ambulance, Helicopter, Walk-in)
     * @param arrivalDateTime - date and time of arrival
     */

    public EmergencyCase_Dispatch(int caseID, Patient patient, String chiefComplaint, String arrivalMode, LocalDateTime arrivalDateTime, boolean isUrgent) {
        super(caseID, patient, chiefComplaint, arrivalMode, arrivalDateTime,isUrgent );
    }

    // Variable to store the dispatch info
    private DispatchInfo dispatchInfo;
    // Variable to store the time when the dispatch team arrives to the patient location
    private LocalDateTime dispatchArrivalTime;
    // Variable to store the time when the call happened
    private LocalDateTime timeOfCall;
    // Variable to store the time taken for dispatch team to arrive to the patient location
    private Duration responseTime = Duration.ZERO;


    /**
     * Constructor for EmergencyCase Dispatch. Inherits from base Emergency Case class but contains dispatch parameters for ambulance or helicopter dispatch.
     * The timeOfCall variable gets automatically assigned to LocalDateTime.now() upon instantiating an object of this class
     * @param caseID used to identify the emergency case from others. Cannot have any duplicates. Shares with Emergency Case class
     * @param patientInfo use as reference for the patient in the emergency case as well as stores any patient information outside the emergency case
     * @param chiefComplaint primary cause or reason for the emergency case
     * @param arrivalMode set the transportation used by the patient. Dispatch should use either Ambulance or Helicopter
     * @param patientStatus initialize the state of the patient. Dispatch should use ONDISPATCHED status
     * @param dispatchInfo use as reference for the medivac staff members that are deployed for the emergency dispatch. Contains vehicle id, medivac staff members and special equipment brought
     */
    public EmergencyCase_Dispatch(int caseID, Patient patientInfo, String chiefComplaint, String arrivalMode, PatientStatus patientStatus, DispatchInfo dispatchInfo, boolean isUrgent){
        super(caseID, patientInfo, chiefComplaint, arrivalMode, patientStatus,isUrgent);

        this.dispatchInfo = dispatchInfo;
        this.timeOfCall = LocalDateTime.now(); // Set the time of call be the current time
        this.dispatchArrivalTime = LocalDateTime.now();
        this.setScreeningDateTime(LocalDateTime.now());
        this.setArrivalDateTime(LocalDateTime.now());
    }

//    public static void printAllNurses() {
//        // Print the header
//        System.out.printf("%-5s | %-20s | %-10s | %-10s\n", "No.", "Name", "ID", "Role");
//        System.out.println("-----------------------------------------------");
//
//        // Print each nurse's information
//        int counter = 1;
//        for (Nurse nurse : allNurses) {
//            System.out.printf("%-5d | %-20s | %-10s | %-10s\n", counter, nurse.getName(), nurse.getId(), nurse.getRole());
//            counter++;
//        }
//    }

    /**
     * Updates the dispatch case state to have the medivac team arrive to patient's location.
     * This function should be called to update the case state from initial state to medivac arrival state.
     * Calling this function also updates the responseTime to store the duration between timeOfCall and dispatchArrivalTime.
     * @param dispatchArrivalTime set the case's medivac arrival time to this value. Most of the time will set the value to LocalDateTime.now()
     */

    public void setDispatchTeamArrivalTime(LocalDateTime dispatchArrivalTime){
        this.dispatchArrivalTime = dispatchArrivalTime;
        this.responseTime = Duration.between(timeOfCall, dispatchArrivalTime);
    }

    /**
     * Get Function for dispatchArrivalTime tied to the dispatch case
     */
    public LocalDateTime getDispatchTeamArrivalTime(){
        return dispatchArrivalTime;
    }

    /**
     * Get Function for getTimeOfCall tied to the dispatch case
     */
    public LocalDateTime getTimeOfCall(){
        return timeOfCall;
    }


    /**
     * Set Function for getTimeOfCall. Used in initialising from reading a saved text file
     * @param timeOfCall set the timeOfCall to value
     */
    public void setTimeOfCall(LocalDateTime timeOfCall){
        this.timeOfCall = timeOfCall;
    }

    /**
     * Reinitialise the responseTime to be the duration between timeOfCall and dispatchArrivalTime.
     * Used in initialising from reading a saved text file.
     */
    public void calculateResponseTime(){
        if (responseTime == Duration.ZERO && dispatchArrivalTime != null)
            responseTime = Duration.between(timeOfCall, dispatchArrivalTime);
        else System.out.println("Dispatch ArrivalTime is not yet set.");
    }


    /**
     *  Updates the dispatch case state to have the patient and medivac team to arrive back to the hospital.
     *  This function should be called to update the case state from medivac arrival state to patient arrival state.
     *  The case will behave like the base EmergencyCase from this point onwards.
     *  Updates the arrivalDateTime function to be LocalDateTime.now() and the patientStatus from ONDISPATCHED to WAITING state.
     */
    // Set the patient arrival time to be now and update the patient status to be from OnDispatch to Waiting.
    public void setPatientStatusToArrived(){
        SetArrivalDateTime(LocalDateTime.now());
        setPatientStatus(PatientStatus.DONE);
    }

    /**
     *  returns a string of report containing information on the case dispatch timings.
     *  If the responseTime is currently at 0, only return the timing of the distress call. Set the display value of medivac arrival time to be "Dispatch In Progress".
     *  Else, return the timing of the distress call and the timing of the medivac arrival time.
     */
    public String getResponseDetails(){
        String responseDetails = "";

        // If responseTime is not zero, get the function to return information of the responseTime, timeOfCall and dispatchArrivalTime
        // Else, simply return inforamtion on the timeOfCall with dispatchArrivalTime displayed as in progress.
        if(responseTime != Duration.ZERO){
            if (responseTime.isPositive()) {
                DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern(("dd-MM-yyyy HH:mm:ss"));

                responseDetails = String.format("Time of Distress Call: %s\nTime of Dispatch Team Arrival: %s",
                        timeOfCall.format(displayFormat), dispatchArrivalTime.format(displayFormat));

                String reponseTimeString = String.format("Reponse Time: %d Days %d Hours %d Mins %d Seconds",
                        responseTime.toDaysPart(), responseTime.toHoursPart(), responseTime.toMinutesPart(), responseTime.toSecondsPart());

                responseDetails += reponseTimeString;
            }
            else System.out.println("Reponse Time value is in the negative. Please check if the Time of Call and Dispatch Arrival Time is set up properly");
        }
        else {
            responseDetails = String.format("Time of Distress Call: %s\nTime of Dispatch Team Arrival: %s",
                    timeOfCall.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), "Dispatch In Progress");
        }

        return responseDetails;
    }

    /**
     * Print out the current state of dispatch Info and response details.
     * Useful for testing and checking the current value of responseTime and the respective timing for timeOfCall and dispatchArrivalTime.
     */
    public void printDispatchDetails(){
        String dispatchDetails = "\n----------Dispatch Details----------\n";
        dispatchDetails += dispatchInfo.getInfo();
        dispatchDetails += getResponseDetails() +"\n";

        System.out.println(dispatchDetails);
    }

    /**
     * Get function for dispatchInfo tied to the dispatch case
     */
    public DispatchInfo getDispatchInfo() {
        return dispatchInfo;
    }

    /**
     * Return a String value that contains the information of current state of dispatch case.
     * Uses the same printIncidentReport() function from base EmergencyCase class but also adds on with dispatch info at the end of the report.
     */
//    @Override public String printIncidentReport(){
//        String report = "\n----------Incident Report----------\n";
////        report += super.printIncidentReport();
//        report += "\n----------Dispatch Info----------\n";
//        report += dispatchInfo.getInfo();
//        report += "\n----------Response Info----------\n";
//        report += getResponseDetails();
//        return report;
//    }

    /**
     * Get function for responseTime tied to the dispatch case.
     */
    public Duration getResponseTime(){
        return responseTime;
    }

    public LocalDateTime getDispatchArrivalTime() {
        return this.dispatchArrivalTime;
    }


    // For testing
//    public static void main(String[] args) {
//        Patient testPatient = new Patient(2222, "Adli");
//        List<HealthcareProfessional> testDispatchMember = new ArrayList<HealthcareProfessional>();
//
//        testDispatchMember.add(new HealthcareProfessional("Adli",8888,"Driver"));
//        testDispatchMember.add(new HealthcareProfessional("Bdli",8887,"Driver2"));
//        testDispatchMember.add(new HealthcareProfessional("Cdli",8886,"Driver3"));
//
//
//        List<String> testDispatchEquipment = new ArrayList<String>();
//
//        testDispatchEquipment.add("Nintendo Switch");
//
//        DispatchInfo testDispatch = new DispatchInfo(4444, testDispatchMember,testDispatchEquipment);
//        com.oop.ECSystem.EmergencyCase_Dispatch testCase = new com.oop.ECSystem.EmergencyCase_Dispatch(9999, testPatient, "Hello", "Helicopter", PatientStatus.ONDISPATCHED, testDispatch);
//
//        testCase.setDispatchTeamArrivalTime(LocalDateTime.now().plusHours(1));
//        testCase.SetArrivalDateTime(LocalDateTime.now().plusHours(2));
//        testCase.printDispatchDetails();
//
//        System.out.println(testCase.printIncidentReport());
//    }
}
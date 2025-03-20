package org.lucas.Emergency;

import org.lucas.models.Nurse;
import org.lucas.models.Patient;
import org.lucas.controllers.UserController;
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
    private static EmergencySystem ECsystem;
    private static Scanner scanner;
    private static List<Patient> allPatients;
    private static List<Nurse> allNurses;

    public EmergencyCase_Dispatch(int caseID, Patient patient, String chiefComplaint, String arrivalMode, LocalDateTime arrivalDateTime) {
        super(caseID, patient, chiefComplaint, arrivalMode, arrivalDateTime);
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
    public EmergencyCase_Dispatch(int caseID, Patient patientInfo, String chiefComplaint, String arrivalMode, PatientStatus patientStatus, DispatchInfo dispatchInfo){
        super(caseID, patientInfo, chiefComplaint, arrivalMode, patientStatus);

        this.dispatchInfo = dispatchInfo;
        this.timeOfCall = LocalDateTime.now(); // Set the time of call be the current time
        ECsystem = new EmergencySystem();
        scanner = new Scanner(System.in);
        allPatients = UserController.getAvailablePatients();
        allNurses = UserController.getAvailableNurses();
    }

    public static void printAllNurses() {
        // Print the header
        System.out.printf("%-5s | %-20s | %-10s | %-10s\n", "No.", "Name", "ID", "Role");
        System.out.println("-----------------------------------------------");

        // Print each nurse's information
        int counter = 1;
        for (Nurse nurse : allNurses) {
            System.out.printf("%-5d | %-20s | %-10s | %-10s\n", counter, nurse.getName(), nurse.getId(), nurse.getRole());
            counter++;
        }
    }

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
        updatePatientStatus(PatientStatus.WAITING);
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
    @Override public String printIncidentReport(){
        String report = "\n----------Incident Report----------\n";
//        report += super.printIncidentReport();
        report += "\n----------Dispatch Info----------\n";
        report += dispatchInfo.getInfo();
        report += "\n----------Response Info----------\n";
        report += getResponseDetails();
        return report;
    }

    /**
     * Get function for responseTime tied to the dispatch case.
     */
    public Duration getResponseTime(){
        return responseTime;
    }


    private static void createNewDispatch() {
//        EmergencySystem ECsystem = new EmergencySystem();
//        Scanner scanner = new Scanner(System.in);
//        List<Patient> allPatients = UserController.getAvailablePatients();
//        List <Nurse> allNurses = UserController.getAvailableNurses();

        // Use EmergencyCase_Dispatch class
        // Record: DispatchID, AmbulanceID, CrewMembers, Equipment
        // system.addEmergencyCaseDispatch(newDispatchCase);
        System.out.println("\n___- Register New Dispatch Case -___");
        int caseId = ECsystem.setCaseID();

        boolean continueChecking = true;
        boolean useExistingPatient = false;
        String existPatientName = "";

        // Get user input and if the user set the wrong value type, the system will keep
        // repeating until the user type in the correct data type.
        // If the user existing id, they can use if they want to use the existing id or
        // type unused id value
        String patientId;
        do {
            System.out.print("Enter patient ID: ");
            while (!scanner.hasNext()) {
                System.out.println("Invalid input! Please enter a valid patient ID.");
                scanner.next(); // Clear incorrect input
            }
            String enteredPatientID = scanner.next();
            scanner.nextLine(); // Clears leftover \n from nextInt()
            // Check if patient ID already exists
            if (allPatients.stream().anyMatch(patient -> patient.getId().equals(enteredPatientID))) {
                for (Patient patientX : allPatients) {
                    if (enteredPatientID.equals(patientX.getId())) {
                        existPatientName = patientX.getName();
                        break;
                    }
                }
            System.out.println("Patient ID already exists.\nExisting patient found --> [ ID: " + enteredPatientID);
            System.out.println("Please select to use the existing patient or enter a new patient ID.");
            System.out.print("1. Use existing patient\n2. Enter new patient ID \n");
            int choice = InputValidator.getValidIntInput("Choice: ");
            if (choice == 1) { // Use existing patient
                patientId = enteredPatientID;
                continueChecking = false;
                    useExistingPatient = true;
                } // Else continue checking for new patient ID
            } else { // No existing patient found
                patientId = enteredPatientID; // Set patient ID
                continueChecking = false;
            }
        }
        while (continueChecking);

        // Check if the patient name if its valid. If empty or contains numbers, the
        // input will be invalid.
        String patientName = "";
        if (useExistingPatient) {
            patientName = existPatientName;
            System.out.println("Using existing patient: " + patientName);
        } else {
            System.out.print("Enter patient name: ");
            patientName = scanner.nextLine().trim();
            while (!patientName.matches("[a-zA-Z ]+") || patientName.isBlank()) {
                System.out.println("Patient name cannot be empty. Please enter a valid name.");
                System.out.print("Enter patient name: ");
                patientName = scanner.nextLine().trim();
            }
        }

        String chiefComplaint = "";
        while (chiefComplaint.isBlank()) {
            System.out.print("Enter reason of patient's call (Chief Complaint): ");
            chiefComplaint = scanner.nextLine().trim();
        }

        // Set the arrival mode based on the valid input range.
        System.out.print(
                "___- Select dispatch vehicle -___\n (1. Ambulance) \n (2. Helicopter) \n");
        int choice = InputValidator.getValidIntInput("Choice (enter integer value): ");
        String arrivalMode;

        switch (choice) {
            case 1:
                arrivalMode = "Ambulance";
                break;
            case 2:
                arrivalMode = "Helicopter";
                break;
            default:
                arrivalMode = "Error";
                break;
        }

        PatientStatus patientStatus = PatientStatus.ONDISPATCHED;



        // Set Dispatch Info
        System.out.print("Enter Vehicle ID: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid vehicle ID");
            scanner.next();
        }

        int ambulanceId = scanner.nextInt();

        List<Nurse> dispatchMembers = new ArrayList<Nurse>();

        System.out.print("Enter dispatched medivac member staff ID: ");
        boolean validStaffID = false;
        int inputStaffId = 0;

        while (!validStaffID) {
            if (scanner.hasNextInt()) {
                inputStaffId = scanner.nextInt();
                for (Nurse nurses : allNurses) {
                    if (nurses.getId().equals(inputStaffId)) {
                        validStaffID = true;
                        dispatchMembers.add(nurses);
                        break;
                    }

            else{
                System.out.println("Invalid staff ID. Please enter a valid ID");
                scanner.next();
                    }
                }
            }
        }
//        dispatchMembers.add(StaffMember.getStaffMember(inputStaffId));

        boolean addMoreStaff = true;

        while (addMoreStaff) {
            System.out
                    .print("___- Select Option -___\n (1. Add more member)\n (2. End)\n ");
            choice = InputValidator.getValidIntInput("Choice (enter integer value): ");

            if (choice == 2)
                addMoreStaff = false;
            else {
                System.out.print("Enter dispatched medivac member staff ID: ");
                validStaffID = false;
                while (!validStaffID) {
                    if (scanner.hasNextInt()) {
                        inputStaffId = scanner.nextInt();
                        for (Nurse nurses : allNurses) {
                            if (nurses.getId().equals(inputStaffId)) {
                                validStaffID = true;
                                dispatchMembers.add(nurses);
                                break;
                            }

                            else{
                                System.out.println("Invalid staff ID. Please enter a valid ID");
                                scanner.next();
                            }
                        }
                    }
                }
            }
        }

        System.out.print(
                "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n ");
        choice = InputValidator.getValidIntInput("Choice (enter integer value): ");

        List<String> equipmentList = new ArrayList<String>();
        boolean addEquipment = false;
        if (choice == 1)
            addEquipment = true;

        while (addEquipment) {
            System.out.print("Enter special equipment: ");
            String equipment = scanner.nextLine().trim();

            while (equipment.isBlank()) {
                System.out.println("Equipment name cannot be empty. Please enter a valid name");
                System.out.print("Enter special equipment: ");
                equipment = scanner.nextLine().trim();
            }

            equipmentList.add(equipment);

            System.out.print(
                    "___- Select Option -___\n (1. Add special equipment)\n (2. End)\n ");
            choice = InputValidator.getValidIntInput("Choice (enter integer value): ");

            if (choice == 2)
                addEquipment = false;
        }

//        // creates a patient object based on the value inserted by the user
//        Patient patient = Patient.checkOrCreatePatient(allPatients, patientId);
//
//        // create a dispatch info object based on the value inserted by the user
//        DispatchInfo dispatchInfo = new DispatchInfo(ambulanceId, dispatchMembers, equipmentList);
//
//        // create the emergency case dispatch object from all the above variables and
//        // add to the existing list of emergency case dispatch in the system
//        EmergencyCase_Dispatch newDispatchCase = new EmergencyCase_Dispatch(caseId, patient, chiefComplaint,
//                arrivalMode, patientStatus, dispatchInfo);
//        ECsystem.addEmergencyCaseDispatch(newDispatchCase);
//        ECsystem.saveAllCases();
        System.out.println(
                "______________________________________________________________________________________________");
        System.out.println("New Dispatch Case | Case ID: " + caseId + " | Patient Name: " + patientName
                + " | Registered successfully!\n");
        System.out.println("<--- Back");
    }



    private static void updateDispatchStatus() {
        // TODO: Implement dispatch status updates
        // Update dispatch status and location
        System.out.println("\n___- Update Dispatch Case Status -___");
        System.out.print("Enter case ID: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid case ID.");
            scanner.next();
        }

        int caseID = scanner.nextInt();
        scanner.nextLine();
        boolean caseFound = false;

        // Get the referenced dispatch case from the existing cases
        for (EmergencyCase_Dispatch dispatchCase : ECsystem.getEmergencyCaseDispatch()) {
            if (dispatchCase.getCaseID() == caseID) {
                int choice;
                // If the patient status is waiting, run the same sequence from emergency case
                // If the patient status is ondispatched, run the sequence for cases that are
                // currently in dispatch
                switch (dispatchCase.getPatientStatus()) {
                    case PatientStatus.WAITING: /**dependent on registerNewEmergencyCases
                        in EmergencyCase.java to create a new EmergencyCase obj**/
                        System.out.println("Select new triage level:");
                        String[] triageLevels = EmergencyCase_Dispatch.getTriageLevels();

                        for (int i = 0; i < triageLevels.length; i++) {
                            System.out.println((i + 1) + "," + triageLevels[i]);
                        }

                        do {
                            System.out.print("Enter choice (1-" + triageLevels.length + "): ");
                            while (!scanner.hasNextInt()) {
                                System.out.println(
                                        "Invalid input! Please enter a valid choice (1-" + triageLevels.length + ").");
                                scanner.next();
                            }
                            choice = scanner.nextInt();
                        } while (choice < 1 || choice > triageLevels.length);

                        String selectedTriageLevel = triageLevels[choice - 1];

                        // Staff member selection
                        System.out.println("\nDo you want to:");
                        System.out.println("1. Select existing nurse");
//                        System.out.println("2. Add new nurse");
//                        System.out.print("Enter your choice (1-2): ");

                        int staffChoice = InputValidator.getValidIntInput("Enter your choice (1-2): ");
                        Nurse nurses = null;

                        if (staffChoice == 1) {
                            // Display all available nurses and select nurse
                            System.out.println("\nAvailable Nurses:");
                            printAllNurses();

                            System.out.print("\nEnter the nurse's ID: ");
                            int nurseID = scanner.nextInt();
                            scanner.nextLine(); // Clear buffer

                            // Find the selected nurse
                            for (Nurse nurse : allNurses) {
                                if (nurse.getId().equals(nurseID)) {
                                    nurses = nurse;
                                    break;
                                }
                            }

                            if (nurses == null) {
                                System.out.println("Invalid nurse ID or staff member is not a nurse.");
                                return;
                            }
                            // I don't think should add new nurses, but only can select from existing
//                        } else {
//                            // Add new nurse
//                            System.out.print("Enter staff member ID: ");
//                            int staffID = scanner.nextInt();
//                            scanner.nextLine();
//
//                            System.out.print("Enter staff member name: ");
//                            String staffName = scanner.nextLine().trim();
//
//                            staffMember = new StaffMember(staffName, staffID, "Nurse");
//                            StaffMember.getStaff().add(staffMember);
//                            StaffMember.saveStaff();
                        }

                        System.out.print("Enter patient's vital signs: ");
                        String vitalSigns = scanner.nextLine().trim();

                        dispatchCase.updateInitialScreening(nurses, selectedTriageLevel, vitalSigns);

//                        ECsystem.saveAllCases();
//                        StaffMember.saveStaff();
                        System.out.println("Dispatch case updated successfully!");
                        break;

                    // if the case is on dispatched, if the dispatch response time is currently
                    // zero, check if user want to update the case for the dispatch team to be
                    // arrival to patient location
                    case PatientStatus.ONDISPATCHED:
                        if (dispatchCase.getResponseTime() == Duration.ZERO) {
                            System.out.println("Dispatch Team is in progress. Select Option");
                            System.out.print(
                                    "___- Select Option -___\n (1. Set Dispatch Team status to arrived to location)\n (2. Back)\n");
                            choice = InputValidator.getValidIntInput("Choice (enter integer value): ");

                            if (choice == 1) {
                                System.out.println("Dispatch team has been set to arrived to patient location");
                                dispatchCase.setDispatchTeamArrivalTime(LocalDateTime.now());
//                                ECsystem.saveAllCases();
                            } else {
                                System.out.println("<--- Back");
                                break;
                            }
                        }

                        // Check if the user want to udpate the case for the dispatch team to arrived
                        // back to the hospital with the patient
                        if (dispatchCase.getResponseTime() != Duration.ZERO) {
                            System.out.println("Dispatch Team is in progress. Select Option");
                            System.out.print(
                                    "___- Select Option -___\n (1. Set Dispatch Team status to arrived to hospital)\n (2. Back)\n");
                            choice = InputValidator.getValidIntInput("Choice (enter integer value): ");

                            if (choice == 1) {
                                System.out.println("Dispatch team has been set to arrive back to hospital");
                                dispatchCase.setPatientStatusToArrived();
                                System.out.println(dispatchCase.getResponseDetails());
//                                ECsystem.saveAllCases();
                            } else {
                                System.out.println("<--- Back");
                                break;
                            }
                        }

                        System.out.println("Dispatch case has been updated successfully!");
                        break;

                    default:
                        System.out.println("Dispatch case has already been resolved.");
                        break;
                }


                caseFound = true;
                break;
            }
        }

        if (!caseFound)
            System.out.println("No Dispatch case found. Exiting");

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
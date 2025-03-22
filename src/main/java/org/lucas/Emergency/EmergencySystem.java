package org.lucas.Emergency;


import org.lucas.controllers.ESController;
import org.lucas.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**javadoc
 * EmergencySystem class to manage emergency cases and dispatches
 * Stores emergency cases and dispatches in lists
 */
public class EmergencySystem {
    private List<EmergencyCase> emergencyCases; // list of emergency cases
    private List<EmergencyCase_Dispatch> emergencyCaseDispatch; // list of emergency case dispatch

    public EmergencySystem() {
        emergencyCases = new ArrayList<>(); // initialize the list of emergency cases
        emergencyCaseDispatch = new ArrayList<>(); // initialize the list of emergency case dispatch
    }

    /**
     * Getter for emergency cases
     * return list of emergency cases
     */
    public List<EmergencyCase> getEmergencyCases() {
        return emergencyCases; // return the list of emergency cases
    }

    /**
     * Getter for emergency case dispatch
     * return list of emergency case dispatch
     */
    public List<EmergencyCase_Dispatch> getEmergencyCaseDispatch() {
        return emergencyCaseDispatch; // return the list of emergency case dispatch
    }

    /**
     * Helper method to check if a case exists
     * @param caseId case ID to check
     * return true if case exists, false otherwise
     */
    public boolean caseExists(int caseId) {
        return emergencyCases.stream().anyMatch(c -> c.getCaseID() == caseId); // check if the case exists
    }

    /**
     * Helper method to set the case ID and return the next available ID
     * return next available case ID
     */
    public int setCaseID () { // Finds the largest existing case ID and returns the next available ID
        int highestID = 0; // initialize the highest ID

        if (!emergencyCases.isEmpty()) {
            for (EmergencyCase x : emergencyCases) {
                if (x.getCaseID() > highestID)
                    highestID = x.getCaseID(); // update the highest ID
            }
        }

        if (!emergencyCaseDispatch.isEmpty()) {
            for (EmergencyCase_Dispatch x : emergencyCaseDispatch) {
                if (x.getCaseID() > highestID)
                    highestID = x.getCaseID(); // update the highest ID
            }
        }
        return highestID + 1; // return the next available ID
    }

    /**
     * Add a new emergency case
     * @param newCase new emergency case to add
     * Saves the cases immediately after adding
     * Prints an error message if the case already exists
     */
    public void addEmergencyCase(EmergencyCase newCase) {
        if (!caseExists(newCase.getCaseID())) {
            emergencyCases.add(newCase); // add the new case
            ESController.saveCasesToFile(); // Save immediately after adding
        } else {
            System.out.println("Case with ID " + newCase.getCaseID() + " already exists!"); // print the error message
            // if the case already
            // exists
        }
    }

    /**
     * Add a new emergency case dispatch
     * @param newCase_Dispatch new emergency case dispatch to add
     * Saves the cases immediately after adding
     * Prints an error message if the case dispatch already exists
     */
    public void addEmergencyCaseDispatch(EmergencyCase_Dispatch newCase_Dispatch) {
        if (!emergencyCaseDispatch.contains(newCase_Dispatch)) {
            emergencyCaseDispatch.add(newCase_Dispatch); // add the new case dispatch
        } else {
            System.out.println("Duplicate emergency case dispatch-not added: " + newCase_Dispatch); // print the error
            // message if the
            // case dispatch
            // already exists
        }

    }

    /**
     * Print case information for a specific case
     * @param caseID case ID to print
     * @param emergencyType emergency type to print
     * Prints an error message if the case is not found
     */
    public void printCaseInfo(int caseID, String emergencyType) {
        boolean found = false; /* initialize the found flag */
        if (emergencyType == "Walk-In") {
            for (EmergencyCase ec : emergencyCases) {
                if (ec.getCaseID() == caseID) {
//                    System.out.println("Emergency case found: " + ec.printIncidentReport());
                    /*
                     * print the case info if
                     * found
                     */
                    found = true;
                    break; /* Exit the loop once we find the case */
                }
            }
        }
        if (emergencyType == "Dispatch") {
            for (EmergencyCase_Dispatch ec : emergencyCaseDispatch) {
                if (ec.getCaseID() == caseID) {
                    System.out.println("Emergency Dispatch Case found: " + ec.printIncidentReport()); /*
                     * print the
                     * dispatch case
                     * info if found
                     */
                    found = true;
                    break; /* Exit the loop once we find the dispatch case */
                }
            }
        }
        if (!found && emergencyType == "Walk-In") {
            System.out.println("Emergency case with ID " + caseID + " not found."); /*
             * print the error message if the
             * case is not found
             */
        } else if (!found && emergencyType == "Dispatch") {
            System.out.println("Emergency Dispatch Case with ID " + caseID + " not found."); /*
             * print the error message
             * if the dispatch case is
             * not found
             */
        }

    }

    /**
     * Print all case information for a specific emergency type
     * @param emergencyType emergency type to print
     * Prints an error message if there are no cases
     */
    public void printAllCaseInfo(String emergencyType) {
        if (emergencyType == "Walk-In") {
            if (emergencyCases.isEmpty()) {
                System.out.println("No emergency cases in the system."); /*
                 * print the error message if there are no
                 * emergency cases
                 */
                return;
            }
            for (EmergencyCase c : emergencyCases) {
                System.out.println("=== Case Details ===");
                System.out.println(c.printIncidentReport()); /* print the case info */
            }
        } else if (emergencyType == "Dispatch") {
            if (emergencyCaseDispatch.isEmpty()) {
                System.out.println("No emergency dispatch cases in the system."); /*
                 * print the error message if there
                 * are no emergency dispatch cases
                 */
                return;
            }
            for (EmergencyCase_Dispatch c : emergencyCaseDispatch) {
                System.out.println("=== Case Details ===");
                System.out.println(c.printIncidentReport()); /* print the case info */
            }
        }

    }

    /**
     * Save all cases to a txt file
     * Prints an error message if the cases are not saved
     * Uses a BufferedWriter and FileWriter to write to the file
     */
//    public void saveAllCases() {
//        try (BufferedWriter writer = new BufferedWriter(
//                new FileWriter("data/emergency_cases_and_dispatch.txt"))) { // save the cases to a file
//            // Save emergency cases
//            for (EmergencyCase emergencyCase : emergencyCases) {
//                writer.write("EMERGENCY," + emergencyCaseToString(emergencyCase)); // save the emergency case
//
//                writer.newLine(); // save the new line
//            }
//            // Save dispatch cases
//            for (EmergencyCase_Dispatch dispatchCase : emergencyCaseDispatch) {
//                writer.write("DISPATCH," + dispatchCaseToString(dispatchCase)); // save the dispatch case
//                writer.newLine(); // save the new line
//            }
//            System.out.println("Emergency and dispatch cases saved successfully."); // print the success message
//        } catch (IOException e) {
//            System.out.println("Error saving cases: " + e.getMessage()); // print the error message
//        }
//    }

    /**
     * Load all cases from a txt file
     * Prints an error message if the cases are not loaded
     * Uses a BufferedReader and FileReader to read from the file
     * Uses stringToEmergencyCase and stringToDispatchCase to convert the data to cases
     * Uses a try-with-resources to handle the file reading
     */
//    public void loadAllCases() {
//        emergencyCases = new ArrayList<>(); // initialize the list of emergency cases
//        File file = new File("data/emergency_cases_and_dispatch.txt"); // load the cases from a file
//        if (!file.exists()) {
//            System.out.println("No saved cases found."); // print the error message if there are no saved cases
//            return;
//
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            emergencyCases = new ArrayList<>(); // initialize the list of emergency cases
//            emergencyCaseDispatch = new ArrayList<>(); // initialize the list of emergency case dispatch
//            // Debug print - show file path
//            System.out.println("\nLoading cases from: " + file.getAbsolutePath()); // print the file path
//            while ((line = reader.readLine()) != null) {
//                System.out.println("Reading line: " + line); // print the line
//                String[] parts = line.split(",", 2); // Split into type and data
//                if (parts.length < 2)
//                    continue; // Skip invalid lines
//
//                String type = parts[0]; // get the type
//                String data = parts[1]; // get the data
//
//                // Debug print - show type and data
//                System.out.println("Type: " + type); // print the type
//                System.out.println("Data: " + data); // print the data
//
//                if ("EMERGENCY".equals(type)) {
//                    EmergencyCase emergencyCase = stringToEmergencyCase(data); // convert the data to an emergency case
//                    if (emergencyCase != null) {
//                        emergencyCases.add(emergencyCase); // add the emergency case to the list
//                        // Debug print - show successfully added case
//                        System.out.println("Successfully added emergency case with ID: " + emergencyCase.getCaseID()); // print
//                        // the
//                        // success
//                        // message
//                    } else {
//                        System.out.println("Failed to parse emergency case from data"); // print the error message if
//                        // the emergency case is not
//                        // parsed
//                    }
//                } else if ("DISPATCH".equals(type)) {
//                    EmergencyCase_Dispatch dispatchCase = stringToDispatchCase(data); // convert the data to an
//                    // emergency case dispatch
//                    if (dispatchCase != null) {
//                        emergencyCaseDispatch.add(dispatchCase); // add the emergency case dispatch to the list
//                        // Debug print - show successfully added dispatch case
//                        System.out.println("Successfully added dispatch case with ID: " + dispatchCase.getCaseID()); // print
//                        // the
//                        // success
//                        // message
//                    } else {
//                        System.out.println("Failed to parse dispatch case from data"); // print the error message if the
//                        // emergency case dispatch is not
//                        // parsed
//                    }
//                } else {
//                    System.out.println("Unknown case type: " + type); // print the error message if the case type is
//                    // unknown
//                }
//            }
//            // Debug print - show final counts
//            System.out.println("\nLoading complete:");
//            System.out.println("Total emergency cases loaded: " + emergencyCases.size()); // print the total number of
//            // emergency cases loaded
//            System.out.println("Total dispatch cases loaded: " + emergencyCaseDispatch.size()); // print the total
//            // number of dispatch
//            // cases loaded
//
//            // Debug print - show all emergency case IDs
//            System.out.println("\nLoaded emergency case IDs:");
//            for (EmergencyCase ec : emergencyCases) {
//                System.out.println("Case ID: " + ec.getCaseID()); // print the case ID
//            }
//            System.out.println("Emergency and dispatch cases loaded successfully."); // print the success message
//
//        } catch (IOException e) {
//            System.out.println("Error loading cases: " + e.getMessage()); // print the error message if the cases are not loaded
//        }
//    }

    /**
     * Convert an emergency case to a CSV string
     * @param emergencyCase emergency case to convert
     * return CSV string of the emergency case
     * Handles nurse details, vital signs, doctor details, procedures, and patient status
     * Uses String.format to format the string with proper ordering
     */
//    private String emergencyCaseToString(EmergencyCase emergencyCase) {
//        // Handle nurse details
//        List<User> nurses = emergencyCase.getInitialScreeningNurses(); // get the initial screening nurses
//        String nurseDetails = (nurses != null && !nurses.isEmpty())
//                ? nurses.get(0).getName() + "," +
//                nurses.get(0).getId() + "," +
//                nurses.get(0).get()
//                : "No staff assigned";
//
//        // Get vital signs
//        List<String> vitalSignsList = emergencyCase.getPatient().getVitalSignsHistory();
//        String vitalSigns = (vitalSignsList != null && !vitalSignsList.isEmpty())
//                ? String.join(", ", vitalSignsList)
//                : "No vital signs recorded";
//
//        // Handle doctor details
//        List<StaffMember> doctors = emergencyCase.getScreeningDoctors();
//        String doctorDetails = (doctors != null && !doctors.isEmpty())
//                ? "," + doctors.get(0).getStaffName() + "," +
//                doctors.get(0).getStaffID() + "," +
//                doctors.get(0).getJobScope()
//                : ""; // Empty string if no doctor assigned
//        // Handle procedures
//        List<String> procedures = emergencyCase.getEmergencyProcedures();
//        String proceduresStr = (procedures != null && !procedures.isEmpty())
//                ? "," + String.join("; ", procedures)
//                : "";
//
//        // Build the complete string with proper ordering
//        return String.format("%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s%s%s,%s",
//                emergencyCase.getCaseID(),
//                emergencyCase.getPatient().getPatientID(),
//                emergencyCase.getPatient().getPatientName(),
//                emergencyCase.getChiefComplaint(),
//                emergencyCase.getArrivalMode(),
//                emergencyCase.getArrivalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
//                emergencyCase.getTriageLevel(),
//                emergencyCase.getCurrentLocation() != null ? emergencyCase.getCurrentLocation()
//                        : "Emergency room - Waiting Room", // Add default location
//                nurseDetails,
//                emergencyCase.getDateAndTimeOfScreening(),
//                vitalSigns,
//                doctorDetails,
//                proceduresStr,
//                emergencyCase.getPatientStatus().toString());
//    }


    /**
     * Converts an EmergencyCase_Dispatch object to a string value.
     * Mainly used to save the emergency case dispatch cases to a text value in saveAllCases() function.
     * Returns a string that contains all the information of the EmergencyCase_Dispatch object.
     * @param dispatchCase object to reference from
     */
//    private String dispatchCaseToString(EmergencyCase_Dispatch dispatchCase) {
//
//        // Get the nurse information to save to nurseDetails string
//        List<StaffMember> nurses = dispatchCase.getInitialScreeningNurses();
//        String nurseDetails = "";
//
//        // If the nurse information list is not empty, add the nurse information to the list in the following order, name;id;jobscope
//        if (nurses != null && !nurses.isEmpty()) {
//            for (StaffMember member : nurses) {
//                nurseDetails += member.getStaffName() + ";";
//                nurseDetails += member.getStaffID() + ";";
//                nurseDetails += member.getJobScope() + ";";
//            }
//        } else
//            nurseDetails = "No staff assigned";
//
//        // Get the list of vital signs and convert any , found in the string to &
//        List<String> vitalSignsList = dispatchCase.getPatient().getVitalSignsHistory();
//        for (int i = 0; i < vitalSignsList.size(); i++) {
//            String updatedVitalSign = vitalSignsList.get(i).replace(",", " &");
//            vitalSignsList.set(i, updatedVitalSign);
//        }
//
//        // Store all the elements in the vitalSign list to vitalSigns variable
//        String vitalSigns = (vitalSignsList != null && !vitalSignsList.isEmpty())
//                ? String.join(";", vitalSignsList)
//                : "No vital signs recorded";
//
//        // Do the same for doctors information
//        List<StaffMember> doctors = dispatchCase.getScreeningDoctors();
//        String doctorDetails = "";
//
//        if (doctors != null && !doctors.isEmpty()) {
//            for (StaffMember member : doctors) {
//                doctorDetails += member.getStaffName() + ";";
//                doctorDetails += member.getStaffID() + ";";
//                doctorDetails += member.getJobScope() + ";";
//            }
//        } else
//            doctorDetails = "-";
//
//        // Do the same for procedures information
//        List<String> procedures = dispatchCase.getEmergencyProcedures();
//        String proceduresStr = (procedures != null && !procedures.isEmpty())
//                ? String.join(";", procedures)
//                : "-";
//
//        List<StaffMember> medivacMembers = dispatchCase.getDispatchInfo().getMedivacMembers();
//        String medivacMemberDetails = "";
//
//        // Do the same for medivac members
//        if (medivacMembers != null && !medivacMembers.isEmpty()) {
//            for (StaffMember member : medivacMembers) {
//                medivacMemberDetails += member.getStaffName() + ";";
//                medivacMemberDetails += member.getStaffID() + ";";
//                medivacMemberDetails += member.getJobScope() + ";";
//            }
//        } else
//            medivacMemberDetails = "-";
//
//        // Handle Dispatch Equipment details
//        List<String> equipments = dispatchCase.getDispatchInfo().getEquipment();
//        String equipmentDetails = (equipments != null && !equipments.isEmpty())
//                ? String.join(";", equipments)
//                : "-";
//
//        DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//
//        // Save the arrivalDateTime and dispatchTeamArrivalTime to the respective string variable
//        String arrivalDateTime = "-";
//        if (dispatchCase.GetArrivalDateTime() != null)
//            arrivalDateTime = dispatchCase.getArrivalDateTime().format(dateFormat);
//
//        String dispatchTeamArrivalTime = "-";
//        if (dispatchCase.getDispatchTeamArrivalTime() != null)
//            dispatchTeamArrivalTime = dispatchCase.getDispatchTeamArrivalTime().format(dateFormat);
//
//        // Return the string that contains all the EmergencyCase_Dispatch information
//        return String.format("%d,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s",
//                dispatchCase.getCaseID(),
//                dispatchCase.getPatient().getPatientID(),
//                dispatchCase.getPatient().getPatientName(),
//                dispatchCase.getChiefComplaint(),
//                dispatchCase.getArrivalMode(),
//                arrivalDateTime,
//                dispatchCase.getTriageLevel(),
//                dispatchCase.getCurrentLocation(),
//                nurseDetails,
//                dispatchCase.getDateAndTimeOfScreening(),
//                vitalSigns,
//                doctorDetails,
//                proceduresStr,
//                dispatchCase.getPatientStatus().toString(),
//                dispatchCase.getDispatchInfo().getVehicleId(),
//                medivacMemberDetails,
//                equipmentDetails,
//                dispatchCase.getTimeOfCall().format(dateFormat),
//                dispatchTeamArrivalTime);
//    }


    /**
     * Convert a CSV string back to an EmergencyCase
     * @param line CSV string to convert
     * return EmergencyCase object
     * Handles nurse details, vital signs, doctor details, procedures, and patient status
     */
//    private EmergencyCase stringToEmergencyCase(String line) {
//        try {
//            String[] parts = line.split(","); // split the line into parts
//            System.out.println("\nParsing emergency case line: " + line); // print the line
//            System.out.println("Number of parts after split: " + parts.length); // print the number of parts
//
//            if (parts.length < 11) {
//                System.out.println("Not enough parts in line (minimum 11 required)"); // print the error message if the
//                // number of parts is less than 11
//                return null;
//            }
//
//            // Parse the basic data
//            int caseID = Integer.parseInt(parts[0]); // get the case ID
//            int patientID = Integer.parseInt(parts[1]); // get the patient ID
//            String patientName = parts[2]; // get the patient name
//            String chiefComplaint = parts[3]; // get the chief complaint
//            String arrivalMode = parts[4]; // get the arrival mode
//            LocalDateTime arrivalDateTime = LocalDateTime.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME); // get the arrival date time
//            String triageLevel = parts[6]; // get the triage level
//            String location = parts[7].equals("null") ? null : parts[7]; // get the location
//
//            // Create patient and case
//            List<String> vitalSigns = new ArrayList<>(); // initialize the list of vital signs
//            Patient patient = new Patient(patientID, patientName, vitalSigns); // create the patient
//            EmergencyCase emergencyCase = new EmergencyCase(caseID, patient, chiefComplaint, arrivalMode,
//                    arrivalDateTime); // create the emergency case
//
//            // Set triage level and location
//            emergencyCase.setTriageLevel(triageLevel); // set the triage level
//            if (location != null) {
//                emergencyCase.setLocation(location); // set the location
//            }
//
//            // Handle nurse details
//            if (!parts[8].equals("No staff assigned") && !parts[8].equals("null")) { // if the nurse is not assigned or
//                // null
//                String nurseName = parts[8]; // get the nurse name
//                int nurseID = Integer.parseInt(parts[9]); // get the nurse ID
//                String nurseRole = parts[10]; // get the nurse role
//
//                StaffMember nurse = new StaffMember(nurseName, nurseID, nurseRole); // create the nurse
//                List<StaffMember> nurses = new ArrayList<>(); // initialize the list of nurses
//                nurses.add(nurse); // add the nurse to the list
//                emergencyCase.setInitialScreeningNurses(nurses); // set the initial screening nurses
//            }
//
//            // Handle screening time
//            if (parts.length > 11 && !parts[11].equals("null")) { // if the screening time is not null
//                try {
//                    LocalDateTime screeningTime = LocalDateTime.parse(parts[11]); // get the screening time
//                    emergencyCase.setDateAndTimeOfScreening(screeningTime); // set the screening time
//                } catch (DateTimeParseException e) {
//                    System.out.println("Could not parse screening time: " + parts[11]); // print the error message if
//                    // the screening time is not
//                    // parsed
//                }
//            }
//
//            // Handle vital signs
//            if (parts.length > 12 && !parts[12].equals("No vital signs recorded")) { // if the vital signs are not
//                // recorded
//                StringBuilder vitalSign = new StringBuilder(parts[12]); // create the vital sign
//                // Combine vital signs that might have been split due to commas
//                for (int i = 13; i < parts.length && !parts[i].equals("Doctor")
//                        && !parts[i - 1].matches("\\d{7}"); i++) { // if the vital sign is not a doctor and not a 7
//                    // digit number
//                    vitalSign.append(",").append(parts[i]); // append the vital sign to the list
//                }
//                vitalSigns.add(vitalSign.toString()); // add the vital sign to the list
//            }
//
//            // Handle doctor details
//            if (parts.length > 14) { // if the doctor details are present
//                int doctorPartsIndex = parts.length - 3; // Doctor details should be the last 3 parts
//                if (parts[doctorPartsIndex + 2].equals("Doctor")) { // if the doctor is a doctor
//                    String doctorName = parts[doctorPartsIndex]; // get the doctor name
//                    int doctorID = Integer.parseInt(parts[doctorPartsIndex + 1]); // get the doctor ID
//                    String doctorRole = parts[doctorPartsIndex + 2]; // get the doctor role
//
//                    StaffMember doctor = new StaffMember(doctorName, doctorID, doctorRole); // create the doctor
//                    List<StaffMember> doctors = new ArrayList<>(); // initialize the list of doctors
//                    doctors.add(doctor); // add the doctor to the list
//                    emergencyCase.setScreeningDoctors(doctors); // set the screening doctors
//                }
//            }
//
//            // Handle procedures if present
//            if (parts.length > 16) { // if the procedures are present
//                String proceduresStr = parts[parts.length - 2]; // Procedures are before status
//                if (!proceduresStr.equals("null") && !proceduresStr.isEmpty()) { // if the procedures are not null and
//                    // not empty
//                    String[] proceduresList = proceduresStr.split("; "); // split the procedures
//                    for (String procedure : proceduresList) {
//                        emergencyCase.addEmergencyProcedure(procedure); // add the procedure to the list
//                    }
//                }
//            }
//
//            // Handle patient status (last field)
//            if (parts.length > 16) { // if the patient status is present
//                try {
//                    EmergencyCase.PatientStatus status = EmergencyCase.PatientStatus.valueOf(parts[parts.length - 1]); // get
//                    // the
//                    // patient
//                    // status
//                    emergencyCase.updatePatientStatus(status); // update the patient status
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Could not parse status: " + parts[parts.length - 1]); // print the error message
//                    // if the patient status
//                    // is not parsed
//                }
//            }
//
//            System.out.println("Successfully created emergency case with ID: " + caseID); // print the success message
//            return emergencyCase; // return the emergency case
//
//        } catch (Exception e) {
//            System.out.println("Error parsing emergency case at line: " + line); // print the error message if the
//            // emergency case is not parsed
//            System.out.println("Error message: " + e.getMessage()); // print the error message
//            return null; // return null
//        }
//    }

    /**
     * Converts a string line to an EmergencyCase_Dispatch object.
     * Used in loading EmergencyCase_Dispatch objects from a text file in loadAllCases().
     * Returns a EmergencyCase_Dispatch object if the string line can be successfully converted into the object
     * @param line used as the string content and convert it into an EmergencyCase_Dispatch object
     */
//    private EmergencyCase_Dispatch stringToDispatchCase(String line) {
//        try {
//            String[] parts = line.split(","); // split the line into parts
//
//            System.out.println("\nParsing dispatch case line: " + line); // print the line
//            System.out.println("Number of parts after split: " + parts.length); // print the number of parts
//
//            if (parts.length < 11) {
//                System.out.println("Not enough parts in line (minimum 11 required");
//                return null;
//            }
//
//            // Parts index
//            // 0 -> caseId, 1 -> patientID, 2 -> patientName, 3 -> chiefComplaint, 4 ->
//            // arrivalMode, 5 -> arrivalDateTime
//            // 6 -> triageLevel, 7 -> patientLocation, 8 -> nurseDetails, 9 ->
//            // dateAndTimeOfScreening, 10 -> vitalSigns
//            // 11 -> doctorDetails, 12 -> procedureStr, 13 -> patientStatus, 14 ->
//            // ambulanceID, 15 -> medivacMemberDetails, 16 -> equipmentDetails
//            // 17 -> timeOfCall, 18 -> dispatchTeamArrivalTime
//
//            // Parse the basic data
//            int caseID = Integer.parseInt(parts[0]);
//            int patientID = Integer.parseInt(parts[1]);
//            String patientName = parts[2];
//            String chiefComplaint = parts[3];
//            String arrivalMode = parts[4];
//            String triageLevel = parts[6];
//            String location = parts[7].equals("null") ? null : parts[7];
//
//            // Get the string from the respective part and try to convert the value into a LocalDateTime variable
//            LocalDateTime arrivalDateTime = LocalDateTime.MIN;
//            if (parts[5] != null && !parts[5].equals("-")) {
//                try {
//                    arrivalDateTime = LocalDateTime.parse(parts[5]);
//                } catch (DateTimeParseException e) {
//                    System.out.println(("Could not parse patient arrival time: " + parts[5]));
//                }
//            }
//
//            // Get the string form the respective part and try to convert the value into a StaffMember List variable
//            List<StaffMember> nurses = new ArrayList<StaffMember>();
//            if (parts[8] != null && !parts[8].equals("No staff assigned")) {
//                String[] subParts = parts[8].split(";");
//
//                // Each subparts in the content uses 3 subparts for 1 staff member. Create a staffmember and store into the list
//                for (int i = 0; i < subParts.length; i += 3) {
//                    String nurseStaffName = subParts[i];
//                    int nurseStaffID = Integer.parseInt(subParts[i + 1]);
//                    String nurseJobScope = subParts[i + 2];
//
//                    StaffMember nurse = new StaffMember(nurseStaffName, nurseStaffID, nurseJobScope);
//                    nurses.add(nurse);
//                }
//            }
//
//            // Do the same for screening time and store into LocalDateTime variable
//            LocalDateTime screeningTime = LocalDateTime.MIN;
//            if (parts[9] != null && !parts[9].equals("null")) {
//                try {
//                    screeningTime = LocalDateTime.parse(parts[9]);
//                } catch (DateTimeParseException e) {
//                    System.out.println("Could not parse screening time: " + parts[9]);
//                }
//            }
//
//            // Do the same for vitalSigns and store into String List variable
//            List<String> vitalSigns = new ArrayList<String>();
//            if (parts[10] != null && !parts[10].equals("No vital signs recorded")) {
//                String[] subParts = parts[10].split(";");
//
//                for (int i = 0; i < subParts.length; i++) {
//                    String vitalSign = subParts[i];
//                    vitalSigns.add((vitalSign));
//                }
//            }
//
//            // Do the same for doctors and store into StaffMembers List variable
//            List<StaffMember> doctors = new ArrayList<StaffMember>();
//            if (parts[11] != null && !parts[11].equals("-")) {
//                String[] subParts = parts[11].split(";");
//
//                for (int i = 0; i < subParts.length; i += 3) {
//                    String doctorStaffName = subParts[i];
//                    int doctorStaffID = Integer.parseInt(subParts[i]);
//                    String doctorJobScope = subParts[i];
//
//                    StaffMember doctor = new StaffMember(doctorStaffName, doctorStaffID, doctorJobScope);
//                    doctors.add(doctor);
//                }
//            }
//
//            // Do the same for procedures and store into String List variable
//            List<String> procedures = new ArrayList<String>();
//            if (parts[12] != null && !parts[12].equals("-")) {
//                String[] subParts = parts[12].split((";"));
//
//                for (int i = 0; i < subParts.length; i++) {
//                    String procedure = subParts[i];
//                    procedures.add(procedure);
//                }
//            }
//
//            // Get Dispatch Info and store into their respective variables
//            EmergencyCase.PatientStatus patientStatus = EmergencyCase.PatientStatus.valueOf(parts[13]);
//            int ambulanceID = Integer.parseInt((parts[14]));
//
//            List<StaffMember> medivacMembers = new ArrayList<StaffMember>();
//            // Get Information on MedivacDetails from the string
//            if (!parts[15].isEmpty() && !parts[15].equals("-")) {
//                String[] subParts = parts[15].split(";");
//
//                // Each Staff Members contains staff id, staff name, jobscope. Thus, 1 member
//                // details = 3 subparts. Hence i += 3 for each loop
//                for (int i = 0; i < subParts.length; i += 3) {
//                    String medivacStaffName = subParts[i];
//                    int medivacStaffID = Integer.parseInt(subParts[i + 1]);
//                    String medivacStaffJobScope = subParts[i + 2];
//
//                    StaffMember medivacMember = new StaffMember(medivacStaffName, medivacStaffID, medivacStaffJobScope);
//                    medivacMembers.add(medivacMember);
//                }
//            }
//
//            // Do the same for equipments and store into a String List variable
//            List<String> equipments = new ArrayList<String>();
//            // Get Information on equipmentList
//            if (!parts[16].isEmpty() && !parts[16].equals("-")) {
//                String[] subParts = parts[16].split(";");
//
//                for (int i = 0; i < subParts.length; i++) {
//                    String equipment = subParts[i];
//                    equipments.add(equipment);
//                }
//            }
//
//            // Get the timeOfCall and dispatchTeamArrivalTime and store into their respective LocalDateTime variables
//            LocalDateTime timeOfCall = LocalDateTime.MIN;
//            if (parts[17] != null) {
//                try {
//                    timeOfCall = LocalDateTime.parse(parts[17], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                } catch (DateTimeParseException e) {
//                    System.out.println("Could not parse time of call: " + parts[17]);
//                }
//            }
//
//            LocalDateTime dispatchTeamArrivalTime = LocalDateTime.MIN;
//            if (parts[18] != null && !parts[18].equals("-")) {
//                try {
//                    dispatchTeamArrivalTime = LocalDateTime.parse(parts[18], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                } catch (DateTimeParseException e) {
//                    System.out.println("Could not parse dispatch team arrival time: " + parts[18]);
//                }
//            }
//
//            // Initialize the values from the string to the dispatch case object
//            Patient patient = new Patient(patientID, patientName, vitalSigns);
//            DispatchInfo dispatchInfo = new DispatchInfo(ambulanceID, medivacMembers, equipments);
//
//            // Set the patient status to be on dispatch to initialize the location
//            EmergencyCase_Dispatch dispatchCase = new EmergencyCase_Dispatch(caseID, patient, chiefComplaint,
//                    arrivalMode, EmergencyCase.PatientStatus.ONDISPATCHED, dispatchInfo);
//
//            if (arrivalDateTime != LocalDateTime.MIN)
//                dispatchCase.SetArrivalDateTime(arrivalDateTime);
//
//            if (!nurses.isEmpty())
//                dispatchCase.setInitialScreeningNurses(nurses);
//            if (screeningTime != LocalDateTime.MIN)
//                dispatchCase.setDateAndTimeOfScreening(screeningTime);
//            if (!doctors.isEmpty())
//                dispatchCase.setScreeningDoctors(doctors);
//
//            if (!procedures.isEmpty()) {
//                for (String procedure : procedures) {
//                    dispatchCase.addEmergencyProcedure(procedure);
//                }
//            }
//
//            // Update the patient status to the correct status and update the patient
//            // location accordingly
//            dispatchCase.updatePatientStatus(patientStatus);
//
//            // Set the time of call to be the value received from the string
//            if (timeOfCall != LocalDateTime.MIN)
//                dispatchCase.setTimeOfCall(timeOfCall);
//
//            // Set the dispatch Team arrival time
//            if (dispatchTeamArrivalTime != LocalDateTime.MIN) {
//                dispatchCase.setDispatchTeamArrivalTime(dispatchTeamArrivalTime);
//                dispatchCase.calculateResponseTime();
//            }
//            System.out.println("Successfully created emergency case with ID: " + caseID);
//            return dispatchCase;
//
//        } catch (Exception e) {
//            System.out.println("Error parsing dispatch case at line: " + line);
//            System.out.println("Error message: " + e.getMessage());
//            return null;
//        }
//    }
//
//    // print active dispatch cases
    public void printActiveDispatch() {

        if (emergencyCaseDispatch.isEmpty()) {
            System.out.println("No emergency dispatch cases in the system.");
            return;
        }
        for (EmergencyCase c : emergencyCaseDispatch) {
            if (c.getPatientStatus().toString() == "ONDISPATCHED") {
                System.out.println(c.printIncidentReport());
            } else {
                System.out.println("No active Dispatch Cases");
            }
        }

    }
    public void printAllDispatch(){
        if (emergencyCaseDispatch.isEmpty()) {
            System.out.println("No emergency dispatch cases in the system.");
            return;
        }
        for (EmergencyCase c : emergencyCaseDispatch){
            System.out.println(c.printIncidentReport());
        }
    }

//    public static void main(String[] args) {
//        // Create a new emergency system
//        EmergencySystem system = new EmergencySystem();
//
//        // Create test patients
//        Patient patient1 = new Patient(1001, "John Doe");
//        Patient patient2 = new Patient(1002, "Jane Smith");
//
//        // Create test staff members
//        StaffMember nurse = new StaffMember("Sarah Smith", 1234567, "Nurse");
//
//        // Create emergency cases
//        EmergencyCase case1 = new EmergencyCase(
//                1,
//                patient1,
//                "Chest Pain",
//                "Ambulance",
//                LocalDateTime.now());
//
//        EmergencyCase case2 = new EmergencyCase(
//                2,
//                patient2,
//                "Broken Arm",
//                "Walk-in",
//                LocalDateTime.now());
//
//        // Test adding emergency cases
//        System.out.println("=== Testing Adding Emergency Cases ===");
//        system.addEmergencyCase(case1);
//        system.addEmergencyCase(case2);
//
//        // Update cases with some information
//        case1.updateInitialScreening(
//                nurse,
//                EmergencyCase.getTriageLevels()[0], // PRIORITY 1: CRITICALLY-ILL
//                "BP: 150/90, HR: 95, Temp: 37.5");
//
//        case2.updateInitialScreening(
//                nurse,
//                EmergencyCase.getTriageLevels()[2], // PRIORITY 3: MINOR EMERGENCIES
//                "BP: 120/80, HR: 75, Temp: 36.8");
//
//        // Test printing all cases
//        System.out.println("\n=== Printing All Emergency Cases ===");
//        system.printAllCaseInfo("Walk-In");
//
//        // Test printing specific case
//        System.out.println("\n=== Printing Specific Case (ID: 1) ===");
//        system.printCaseInfo(1, "Walk-In");
//
//        // Test adding a duplicate case
//        System.out.println("\n=== Testing Duplicate Case Addition ===");
//        system.addEmergencyCase(case1);
//
//        // // Testing Dispatch case
//        // EmergencyCase_Dispatch dispatchCase1 = new EmergencyCase_Dispatch(
//        // 99999,
//        // patient1,
//        // "Sad",
//        // "Ambulance",
//        // EmergencyCase.PatientStatus.ONDISPATCHED,
//        // new DispatchInfo(
//        // 555,
//        // List.of(new String[]{"Adli", "Bdli"}),
//        // new ArrayList<String>())
//        // );
//        //
//        // dispatchCase1.SetDispatchTeamArrivalTime(LocalDateTime.now().plusHours(2));
//        // dispatchCase1.SetArrivalDateTime(LocalDateTime.now().plusHours(4));
//        //
//        // // Adding to and saving dispatch case to txt
//        // system.addEmergencyCaseDispatch(dispatchCase1);
//        // system.saveAllCases();
//        //
//        // // Loading from txt to dispatch case list and load
//        // system.loadAllCases();
//        // system.emergencyCaseDispatch.getFirst().printIncidentReport();
//    }
}

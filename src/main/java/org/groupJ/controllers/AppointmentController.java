package org.groupJ.controllers;
import com.google.gson.reflect.TypeToken;
import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.models.*;
import org.groupJ.models.enums.AppointmentStatus;
import org.groupJ.util.JarLocation;
import org.groupJ.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Manages the storage and retrieval of {@link Appointment} objects.
 * This class provides centralized management of appointments through a static list and supports operations such as adding, removing, and searching for appointments. It also handles persistence by reading from and writing to a file.
 * The appointments are stored in a static list, allowing for easy access and manipulation across different parts of the application without instantiating the controller.
 */
public class AppointmentController {
    private static List<Appointment> appointments = new ArrayList<>();
    private static final String fileName = "appointments.txt";

    /**
     * Adds a new appointment to the list and sorts the list of appointments.
     * This method inserts the specified appointment into the static list and then sorts all appointments based on the recent date ,
     * this simulates a queue system where those who requested for an earlier appointment will be given a priority
     * potentially based on criteria such as date and time or priority (depending on the implementation of the sortAppointment method).
     *
     * @param appointment the appointment to be added to the list; cannot be null
     */

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
        sortAppointment();
    }

    /**
     * Sorts the list of appointments by appointment time in ascending order.
     * This method uses a comparator based on the appointment time to order the appointments,
     * ensuring that earlier times come first in the list. This is useful for organizing appointments chronologically
     * for display or processing purposes.
     */
    public void sortAppointment(){
        appointments.sort(Comparator.comparing(Appointment::getAppointmentTime));
    }

    /**
     * Retrieves the current list of appointments.
     * If the list is initially empty, it attempts to load appointments from a file before returning the list. This ensures that the method returns up-to-date data by checking and potentially reloading the list from persistent storage.
     * This method is critical for accessing the list of all appointments managed by the AppointmentController.
     *
     * @return a list of appointments; never null but may be empty if no appointments are available or if loading from the file fails.
     */
    public List<Appointment> getAppointments(){
        if(appointments.isEmpty()){
            loadAppointmentFromFile();
        }
        return appointments;
    }
    /**
     * Loads appointments from a file into the appointments list.
     * This method clears the existing list of appointments and attempts to load a new list from a specified file.
     * It reads the file content into a string, then deserializes it into a list of Appointment objects using a JSON parser.
     * If an IOException occurs during file reading or parsing, the error is caught and printed to the standard error stream.
     * <p>
     * Note: This method assumes that the file format is JSON and that it correctly represents a list of Appointment objects.
     * Ensure the file path and format are correct to prevent runtime errors or data corruption.
     */
    private void loadAppointmentFromFile() {
        appointments.clear();
        StringBuilder sb = new StringBuilder();
        String basePath;

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
            throw new RuntimeException(e);
        }
        try {
            assert basePath != null;
            try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Type listType = new TypeToken<List<Appointment>>() {
                }.getType();
                appointments = Util.fromJsonString(sb.toString(), listType);
            }
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Saves the current list of appointments to a file.
     * This method serializes the list of appointments into JSON format using Gson and writes it to the specified file.
     * The file name is determined by the {@code fileName} class variable. If the writing process encounters an IOException,
     * the exception is caught and the stack trace is printed, which can help in diagnosing the issue.
     *
     * Upon successful completion, a message is printed to the console indicating that the appointments have been saved.
     *
     * @throws IOException if there is an error writing to the file. While the exception is caught internally and a stack trace is printed,
     * it's important for users of the method to be aware that an I/O exception could indicate a failure to save data properly.
     */
    public void saveAppointmentsToFile() {
        String basePath;

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
            throw new RuntimeException(e);
        }
        assert basePath != null;
        String path = Paths.get(basePath, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = Globals.gsonPrettyPrint.toJson(appointments);
            writer.write(json);
            System.out.println("Appointments saved to " + fileName);
            System.out.println("Press 0 to continue");
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }
    
    /**
     * Generates dummy data for testing and demonstrating purposes
     * @param numberOfAppointments indicate the number of appointment to be created
     */
    public void generateRandomAppointmentData(int numberOfAppointments) {
        Random random = new Random();
        // Get the list of dummy patients
        //Pair<List<Patient>, List<Doctor>> patientDoctor = Globals.userController.generateDummyUsers();
        List<Doctor> doctors = UserController.getAvailableDoctors();
        List<Patient> patients = UserController.getAvailablePatients();

        //String[] Reasons = {"Flu", "Cough", "Migraine", "Vertigo"};
        for (int i = 0; i < numberOfAppointments; i++) {
            Patient patient = patients.get(i % patients.size()); // Cycle through the available patients
            Doctor doctor = doctors.get(random.nextInt(doctors.size())); // Choose a random doctor

            // Generate random data for the appointment
            //String reason = Reasons[random.nextInt(Reasons.length)];

            List <String> symptoms = patient.getEHR()
                    .getSymptoms()
                    .stream()
                    .map(Symptoms::getSymptomName)
                    .toList();
            String StringOfSymptoms = String.join(", ", symptoms);

            LocalDateTime appointmentTime = LocalDateTime.now().plusMinutes(random.nextInt(1440));
            AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;
            String diagnosis = "";

            // Create the Appointment object
            Appointment appointment = new Appointment(patient, StringOfSymptoms, appointmentTime, appointmentStatus , diagnosis);
            appointment.setDoctor(doctor);

            // Add the appointment to the list
            appointments.add(appointment);
        }

        saveAppointmentsToFile();
    }

}

package org.groupJ.controllers;
import com.google.gson.reflect.TypeToken;
import org.groupJ.Globals;
import org.groupJ.audit.*;
import org.groupJ.core.Alert;
import org.groupJ.models.*;
import org.groupJ.models.enums.UserType;
import org.groupJ.util.InputValidator;
import org.groupJ.util.JarLocation;
import org.groupJ.util.Pair;
import org.groupJ.util.Util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;

import static org.groupJ.controllers.MedicationController.getAvailableMedications;

/**
 * Controller class that stores the logged-in user. Can be either a {@link Doctor} or {@link Patient}.
 * As well as login logic and stores/loads the users to a JSON file for persistence.
 */
public class UserController {
    private static List<User> users = new ArrayList<>();
    private static List<Patient> generatePatients = new ArrayList<>();
    private static List<Nurse> generateNurses = new ArrayList<>();
    private static List<Doctor> allDoctors = new ArrayList<>();
    private static List<Nurse> allNurses = new ArrayList<>();
    private static List<Paramedic> allParamedics = new ArrayList<>();

    private static final String doctorFileName = "doctor_data.txt";
    private static final String patientFileName = "patient_data.txt";
    private static final String nurseFileName = "nurse_data.txt";
    private static final String paramedicFileName = "paramedics_data.txt";

    /**
     * the active doctor that is currently logged in.
     */
    private static Doctor activeDoctor;
    /**
     * Placeholder nurse, for the scope of this project, not required. Handle this case gracefully
     */
    private static Nurse activeNurse;
    /**
     * Placeholder patient, for the scope of this project, not required. Handle this case gracefully
     */
    private static Patient activePatient;

    private static Paramedic activeParamedic;

    private static UserType activeUserType;

    /**
     * Loads users (Patients, Doctors and Nurses) from files specified by patientFileName and doctorFileName.
     * This method first clears the current list of users, then loads patients from the patientFileName
     * and subsequently loads doctors from the doctorFileName, adding them to the users list.
     * The files are expected to contain JSON representations of lists of {@link Patient} and {@link Doctor} objects, respectively.
     * If a file does not exist or an error occurs during reading or parsing, an error message is logged, and loading from that file is skipped.
     * Note: This method replaces the entire existing list of users.*/

    public static void loadPatientsFromFile() {
        allpatients.clear();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(patientFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Patient>>() {
            }.getType();
            allpatients = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Loads doctor data from the specified file.
     * This method clears the existing list of doctors and then attempts to read doctor data from the file
     * specified by {@code doctorFileName}. It assumes the file contains a JSON representation of a list of
     * {@link Doctor} objects.
     */

    private static void loadDoctorsFromFile() {
        allDoctors.clear();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(doctorFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Doctor>>() {
            }.getType();
            allDoctors = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Loads nurse data from the specified file.
     * This method clears the existing list of nurses and then attempts to read nurse data from the file
     * specified by {@code nurseFileName}. It assumes the file contains a JSON representation of a list of
     * {@link Nurse} objects.
     */

    public static void loadNursesFromFile() {
        allNurses.clear();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(nurseFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Nurse>>() {
            }.getType();
            allNurses = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }
    /**
     * Saves the list of generated patients to a file.
     * This method serializes the {@code generatePatients} list to a JSON string using Gson's pretty printing feature
     * and writes it to the file specified by {@code patientFileName}.
     */

    public static void savePatientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(patientFileName))) {
            String json = Globals.gsonPrettyPrint.toJson(generatePatients);
            writer.write(json);
            System.out.println("Medicines saved to " + patientFileName);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveUserType().name(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    public static void loadParamedicFromFile() {
        allParamedics.clear();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(paramedicFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Paramedic>>() {
            }.getType();
            allParamedics = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveParamedic().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Saves the list of generated nurses to a file.
     * This method serializes the {@code generateNurses} list to a JSON string using Gson's pretty printing feature
     * and writes it to the file specified by {@code nurseFileName}.
     */

    public static void saveNurseToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nurseFileName))) {
            String json = Globals.gsonPrettyPrint.toJson(generateNurses);
            writer.write(json);
            System.out.println("Medicines saved to " + nurseFileName);
        } catch (IOException e) {
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Retrieves a list of available patients.
     * If the list of patients is empty, it attempts to load patients from file first.
     *
     * @return A list of {@link Patient} objects.
     */

    public static List<Patient> getAvailablePatients() {
        if (allpatients.isEmpty()) {
            loadPatientsFromFile();
        }
        return new ArrayList<>(allpatients); // Return a *copy*
    }

    /**
     * Retrieves a list of available doctors.
     * If the list of doctors is empty, it attempts to load doctors from file first.
     *
     * @return A list of {@link Doctor} objects.
     */

    public static List<Doctor> getAvailableDoctors() {
        if (allDoctors.isEmpty()) {
            loadDoctorsFromFile();
        }
        return new ArrayList<>(allDoctors); // Return a *copy*
    }

    /**
     * Retrieves a list of available nurses.
     * If the list of nurses is empty, it attempts to load nurses from file first.
     *
     * @return A list of {@link Nurse} objects.
     */

    public static List<Nurse> getAvailableNurses() {
        if (allNurses.isEmpty()) {
            loadNursesFromFile();
        }
        return new ArrayList<>(allNurses); // Return a *copy*
    }
    /**
     * Retrieves a list of patients from the loaded users.
     * If the users list is empty, it first attempts to load users from the file using {@link #loadUsersFromFile()}.
     * Then, it filters the users to include only instances of {@link Patient}, casts them to {@link Patient},
     * and collects them into a new list.
     *
     * @return A list of {@link Patient} objects. Returns an empty list if no patients are found or if an error occurs during loading.
     * @see #loadUsersFromFile()
     * @see Patient
     */

    private void loadUsersFromFile() {
        users.clear();
        StringBuilder sb = new StringBuilder();
        String basePath = "";

        // Load base path
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            AuditManager.getInstance().logAction(
                    "SYSTEM", "JAR DIRECTORY ERROR", e.getMessage(), "FAILURE", "SYSTEM");
            throw new RuntimeException(e);
        }

        // Load Patients
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, patientFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Patient>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        } catch (IOException e) {
            AuditManager.getInstance().logAction(
                    "SYSTEM", "LOAD PATIENT FILE FAILED", e.getMessage(), "FAILURE", "SYSTEM");
        }

        // Load Doctors
        sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, doctorFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Doctor>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        } catch (IOException e) {
            AuditManager.getInstance().logAction(
                    "SYSTEM", "LOAD DOCTOR FILE FAILED", e.getMessage(), "FAILURE", "SYSTEM");
        }

        // Load Nurses
        sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, nurseFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Nurse>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        } catch (IOException e) {
            AuditManager.getInstance().logAction(
                    "SYSTEM", "LOAD NURSE FILE FAILED", e.getMessage(), "FAILURE", "SYSTEM");
        }

        // Load Paramedics
        sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, paramedicFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Paramedic>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        } catch (IOException e) {
            AuditManager.getInstance().logAction(
                    "SYSTEM", "LOAD PARAMEDIC FILE FAILED", e.getMessage(), "FAILURE", "SYSTEM");
        }
    }

    /**
     * Retrieves a list of all patients.
     * If the user list is empty, it attempts to load users from files first.
     *
     * @return A list of {@link Patient} objects.
     */

    public List<Patient> getPatients(){
        if(users.isEmpty()){
            loadUsersFromFile();
        }
        // get the users which are instanceof patient, then map and cast them all to Patients
        return users.stream().filter(usr->usr instanceof Patient).map(usr->(Patient)usr).toList();
    }

    /**
     * Saves a list of users to a JSON file.
     * This method serializes the given list of users to JSON format using a pretty-printing Gson instance
     * and writes the JSON string to the file specified by the fileName.
     * The type T must extend {@link User}.
     * If an error occurs during file writing, it logs an error message.
     * @param <T>      The type of User (e.g., Patient, Doctor). Must extend {@link User}.
     * @param users    The list of users to save.
     * @param fileName The name of the file to save the users to.
     * @see User
     * @throws IllegalArgumentException if the file name is null or empty.
     */

    private static <T extends User> void saveUsersToFile(List<Patient> users, String fileName) {
        String basePath = "";
        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String path = Paths.get(basePath, fileName).toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = Globals.gsonPrettyPrint.toJson(users);
            writer.write(json);
            System.out.println("Users saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user based on the provided username and password.
     * This method searches the loaded users for a user with a matching login name and password.
     * If the user is found and the credentials are valid, the method returns the {@link UserType}
     * (DOCTOR or PATIENT). It also sets the activeDoctor or activePatient based on the user type.
     * If the user is not found or the credentials are invalid, it returns {@link UserType#ERROR}.
     * If the users list is empty, it first attempts to load users from the file using {@link #loadUsersFromFile()}.
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return The {@link UserType} of the authenticated user (DOCTOR, PATIENT, or ERROR).
     * @see User
     * @see UserType
     * @see #loadUsersFromFile()*/

    public UserType authenticate(String username, String password){
        if(users.isEmpty()){
            loadUsersFromFile();
        }
        ESController.loadEmergencyCaseFromFile();
        List<User> authenticated = users.stream().filter(usr->
            usr.getLoginName().equals(username) && usr.checkPassword(password)
        ).toList();

        if(authenticated.isEmpty()){
            AuditManager.getInstance().logAction("NIL", "LOGIN", "System", "FAILED","");
            return UserType.ERROR;
        }
        if(authenticated.getFirst() instanceof Doctor){
            activeDoctor = (Doctor)authenticated.getFirst();
            activeUserType = UserType.DOCTOR;
            AuditManager.getInstance().logAction(activeDoctor.getId(), "LOGIN", "System", "SUCCESS", "DOCTOR");
            return UserType.DOCTOR;
        }
        if(authenticated.getFirst() instanceof Nurse){
            activeNurse = (Nurse)authenticated.getFirst();
            activeUserType = UserType.NURSE;
            AuditManager.getInstance().logAction(activeNurse.getId(), "LOGIN", "System", "SUCCESS", "NURSE");
            return UserType.NURSE;
        }
        if(authenticated.getFirst() instanceof Paramedic){
            activeParamedic = (Paramedic) authenticated.getFirst();
            activeUserType = UserType.PARAMEDIC;
            AuditManager.getInstance().logAction(activeParamedic.getId(), "LOGIN", "System", "SUCCESS", "PARAMEDIC");
            return UserType.PARAMEDIC;
        }
        if(authenticated.getFirst() instanceof Patient){
            activePatient = (Patient)authenticated.getFirst();
            activeUserType = UserType.PATIENT;
            AuditManager.getInstance().logAction(activePatient.getId(), "LOGIN", "System", "SUCCESS", "PATIENT");
            return UserType.PATIENT;
        }
        AuditManager.getInstance().logAction("NIL", "LOGIN", "System", "FAILED","");
        return UserType.ERROR;
    }

    public static boolean comparePasswordHash(Pair<byte[], byte[]> hashSalt, String password){
        return hashPassword(password, hashSalt.second).equals(hashSalt);
    }

    /**
     * Static method that hashes the password, and automatically generates the hash
     * Reference : <a href="https://www.baeldung.com/java-password-hashing">...</a>
    /**
     * Static method that hashes the password, and automatically generates the hash
     * Reference : <a href="https://www.baeldung.com/java-password-hashing">https://www.baeldung.com/java-password-hashing</a>
     * @param password The plaintext password
     * @param salt The password salt
     * @return a Pair object (first is password hash) (second is password salt)
     */
    public static Pair<byte[], byte[]> hashPassword(String password, byte[] salt){
        SecureRandom random = new SecureRandom();
        byte[] hash = null;
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Pair<>(hash, salt);
    }

    /**
     * Generates a random salt value to be used for password hashing.
     *
     * @return A byte array representing the generated salt.
     */

    public static byte[] generateRandomPasswordSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**Gets the active user type.
     * @return the {@link UserType} of the currently active user, or null if no user is active.*/
    public static UserType getActiveUserType() {
        return activeUserType;
    }

    /** Gets the active doctor.
     * @return the active {@link Doctor}, or null if no doctor is active.*/
    public static Doctor getActiveDoctor() {
        return activeDoctor;
    }

    /** Gets the active nurse.
     * @return the active {@link Nurse}, or null if no nurse is active.*/
    public static Nurse getActiveNurse() {
        return activeNurse;
    }

    public static Paramedic getActiveParamedic() {
        return activeParamedic;
    }

    /**Gets the active patient.
     * @return the active {@link Patient}, or null if no patient is active.*/
    public static Patient getActivePatient() { return activePatient;}

    public static void generateDummyNurses(){
        List<Nurse> dummyNurses = new ArrayList<>();
        dummyNurses.add(new Nurse("N001","emily", "Emily Carter", "password123","emilycarter@gmail","Female", "87654321",
                "Pediatrics", "Pediatric Nurse", "Ward A",  true, "RN-2023-00123"));
        dummyNurses.add(new Nurse("N002","michael", "Michael Roberts", "password123","MichaelRoberts@gmail","Male", "87654221",
                "Emergency", "Emergency Nurse", "ER",  true, "RN-2020-00567"));
        dummyNurses.add(new Nurse("N003", "daniel", "Daniel lee","password123","daniellee@gmail.com", "Male","81237111", "" +
                "Oncology Nurse", "Oncology", "Ward B", false, "RN-2021-00345"));
        generateNurses.add(dummyNurses.get(0));
        generateNurses.add(dummyNurses.get(1));
        generateNurses.add(dummyNurses.get(2));
        saveNurseToFile();
    }
    // method used for generating patients.
    public static void generateDummyUsers() {

        List<Patient> dummyPatients = new ArrayList<>();
        dummyPatients.add(new Patient("P001", "JohnDoe", "John Doe", "password123", "John Doe@example.com", "Male","81234567", "15-08-1990","Diabetic", "N001", "D003",
                null,180, 75, "O+", "123 CDSS.Main.Main.CDSS.Main.Main St, NY", 98765432,"Engineer", "Asian", "Cardiology"));

        dummyPatients.add(new Patient("P002", "JaneSmith", "Jane Smith", "password123", "janesmith@example.com", "Female","81234567", "15-08-1990","Diabetic", "N001", "D003",
                null,180, 75, "O+", "123 CDSS.Main.Main.CDSS.Main.Main St, NY", 98765432, "Engineer", "Asian", "Neurology"));

        dummyPatients.add(new Patient("P003", "BobBrown", "Bob Brown", "password123", "BobBrown@example.com","Male","87654321", "15-08-1990",  "Asthmatic", "N002", "D002",
                null,158, 55, "B+", "789 Pine St, TX", 76543210, "Nurse", "Hispanic",  "Pulmonology"));

        dummyPatients.add(new Patient("P004", "CharlieDavis", "Charlie Davis", "password123", "charlied@example.com","Male","87654321", "15-08-1990",  "Asthmatic", "N002", "D002",
                null,170, 58, "B+", "159 Maple St, AZ", 76543210, "Nurse", "Hispanic",  "Pulmonology"));

        dummyPatients.add(new Patient("P005", "HannahLee", "Hannah Lee", "password123", "hannahl@example.com","Female","87654321", "15-08-1990",  "Asthmatic", "N003", "D003",
                null,170, 58, "B+", "159 Maple St, AZ", 76543210, "Nurse", "Hispanic",  "Pulmonology"));

        dummyPatients.add(new Patient("P006", "DianaEvans", "Diana Evans","password123","dianae@example.com","Female","81234567","15-08-1990","Pregnant", "N003", "D002",
                null,160, 58, "A+", "753 Cedar St, WA", 43210987, "Artist", "Asian", "Obstetrics"));

        dummyPatients.add(new Patient("P007", "EdwardHarris", "Edward Harris","password123","edwardh@example.com","Male","81234567","15-08-1990","Pregnant", "N003", "D002",
                null,160, 58, "A-", "357 Birch St, CO", 43210987, "Artist", "Caucasian", "Nephrology"));

        dummyPatients.add(new Patient("P008", "FionaGreen", "Fiona Green","password123","edwardh@example.com","Male","81234567","15-08-1990","Pregnant", "N003", "D002",
                null,160, 58, "A+", "951 Spruce St, NV", 43210987, "Chef", "Hispanic", "Hematology"));

        dummyPatients.add(new Patient("P009", "GeorgeKing", "George King","password123","georgek@example.com","Male","81234567","15-08-1990","Pregnant", "N003", "D002",
                null,160, 58, "A+", "951 Spruce St, NV", 43210987, "Chef", "Hispanic", "Hematology"));


        generatePatients.add(dummyPatients.get(0));
        generatePatients.add(dummyPatients.get(1));
        generatePatients.add(dummyPatients.get(2));
        generatePatients.add(dummyPatients.get(3));
        generatePatients.add(dummyPatients.get(4));
        generatePatients.add(dummyPatients.get(5));
        generatePatients.add(dummyPatients.get(6));
        generatePatients.add(dummyPatients.get(7));
        generatePatients.add(dummyPatients.get(8));
        populatePatientEHR(generatePatients);
        savePatientsToFile();
    }
    private static List<String> getRandomSublist(List<String> list, Random random) {
        List<String> copy = new ArrayList<>(list);
        Collections.shuffle(copy, random);
        int count = random.nextInt(copy.size() + 1); // count between 0 and list.size()
        return copy.subList(0, count);
    }
    private static void populatePatientEHR(List<Patient> patients) {
        String[] allergyOptions = {"Peanuts", "Dust", "Pollen", "Shellfish", "Latex", "Gluten", "Penicillin"};
        String[] medicalHistoryOptions = {"Hypertension", "Diabetes", "Asthma", "Arthritis", "Obesity"};
        String[] labResultsOptions = {"CBC: Normal", "Lipid Panel: Elevated", "Blood Glucose: High", "Hemoglobin: Low", "Thyroid: Normal"};
        String[] imagingOptions = {"X-Ray", "MRI", "CT Scan", "Ultrasound", "PET Scan"};
        String[] clinicalNotesOptions = {
                "Patient complains of headache.",
                "No significant issues.",
                "Follow-up needed in 2 weeks.",
                "Patient is recovering well.",
                "Monitor blood pressure closely."
        };
        String[] pastSurgeriesOptions = {"Appendectomy", "Gallbladder Removal", "Knee Replacement", "Cataract Surgery", "Hernia Repair"};
        String[] vaccinationOptions = {"COVID-19 Vaccine", "Influenza Vaccine", "Hepatitis B Vaccine", "Tetanus Booster", "Measles, Mumps, Rubella (MMR) Vaccine"};

        Random random = new Random();
        for (int i = 0; i < patients.size(); i++) {
            ElectronicHealthRecord electronicHealthRecord = new ElectronicHealthRecord();
            electronicHealthRecord.setAllergies(getRandomSublist(Arrays.asList(allergyOptions), random));
            electronicHealthRecord.setMedicalHistory(getRandomSublist(Arrays.asList(medicalHistoryOptions), random));
            electronicHealthRecord.setPastSurgeries(getRandomSublist(Arrays.asList(pastSurgeriesOptions), random));
            electronicHealthRecord.setVaccinationRecord(getRandomSublist(Arrays.asList(vaccinationOptions), random));
            electronicHealthRecord.setVitalSigns(generateRandomVitalSigns(random));
            electronicHealthRecord.setLabResults(getRandomSublist(Arrays.asList(labResultsOptions), random));
            electronicHealthRecord.setImagingRecords(getRandomSublist(Arrays.asList(imagingOptions), random));
            electronicHealthRecord.setClinicalNotes(clinicalNotesOptions[random.nextInt(clinicalNotesOptions.length)]);

            List<Symptoms> allSymptoms = generateSymptoms();
            List<Symptoms> selectedSymptoms = getRandomSymptoms(allSymptoms);
            electronicHealthRecord.setSymptoms(selectedSymptoms);
            electronicHealthRecord.setCurrentMedications(getMedicationsForSymptom(selectedSymptoms));
            patients.get(i).setEHR(electronicHealthRecord);
        }
    }
    private static VitalSigns generateRandomVitalSigns(Random random) {
        int systolic = random.nextInt(51) + 90;
        int diastolic = random.nextInt(31) + 60;
        double temperature = 36.0 + random.nextDouble() * 1.5;
        temperature = Math.round(temperature * 10.0) / 10.0;
        int heartRate = random.nextInt(41) + 60;
        int respiratoryRate = random.nextInt(11) + 12;
        return new VitalSigns(temperature, heartRate, systolic, diastolic, respiratoryRate);
    }

    public static List <Symptoms> generateSymptoms(){
        List<Symptoms> symptomsList = new ArrayList<>();

        // Common Cold / Flu Symptoms
        symptomsList.add(new Symptoms("Fever, chills, body aches", 6, 5, "Patient has flu-like symptoms, monitor temperature."));
        symptomsList.add(new Symptoms("Sore Throat", 4, 3, "Recommend warm fluids and throat lozenges."));
        symptomsList.add(new Symptoms("Runny Nose, Nasal Congestion", 3, 4, "May be allergic reaction or viral infection."));

        // Respiratory Issues
        symptomsList.add(new Symptoms("Chest Pain", 8, 2, "Check for possible heart conditions or respiratory infections."));
        symptomsList.add(new Symptoms("Difficulty Breathing", 9, 1, "Emergency! Consider asthma attack, pneumonia, or COVID-19."));
        symptomsList.add(new Symptoms("Chronic Cough", 5, 10, "Check for tuberculosis or bronchitis."));

        // Neurological Symptoms
        symptomsList.add(new Symptoms("Severe Headache", 7, 2, "Possible migraine or tension headache. Consider CT scan if persistent."));
        symptomsList.add(new Symptoms("Dizziness, Light headedness", 6, 1, "Check for dehydration or inner ear issues."));
        symptomsList.add(new Symptoms("Loss of Consciousness",  10, 0, "Medical emergency. Check for head trauma or stroke."));

        // Digestive Issues
        symptomsList.add(new Symptoms("Nausea and Vomiting", 5, 1, "Check for food poisoning or stomach flu."));
        symptomsList.add(new Symptoms("Severe Abdominal Pain", 9, 2, "Possible appendicitis or pancreatitis."));
        symptomsList.add(new Symptoms("Constipation", 3, 4, "Encourage fiber-rich diet and hydration."));

        // Cardiovascular Symptoms
        symptomsList.add(new Symptoms("Rapid or Irregular Heartbeat", 8, 1, "Possible arrhythmia, conduct ECG."));
        symptomsList.add(new Symptoms("Swelling in Legs and Ankles", 6, 5, "Check for heart failure or kidney disease."));
        symptomsList.add(new Symptoms("Extreme Fatigue", 5, 7, "Could indicate anemia or chronic fatigue syndrome."));

        // COVID-19 Specific Symptoms
        symptomsList.add(new Symptoms("Loss of Taste and Smell", 4, 14, "Check for COVID-19 infection."));
        symptomsList.add(new Symptoms("Persistent Dry Cough", 6, 10, "Monitor for COVID-19 or other respiratory infections."));

        // Skin and Musculoskeletal Symptoms
        symptomsList.add(new Symptoms("Skin Rash or Itching", 4, 3, "Could indicate allergic reaction or dermatitis."));
        symptomsList.add(new Symptoms("Joint Pain and Swelling", 6, 5, "Possible arthritis or autoimmune disorder."));
        symptomsList.add(new Symptoms("Muscle Weakness", 7, 3, "Check for neurological or muscular disorders."));

        return symptomsList;
    }
    // Add this method to generate unique symptoms for each user
    public static List<Symptoms> getRandomSymptoms(List<Symptoms> allSymptoms) {
        // Create a copy to avoid modifying the original list
        List<Symptoms> copyOfSymptoms = new ArrayList<>(allSymptoms);

        // Shuffle with a new Random instance each time
        Collections.shuffle(copyOfSymptoms, new Random(System.currentTimeMillis()));

        // Select 2-3 symptoms
        int count = new Random().nextInt(2) + 2; // 2 or 3

        // Make sure we don't exceed the list size
        count = Math.min(count, copyOfSymptoms.size());

        return copyOfSymptoms.subList(0, count);
    }

    private static Map<String, List<Medication>> symptomToMedications = new HashMap<>();
    private static Map<String, List<Medication>> getSymptomToMedication() {
        if (symptomToMedications.isEmpty()) {
            List<Medication> availableMedication = getAvailableMedications();
            // Make sure the list is not empty
            if (availableMedication == null || availableMedication.isEmpty()) {
                throw new IllegalStateException("Available medications list is empty");
            }
            symptomToMedications.put("Fever, chills, body aches", Arrays.asList(availableMedication.get(0), availableMedication.get(7))); // Paracetamol, Ibuprofen
            symptomToMedications.put("Sore Throat", Arrays.asList(availableMedication.get(3), availableMedication.get(5))); // Diphenhydramine, Amoxicillin
            symptomToMedications.put("Runny Nose, Nasal Congestion", Arrays.asList(availableMedication.get(3), availableMedication.get(4))); // Diphenhydramine, Loperamide
            symptomToMedications.put("Chest Pain", Arrays.asList(availableMedication.get(2), availableMedication.get(1))); // Furosemide, Aspirin
            symptomToMedications.put("Difficulty Breathing", Arrays.asList(availableMedication.get(6), availableMedication.get(2))); // Metformin, Furosemide
            symptomToMedications.put("Chronic Cough", Arrays.asList(availableMedication.get(3), availableMedication.get(5))); // Diphenhydramine, Amoxicillin
            symptomToMedications.put("Severe Headache", Arrays.asList(availableMedication.get(7), availableMedication.get(0))); // Ibuprofen, Paracetamol
            symptomToMedications.put("Dizziness, Light headedness", Arrays.asList(availableMedication.get(3))); // Diphenhydramine
            symptomToMedications.put("Loss of Consciousness", Arrays.asList(availableMedication.get(1))); // Aspirin
            symptomToMedications.put("Nausea and Vomiting", Arrays.asList(availableMedication.get(3), availableMedication.get(4))); // Diphenhydramine, Loperamide
            symptomToMedications.put("Severe Abdominal Pain", Arrays.asList(availableMedication.get(2), availableMedication.get(7))); // Furosemide, Ibuprofen
            symptomToMedications.put("Constipation", Arrays.asList(availableMedication.get(4))); // Loperamide
            symptomToMedications.put("Rapid or Irregular Heartbeat", Arrays.asList(availableMedication.get(6), availableMedication.get(8))); // Metformin, Atorvastatin
            symptomToMedications.put("Swelling in Legs and Ankles", Arrays.asList(availableMedication.get(2))); // Furosemide
            symptomToMedications.put("Extreme Fatigue", Arrays.asList(availableMedication.get(9))); // Levothyroxine
            symptomToMedications.put("Loss of Taste and Smell", Arrays.asList(availableMedication.get(5))); // Amoxicillin
            symptomToMedications.put("Persistent Dry Cough", Arrays.asList(availableMedication.get(3))); // Diphenhydramine
            symptomToMedications.put("Skin Rash or Itching", Arrays.asList(availableMedication.get(3), availableMedication.get(8))); // Diphenhydramine, Atorvastatin
            symptomToMedications.put("Joint Pain and Swelling", Arrays.asList(availableMedication.get(7))); // Ibuprofen
            symptomToMedications.put("Muscle Weakness", Arrays.asList(availableMedication.get(6), availableMedication.get(9))); // Metformin, Levothyroxine
            symptomToMedications.put("High Cholesterol", Arrays.asList(availableMedication.get(8))); // Atorvastatin
            symptomToMedications.put("Thyroid Issues", Arrays.asList(availableMedication.get(9))); // Levothyroxine
        }
        return symptomToMedications;
    }

    public static List<Medication> getMedicationsForSymptom(List<Symptoms> symptoms) {
        // Use a Set to automatically eliminate duplicates
        if (symptomToMedications.isEmpty()) {
            getSymptomToMedication();
        }
        Set<Medication> medicationSet = new HashSet<>();

        for (Symptoms symptom : symptoms) {
            String symptomName = symptom.getSymptomName();
            // Try case-insensitive lookup
            for (Map.Entry<String, List<Medication>> entry : symptomToMedications.entrySet()) {
                String key = entry.getKey();

                if (key.equalsIgnoreCase(symptomName)) {
                    // Add all medications to the set (duplicates will be automatically eliminated)
                    medicationSet.addAll(entry.getValue());
                    break;
                }
            }
        }
        // Convert the set back to a list for return
        List<Medication> medicationList = new ArrayList<>(medicationSet);

        // Limit the list to a maximum of 3 medications
        if (medicationList.size() > 3) {
            medicationList = medicationList.subList(0, 3);
        }
        return medicationList;
    }

    //debugging patients(console print version)
    private static List<Patient> allpatients = new ArrayList<>();

    public static void printAllPatients() {
        System.out.println("All Patients:");
        for (Patient patient : allpatients) {
            System.out.println("=======================================");
            System.out.println("          PATIENT INFORMATION          ");
            System.out.println("=======================================");
            System.out.printf("%-20s: %s%n", "Patient ID", patient.getId());
            System.out.printf("%-20s: %s%n", "Name", patient.getName());
            System.out.printf("%-20s: %s%n", "Gender", patient.getGender());
            System.out.printf("%-20s: %s%n", "Date of Birth", patient.getDateOfBirth());
            System.out.printf("%-20s: %s%n", "Height", patient.getHeight() + " cm");
            System.out.printf("%-20s: %s%n", "Weight", patient.getWeight() + " kg");
            System.out.printf("%-20s: %s%n", "Blood Type", patient.getBloodType());
            System.out.printf("%-20s: %s%n", "House Address", patient.getHouseAddress());
            System.out.printf("%-20s: %s%n", "Emergency Contact", patient.getEmergencyContactNumber());
            System.out.printf("%-20s: %s%n", "Occupation", patient.getOccupation());
            System.out.printf("%-20s: %s%n", "Ethnicity", patient.getEthnicity());
            System.out.printf("%-20s: %s%n", "Email", patient.getEmail());
            System.out.printf("%-20s: %s%n", "Healthcare Dept.", patient.getHealthcareDepartment());
            System.out.printf("%-20s: %s%n", "Patient Consent", patient.getPatientConsent().isConsentGiven());

            System.out.println("\n---------------------------------------");
            System.out.println("          ELECTRONIC HEALTH RECORD     ");
            System.out.println("---------------------------------------");
            System.out.printf("%-20s: %s%n", "Allergies", formatList(patient.getEHR().getAllergies()));
            System.out.printf("%-20s: %s%n", "Medical History", formatList(patient.getEHR().getMedicalHistory()));

            System.out.println("\nCurrent Medications:");
            for (Medication med : patient.getEHR().getCurrentMedications()) {
                System.out.println("  - " + med.getMedicationName());
            }

            System.out.printf("%-20s: %s%n", "\nVital Signs", patient.getEHR().getVitalSigns());

            System.out.println("\nPast Surgeries:");
            for (String surgery : patient.getEHR().getPastSurgeries()) {
                System.out.println("  - " + surgery);
            }

            System.out.println("\nVaccination Record:");
            for (String vaccine : patient.getEHR().getVaccinationRecord()) {
                System.out.println("  - " + vaccine);
            }

            System.out.println("\nLab Results:");
            for (String result : patient.getEHR().getLabResults()) {
                System.out.println("  - " + result);
            }

            System.out.println("\nImaging Records:");
            for (String image : patient.getEHR().getImagingRecords()) {
                System.out.println("  - " + image);
            }

            if (patient.getEHR().getSymptoms().isEmpty()) {
                System.out.printf("%-20s: %s%n", "\nSymptoms", "Nil");
            } else {
                System.out.printf("%-20s:%n", "\nSymptoms"); // Print the label first
                for (Symptoms symptom : patient.getEHR().getSymptoms()) {
                    System.out.printf("  - %-18s: %s%n", "Symptom Name", symptom.getSymptomName());
                    System.out.printf("    %-18s: %s%n", "Symptom ID", symptom.getSymptomId());
                    System.out.printf("    %-18s: %d%n", "Severity Level", symptom.getSeverity());
                    System.out.printf("    %-18s: %d%n", "Duration (days)", symptom.getDuration());
                    System.out.printf("    %-18s: %s%n", "Doctor Notes", symptom.getDoctorNotes());
                    System.out.println(); // Add space between symptoms
                }
            }

            System.out.printf("%-20s: %s%n", "Diagnosis", patient.getEHR().getDiagnosis());
            System.out.printf("%-20s: %s%n", "Clinical Notes", patient.getEHR().getClinicalNotes());

            if (!patient.getEHR().getOutcomeMonitoringRecords().isEmpty()) {
                System.out.println("\nOutcome Monitoring Records:");
                for (String record : patient.getEHR().getOutcomeMonitoringRecords()) {
                    System.out.println("  - " + record);
                }
            }

            System.out.println("=======================================");
        }
    }

    private static String formatList(List<String> list) {
        return list != null && !list.isEmpty() ? String.join(", ", list) : "None";
    }

    public static Patient checkOrCreatePatient(User user) {
        List<Patient> allPatients = getAvailablePatients();
        String patientID = ""; // Keep prompting until patientID contains "P" (ignoring case)
        while (true) {
            patientID = InputValidator.getValidStringInput("Enter Patient ID: ");
            if (patientID.toUpperCase().contains("P")) {
                break;
            } else {
                System.out.println("Invalid ID. The Patient ID must contain the letter 'P'. Please try again.");
            }
        }
        // Check if patient exists in our list
        for (Patient p : allPatients) {
            if (p.getId().equalsIgnoreCase(patientID)) {
                System.out.println("\nExisting patient found: " + p.getName());
                AuditManager.getInstance().logAction(user.getId(), "GET EXISTING PATIENT", patientID, "SUCCESS", UserController.getActiveUserType().toString());
                return p;
            }
        }

// If patient does not exist, create a new one
        System.out.println("No existing patient found. Registering a new patient...");
        Patient newPatient = createEmergencyPatient(patientID);
        AuditManager.getInstance().logAction(user.getId(), "CREATE NEW PATIENT", patientID, "SUCCESS", UserController.getActiveUserType().toString());
        allPatients.add((newPatient));
        savePatientsToFile();
        AuditManager.getInstance().logAction(user.getId(), "SAVE PATIENT TO FILE", patientID, "SUCCESS", UserController.getActiveUserType().toString());
        return newPatient;
    }

    public static Nurse checkParamedicNurse(String nurseID) {
        Scanner scanner = new Scanner(System.in);
        Nurse foundNurse = null;
        String role = "paramedic nurse";
        List<Nurse> allNurse = getAvailableNurses();
        while (foundNurse == null) {
            for (Nurse n : allNurse) {
                if (n.getId().equalsIgnoreCase(nurseID) && n.getRole().equalsIgnoreCase(role)) {
                    System.out.println("\nExisting nurse found: " + n.getName());
                    foundNurse = n;
                    return n;
                }
            }
            System.out.println("No existing paramedic nurse found. Please enter a valid paramedic nurse ID: ");
            nurseID = scanner.nextLine();
        }
        return foundNurse;
    }

    private static Patient createPatient (String patientID) {

        // Collect user input for each field
        String userName = InputValidator.getValidStringInput("Enter username: ");
        String name = InputValidator.getValidStringInput("Enter name: ");
        String password = InputValidator.getValidStringInput("Enter password: ");
        String gender = InputValidator.getValidStringInput("Enter gender: ");
        String phoneNumber = InputValidator.getValidStringInput("Enter phone number: ");
        String dateOfBirth = InputValidator.getValidStringInput("Enter date of birth: ");
        String patientSpecificFactor = InputValidator.getValidStringInput("Enter patient-specific factor: ");
        String bloodType = InputValidator.getValidStringInput("Enter blood type: ");
        String assignedNurse = InputValidator.getValidStringInput("Enter assigned Nurse Id: ");
        String assignedDoctor = InputValidator.getValidStringInput("Enter assigned Doctor Id: ");

        // Validate numeric inputs for height, weight, and emergency contact
        double height = InputValidator.getValidDoubleInput("Enter height: ");
        double weight = InputValidator.getValidDoubleInput("Enter weight: ");

        // Additional string inputs
        String houseAddress = InputValidator.getValidStringInput("Enter house address: ");
        String occupation = InputValidator.getValidStringInput("Enter occupation: ");
        String ethnicity = InputValidator.getValidStringInput("Enter ethnicity: ");
        String healthcareDepartment = InputValidator.getValidStringInput("Enter healthcare department: ");

        // Validate emergency contact number (assuming it's an integer)
        int emergencyContactNumber = InputValidator.getValidIntInput("Enter emergency contact number: ");

        // Create an empty list for alert history
        List<Alert> alertHistory = new ArrayList<>();

        // Create a new Patient object with the collected data
        Patient newPatient = new Patient(patientID, userName, name, password, "", gender, phoneNumber, dateOfBirth, patientSpecificFactor,
                assignedNurse, assignedDoctor, alertHistory, height, weight, bloodType, houseAddress, emergencyContactNumber,
                occupation, ethnicity, healthcareDepartment);

        // Output confirmation or the new patient info (you can display the patient info here as needed)
        System.out.println("New patient created: " + newPatient);

        return newPatient;
    }

    private static Patient createEmergencyPatient (String patientID) {

        // Collect user input for each field
        String name = InputValidator.getValidStringInput("Enter name: ");
        String gender = InputValidator.getValidStringInput("Enter gender: ");
        String phoneNumber = InputValidator.getValidStringInput("Enter phone number: ");
        ElectronicHealthRecord electronicHealthRecord = new ElectronicHealthRecord();
        List<Alert> alertHistory = new ArrayList<>();
        PatientConsent patientConsent = new PatientConsent();

        // Create a new Patient object with the collected data
        Patient newPatient = new Patient( patientID, name, gender,  phoneNumber, electronicHealthRecord,patientConsent,alertHistory);
        // Output confirmation or the new patient info (you can display the patient info here as needed)
        System.out.println("New patient " + newPatient.getName() + " created!");

        return newPatient;
    }
}

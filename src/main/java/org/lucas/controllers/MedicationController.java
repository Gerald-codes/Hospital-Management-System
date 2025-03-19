package org.lucas.controllers;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.models.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Simple controller class that manages the storage of pre-populated medicine variables.
 * Common medicines such as acetominophen may already have fixed dosages and come in packages.
 * Hence, help to fill in those inside a JSON file, then load them at the beginning of the application.
 */
public class MedicationController {
    private static final String fileName = "medication.txt";
    private static List<Medication> availableMedication = new ArrayList<>(); // In-memory list


    /**
     * returns a *copy* of the available medicine array.
     * This ensures that the original array is never modified.
     *
     * @return
     */
    public static List<Medication> getAvailableMedications() {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile(); //for when add new medicines
        }
        return new ArrayList<>(availableMedication); // Return a *copy*
    }
    /**
     * Prints a list of all available medicines to the console, All Attributes.
     * This method is for Pharmacist to see .
     */


    /**
     * Prints a list of all available medicines to the console, including their names and prices.
     * This method is primarily for diagnostic and user interface purposes.
     */

    public static void printAllMedications() {
        System.out.println("All Medications:");
        for (Medication medications : availableMedication) {
            System.out.println("\n=======================================");
            System.out.println("          MEDICATION DETAILS           ");
            System.out.println("=======================================");
            System.out.printf("%-25s: %s%n", "Medication Name", medications.getMedicationName());
            System.out.printf("%-25s: %s%n", "Medication ID", medications.getMedicationId());
            System.out.printf("%-25s: %s%n", "Clinical Guideline ID", medications.getGuidelineId());
            System.out.printf("%-25s: %s%n", "Dosage", medications.getDosage());
            System.out.printf("%-25s: %s%n", "Side Effects", medications.getSideEffects());
            System.out.printf("%-25s: %s%n", "Brand Name", medications.getBrandName());
            System.out.printf("%-25s: %d mg%n", "Dosage Strength", medications.getDosageStrength());
            System.out.printf("%-25s: %s%n", "Frequency", medications.getFrequency());
            System.out.printf("%-25s: %s%n", "Max Daily Dosage", medications.getMaximumDailyDosage());
            System.out.printf("%-25s: %d units%n", "Stock Available", medications.getStockAvailable());
            System.out.printf("%-25s: %s%n", "Controlled Substance", medications.isControlledSubstance() ? "Yes" : "No");
            System.out.printf("%-25s: %s%n", "Manufacturer", medications.getManufactureName());
            System.out.printf("%-25s: %s%n", "Batch Number", medications.getBatchNumber());
            System.out.printf("%-25s: %s%n", "Manufacture Date", medications.getManufactureDate());
            System.out.printf("%-25s: %s%n", "Expiry Date", medications.getExpiryDate());
            System.out.println("=======================================\n");
        }
    }

    /**
     * Finds a medicine by its name.  This method searches the list of available
     * medicines and returns the first medicine that matches the given name (case-insensitive).
     *
     * @param name The name of the medicine to search for.
     * @return The {@code Medicine} object if found, or {@code null} if no medicine
     * with the given name is found.
     */
    public static Medication findAvailableMedicationByName(String name) {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile();
        }

        for (Medication medication : availableMedication) {
            if (medication != null && medication.getMedicationName() != null && medication.getMedicationName().equalsIgnoreCase(name)) {
                return medication;
            }
        }
        return null; // Return null if not found.
    }

    /**
     * Loads the list of available medicines from the (medicines.json) file.
     * This method uses Gson to deserialize the JSON data into a list of (Medicine) objects.
     * If an IOException occurs during file reading, the stack trace is printed to the error stream.
     */
    private static void loadMedicationFromFile() {
        availableMedication.clear();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Medication>>() {
            }.getType();
            availableMedication = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current list of available medicines to the medicines.json file.
     * This method uses Gson to serialize the list of Medicine objects into JSON format.
     * If an IOException occurs during file writing, the stack trace is printed to the error stream.
     */
    public static void saveMedicationToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String json = Globals.gsonPrettyPrint.toJson(availableMedication);
            writer.write(json);
            System.out.println("Medicines saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the availableMedicines list with some sample OTC medicines.
     */

    public static void populateExampleMedication() {
        // Only add examples if the file was empty or didn't exist
        if (availableMedication.isEmpty()) {
            Medication Paracetamol = new Medication("Paracetamol", "C001", "M001", "325mg, Once a Week",
                    "Drowsiness, Nausea", "Tylenol", 325, "Once a Day",
                    "6969mg", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026",23.1);
            Medication Aspirin = new Medication("Aspirin", "C002", "M002", "325mg, Once a Week",
                    "Bleeding, Stomach Pain", "Bayer", 325, "Once a Day",
                    "6969mg", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026",13.1);
            Medication Furosemide = new Medication("Furosemide", "C003", "M003", "325mg, Once a Week",
                    "Dehydration, Low Blood Pressure", "Lasix", 325, "Once a Day",
                    "6969mg", 80, true, "Sanofi", "B002",
                    "12-12-2025", "04-04-2026",10.1);
            Medication Diphenhydramine = new Medication("Diphenhydramine", "C004", "M004", "200mg, Once a Week",
                    "Bleeding Cock", "Bayer", 200, "Thrice a Day",
                    "6969mg", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026",100.1);
            Medication Loperamide = new Medication("Loperamide", "C005", "M005", "325mg, Once a Week",
                    "Bleeding Cock", "Bayer", 325, "Twice a Day",
                    "6969mg", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026",53.1);

        availableMedication.add(Paracetamol);
        availableMedication.add(Aspirin);
        availableMedication.add(Furosemide);
        availableMedication.add(Diphenhydramine);
        availableMedication.add(Loperamide);
    }

    saveMedicationToFile();
}

    private static List<Medication> medication = new ArrayList<>();
    private static final String medicationFileName = "medication.txt";

    private void loadMedicationsFromFile(){
        medication.clear();
        //first load the patients
        StringBuilder sb = new StringBuilder();

        // get the jar location
        String basePath = "";
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, medicationFileName))) {
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            Type listType = new TypeToken<List<Medication>>() {}.getType();
            medication.addAll(Util.fromJsonString(sb.toString(), listType));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public List<Medication> getMedication(){
        if(medication.isEmpty()){
            loadMedicationsFromFile();
        }
        // get the users which are instanceof patient, then map and cast them all to Patients
        return medication.stream().filter(med -> med instanceof Medication).map(med -> (Medication) med).toList();
    }
    public static List<Medication> generateRandomMedications(List<Medication> availableMedications){
        Set<Medication> selectedMedications = new HashSet<>();
        Random random = new Random();
        int numToSelect = random.nextInt(3) + 1; // Randomly pick 1 to 3

        while (selectedMedications.size() < numToSelect) {
            Medication randomMed = availableMedications.get(random.nextInt(availableMedications.size()));
            selectedMedications.add(randomMed);  // Ensures no duplicates
        }

        return new ArrayList<>(selectedMedications);
    }
    //TODO: find medication by ID
    public static Medication findAvailableMedicationByID(String id) {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile();
        }

        for (Medication medication : availableMedication) {
            if (medication != null && medication.getMedicationId() != null && medication.getMedicationId().equalsIgnoreCase(id)) {
                return medication;
            }
        }
        return null; // Return null if not found.
    }

    //add to availableMedication list
    public static void addNewMedication(String medicationName, String guidelineId, String medicationId, String dosage, String sideEffects, String brandName, int dosageStrength, String frequency, String maxDailyDosage, int stockAvailable, boolean controlledSubstance, String manufactureName, String batchNumber, String manufactureDate, String expiryDate, double price) {
        Medication newMedication = new Medication(medicationName, guidelineId, medicationId, dosage, sideEffects, brandName, dosageStrength, frequency, maxDailyDosage, stockAvailable, controlledSubstance, manufactureName, batchNumber, manufactureDate, expiryDate, price);
        availableMedication.add(newMedication);
        saveMedicationToFile();
    }

    //TODO: Method to show the latest Guideline ID
    public static String getLatestGuidelineId() {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile();
        }
        String latestGuidelineId = "";
        for (Medication medication : availableMedication) {
            if (medication.getGuidelineId().compareTo(latestGuidelineId) > 0) {
                latestGuidelineId = medication.getGuidelineId();
            }
        }
        return latestGuidelineId;
    }

    //TODO: Method to show latest Medication ID
    public static String getLatestMedicationId() {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile();
        }

        //Initialize variable
        String latestMedicationId = "";
        for (Medication medication : availableMedication) {
            if (medication.getMedicationId().compareTo(latestMedicationId) > 0) {
                latestMedicationId = medication.getMedicationId();
            }
        }
        return latestMedicationId;
    }
    //TODO: Method to show the latest Batch Number
    public static String getLatestBatchNumber() {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile();
        }

        //Initialize variable
        String latestBatchNumber = "";
        for (Medication medication : availableMedication) {
            if (medication.getMedicationId().compareTo(latestBatchNumber) > 0) {
                latestBatchNumber = medication.getBatchNumber();
            }
        }
        return latestBatchNumber;
    }

    // Method to collect user input and add a new medication to the list of available medications
    public static void collectUserInputAndAddMedication() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Medicine Name :");
        String medicationName = scan.nextLine();

        System.out.println("Key in a Guideline ID :     Latest ID: " + getLatestGuidelineId());
        String guidelineId = scan.nextLine();

        System.out.println("Key in a Medication ID :     Latest ID: " + getLatestMedicationId());
        String medicationID = scan.nextLine();

        System.out.println("Enter dosage :");
        String dosage = scan.nextLine();

        System.out.println("Describe Side Effects :");
        String sideEffects = scan.nextLine();

        System.out.println("Enter the Brand Name :");
        String brandName = scan.nextLine();

        System.out.println("Enter the dosage strength :");
        int dosageStrength = Integer.parseInt(scan.nextLine());

        System.out.println("How often can patient take the medicine :");
        String frequency = scan.nextLine();

        System.out.println("Enter maximum dosage :");
        String maximumDailyDosage = scan.nextLine();

        System.out.println("Enter stock amount to be added :");
        int stockAvailable = Integer.parseInt(scan.nextLine());

        System.out.println("Is this a controlled substance? (Yes/No) :");
        boolean controlledSubstance = scan.nextLine().equalsIgnoreCase("Yes");

        System.out.println("Enter the Manufacturer Name :");
        String manufactureName = scan.nextLine();

        System.out.println("Enter the Batch Number :    Latest Batch Number: " + getLatestBatchNumber());
        String batchNumber = scan.nextLine();

        System.out.println("Enter the Manufacture Date :");
        String manufactureDate = scan.nextLine();

        System.out.println("Enter the Expiry Date :");
        String expiryDate = scan.nextLine();

        System.out.println("Enter the standard pricing of this medicine :");
        double medicationPrice = Double.parseDouble(scan.nextLine());

        addNewMedication(medicationName, guidelineId, medicationID, dosage, sideEffects, brandName, dosageStrength, frequency, maximumDailyDosage, stockAvailable, controlledSubstance, manufactureName, batchNumber, manufactureDate, expiryDate, medicationPrice);
    }


    //method to remove stock from total stock
    public static void removeStockfromMedication(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the Medication ID of the stock you wish to deduct from :   Example: M001");
        String medicationID = scan.nextLine();

        Medication medication = findAvailableMedicationByID(medicationID);
        if (medication == null) {
            System.out.println("Medication not found");
            return;
        }

        System.out.println("Enter the amount you wish to deduct :   Current Stock: " + medication.getStockAvailable());
        int stocktoRemove = Integer.parseInt(scan.nextLine());
        int currentStock = medication.getStockAvailable();
        int newStock = currentStock - stocktoRemove;

        if (stocktoRemove <= currentStock) {
            medication.setStockAvailable(newStock);
            saveMedicationToFile();
            System.out.println("Total amount has been successfully deducted. Current balance is " + newStock);
        }
        else {
            System.out.println("Invalid amount given. Please enter a valid amount that can be deducted from the total" + currentStock);
        }
    }


    public static void editMedicineDetails(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Medicine ID of the medicine you wish to find :    Example: M001");
        String medicationID = scan.nextLine();

        Medication medication = findAvailableMedicationByID(medicationID);
        if (medication == null) {
            System.out.println("Medication not found");
            return;
        }

        printMedicationDetails(medication);


        System.out.println("1. Medicine Name");
        System.out.println("2. Guideline ID");
        System.out.println("3. Dosage");
        System.out.println("4. Side Effects");
        System.out.println("5. Brand Name");
        System.out.println("6. Dosage Strength");
        System.out.println("7. Frequency");
        System.out.println("8. Maximum Daily Dosage");
        System.out.println("9. Stock Available");
        System.out.println("10. Controlled Substance");
        System.out.println("11. Manufacturer Name");
        System.out.println("12. Batch Number");
        System.out.println("13. Manufacture Date");
        System.out.println("14. Expiry Date");
        System.out.println("15. Medication Price");
        System.out.println("Enter the index number of the detail you wish to edit:     Example: 1 ");

        int choice = Integer.parseInt(scan.nextLine());

        switch (choice) {
            case 1:
                System.out.println("Enter new Medicine Name:");
                medication.setMedicationName(scan.nextLine());
                break;
            case 2:
                System.out.println("Enter new Guideline ID:");
                medication.setGuidelineId(scan.nextLine());
                break;
            case 3:
                System.out.println("Enter new Dosage:");
                medication.setDosage(scan.nextLine());
                break;
            case 4:
                System.out.println("Enter new Side Effects:");
                medication.setSideEffects(scan.nextLine());
                break;
            case 5:
                System.out.println("Enter new Brand Name:");
                medication.setBrandName(scan.nextLine());
                break;
            case 6:
                System.out.println("Enter new Dosage Strength:");
                medication.setDosageStrength(Integer.parseInt(scan.nextLine()));
                break;
            case 7:
                System.out.println("Enter new Frequency:");
                medication.setFrequency(scan.nextLine());
                break;
            case 8:
                System.out.println("Enter new Maximum Daily Dosage:");
                medication.setMaximumDailyDosage(scan.nextLine());
                break;
            case 9:
                System.out.println("Enter new Stock Available:");
                medication.setStockAvailable(Integer.parseInt(scan.nextLine()));
                break;
            case 10:
                System.out.println("Is this a controlled substance? (Yes/No):");
                medication.setControlledSubstance(scan.nextLine().equalsIgnoreCase("Yes"));
                break;
            case 11:
                System.out.println("Enter new Manufacturer Name:");
                medication.setManufactureName(scan.nextLine());
                break;
            case 12:
                System.out.println("Enter new Batch Number:");
                medication.setBatchNumber(scan.nextLine());
                break;
            case 13:
                System.out.println("Enter new Manufacture Date:");
                medication.setManufactureDate(scan.nextLine());
                break;
            case 14:
                System.out.println("Enter new Expiry Date:");
                medication.setExpiryDate(scan.nextLine());
                break;
            case 15:
                System.out.println("Enter new Medication Price:");
                medication.setMedicationPrice(Double.parseDouble(scan.nextLine()));
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        saveMedicationToFile();
        System.out.println("Medication details updated successfully.");
    }


    private static void printMedicationDetails(Medication medication) {
        System.out.println("\n=======================================");
        System.out.println("          MEDICATION DETAILS           ");
        System.out.println("=======================================");
        System.out.printf("%-25s: %s%n", "Medication Name", medication.getMedicationName());
        System.out.printf("%-25s: %s%n", "Medication ID", medication.getMedicationId());
        System.out.printf("%-25s: %s%n", "Clinical Guideline ID", medication.getGuidelineId());
        System.out.printf("%-25s: %s%n", "Dosage", medication.getDosage());
        System.out.printf("%-25s: %s%n", "Side Effects", medication.getSideEffects());
        System.out.printf("%-25s: %s%n", "Brand Name", medication.getBrandName());
        System.out.printf("%-25s: %d mg%n", "Dosage Strength", medication.getDosageStrength());
        System.out.printf("%-25s: %s%n", "Frequency", medication.getFrequency());
        System.out.printf("%-25s: %s%n", "Max Daily Dosage", medication.getMaximumDailyDosage());
        System.out.printf("%-25s: %d units%n", "Stock Available", medication.getStockAvailable());
        System.out.printf("%-25s: %s%n", "Controlled Substance", medication.isControlledSubstance() ? "Yes" : "No");
        System.out.printf("%-25s: %s%n", "Manufacturer", medication.getManufactureName());
        System.out.printf("%-25s: %s%n", "Batch Number", medication.getBatchNumber());
        System.out.printf("%-25s: %s%n", "Manufacture Date", medication.getManufactureDate());
        System.out.printf("%-25s: %s%n", "Expiry Date", medication.getExpiryDate());
        System.out.println("=======================================\n");
    }
}



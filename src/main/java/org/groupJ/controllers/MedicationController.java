package org.groupJ.controllers;
import java.util.*;

import com.google.gson.reflect.TypeToken;
import org.groupJ.Globals;
import org.groupJ.audit.AuditManager;
import org.groupJ.models.*;
import org.groupJ.util.InputValidator;
import org.groupJ.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple controller class that manages the storage of pre-populated medicine variables.
 * Common medicines such as acetaminophen may already have fixed dosages and come in packages.
 * Hence, help to fill in those inside a JSON file, then load them at the beginning of the application.
 */
public class MedicationController {
    private static final String fileName = "medication.txt";
    private static List<Medication> availableMedication = new ArrayList<>(); // In-memory list
    public static String medicationID;
    public static String medicationID1;

    /**
     * returns a *copy* of the available medicine array.
     * This ensures that the original array is never modified.
     *
     */
    public static List<Medication> getAvailableMedications() {
        if (availableMedication.isEmpty()) {
            loadMedicationFromFile(); //for when add new medicines
        }
        return new ArrayList<>(availableMedication); // Return a *copy*
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
     * Finds a medicine by its ID.  This method searches the list of available
     * medicines and returns the first medicine that matches the given ID.
     *
     * @param id The id of the medicine to search for.
     * @return The {@code Medicine} object if found, or {@code null} if no medicine
     * with the given name is found.
     */
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
    /**
     * Finds the latest medicine ID. This method searches the list of available
     * medicines and returns the latest medicine ID.
     */
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
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
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
            AuditManager.getInstance().logAction(UserController.getActiveDoctor().getId(), "EXCEPTION OCCURRED", e.getMessage(),
                    "FAILURE", UserController.getActiveUserType().toString());
        }
    }

    /**
     * Populates the availableMedicines list with some sample OTC medicines.
     */

    public static void populateExampleMedication() {
        // Only add examples if the file was empty or didn't exist
        if (availableMedication.isEmpty()) {

            Medication Paracetamol = new Medication("Paracetamol", "C001", "M001", "325mg, Once a Week",
                    "Drowsiness, Nausea", "Tylenol", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026", 23.1);
            Medication Aspirin = new Medication("Aspirin", "C002", "M002", "325mg, Once a Week",
                    "Bleeding, Stomach Pain", "Bayer", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026", 13.1);
            Medication Furosemide = new Medication("Furosemide", "C003", "M003", "325mg, Once a Week",
                    "Frequent Urination", "Lasix", 80, true, "Sanofi", "B002",
                    "12-12-2025", "04-04-2026", 10.1);
            Medication Diphenhydramine = new Medication("Diphenhydramine", "C004", "M004", "200mg, Once a Week",
                    "Drowsiness, Sleepiness", "HealthA2Z Sleep Aid", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026", 14.69);
            Medication Loperamide = new Medication("Loperamide", "C005", "M005", "325mg, Once a Week",
                    "Constipation, Dry Mouth", "Imodium", 80, false, "Bayer Pharmaceuticals", "B002",
                    "12-12-2025", "04-04-2026", 3.33);
            Medication Amoxicillin = new Medication("Amoxicillin", "C006", "M006", "500mg, Twice a Day",
                    "Nausea, Diarrhea", "Amoxil", 100, false, "GlaxoSmithKline", "B003",
                    "15-08-2024", "20-12-2025", 8.99);
            Medication Metformin = new Medication("Metformin", "C007", "M007", "850mg, Once Daily",
                    "Stomach Upset, Lactic Acidosis (Rare)", "Glucophage", 120, false, "Merck", "B004",
                    "10-09-2023", "11-11-2025", 5.75);
            Medication Ibuprofen = new Medication("Ibuprofen", "C008", "M008", "200mg, Every 6 Hours",
                    "Stomach Pain, Heartburn", "Advil", 150, false, "Pfizer", "B005",
                    "01-03-2024", "02-07-2026", 6.49);
            Medication Atorvastatin = new Medication("Atorvastatin", "C009", "M009", "10mg, Once Daily",
                    "Muscle Pain, Liver Enzyme Changes", "Lipitor", 90, false, "Pfizer", "B006",
                    "28-02-2024", "15-05-2026", 12.99);
            Medication Levothyroxine = new Medication("Levothyroxine", "C010", "M010", "100mcg, Once Daily",
                    "Hair Loss, Nervousness", "Synthroid", 60, false, "AbbVie", "B007",
                    "05-07-2024", "30-09-2026", 9.50);

            // Adding all medications to the list
            availableMedication.add(Paracetamol);
            availableMedication.add(Aspirin);
            availableMedication.add(Furosemide);
            availableMedication.add(Diphenhydramine);
            availableMedication.add(Loperamide);
            availableMedication.add(Amoxicillin);
            availableMedication.add(Metformin);
            availableMedication.add(Ibuprofen);
            availableMedication.add(Atorvastatin);
            availableMedication.add(Levothyroxine);
        }
        saveMedicationToFile();
    }

    /**
     * Adds a new medication to the list of available medications and saves the updated list to file.
     *
     * @param medicationName      The name of the medication.
     * @param guidelineId         The ID of the clinical guideline associated with the medication.
     * @param medicationId        The unique ID of the medication.
     * @param dosage              The dosage instructions for the medication.
     * @param sideEffects         The potential side effects of the medication.
     * @param brandName           The brand name of the medication.
     * @param stockAvailable      The current stock level of the medication.
     * @param controlledSubstance Indicates whether the medication is a controlled substance.
     * @param manufactureName     The name of the medication's manufacturer.
     * @param batchNumber         The batch number of the medication.
     * @param manufactureDate     The manufacturing date of the medication.
     * @param expiryDate          The expiry date of the medication.
     * @param price               The price of the medication.
     */
    //add to availableMedication list
    public static void addNewMedication(String medicationName, String guidelineId, String medicationId, String dosage, String sideEffects, String brandName, int stockAvailable, boolean controlledSubstance, String manufactureName, String batchNumber, String manufactureDate, String expiryDate, double price) {
        Medication newMedication = new Medication(medicationName, guidelineId, medicationId, dosage, sideEffects, brandName, stockAvailable, controlledSubstance, manufactureName, batchNumber, manufactureDate, expiryDate, price);
        availableMedication.add(newMedication);
        saveMedicationToFile();
    }

    /**
     * Retrieves the latest guideline ID from the list of available medications.
     *
     * @return The latest guideline ID.
     */
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

    /**
     * Retrieves the latest batch number from the list of available medications.
     *
     * @return The latest batch number.
     */
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

    /**
     * Collects user input for adding a new medication and adds it to the list of available medications.
     */
    // Method to collect user input and add a new medication to the list of available medications
    public static void collectUserInputAndAddMedication() {

        String medicationName = InputValidator.getValidStringInput("Enter Medicine Name : ");

        String guidelineId = InputValidator.getValidStringInput("Key in a Guideline ID :     Latest ID: " + getLatestGuidelineId());

        String medicationID = InputValidator.getValidStringInput("Key in a Medication ID :     Latest ID: " + getLatestMedicationId());

        String dosage = InputValidator.getValidStringInput("Enter dosage : ");

        String sideEffects = InputValidator.getValidStringInput("Describe Side Effects :");

        String brandName = InputValidator.getValidStringInput("Enter the Brand Name : ");

        int stockAvailable = Integer.parseInt(InputValidator.getValidStringInput("Enter stock amount to be added : "));

        boolean controlledSubstance = InputValidator.getValidStringInput("Is this a controlled substance? (Yes/No) : ").equalsIgnoreCase("Yes");

        String manufactureName = InputValidator.getValidStringInput("Enter the Manufacturer Name : ");

        String batchNumber = InputValidator.getValidStringInput("Enter the Batch Number :    Latest Batch Number: " + getLatestBatchNumber());

        String manufactureDate = InputValidator.getValidStringInput("Enter the Manufacture Date : ");

        String expiryDate = InputValidator.getValidStringInput("Enter the Expiry Date : ");

        double medicationPrice = Double.parseDouble(InputValidator.getValidStringInput("Enter the standard pricing of this medicine : "));

        addNewMedication(medicationName, guidelineId, medicationID, dosage, sideEffects, brandName, stockAvailable, controlledSubstance, manufactureName, batchNumber, manufactureDate, expiryDate, medicationPrice);
    }

    /**
     * Removes a specified amount of stock from a medication.
     */
    //method to remove stock from total stock
    public static void removeStockfromMedication(){

        String medicationID = InputValidator.getValidStringInput("Enter the Medication ID of the stock you wish to deduct from :   Example: M001");

        Medication medication = findAvailableMedicationByID(medicationID);
        if (medication == null) {
            System.out.println("Medication not found");
            return;
        }

        int stockRemove = Integer.parseInt(InputValidator.getValidStringInput("Enter the amount you wish to deduct :   Current Stock: " + medication.getStockAvailable()));
        int currentStock = medication.getStockAvailable();
        int newStock = currentStock - stockRemove;

        if (stockRemove <= currentStock) {
            medication.setStockAvailable(newStock);
            saveMedicationToFile();
            System.out.println("Total amount has been successfully deducted. Current balance is " + newStock);
        }
        else {
            System.out.println("Invalid amount given. Please enter a valid amount that can be deducted from the total" + currentStock);
        }
    }
    /**
     * Edits the details of a specific medication identified by its ID.
     * Allows modification of various attributes such as name, dosage, side effects, etc.
     */
    public static void editMedicineDetails(){

        String medicationID1 = InputValidator.getValidStringInput("Enter Medicine ID of the medicine you wish to find :    Example: M001\n");

        Medication medication = findAvailableMedicationByID(medicationID1);
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
        System.out.println("6. Stock Available");
        System.out.println("7. Controlled Substance");
        System.out.println("8. Manufacturer Name");
        System.out.println("9. Batch Number");
        System.out.println("10. Manufacture Date");
        System.out.println("11. Expiry Date");
        System.out.println("12. Medication Price");

        int choice = Integer.parseInt(InputValidator.getValidStringInput("Enter the index number of the detail you wish to edit:     Example: 1 "));

        switch (choice) {
            case 1:
                medication.setMedicationName(InputValidator.getValidStringInput("Enter new Medicine Name: "));
                break;
            case 2:
                medication.setGuidelineId(InputValidator.getValidStringInput("Enter new Guideline ID: "));
                break;
            case 3:
                medication.setDosage(InputValidator.getValidStringInput("Enter new Dosage: "));
                break;
            case 4:
                medication.setSideEffects(InputValidator.getValidStringInput("Enter new Side Effects: "));
                break;
            case 5:
                medication.setBrandName(InputValidator.getValidStringInput("Enter new Brand Name: "));
                break;
            case 6:
                medication.setStockAvailable(Integer.parseInt(InputValidator.getValidStringInput("Enter new Stock Available: ")));
                break;
            case 7:
                medication.setControlledSubstance(InputValidator.getValidStringInput("Is this a controlled substance? (Yes/No): ").equalsIgnoreCase("Yes"));
                break;
            case 8:
                medication.setManufactureName(InputValidator.getValidStringInput("Enter new Manufacturer Name: "));
                break;
            case 9:
                medication.setBatchNumber(InputValidator.getValidStringInput("Enter new Batch Number: "));
                break;
            case 10:
                medication.setManufactureDate(InputValidator.getValidStringInput("Enter new Manufacture Date: "));
                break;
            case 11:
                medication.setExpiryDate(InputValidator.getValidStringInput("Enter new Expiry Date: "));
                break;
            case 12:
                medication.setMedicationPrice(Double.parseDouble(InputValidator.getValidStringInput("Enter new Medication Price: ")));
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        saveMedicationToFile();
        System.out.println("Medication details updated successfully.");
    }


    /**
     * Prints the details of a given medication in a formatted manner.
     *
     * @param medication The medication whose details are to be printed.
     */
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
        System.out.printf("%-25s: %d units%n", "Stock Available", medication.getStockAvailable());
        System.out.printf("%-25s: %s%n", "Controlled Substance", medication.isControlledSubstance() ? "Yes" : "No");
        System.out.printf("%-25s: %s%n", "Manufacturer", medication.getManufactureName());
        System.out.printf("%-25s: %s%n", "Batch Number", medication.getBatchNumber());
        System.out.printf("%-25s: %s%n", "Manufacture Date", medication.getManufactureDate());
        System.out.printf("%-25s: %s%n", "Expiry Date", medication.getExpiryDate());
        System.out.println("=======================================\n");
    }

    /**
     * Retrieves the medication ID.
     *
     * @return The medication ID.
     */
    public static String getMedicationID() {
        return medicationID;
    }
    /**
     * Retrieves the second medication ID.
     *
     * @return The second medication ID.
     */
    public static String getMedicationID1() {
        return medicationID1;
    }

}



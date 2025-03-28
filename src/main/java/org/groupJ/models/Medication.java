package org.groupJ.models;


/**
 * The {@code Medication} class represents a medication entity within a Clinical Decision Support System (CDSS).
 * <p>
 * It encapsulates detailed information about a medication, including its name, dosage, side effects, brand name,
 * dosage strength, administration frequency, maximum daily dosage, stock availability, and whether it is a controlled substance.
 * Additionally, it includes manufacturing details such as the manufacturer's name, batch number, manufacture date, and expiry date.
 **/
public class Medication {
    /**
     * The Medication class represents a medication entity within a Clinical Decision Support System (CDSS).
     * It encapsulates detailed information about a medication, including its name, dosage, side effects, brand name,
     * dosage strength, administration frequency, maximum daily dosage, stock availability, and whether it is a controlled substance.
     * Additionally, it includes manufacturing details such as the manufacturer's name, batch number, manufacture date, and expiry date.
     * This class provides methods to display medication information in various formats, including a patient-friendly summary
     * and a detailed view for healthcare providers. It also includes getters and setters for all attributes to ensure proper
     * encapsulation and data manipulation.
     * Instances of this class are used to manage and display medication-related data, ensuring accurate and timely information
     * is available for clinical decision-making.
     */
    // Private fields for storing medication details
    private String medicationName;         // Name of the medication
    private String guidelineId;            // ID of the clinical guideline associated with the medication
    private String medicationId;           // Unique ID for the medication
    private String dosage;                 // Dosage amount in mg
    private String sideEffects;            // Potential side effects of the medication
    private String brandName;              // Brand name of the medication
    private int stockAvailable;            // Available stock in units
    private boolean controlledSubstance;   // Indicates if the medication is a controlled substance
    private String manufactureName;        // Name of the manufacturer
    private String batchNumber;            // Batch number of the medication
    private String manufactureDate;     // Date of manufacture
    private String expiryDate;          // Expiry date of the medication
    private double medicationPrice;

    /**
     * Parameterized constructor for the medications class.
     * Initializes the medication object with the provided values.
     *
     * @param medicationName       The name of the medication.
     * @param guidelineId          The ID of the clinical guideline associated with the medication.
     * @param medicationId         The unique ID for the medication.
     * @param dosage               The dosage amount in mg.
     * @param sideEffects          The potential side effects of the medication.
     * @param brandName            The brand name of the medication.
     * @param stockAvailable       The available stock in units.
     * @param controlledSubstance  It Indicates if the medication is a controlled substance.
     * @param manufactureName      The name of the manufacturer.
     * @param batchNumber          The batch number of the medication.
     * @param manufactureDate      The date of manufacture.
     * @param expiryDate           The expiry date of the medication.
     * @param medicationPrice      The cost of the medication.
     */
    // Parameterized constructor to initialize Medication object with provided values
    public Medication(String medicationName, String guidelineId, String medicationId, String dosage,
                      String sideEffects, String brandName,int stockAvailable, boolean controlledSubstance,
                      String manufactureName, String batchNumber, String manufactureDate, String expiryDate, double medicationPrice) {
        this.medicationName = medicationName;
        this.guidelineId = guidelineId;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.sideEffects = sideEffects;
        this.brandName = brandName;
        this.stockAvailable = stockAvailable;
        this.controlledSubstance = controlledSubstance;
        this.manufactureName = manufactureName;
        this.batchNumber = batchNumber;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.medicationPrice = medicationPrice;
    }
    public Medication(String medicationName, int stockAvailable, String dosage, double medicationPrice){
        this.medicationName = medicationName;
        this.stockAvailable = stockAvailable;
        this.dosage = dosage;
        this.medicationPrice = medicationPrice;
    }

//    public Medication(String medicationId, String medicationName, String combined) {
//    }
    // Getters and Setters for all attributes
    /**
     * Retrieves the name of the medication.
     *
     * @return The name of the medication.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets the name of the medication.
     *
     * @param medicationName The name of the medication to set.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Retrieves the ID of the clinical guideline associated with the medication.
     *
     * @return The guideline ID.
     */
    public String getGuidelineId() {
        return guidelineId;
    }

    /**
     * Sets the ID of the clinical guideline associated with the medication.
     *
     * @param guidelineId The guideline ID to set.
     */
    public void setGuidelineId(String guidelineId) {
        this.guidelineId = guidelineId;
    }

    /**
     * Retrieves the unique ID of the medication.
     *
     * @return The medication ID.
     */
    public String getMedicationId() {
        return medicationId;
    }

    /**
     * Sets the unique ID of the medication.
     *
     * @param medicationId The medication ID to set.
     */
    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    /**
     * Retrieves the dosage amount of the medication in milligrams (mg).
     *
     * @return The dosage amount in mg.
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Sets the dosage amount of the medication in milligrams (mg).
     *
     * @param dosage The dosage amount to set.
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Retrieves the potential side effects of the medication.
     *
     * @return The side effects of the medication.
     */
    public String getSideEffects() {
        return sideEffects;
    }

    /**
     * Sets the potential side effects of the medication.
     *
     * @param sideEffects The side effects to set.
     */
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    /**
     * Retrieves the brand name of the medication.
     *
     * @return The brand name of the medication.
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * Sets the brand name of the medication.
     *
     * @param brandName The brand name to set.
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * Retrieves the available stock of the medication in units.
     *
     * @return The available stock in units.
     */
    public int getStockAvailable() {
        return stockAvailable;
    }

    /**
     * Sets the available stock of the medication in units.
     *
     * @param stockAvailable The stock available to set.
     */
    public void setStockAvailable(int stockAvailable) {
        this.stockAvailable = stockAvailable;
    }

    /**
     * Checks if the medication is a controlled substance.
     *
     * @return {@code true} if the medication is a controlled substance, {@code false} otherwise.
     */
    public boolean isControlledSubstance() {
        return controlledSubstance;
    }

    /**
     * Sets whether the medication is a controlled substance.
     *
     * @param controlledSubstance {@code true} if the medication is a controlled substance, {@code false} otherwise.
     */
    public void setControlledSubstance(boolean controlledSubstance) {
        this.controlledSubstance = controlledSubstance;
    }

    /**
     * Retrieves the name of the manufacturer of the medication.
     *
     * @return The manufacturer's name.
     */
    public String getManufactureName() {
        return manufactureName;
    }

    /**
     * Sets the name of the manufacturer of the medication.
     *
     * @param manufactureName The manufacturer's name to set.
     */
    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    /**
     * Retrieves the batch number of the medication.
     *
     * @return The batch number.
     */
    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * Sets the batch number of the medication.
     *
     * @param batchNumber The batch number to set.
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * Retrieves the manufacture date of the medication.
     *
     * @return The manufacture date.
     */
    public String getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Sets the manufacture date of the medication.
     *
     * @param manufactureDate The manufacture date to set.
     */
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * Retrieves the expiry date of the medication.
     *
     * @return The expiry date.
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the medication.
     *
     * @param expiryDate The expiry date to set.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * Displays all attributes of the medication using the {@code toString()} method.
     */
    // Method to display all medication attributes using toString()
    public void displayMedicationAttributes() {
        System.out.println(this.toString());
    }
    /**
     * Displays a patient-friendly summary of the medication, including only the medication name.
     */
    // Method to display medication name for the patient
    public void displayPatientMedication() {
        System.out.println("\n===== Medication: " + medicationName + " =====");
    }

    public double getMedicationPrice() {
        return medicationPrice;
    }

    public void setMedicationPrice(double medicationPrice) {
        this.medicationPrice = medicationPrice;
    }

    public void displayMedicationInfo() {
        System.out.println("===================================");
        System.out.println("         MEDICATION DETAILS        ");
        System.out.println("===================================");
        System.out.printf("%-20s: %s%n", "Medication Name", medicationName);
        System.out.printf("%-20s: %s%n", "Guideline ID", guidelineId);
        System.out.printf("%-20s: %s%n", "Medication ID", medicationId);
        System.out.printf("%-20s: %s%n", "Dosage", dosage);
        System.out.printf("%-20s: %s%n", "Side Effects", sideEffects);
        System.out.printf("%-20s: %s%n", "Brand Name", brandName);
        System.out.printf("%-20s: %d units%n", "Stock Available", stockAvailable);
        System.out.printf("%-20s: %s%n", "Controlled Substance", controlledSubstance ? "Yes" : "No");
        System.out.printf("%-20s: %s%n", "Manufacturer", manufactureName);
        System.out.printf("%-20s: %s%n", "Batch Number", batchNumber);
        System.out.printf("%-20s: %s%n", "Manufacture Date", manufactureDate);
        System.out.printf("%-20s: %s%n", "Expiry Date", expiryDate);
        System.out.printf("%-20s: $%.2f%n", "Medication Price", medicationPrice);
        System.out.println("===================================");
    }
}

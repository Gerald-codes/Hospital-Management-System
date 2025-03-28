package org.groupJ.models;

import org.groupJ.models.enums.DoctorType;
import org.groupJ.util.ObjectBase;

import java.util.UUID;

/**
 * Represents the billing information associated with a medical treatment or service.
 * This class handles details such as the total bill amount, a unique billing ID, payment status, and a prescription associated with the billing.
 * It is essential for managing financial transactions in healthcare settings, ensuring accurate billing and payment tracking.
 */
public class Billing implements ObjectBase {
    private double billAmount;
    private String billingID;
    private boolean isPaid;
    private boolean isReCalculateBillAmount = true;
    private Prescription prescription; //to get list of prescribed medicine

    /**
     * Constructs a new Billing instance with default settings.
     * Initializes a new Billing object with an initial bill amount set to 0.0, generates a unique billing ID,
     * sets the payment status to unpaid, and creates a new empty Prescription object. This setup is suitable for
     * starting a fresh billing process where charges and prescriptions will be added subsequently.
     */
    public Billing() {
        this.billAmount = 0.0;
        this.billingID = UUID.randomUUID().toString();
        this.isPaid = false;
        this.prescription = new Prescription();
    }

    /**
     * Retrieves the unique billing ID.
     *
     * @return The billing ID.
     */
    public String getBillingID() {
        return billingID;
    } //getter for billingID

    /**
     * Checks if the bill has been paid.
     *
     * @return True if paid, false otherwise.
     */
    public boolean isPaid() {
        return isPaid;
    } //check if paid

    /**
     * Sets the payment status of the bill.
     *
     * @param isPaid True if the bill is paid, false otherwise.
     */
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    } //set payment status
    /**
     * Initializes the billing amount based on the doctor's type.
     * This method sets the bill amount to a predetermined value depending on whether the doctor is categorized
     * as a general practitioner or a specialist. It's designed to be called when creating a new bill for a session
     * or appointment.
     *
     * @param doctor The doctor object whose type determines the billing amount.
     *  Must not be null and must have a valid type defined.
     *  {@link DoctorType#GENERAL_PRACTICE} will set the bill amount to 20.0
     *  {@link DoctorType#SPECIALIST} will set the bill amount to 50.0
     */

    public void initialBill(Doctor doctor){
        if(doctor.getDoctorType()== DoctorType.GENERAL_PRACTICE){
            this.billAmount = 20.0;
        }
        if(doctor.getDoctorType() == DoctorType.SPECIALIST){
            this.billAmount = 50.0;
        }
        isReCalculateBillAmount = true;
    }

    /**
     * Calculates the total bill amount by summing up the cost of prescribed medications.
     */
    private void calculateBillAmount() {
        for (Medication medication : prescription.getMedication()) {
            this.billAmount += medication.getMedicationPrice() * medication.getStockAvailable();
        }
    }

    /**
     * loops through every medicine in list of medicines from prescription class
     * to calculate total medicinal bill
     */
    public double getBillAmount() {
        if(isReCalculateBillAmount){
            calculateBillAmount();
            isReCalculateBillAmount = false;
        }
        return billAmount;
    }

    /**
     * Retrieves the prescription associated with this billing.
     *
     * @return The prescription object.
     */
    public Prescription getPrescription(){
        return this.prescription;
    }

    /**
     * Generates a String of an itemised bill for the user to look through.
     * @return a String itemised bill,
     */
    public String generateBillString() {
        // Use string builder when concatenating an unknown/large number of strings
        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Bill ID: ").append(billingID).append("\n");
        billDetails.append("----------------------------------------\n");
        billDetails.append("Prescribed Medicines:\n");

        // loop through the medication to itemise it.
        for (Medication medication : prescription.getMedication()) {
            billDetails.append("  - ").append(medication.getMedicationName())
                    .append(" (Qty: ").append(medication.getStockAvailable())
                    .append(", Price: $").append(String.format("%.2f", medication.getMedicationPrice()))
                    .append(", Dosage: ").append(medication.getDosage())
                    .append(") - Subtotal: $").append(String.format("%.2f", medication.getMedicationPrice() * medication.getStockAvailable()))
                    .append("\n");
        }
        billDetails.append("----------------------------------------\n");
        billDetails.append("Total Amount: $").append(String.format("%.2f", billAmount)).append("\n");
        billDetails.append("Payment Status: ").append(isPaid ? "Paid" : "Unpaid").append("\n");
        return billDetails.toString();
    }

    /**
     * Returns a string representation of the Billing object.
     *
     * @return A string containing billing details.
     */
    @Override
    public String toString() {
        return
                "\n  Billing ID       : " + billingID +
                "\n  Bill Amount      : $" + String.format("%.2f", billAmount) +
                "\n  Paid             : " + (isPaid ? "Yes" : "No") +
                "\n  Recalculation    : " + (isReCalculateBillAmount ? "Enabled" : "Disabled") +
                "\n  Prescription     : " + (prescription != null ? prescription.toString() : "None");
    }
}

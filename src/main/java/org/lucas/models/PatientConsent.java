package org.lucas.models;

import java.time.LocalDateTime;

/**
 * Represents the consent status of a patient within the healthcare system.
 * This class stores details about whether consent has been given, the date consent was given, and the specific terms of the consent.
 * It is crucial for compliance with legal and ethical standards in patient care and data handling.
 */
public class PatientConsent {
    private boolean consentGiven;
    private LocalDateTime consentDate;
    private String consentTerms;

    /**
     * Constructs a new PatientConsent instance with specific consent status and terms.
     * This constructor initializes the consent status and the detailed terms of the consent. It is essential for creating a consent record
     * when the exact date of consent is not required at the moment of initialization and will be set later or defaults to the creation date.
     *
     * @param consentGiven A boolean value indicating whether the patient has explicitly given consent. True if consent is given, false otherwise.
     * This is crucial for legal compliance and ensures that patient interactions are conducted with proper authorization.
     * @param consentTerms A string detailing the specific terms of the consent. This is for the patient to read and agree to give consent
     */
    public PatientConsent(boolean consentGiven, String consentTerms) {
        this.consentGiven = consentGiven;
        this.consentTerms = consentTerms;
    }

    public PatientConsent(){};

    // Getters and Setters
    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
    /**
     * Marks consent as given by updating the consent status to true and recording the date and time when the consent was given.
     * This method is typically used to record a user's or patient's agreement to specific terms and conditions at the point of consent.
     */
    public void acceptConsent() {
        setConsentGiven(true);
        this.consentDate = LocalDateTime.now();
    }

    public LocalDateTime getConsentDate() {
        if(consentGiven) {
            return consentDate;
        }
        else{
            return consentDate=null;
        }
    }

    public void setConsentDate(LocalDateTime consentDate) {
        this.consentDate = consentDate;
    }

    public String getConsentTerms() {
        return consentTerms;
    }

    public void setConsentTerms(String consentTerms) {
        this.consentTerms = consentTerms;
    }
}
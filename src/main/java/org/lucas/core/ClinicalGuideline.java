package org.lucas.core;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a clinical guideline with associated metadata and functionalities.
 * This class includes methods to display and manage clinical guidelines,
 * including saving feedback to a file.
 */
public class ClinicalGuideline {

    // Private fields for storing clinical guideline details
    private String guideLineType;                    // Type of the guideline (e.g., treatment, diagnostic)
    private String guidelineId;                     // Unique identifier for the guideline
    private String guideDescription;                // Description of the guideline
    private String supportingEvidence;              // Evidence supporting the guideline
    private int bloodPressureSystolicThreshHold;    // Systolic blood pressure threshold for the guideline
    private String authoringCommittee;              // Committee that authored the guideline
    private String referenceDocuments;              // Reference documents for the guideline
    private String lastUpdated;                  // Date when the guideline was last updated
    private String followUpRecommendation;          // Follow-up recommendations based on the guideline
    private String priorityLevel;

    // Priority level of the guideline

    /**
     * Constructs a ClinicalGuideline object with all fields.
     *
     * @param guidelineId Unique identifier for the guideline.
     * @param guideDescription Description of the guideline.
     * @param supportingEvidence Evidence supporting the guideline.
     * @param bloodPressureSystolicThreshHold Systolic blood pressure threshold.
     * @param guideLineType Type of the guideline.
     * @param authoringCommittee Committee that authored the guideline.
     * @param referenceDocuments Reference documents for the guideline.
     * @param lastUpdated Date when the guideline was last updated.
     * @param followUpRecommendation Follow-up recommendations based on the guideline.
     * @param priorityLevel Priority level of the guideline.
     */
    public ClinicalGuideline(String guidelineId, String guideDescription, String supportingEvidence,
                             int bloodPressureSystolicThreshHold, String guideLineType, String authoringCommittee,
                             String referenceDocuments, String lastUpdated, String followUpRecommendation,
                             String priorityLevel) {
        this.guidelineId = guidelineId;
        this.guideDescription = guideDescription;
        this.supportingEvidence = supportingEvidence;
        this.bloodPressureSystolicThreshHold = bloodPressureSystolicThreshHold;
        this.guideLineType = guideLineType;
        this.authoringCommittee = authoringCommittee;
        this.referenceDocuments = referenceDocuments;
        this.lastUpdated = lastUpdated;
        this.followUpRecommendation = followUpRecommendation;
        this.priorityLevel = priorityLevel;

    }

    /**
     * Constructs a ClinicalGuideline object without specifying the blood pressure threshold.
     * The threshold defaults to 0.
     *
     * @param guidelineId Unique identifier for the guideline.
     * @param guideDescription Description of the guideline.
     * @param supportingEvidence Evidence supporting the guideline.
     * @param guideLineType Type of the guideline.
     * @param authoringCommittee Committee that authored the guideline.
     * @param referenceDocuments Reference documents for the guideline.
     * @param lastUpdated Date when the guideline was last updated.
     * @param followUpRecommendation Follow-up recommendations based on the guideline.
     * @param priorityLevel Priority level of the guideline.
     */
    public ClinicalGuideline(String guidelineId, String guideDescription, String supportingEvidence,
                             String guideLineType, String authoringCommittee,
                             String referenceDocuments, String lastUpdated, String followUpRecommendation,
                             String priorityLevel) {
        this(guidelineId, guideDescription, supportingEvidence, 0, guideLineType, authoringCommittee,
                referenceDocuments, lastUpdated, followUpRecommendation, priorityLevel);
    }

    /**
     * Gets the unique identifier of the guideline.
     * @return The guideline ID.
     */
    public String getGuidelineId() { return guidelineId; }

    /**
     * Sets the unique identifier of the guideline.
     * @param guidelineId The guideline ID.
     */
    public void setGuidelineId(String guidelineId) { this.guidelineId = guidelineId; }

    /**
     * Gets the description of the guideline.
     * @return The guideline description.
     */
    public String getGuideDescription() { return guideDescription; }

    /**
     * Sets the description of the guideline.
     * @param guideDescription The guideline description.
     */
    public void setGuideDescription(String guideDescription) { this.guideDescription = guideDescription; }

    /**
     * Gets the supporting evidence for the guideline.
     * @return The supporting evidence.
     */
    public String getSupportingEvidence() { return supportingEvidence; }

    /**
     * Sets the supporting evidence for the guideline.
     * @param supportingEvidence The supporting evidence.
     */
    public void setSupportingEvidence(String supportingEvidence) { this.supportingEvidence = supportingEvidence; }

    /**
     * Gets the systolic blood pressure threshold.
     * @return The systolic blood pressure threshold.
     */
    public int getBloodPressureSystolicThreshHold() { return bloodPressureSystolicThreshHold; }

    /**
     * Sets the systolic blood pressure threshold.
     * @param bloodPressureSystolicThreshHold The systolic blood pressure threshold.
     */
    public void setBloodPressureSystolicThreshHold(int bloodPressureSystolicThreshHold) {
        this.bloodPressureSystolicThreshHold = bloodPressureSystolicThreshHold;
    }

    /**
     * Gets the type of the guideline.
     * @return The guideline type.
     */
    public String getGuideLineType() { return guideLineType; }

    /**
     * Sets the type of the guideline.
     * @param guideLineType The guideline type.
     */
    public void setGuideLineType(String guideLineType) { this.guideLineType = guideLineType; }

    /**
     * Gets the authoring committee of the guideline.
     * @return The authoring committee.
     */
    public String getAuthoringCommittee() { return authoringCommittee; }

    /**
     * Sets the authoring committee of the guideline.
     * @param authoringCommittee The authoring committee.
     */
    public void setAuthoringCommittee(String authoringCommittee) { this.authoringCommittee = authoringCommittee; }

    /**
     * Gets the reference documents for the guideline.
     * @return The reference documents.
     */
    public String getReferenceDocuments() { return referenceDocuments; }

    /**
     * Sets the reference documents for the guideline.
     * @param referenceDocuments The reference documents.
     */
    public void setReferenceDocuments(String referenceDocuments) { this.referenceDocuments = referenceDocuments; }

    /**
     * Gets the last updated date of the guideline.
     * @return The last updated date.
     */
    public String getLastUpdated() { return lastUpdated; }

    /**
     * Sets the last updated date of the guideline.
     * @param lastUpdated The last updated date.
     */
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    /**
     * Gets the follow-up recommendation based on the guideline.
     * @return The follow-up recommendation.
     */
    public String getFollowUpRecommendation() { return followUpRecommendation; }

    /**
     * Sets the follow-up recommendation based on the guideline.
     * @param followUpRecommendation The follow-up recommendation.
     */
    public void setFollowUpRecommendation(String followUpRecommendation) { this.followUpRecommendation = followUpRecommendation; }

    /**
     * Gets the priority level of the guideline.
     * @return The priority level.
     */
    public String getPriorityLevel() { return priorityLevel; }

    /**
     * Sets the priority level of the guideline.
     * @param priorityLevel The priority level.
     */
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }

    /**
     * Generates a list of clinical guidelines with placeholder data.
     * @return A list of clinical guidelines.
     */
    public static List <ClinicalGuideline> generateClinicalGuideLine(){
        List <ClinicalGuideline> clinicalGuidelines = new ArrayList<>();
        // Clinical Guidelines with Placeholder Data
        clinicalGuidelines.add(new ClinicalGuideline(
                // Clinical Guideline for Paracetamol
                "C001", "Administer Paracetamol with food",
                "Paracetamol is generally safe for all patients, but it should be taken with food to prevent stomach discomfort. "
                        +"Monitor liver function in patients with chronic liver disease or those taking high doses.",
                5, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/paracetamol-guideline","05-19-2023",
                "Monitor liver enzymes in high-risk patients.", "LOW"));
        // Clinical Guideline for Aspirin
        clinicalGuidelines.add(new  ClinicalGuideline(
                "C002", "Administer Aspirin Carefully",
                "Aspirin is safe for general use but should be avoided in children due to Reye’s syndrome risk. "
                        + "Monitor bleeding risk in patients with clotting disorders.",
                0, "Medication", "American Heart Association (AHA)",
                "www.placeholder-url.com/aspirin-guideline","05-19-2023",
                "Check platelet count in patients before long-term use.", "MEDIUM"
        ));
        // Clinical Guideline for Furosemide
        clinicalGuidelines.add( new ClinicalGuideline(
                "C003", "Administer Furosemide through the mouth",
                "Furosemide is a loop diuretic that can cause dehydration and electrolyte imbalances. "
                        + "Monitor blood pressure and kidney function in long-term use.",
                90, "Medication", "National Kidney Foundation",
                "www.placeholder-url.com/furosemide-guideline", "05-19-2023",
                "Monitor electrolyte levels every 48 hours in high-risk patients.", "HIGH"
        ));
        // Clinical Guideline for Viral Pharyngitis
        clinicalGuidelines.add( new ClinicalGuideline(
                "C004", "Viral Pharyngitis",
                "Common symptoms include sore throat, runny nose, cough, and mild fever. "
                        + "Self-limiting in most cases; antibiotics are not recommended.",
                0, "Symptom", "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/viral-pharyngitis", "05-19-2023",
                "Recommend rest, hydration, and symptomatic relief with analgesics.", "LOW"
        ));
        // Clinical Guideline for Strep Throat
        clinicalGuidelines.add( new ClinicalGuideline(
                "C005", "Strep Throat (Bacterial Pharyngitis)",
                "Characterized by sore throat, high fever, swollen tonsils, and absence of cough. "
                        + "Requires rapid strep test or throat culture before starting antibiotics.",
                0, "Symptom", "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/strep-throat", "05-19-2023",
                "Treat with 10-day course of penicillin or amoxicillin. Monitor symptoms closely.", "MEDIUM"
        ));
        // Clinical Guideline for Tonsillitis
        clinicalGuidelines.add(new ClinicalGuideline(
                "C006", "Tonsillitis",
                "Severe sore throat, difficulty swallowing, and swollen tonsils. "
                        + "Can be viral or bacterial; clinical evaluation is needed.",
                0, "Symptom", "American Academy of Otolaryngology",
                "www.placeholder-url.com/tonsillitis", "05-19-2023",
                "If bacterial, consider antibiotics; if recurrent, assess for tonsillectomy.", "MEDIUM"
        ));
        // Clinical Guideline for Possible Heart Condition
        clinicalGuidelines.add(new ClinicalGuideline(
                "C007", "Possible Heart Condition",
                "Symptoms include chest pain, shortness of breath, dizziness, and palpitations. "
                        + "Requires immediate ECG and clinical evaluation.",
                120, "Cardiology", "American College of Cardiology (ACC)",
                "www.placeholder-url.com/chest-pain-guideline","05-19-2023",
                "Urgent referral for cardiology evaluation and stress testing.", "CRITICAL"
        ));
        return clinicalGuidelines;
    }

    /** DISPLAY METHODS **/
    /**
     * Displays a brief version of the guideline.
     */
    public void displayGuideline() {
        System.out.println("Clinical Guideline ID: " + guidelineId);
        System.out.println("Guideline: " + guideDescription);
    }

    /**
     * Displays detailed information of the guideline.
     */
    public void displayClinicalGuideline() {
        System.out.println("\n===== Clinical Guideline =====");
        System.out.println("ID: " + this.getGuidelineId());
        System.out.println("Type: " + this.getGuideLineType());
        System.out.println("Description: " + this.getGuideDescription());
        System.out.println("Supporting Evidence: " + this.getSupportingEvidence());
        System.out.println("Blood Pressure Threshold: " + this.getBloodPressureSystolicThreshHold());
        System.out.println("Authoring Committee: " + this.getAuthoringCommittee());
        System.out.println("Reference Document: " + this.getReferenceDocuments());
        System.out.println("Last Updated: " + this.getLastUpdated());
        System.out.println("Priority Level: " + this.getPriorityLevel());
        System.out.println("Follow-up Recommendation: " + this.getFollowUpRecommendation());
        System.out.println("====================================\n");
    }

    /**
     * Displays guideline.
     */
    public void displayGuidelineForPatient() {
        System.out.println("Instruction: " + guideDescription);
        System.out.println("\u26A0\uFE0F Warning: " + supportingEvidence);
    }

    /**
     * Displays a list of clinical guidelines.
     * @param clinicalGuidelines List of clinical guidelines to be displayed.
     */
    public static void displayGuidelineList(List<ClinicalGuideline> clinicalGuidelines) {
        System.out.printf("%-20s %-45s %-55s %n", "Clinical Guideline ID", "Clinical Guideline Description", "Supporting Evidence");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        for (ClinicalGuideline clinicalGuideline : clinicalGuidelines) {
            clinicalGuideline.displayClinicalGuideline();
        }
    }

    /** FILE OPERATIONS **/

    /**
     * Saves feedback related to this guideline to a file named 'feedback.txt'.
     * @param feedback The feedback to be saved.
     */
    public void saveFeedbackToFile(String feedback) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String timestamp = now.format(formatter);
        try (FileWriter writer = new FileWriter(timestamp+ "_Feedback.txt", true)) {
            writer.write("Guideline ID: " + this.getGuidelineId() + "\n");
            writer.write("Guideline: " + this.getGuideDescription() + "\n");
            writer.write("Feedback: " + feedback + "\n");
            writer.write("====================================\n");
            System.out.println("\n\u2705 Feedback saved successfully to!");
        } catch (IOException e) {
            System.err.println("\u274C Error saving feedback: " + e.getMessage());
        }
    }
    public static ClinicalGuideline findGuidelineById(List<ClinicalGuideline> guidelines, String guidelineID) {
        for (ClinicalGuideline guideline : guidelines) {
            if (guideline.getGuidelineId().equalsIgnoreCase(guidelineID)) {
                return guideline;  // Return the found guideline
            }
        }
        return null;  // Return null if no guideline matches
    }
}

<<<<<<< HEAD:src/main/java/org/groupJ/core/ClinicalGuideline.java
package org.groupJ.core;

import java.io.FileWriter;
import java.io.IOException;
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
    private String medicationName;  // For Medication-type guidelines
    private String symptomName;

    /**
     * Constructs a ClinicalGuideline object with all fields.
     *
     * @param guidelineId                     Unique identifier for the guideline.
     * @param guideDescription                Description of the guideline.
     * @param supportingEvidence              Evidence supporting the guideline.
     * @param bloodPressureSystolicThreshHold Systolic blood pressure threshold.
     * @param guideLineType                   Type of the guideline.
     * @param authoringCommittee              Committee that authored the guideline.
     * @param referenceDocuments              Reference documents for the guideline.
     * @param lastUpdated                     Date when the guideline was last updated.
     * @param followUpRecommendation          Follow-up recommendations based on the guideline.
     * @param priorityLevel                   Priority level of the guideline.
     */

    public ClinicalGuideline(String guidelineId, String guideDescription, String supportingEvidence,
                             int bloodPressureSystolicThreshHold, String guideLineType, String authoringCommittee,
                             String referenceDocuments, String lastUpdated, String followUpRecommendation,
                             String priorityLevel, String medicationName, String symptomType) {
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
        this.medicationName = medicationName;
        this.symptomName = symptomType;
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

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }


    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

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
                "Monitor liver enzymes in high-risk patients.", "LOW","Paracetamol",null));
        // Clinical Guideline for Aspirin
        clinicalGuidelines.add(new  ClinicalGuideline(
                "C002", "Administer Aspirin Carefully",
                "Aspirin is safe for general use but should be avoided in children due to Reye’s syndrome risk. "
                        + "Monitor bleeding risk in patients with clotting disorders.",
                0, "Medication", "American Heart Association (AHA)",
                "www.placeholder-url.com/aspirin-guideline","05-19-2023",
                "Check platelet count in patients before long-term use.", "MEDIUM","Aspirin",null
        ));
        // Clinical Guideline for Furosemide
        clinicalGuidelines.add( new ClinicalGuideline(
                "C003", "Administer Furosemide through the mouth",
                "Furosemide is a loop diuretic that can cause dehydration and electrolyte imbalances. "
                        + "Monitor blood pressure and kidney function in long-term use.",
                90, "Medication", "National Kidney Foundation",
                "www.placeholder-url.com/furosemide-guideline", "05-19-2023",
                "Monitor electrolyte levels every 48 hours in high-risk patients.", "HIGH","Furosemide", null
        ));
        // Clinical Guideline for Diphenhydramine
        clinicalGuidelines.add(new ClinicalGuideline(
                "C004", "Administer Diphenhydramine for Allergies or Sleep Aid",
                "Diphenhydramine is an antihistamine commonly used to treat allergies, motion sickness, and as a short-term sleep aid. "
                        + "It may cause drowsiness, dry mouth, or dizziness in some individuals. Caution when driving or operating machinery.",
                70, "Medication", "American Academy of Allergy, Asthma, and Immunology (AAAAI)",
                "www.placeholder-url.com/diphenhydramine-guideline", "06-15-2023",
                "Avoid use with alcohol and sedatives. Monitor elderly patients for confusion or falls.", "MEDIUM","Diphenhydramine",null
        ));
        // Clinical Guideline for Loperamide
        clinicalGuidelines.add(new ClinicalGuideline(
                "C005", "Administer Loperamide for Diarrhea",
                "Loperamide is used to reduce the frequency of diarrhea by slowing intestinal motility. It should not be used for infections causing diarrhea or in children under 2 years old. "
                        + "It may cause constipation, bloating, and abdominal discomfort. In rare cases, high doses can lead to serious heart conditions.",
                85, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/loperamide-guideline", "07-22-2023",
                "Monitor for signs of dehydration and avoid prolonged use. If diarrhea persists, seek further medical evaluation.", "HIGH","Loperamide",null
        ));
        // New Clinical Guidelines for Added Medications
        clinicalGuidelines.add(new ClinicalGuideline(
                "C006", "Prescribe Amoxicillin based on confirmed bacterial infection",
                "Amoxicillin is an antibiotic used for bacterial infections. "
                        + "Ensure proper diagnosis before prescribing to prevent antibiotic resistance.",
                0, "Medication", "Infectious Diseases Society of America (IDSA)",
                "www.placeholder-url.com/amoxicillin-guideline", "06-10-2023",
                "Use for confirmed bacterial infections; avoid unnecessary use.", "MEDIUM","Amoxicillin",null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C007", "Metformin for Diabetes Management",
                "Metformin is the first-line treatment for Type 2 Diabetes. "
                        + "Monitor renal function before prescribing and adjust dose if necessary.",
                0, "Medication", "American Diabetes Association (ADA)",
                "www.placeholder-url.com/metformin-guideline", "07-15-2023",
                "Monitor kidney function every 6 months in diabetic patients.", "MEDIUM","Metformin", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C008", "Ibuprofen for Pain Management",
                "Ibuprofen is used for pain relief and inflammation Avoid in patients with peptic ulcer disease or high cardiovascular risk.",
                0, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/ibuprofen-guideline", "08-05-2023",
                "Limit to short-term use; consider alternatives for long-term pain management.", "LOW","Ibuprofen", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C009", "Statin Therapy: Atorvastatin for Cholesterol Control",
                "Atorvastatin is used to lower cholesterol and reduce cardiovascular risk. Monitor liver function and muscle enzymes in long-term use.",
                10, "Medication", "American College of Cardiology (ACC)",
                "www.placeholder-url.com/atorvastatin-guideline", "09-20-2023",
                "Check liver enzymes and lipid profile every 6 months.", "MEDIUM","Atorvastatin",null));

        // Medication Guideline: Levothyroxine
        clinicalGuidelines.add(new ClinicalGuideline(
                "C010", "Levothyroxine for Hypothyroidism",
                "Levothyroxine is a thyroid hormone replacement. Titrate dose based on TSH levels; avoid sudden dose changes.",
                0, "Medication", "American Thyroid Association (ATA)", "www.placeholder-url.com/levothyroxine-guideline",
                "10-11-2023", "Monitor TSH levels every 3-6 months in newly treated patients.",
                "LOW", "Levothyroxine", null
        ));

// Symptom Guideline: Heart Condition
        clinicalGuidelines.add(new ClinicalGuideline(
                "C011", "Possible Heart Condition",
                "Symptoms include chest pain, shortness of breath, dizziness, and palpitations. Requires immediate ECG and clinical evaluation.", 120,
                "Symptom", "American College of Cardiology (ACC)",
                "www.placeholder-url.com/chest-pain-guideline", "05-19-2023",
                "Urgent referral for cardiology evaluation and stress testing.", "CRITICAL", null,
                "Chest Pain"
        ));

        // Symptom Guideline: Pain Relief
        clinicalGuidelines.add(new ClinicalGuideline(
                "C012", "Ibuprofen for Pain Management",
                "Ibuprofen is used for pain relief and inflammation. Avoid in patients with peptic ulcer disease or high cardiovascular risk.",
                0, "Symptom", "World Health Organization (WHO)", "www.placeholder-url.com/ibuprofen-guideline", "08-05-2023",
                "Limit to short-term use; consider alternatives for long-term pain management.", "LOW", null, "Body Pain"
        ));
        // Clinical Guidelines for General Body Aches
        clinicalGuidelines.add(new ClinicalGuideline(
                "C013",
                "Management of General Body Aches",
                "Body aches can result from muscle strain, viral infections, or chronic pain conditions. "
                        + "Mild cases can be managed with NSAIDs like ibuprofen or acetaminophen. "
                        + "For severe or persistent cases, consider referral for further evaluation.",
                0,
                "Symptom",
                "World Health Organization (WHO)",
                "www.placeholder-url.com/body-ache-guideline",
                "06-01-2024",
                "Recommend NSAIDs, hydration, and rest for mild cases. If pain persists beyond 2 weeks, consider further diagnostics.",
                "MEDIUM",
                null,
                "Body Aches"
        ));

// Clinical Guideline for Viral Pharyngitis
        clinicalGuidelines.add(new ClinicalGuideline(
                "C014",
                "Viral Pharyngitis",
                "Common symptoms include sore throat, runny nose, cough, and mild fever. "
                        + "Self-limiting in most cases; antibiotics are not recommended.",
                0,
                "Symptom",
                "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/viral-pharyngitis",
                "05-19-2023",
                "Recommend rest, hydration, and symptomatic relief with analgesics.",
                "LOW",
                null,
                "Sore Throat"
        ));

// Clinical Guideline for Strep Throat
        clinicalGuidelines.add(new ClinicalGuideline(
                "C015",
                "Strep Throat",
                "Characterized by sore throat, high fever, swollen tonsils, and absence of cough. "
                        + "Requires rapid strep test or throat culture before starting antibiotics.",
                0,
                "Symptom",
                "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/strep-throat",
                "05-19-2023",
                "Treat with 10-day course of penicillin or amoxicillin. Monitor symptoms closely.",
                "MEDIUM",
                null,
                "Strep Throat"
        ));

        // Clinical Guideline for Tonsillitis
        clinicalGuidelines.add(new ClinicalGuideline(
                "C016",
                "Tonsillitis",
                "Severe sore throat, difficulty swallowing, and swollen tonsils. "
                        + "Can be viral or bacterial; clinical evaluation is needed.",
                0,
                "Symptom",
                "American Academy of Otolaryngology",
                "www.placeholder-url.com/tonsillitis",
                "05-19-2023",
                "If bacterial, consider antibiotics; if recurrent, assess for tonsillectomy.",
                "MEDIUM",
                null,
                "Tonsillitis"
        ));

        //Clinal Guideline for Severe Headaches
        clinicalGuidelines.add(new ClinicalGuideline(
                "C017", "Migraine",
                "Migraine is a severe headache often accompanied by nausea, vomiting, and sensitivity to light and sound.",
                80, "Symptom", "American Headache Society",
                "www.placeholder-url.com/migraine-guideline", "08-01-2023",
                "Administer pain relief like Ibuprofen or Acetaminophen. For recurrent migraines, consider preventive treatments and avoid triggers.",
                "MEDIUM", null, "Headache"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C018", "Meningitis",
                "Meningitis is an inflammation of the protective membranes around the brain and spinal cord. Symptoms can include severe headache, fever, chills, body aches.",
                95, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/meningitis-guideline", "08-05-2023",
                "Immediate hospitalization and antibiotic therapy may be required. If bacterial meningitis is suspected, broad-spectrum antibiotics should be initiated.",
                "CRITICAL", null, "Meningitis"));

        //Clinical Guideline for Nausea and Vomiting
        clinicalGuidelines.add(new ClinicalGuideline(
                "C019", "Gastroenteritis (Stomach Flu)",
                "Gastroenteritis causes inflammation of the stomach and intestines, leading to nausea and vomiting, constipation, and occasionally severe abdominal pain.",
                85, "Symptom", "National Institute of Health (NIH)",
                "www.placeholder-url.com/gastroenteritis-guideline", "07-25-2023",
                "Rehydrate the patient with oral rehydration solutions. If vomiting persists, administer antiemetics like Ondansetron.",
                "MEDIUM", null, "Nausea and Vomiting"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C020", "Food Poisoning",
                "Food poisoning is caused by consuming contaminated food or water, resulting in symptoms like nausea and vomiting, constipation, and abdominal pain.",
                80, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/food-poisoning-guideline", "08-10-2023",
                "Ensure rehydration. Administer antiemetics and antibiotics if bacterial infection is confirmed.",
                "MEDIUM", null, "Food Poisoning"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C021", "Dermatitis",
                "Dermatitis is an inflammation of the skin leading to skin rash or itching. It can be caused by allergens or irritants.",
                75, "Symptom", "American Academy of Dermatology",
                "www.placeholder-url.com/dermatitis-guideline", "08-12-2023",
                "Topical steroids may be used to reduce inflammation. Antihistamines like Diphenhydramine can relieve itching.",
                "MEDIUM", null, "Dermatitis"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C022", "Chickenpox",
                "Chickenpox is a highly contagious viral infection that causes flu-like symptoms, skin rash or itching.",
                80, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/chickenpox-guideline", "08-15-2023",
                "Manage fever with paracetamol and calamine lotion for itching. Antiviral drugs may be used in severe cases.",
                "MEDIUM", null, "Chickenpox"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C023", "Asthma",
                "Asthma causes difficulty breathing, wheezing, and tightness in the chest. Symptoms often occur due to triggers like allergens or respiratory infections.",
                90, "Symptom", "American Lung Association",
                "www.placeholder-url.com/asthma-guideline", "07-30-2023",
                "Administer bronchodilators (albuterol) immediately. For persistent symptoms, consider corticosteroids for long-term control.",
                "HIGH", null, "Asthma"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C024", "Pneumonia",
                "Pneumonia is an infection in the lungs that can cause fever, difficulty breathing, chest pain, and fatigue.",
                95, "Symptom", "World Health Organization (WHO)",
                "www.placeholder-url.com/pneumonia-guideline", "08-05-2023",
                "Start with empirical antibiotics (ceftriaxone) and adjust based on cultures. Oxygen therapy may be required in severe cases.",
                "CRITICAL", null, "Pneumonia"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C025", "Fever in Adults",
                "Fever is a common response to infection or inflammation. A temperature above 38°C (100.4°F) is considered febrile. "
                        + "Assess for associated symptoms to identify underlying cause (e.g., cough, rash, urinary complaints).",
                0, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/fever-guideline", "08-05-2023",
                "Use antipyretic like paracetamol or ibuprofen. Avoid overuse in low-grade fever unless symptomatic.",
                "LOW", "Paracetamol", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C026", "Nausea and Vomiting Management",
                "Common causes include viral gastroenteritis, pregnancy, motion sickness, medications, and metabolic disorders. "
                        + "Assess hydration status and identify any red flags (e.g., hematemesis, altered mental status).",
                0, "Medication", "American Gastroenterological Association (AGA)",
                "www.placeholder-url.com/nausea-vomiting", "08-05-2023",
                "Treat underlying cause. Use antiemetics (ondansetron, metoclopramide) cautiously. Monitor fluid and electrolyte balance.",
                "MEDIUM", "Ondansetron", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C027", "Strep Throat",
                "Distinguish strep throat from viral pharyngitis using Centor Criteria (fever, no cough, tonsillar exudates, tender lymph nodes). "
                        + "Confirm diagnosis via rapid antigen test or throat culture.",
                0, "Medication", "Infectious Diseases Society of America (IDSA)",
                "www.placeholder-url.com/strep-testing", "08-05-2023",
                "Avoid empiric antibiotics unless test is positive. Reassess after 48 hours if symptoms persist.",
                "MEDIUM", "Amoxicillin", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C028", "Fever in Children Under 5",
                "Fever in young children is often viral but may signal serious infection. Pay attention to behavioral changes, rashes, or dehydration.",
                0, "Medication", "American Academy of Pediatrics (AAP)",
                "www.placeholder-url.com/pediatric-fever", "08-20-2023",
                "Use paracetamol based on weight. Seek urgent care if fever persists beyond 3 days or if child is lethargic.",
                "MEDIUM", "Paracetamol", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C029", "New-Onset Seizure",
                "Characterized by sudden involuntary movements or loss of consciousness. Causes range from epilepsy to metabolic disturbances or CNS infection.",
                0, "Medication", "American Academy of Neurology",
                "www.placeholder-url.com/seizure-guideline", "08-22-2023",
                "Conduct CT/MRI and EEG. Start anticonvulsant if recurrent. Rule out reversible causes like hypoglycemia.",
                "CRITICAL", "Levetiracetam", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C030", "Epinephrine for Prophylaxis",
                "Prophylaxis is a life-threatening allergic reaction causing airway swelling, hypotension, and rash. Immediate treatment required.",
                0, "Medication", "World Allergy Organization",
                "www.placeholder-url.com/epinephrine-guideline", "08-22-2023",
                "Administer IM epinephrine 0.3mg immediately. Repeat every 5–15 minutes if needed. Call emergency services.",
                "CRITICAL", "Epinephrine", null));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C031", "Sepsis",
                "Sepsis is a life-threatening condition caused by the body's response to infection. "
                        + "Common symptoms include fever or hypothermia, rapid heart rate, low blood pressure, altered mental status, and difficulty breathing. "
                        + "Early identification and intervention significantly reduce mortality.",
                90, "Symptom", "Surviving Sepsis Campaign",
                "www.placeholder-url.com/sepsis-guideline", "08-25-2023",
                "Monitor vital signs closely. Use qSOFA criteria (RR ≥ 22, AMS, SBP ≤ 100 mmHg) to evaluate sepsis risk.",
                "CRITICAL", null, "Sepsis"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C032", "Administer Broad-Spectrum Antibiotics for Sepsis",
                "Early administration of empiric broad-spectrum IV antibiotics is essential in the management of sepsis. "
                        + "Antibiotics should be tailored based on suspected infection site, patient history, and local resistance patterns. "
                        + "Common agents include piperacillin-tazobactam, ceftriaxone, or meropenem.",
                90, "Medication", "Surviving Sepsis Campaign",
                "www.placeholder-url.com/sepsis-antibiotics", "08-25-2023",
                "Administer within 1 hour of recognition. Reassess antibiotic choice after cultures return.",
                "CRITICAL", "Piperacillin-Tazobactam", null));

        return clinicalGuidelines;
    }

    /* DISPLAY METHODS **/
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

    /* FILE OPERATIONS **/

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
=======
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
        // Clinical Guideline for Diphenhydramine
        clinicalGuidelines.add(new ClinicalGuideline(
                "C004", "Administer Diphenhydramine for Allergies or Sleep Aid",
                "Diphenhydramine is an antihistamine commonly used to treat allergies, motion sickness, and as a short-term sleep aid. "
                        + "It may cause drowsiness, dry mouth, or dizziness in some individuals. Caution when driving or operating machinery.",
                70, "Medication", "American Academy of Allergy, Asthma, and Immunology (AAAAI)",
                "www.placeholder-url.com/diphenhydramine-guideline", "06-15-2023",
                "Avoid use with alcohol and sedatives. Monitor elderly patients for confusion or falls.", "MEDIUM"
        ));
        // Clinical Guideline for Loperamide
        clinicalGuidelines.add(new ClinicalGuideline(
                "C005", "Administer Loperamide for Diarrhea",
                "Loperamide is used to reduce the frequency of diarrhea by slowing intestinal motility. It should not be used for infections causing diarrhea or in children under 2 years old. "
                        + "It may cause constipation, bloating, and abdominal discomfort. In rare cases, high doses can lead to serious heart conditions.",
                85, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/loperamide-guideline", "07-22-2023",
                "Monitor for signs of dehydration and avoid prolonged use. If diarrhea persists, seek further medical evaluation.", "HIGH"
        ));
        // New Clinical Guidelines for Added Medications
        clinicalGuidelines.add(new ClinicalGuideline(
                "C006", "Prescribe Amoxicillin based on confirmed bacterial infection",
                "Amoxicillin is an antibiotic used for bacterial infections. "
                        + "Ensure proper diagnosis before prescribing to prevent antibiotic resistance.",
                0, "Medication", "Infectious Diseases Society of America (IDSA)",
                "www.placeholder-url.com/amoxicillin-guideline", "06-10-2023",
                "Use for confirmed bacterial infections; avoid unnecessary use.", "MEDIUM"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C007", "Metformin for Diabetes Management",
                "Metformin is the first-line treatment for Type 2 Diabetes. "
                        + "Monitor renal function before prescribing and adjust dose if necessary.",
                0, "Medication", "American Diabetes Association (ADA)",
                "www.placeholder-url.com/metformin-guideline", "07-15-2023",
                "Monitor kidney function every 6 months in diabetic patients.", "MEDIUM"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C008", "Ibuprofen for Pain Management",
                "Ibuprofen is used for pain relief and inflammation. "
                        + "Avoid in patients with peptic ulcer disease or high cardiovascular risk.",
                0, "Medication", "World Health Organization (WHO)",
                "www.placeholder-url.com/ibuprofen-guideline", "08-05-2023",
                "Limit to short-term use; consider alternatives for long-term pain management.", "LOW"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C009", "Statin Therapy: Atorvastatin for Cholesterol Control",
                "Atorvastatin is used to lower cholesterol and reduce cardiovascular risk. "
                        + "Monitor liver function and muscle enzymes in long-term use.",
                10, "Medication", "American College of Cardiology (ACC)",
                "www.placeholder-url.com/atorvastatin-guideline", "09-20-2023",
                "Check liver enzymes and lipid profile every 6 months.", "MEDIUM"));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C010", "Levothyroxine for Hypothyroidism",
                "Levothyroxine is a thyroid hormone replacement. "
                        + "Titrate dose based on TSH levels; avoid sudden dose changes.",
                0, "Medication", "American Thyroid Association (ATA)",
                "www.placeholder-url.com/levothyroxine-guideline", "10-11-2023",
                "Monitor TSH levels every 3-6 months in newly treated patients.", "LOW"));
        // Here ownwards are guidelines for symptoms
        // Clinical Guideline for Possible Heart Condition
        clinicalGuidelines.add(new ClinicalGuideline(
                "C011", "Possible Heart Condition",
                "Symptoms include chest pain, shortness of breath, dizziness, and palpitations. "
                        + "Requires immediate ECG and clinical evaluation.",
                120, "Symptom", "American College of Cardiology (ACC)",
                "www.placeholder-url.com/chest-pain-guideline","05-19-2023",
                "Urgent referral for cardiology evaluation and stress testing.", "CRITICAL"
        ));
        // Clinical Guideline for Pain Reflief
        clinicalGuidelines.add(new ClinicalGuideline(
                "C012", "Ibuprofen for Pain Management",
                "Ibuprofen is used for pain relief and inflammation. "
                        + "Avoid in patients with peptic ulcer disease or high cardiovascular risk.",
                0, "Symptom", "World Health Organization (WHO)",
                "www.placeholder-url.com/ibuprofen-guideline", "08-05-2023",
                "Limit to short-term use; consider alternatives for long-term pain management.", "LOW"));
        //Clinical Guidelines for General Body Aches
        clinicalGuidelines.add(new ClinicalGuideline(
                "C013", "Management of General Body Aches",
                "Body aches can result from muscle strain, viral infections, or chronic pain conditions. "
                        + "Mild cases can be managed with NSAIDs like ibuprofen or acetaminophen. "
                        + "For severe or persistent cases, consider referral for further evaluation.",
                0, "Symptom", "World Health Organization (WHO)",
                "www.placeholder-url.com/body-ache-guideline", "06-01-2024",
                "Recommend NSAIDs, hydration, and rest for mild cases. If pain persists beyond 2 weeks, consider further diagnostics.", "MEDIUM"));
        // Clinical Guideline for Viral Pharyngitis
        clinicalGuidelines.add( new ClinicalGuideline(
                "C014", "Viral Pharyngitis",
                "Common symptoms include sore throat, runny nose, cough, and mild fever. "
                        + "Self-limiting in most cases; antibiotics are not recommended.",
                0, "Symptom", "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/viral-pharyngitis", "05-19-2023",
                "Recommend rest, hydration, and symptomatic relief with analgesics.", "LOW"
        ));
        // Clinical Guideline for Strep Throat
        clinicalGuidelines.add( new ClinicalGuideline(
                "C015", "Strep Throat (Bacterial Pharyngitis)",
                "Characterized by sore throat, high fever, swollen tonsils, and absence of cough. "
                        + "Requires rapid strep test or throat culture before starting antibiotics.",
                0, "Symptom", "Centers for Disease Control (CDC)",
                "www.placeholder-url.com/strep-throat", "05-19-2023",
                "Treat with 10-day course of penicillin or amoxicillin. Monitor symptoms closely.", "MEDIUM"
        ));
        // Clinical Guideline for Tonsillitis
        clinicalGuidelines.add(new ClinicalGuideline(
                "C016", "Tonsillitis",
                "Severe sore throat, difficulty swallowing, and swollen tonsils. "
                        + "Can be viral or bacterial; clinical evaluation is needed.",
                0, "Symptom", "American Academy of Otolaryngology",
                "www.placeholder-url.com/tonsillitis", "05-19-2023",
                "If bacterial, consider antibiotics; if recurrent, assess for tonsillectomy.", "MEDIUM"
        ));
        //Clinal Guideline for Severe Headaches
        clinicalGuidelines.add(new ClinicalGuideline(
                "C017", "Migraine",
                "Migraine is a severe headache often accompanied by nausea, vomiting, and sensitivity to light and sound.",
                80, "Symptom", "American Headache Society",
                "www.placeholder-url.com/migraine-guideline", "08-01-2023",
                "Administer pain relief like Ibuprofen or Acetaminophen. For recurrent migraines, consider preventive treatments and avoid triggers.", "MEDIUM"
        ));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C018", "Meningitis",
                "Meningitis is an inflammation of the protective membranes around the brain and spinal cord. Symptoms can include severe headache, Fever, chills, body aches",
                95, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/meningitis-guideline", "08-05-2023",
                "Immediate hospitalization and antibiotic therapy may be required. If bacterial meningitis is suspected, broad-spectrum antibiotics should be initiated.", "CRITICAL"
        ));

        //Clinical Guideline for Nausea and Vomiting
        clinicalGuidelines.add(new ClinicalGuideline(
                "C019", "Gastroenteritis (Stomach Flu)",
                "Gastroenteritis causes inflammation of the stomach and intestines, leading to nausea and vomiting, constipation, and occassionally Severe Abdominal Pain",
                85, "Symptom", "National Institute of Health (NIH)",
                "www.placeholder-url.com/gastroenteritis-guideline", "07-25-2023",
                "Rehydrate the patient with oral rehydration solutions. If vomiting persists, administer antiemetics like Ondansetron.", "MEDIUM"
        ));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C020", "Food Poisoning",
                "Food poisoning is caused by consuming contaminated food or water, resulting in symptoms like nausea and vomiting, constipation, and abdominal pain.",
                80, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/food-poisoning-guideline", "08-10-2023",
                "Ensure rehydration. Administer antiemetics and antibiotics if bacterial infection is confirmed.", "MEDIUM"
        ));

        //Clinical Guidelines for skin conditions
        clinicalGuidelines.add(new ClinicalGuideline(
                "C021", "Dermatitis",
                "Dermatitis is an inflammation of the skin leading to skin rash or itching. It can be caused by allergens or irritants.",
                75, "Symptom", "American Academy of Dermatology",
                "www.placeholder-url.com/dermatitis-guideline", "08-12-2023",
                "Topical steroids may be used to reduce inflammation. Antihistamines like Diphenhydramine can relieve itching.", "MEDIUM"
        ));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C022", "Chickenpox",
                "Chickenpox is a highly contagious viral infection that causes flu-like symptoms, skin rash or itching",
                80, "Symptom", "Centers for Disease Control and Prevention (CDC)",
                "www.placeholder-url.com/chickenpox-guideline", "08-15-2023",
                "Manage fever with paracetamol and calamine lotion for itching. Antiviral drugs may be used in severe cases.", "MEDIUM"
        ));
        clinicalGuidelines.add(new ClinicalGuideline(
                "C023", "Asthma",
                "Asthma causes difficulty breathing, wheezing, and tightness in the chest. Symptoms often occur due to triggers like allergens or respiratory infections.",
                90, "Symptom", "American Lung Association",
                "www.placeholder-url.com/asthma-guideline", "07-30-2023",
                "Administer bronchodilators (albuterol) immediately. For persistent symptoms, consider corticosteroids for long-term control.", "HIGH"
        ));

        clinicalGuidelines.add(new ClinicalGuideline(
                "C024", "Pneumonia",
                "Pneumonia is an infection in the lungs that can cause fever, difficulty breathing, chest pain, and fatigue.",
                95, "Symptom", "World Health Organization (WHO)",
                "www.placeholder-url.com/pneumonia-guideline", "08-05-2023",
                "Start with empirical antibiotics (ceftriaxone) and adjust based on cultures. Oxygen therapy may be required in severe cases.", "CRITICAL"
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
>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/core/ClinicalGuideline.java

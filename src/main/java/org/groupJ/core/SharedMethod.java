package org.groupJ.core;

import java.util.List;

/**
 * The SharedMethod class consist of all the functions shared between Nurse and Doctor
 * This will help to enhance modularity for the program
 */

public class SharedMethod {
    /**
     * Finds a Clinical Guideline by its ID from a list of guidelines.
     *
     * @param guidelines List of ClinicalGuideline objects to search through.
     * @param guidelineID The ID of the guideline to find.
     * @return The matching ClinicalGuideline object if found; otherwise, returns null.
     */
    public static ClinicalGuideline findGuidelineById(List<ClinicalGuideline> guidelines, String guidelineID) {
        for (ClinicalGuideline guideline : guidelines) {
            // Compare guideline IDs (case-insensitive) to find the matching guideline
            if (guideline.getGuidelineId().equalsIgnoreCase(guidelineID)) {
                return guideline;  //  Return the found guideline
            }
        }
        return null;  //  Return null if no matching guideline is found
    }

    /**
     * Finds an Alert by its associated Clinical Guideline ID from a list of alerts.
     *
     * @param alerts List of Alert objects to search through.
     * @param guidelineID The ID of the guideline associated with the alert to find.
     * @return The matching Alert object if found; otherwise, returns null.
     */
    public static Alert findAlertByID(List<Alert> alerts, String guidelineID) {
        for (Alert alert : alerts) {
            // Compare guideline IDs (case-insensitive) to find the matching alert
            if (alert.getGuidelineId().equalsIgnoreCase(guidelineID)) {
                return alert;  //  Return the found alert
            }
        }
        return null;  //  Return null if no matching alert is found
    }
}

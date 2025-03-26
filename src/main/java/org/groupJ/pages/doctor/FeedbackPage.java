package org.groupJ.pages.doctor;

import org.groupJ.controllers.UserController;
import org.groupJ.models.enums.UserType;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.core.ClinicalGuideline;
import org.groupJ.core.SharedMethod;
import org.groupJ.util.InputValidator;
import org.groupJ.util.TextSplitter;
import org.groupJ.audit.*;

import java.util.List;

public class FeedbackPage extends UiBase {
    private static final List<ClinicalGuideline> clinicalGuidelines = List.copyOf(ClinicalGuideline.generateClinicalGuideLine());

    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.GREEN);
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Feedback Page");

        lv.attachUserInput("Give your feedback", str -> createFeedbackMechanism(clinicalGuidelines)); // Attach the user input to the list view
        canvas.setRequireRedraw(true);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void createFeedbackMechanism(List<ClinicalGuideline> clinicalGuidelines) {
        String activeUserId;
        String activeUserName;

        if (UserController.getActiveUserType()== UserType.DOCTOR){
            activeUserId = UserController.getActiveDoctor().getId();
            activeUserName = UserController.getActiveDoctor().getName();
        } else if (UserController.getActiveUserType()== UserType.NURSE) {
            activeUserId = UserController.getActiveNurse().getId();
            activeUserName = UserController.getActiveNurse().getName();
        } else {
            System.out.println("No active user found");
            return;
        }


        while (true) {
            ClinicalGuideline matchClinicalGuideline;

            displayGuidelineList(clinicalGuidelines);  // Display all available clinical guidelines

            // Prompt user to select a clinical guideline for feedback or enter 0 to go back
            String selectedClinicalID = InputValidator.getValidStringInput(
                    "Please enter the clinical guideline ID you would like to give feedback for (or enter 0 to go back): ");

            // Check if the selected guideline exists
            matchClinicalGuideline = SharedMethod.findGuidelineById(clinicalGuidelines, selectedClinicalID);

            if (matchClinicalGuideline != null) {
                // If a valid guideline is selected, prompt for feedback
                String feedback = InputValidator.getValidStringWithSpaceInput(
                        "Enter your feedback for this guideline: ");

                // Save the feedback to a file
                matchClinicalGuideline.saveFeedbackToFile(feedback);

                System.out.println("✅ Thank you for your feedback! It has been successfully recorded.");
                AuditManager.getInstance().logAction(activeUserId, "Feedback given", "Guideline ID: " + selectedClinicalID, "Patient Feedback Administered", activeUserName);
                ToPage(new DoctorMainPage());
                break;  // Exit feedback loop after successful submission
            }

            if (selectedClinicalID.equals("0")) {
                break;  // Exit feedback mechanism and return to the previous menu
            } else {
                System.out.print("❌ Invalid input, please try again.\n");
            }
        }
    }

    /**
     * Displays the list of clinical guidelines along with their descriptions and supporting evidence.
     *
     * @param clinicalGuidelines List of clinical guidelines to display.
     */
    public static void displayGuidelineList(List<ClinicalGuideline> clinicalGuidelines) {
        // Adjusted header with fine-tuned column widths for proper alignment
        System.out.printf("%-6s %-15s %-30s %-45s %-10s %-35s %-40s %-12s %-10s %-40s %n",
                "ID",
                "Type",
                "Description",
                "Supporting Evidence",
                "BP Threshold",
                "Authoring Committee",
                "Reference Document",
                "Updated",
                "Priority",
                "Follow-up Recommendation");

        // Line separator for better visual distinction
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Iterate and print each guideline
        for (ClinicalGuideline guideline : clinicalGuidelines) {
            // Split long text for Supporting Evidence and Follow-up Recommendation
            List<String> evidenceLines = TextSplitter.splitText(guideline.getSupportingEvidence(), 50);
            List<String> followUpLines = TextSplitter.splitText(guideline.getFollowUpRecommendation(), 50);

            // Determine the maximum number of lines needed
            int maxLines = Math.max(evidenceLines.size(), followUpLines.size());

            // Print each line, aligning columns properly
            for (int i = 0; i < maxLines; i++) {
                System.out.printf("%-6s %-15s %-30s %-45s %-10s %-35s %-40s %-12s %-10s %-40s %n",
                        i == 0 ? guideline.getGuidelineId() : "",       // Only print on first line
                        i == 0 ? guideline.getGuideLineType() : "",
                        i == 0 ? guideline.getGuideDescription() : "",
                        i < evidenceLines.size() ? evidenceLines.get(i) : "",
                        i == 0 ? guideline.getBloodPressureSystolicThreshHold() : "",
                        i == 0 ? guideline.getAuthoringCommittee() : "",
                        i == 0 ? guideline.getReferenceDocuments() : "",
                        i == 0 ? guideline.getLastUpdated() : "",
                        i == 0 ? guideline.getPriorityLevel() : "",
                        i < followUpLines.size() ? followUpLines.get(i) : "");
            }

            // Add a blank line between entries for readability
            System.out.println();
        }
    }

}

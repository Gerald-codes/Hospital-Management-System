package org.lucas.pages.doctor;

import org.lucas.controllers.UserController;
import org.lucas.models.enums.UserType;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.core.ClinicalGuideline;
import org.lucas.core.SharedMethod;
import org.lucas.util.InputValidator;
import org.lucas.util.TextSplitter;
import org.lucas.audit.*;
import java.util.List;

public class FeedbackPage extends UiBase {
    private static List<ClinicalGuideline> clinicalGuidelines = List.copyOf(ClinicalGuideline.generateClinicalGuideLine());

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Feedback Page");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Give your feedback  ");

        lv.attachUserInput("Give your feedback", str -> {

            createFeedbackMechanism(clinicalGuidelines);
        }); // Attach the user input to the list view
        canvas.setRequireRedraw(true);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void createFeedbackMechanism(List<ClinicalGuideline> clinicalGuidelines) {
        String activeUserId;
        String activeUserRole;

        if (UserController.getActiveUserType()== UserType.DOCTOR){
            activeUserId = UserController.getActiveDoctor().getId();
            activeUserRole = "DOCTOR";
        } else if (UserController.getActiveUserType()== UserType.NURSE) {
            activeUserId = UserController.getActiveNurse().getId();
            activeUserRole = "NURSE";

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
                AuditManager.getInstance().logAction(activeUserId, "FEEDBACK GIVEN", "Guideline ID: " + selectedClinicalID, "SUCCESS", activeUserRole);
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
        if (clinicalGuidelines.isEmpty()) {
            System.out.println("No clinical guidelines available.");
            return;
        }

        // Adjusted printf for precise alignment
        System.out.printf("%-4s | %-10s | %-40s | %-50s | %-10s | %-30s | %-20s | %-10s | %-8s | %-50s%n",
                "ID",
                "Type",
                "Description",
                "Evidence",
                "BP Thresh",
                "Authoring Comm.",
                "Ref. Document",
                "Updated",
                "Priority",
                "Follow-up Rec.");

        // Separator line with consistent width
        System.out.println("-".repeat(240));

        // Iterate and print each guideline
        for (ClinicalGuideline guideline : clinicalGuidelines) {
            System.out.printf("%-4s | %-10s | %-40s | %-50s | %-10s | %-30s | %-20s | %-10s | %-8s | %-50s%n",
                    guideline.getGuidelineId(),
                    guideline.getGuideLineType(),
                    truncate(guideline.getGuideDescription(), 40),
                    truncate(guideline.getSupportingEvidence(), 50),
                    guideline.getBloodPressureSystolicThreshHold(),
                    truncate(guideline.getAuthoringCommittee(), 30),
                    truncate(guideline.getReferenceDocuments(), 20),
                    guideline.getLastUpdated(),
                    guideline.getPriorityLevel(),
                    truncate(guideline.getFollowUpRecommendation(), 50));
        }
    }

    // Utility method to truncate text
    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength
                ? text.substring(0, maxLength - 3) + "..."
                : text;
    }

}

package org.lucas.pages.pharmacy;
import org.lucas.controllers.MedicationController;
import org.lucas.models.Medication;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;
import java.util.Scanner;

public class MedicationPage extends UiBase{
    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        return lv;
    }
    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;// Cast the parent view to a list view
        lv.setTitleHeader("List of Medicines");
        for (Medication medication : MedicationController.getAvailableMedications()) {
            printAllMedications(medication, lv);
        }

        lv.attachUserInput("Add New Medicine", str -> {
            MedicationController.collectUserInputAndAddMedication();
            canvas.setRequireRedraw(true);
        });
        lv.attachUserInput("Remove Medicine from inventory", str -> {
            MedicationController.removeStockfromMedication();
            canvas.setRequireRedraw(true);
        });

        lv.attachUserInput(" Search or Update an existing Medication", str -> {
            MedicationController.editMedicineDetails();

            canvas.setRequireRedraw(true);

            lv.addItem(new TextView(this.canvas, "Select the detail you wish to edit ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "1. Medicine Name ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "2. Guideline ID ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "3. Dosage ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "4. Side Effects ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "5. Brand Name ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "6. Dosage Strength ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "7. Frequency ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "8. Maximum Daily Dosage ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "9. Stock Available ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "10. Controlled Substance ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "11. Manufacturer Name ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "12. Batch Number ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "13. Manufacture Date ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "14. Expiry Date ", Color.BLUE));
            lv.addItem(new TextView(this.canvas, "15. Medication Price ", Color.BLUE));
        });
        canvas.setRequireRedraw(true);
    }
    private void printAllMedications(Medication medication, ListView lv) {
        lv.addItem(new TextView(this.canvas, "\n================================================================ ", Color.RED));
        lv.addItem(new TextView(this.canvas, "                       MEDICATION DETAILS                      ", Color.BLUE));
        lv.addItem(new TextView(this.canvas, "\n================================================================ ", Color.RED));

        lv.addItem(new TextView(this.canvas, "Medicine Name: " + medication.getMedicationName(),Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Medication ID: " + medication.getMedicationId(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Clinical Guideline ID: " + medication.getGuidelineId(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Dosage: " + medication.getDosage(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Side Effects: " + medication.getDosage(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Brand Name: " + medication.getBrandName(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Dosage Strength: " + medication.getDosageStrength(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Frequency: " + medication.getFrequency(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Max Daily Dosage: " + medication.getMaximumDailyDosage(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Stock Available: " + medication.getStockAvailable(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Controlled Substance: " + medication.isControlledSubstance(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Manufacturer: " + medication.getManufactureName(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Batch Number: " + medication.getBatchNumber(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Manufacture Date: " + medication.getManufactureDate(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Expiry Date: " + medication.getExpiryDate(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "Price: " + medication.getMedicationPrice(), Color.BLUE));
        lv.addItem(new TextView(this.canvas, "\n================================================================ ", Color.BLUE));
    }
}

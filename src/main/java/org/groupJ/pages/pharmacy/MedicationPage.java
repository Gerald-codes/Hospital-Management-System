package org.groupJ.pages.pharmacy;
import org.groupJ.controllers.MedicationController;
import org.groupJ.controllers.UserController;
import org.groupJ.models.Medication;
import org.groupJ.ui.framework.Color;
import org.groupJ.ui.framework.UiBase;
import org.groupJ.ui.framework.View;
import org.groupJ.ui.framework.views.ListView;
import org.groupJ.ui.framework.views.TextView;
import org.groupJ.audit.*;

public class MedicationPage extends UiBase{
    private ListView listView;

    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.GREEN);
    }

    @Override
    public void OnViewCreated(View parentView) {
        listView = (ListView) parentView;// Cast the parent view to a list view

        // Initial UI setup
        refreshUI();

        listView.attachUserInput("Add New Medicine", str -> {
            MedicationController.collectUserInputAndAddMedication();
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "NEW MEDICINE ADDED", "SYSTEM", "MEDICINE ADDED", "NURSE");
            refreshUI();
        });

        listView.attachUserInput("Remove Medicine from inventory", str -> {
            MedicationController.removeStockfromMedication();
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "MEDICINE REMOVED FROM STOCK", "SYSTEM", "INVENTORY UPDATED FOR " + MedicationController.getMedicationID() , "NURSE");
            refreshUI();
        });

        listView.attachUserInput(" Search or Update an existing Medication", str -> {
            MedicationController.editMedicineDetails();
            AuditManager.getInstance().logAction(UserController.getActiveNurse().getId(), "MEDICINE DETAILS EDITED", "SYSTEM", "DETAILS UPDATED FOR: " + MedicationController.getMedicationID1() , "NURSE");
            refreshUI();
        });
    }

    /**
     * Refreshes the UI by clearing and rebuilding the medication list view.
     * Called after operations that modify medication data.
     */
    private void refreshUI() {
        listView.clear();
        listView.setTitleHeader("List of Medicines");

        for (Medication medication : MedicationController.getAvailableMedications()) {
            printAllMedications(medication, listView);
        }

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
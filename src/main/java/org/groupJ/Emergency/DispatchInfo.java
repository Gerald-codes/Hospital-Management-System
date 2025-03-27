package org.groupJ.Emergency;


import org.groupJ.models.Nurse;
import org.groupJ.models.User;

import java.util.List;

/**
 * DispatchInfo class stores the information of the dispatch team, the equipment
 * that is being taken,
 * and the vehicle that is being dispatched to the location of the emergency.
 * The class is also used by the EmergencyCase_Dispatch class to store the
 * information of the dispatch.
 */

public class DispatchInfo {

    private int vehicleId;
    private List<Nurse> medivacMembers;
    private List<String> equipment;

    private String dispatchLocation;

    public DispatchInfo(int ambulanceId, List<Nurse> dispatchMembersList, List<String> equipmentList, String dispatchLocation) {
        this.vehicleId = ambulanceId;
        this.medivacMembers = dispatchMembersList;
        this.equipment = equipmentList;
        this.dispatchLocation = dispatchLocation;
    }


    public int getVehicleId() {
        return vehicleId;
    }


    public void setVehicleId(int ambulanceId) {
        this.vehicleId = ambulanceId;
    }

    public List<Nurse> getMedivacMembers() {
        return medivacMembers;
    }


    public void setMedivacMembers(List<Nurse> medivacMembers) {
        this.medivacMembers = medivacMembers;
    }


    public List<String> getEquipment() {
        return equipment;
    }


    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public String getDispatchLocation() {
        return dispatchLocation;
    }

    public void setDispatchLocation(String dispatchLocation) {
        this.dispatchLocation = dispatchLocation;
    }

    /**
     * Print the information of the dispatch team, the equipment that is being
     * taken,
     * and the vehicle that is being dispatched to the location of the emergency.
     */
    public String getInfo() {
        String output = "Vehicle ID: " + this.getVehicleId() + "\n";
        output += "List of Dispatch Members:\n";
        for (User healthcareProfessional : medivacMembers)
            output += healthcareProfessional.getId() + "\n";
        output += "List of Equipments: " + this.getEquipment() + "\n";

        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n  %-20s: %d\n", "Vehicle ID", vehicleId));
        sb.append(String.format("  %-20s: %s\n", "Dispatch Location", dispatchLocation));

        sb.append(String.format("  %-20s: ", "Medivac Members"));
        if (medivacMembers != null && !medivacMembers.isEmpty()) {
            sb.append("\n");
            for (Nurse nurse : medivacMembers) {
                sb.append(String.format("    - %s\n", nurse.getName()));
            }
        } else {
            sb.append("None\n");
        }

        sb.append(String.format("  %-20s: ", "Equipment"));
        if (equipment != null && !equipment.isEmpty()) {
            sb.append("\n");
            for (String item : equipment) {
                sb.append(String.format("    - %s\n", item));
            }
        } else {
            sb.append("None\n");
        }

        return sb.toString();
    }

}


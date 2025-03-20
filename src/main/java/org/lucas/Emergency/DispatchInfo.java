package org.lucas.Emergency;


import org.lucas.models.Nurse;
import org.lucas.models.User;

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

    public DispatchInfo(int ambulanceId, List<Nurse> dispatchMembersList, List<String> equipmentList) {
        this.vehicleId = ambulanceId;
        this.medivacMembers = dispatchMembersList;
        this.equipment = equipmentList;
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

}


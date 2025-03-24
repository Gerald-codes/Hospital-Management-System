package org.lucas.Emergency;


import java.util.List;

public class EmergencyCaseWrapper {
    private List<EmergencyCase> emergencyCases;
    private List<EmergencyCase_Dispatch> emergencyCaseDispatch;

    // Getters and Setters
    public List<EmergencyCase> getEmergencyCases() {
        return emergencyCases;
    }

    public void setEmergencyCases(List<EmergencyCase> emergencyCases) {
        this.emergencyCases = emergencyCases;
    }

    public List<EmergencyCase_Dispatch> getEmergencyCaseDispatch() {
        return emergencyCaseDispatch;
    }

    public void setEmergencyCaseDispatch(List<EmergencyCase_Dispatch> emergencyCaseDispatch) {
        this.emergencyCaseDispatch = emergencyCaseDispatch;
    }
}
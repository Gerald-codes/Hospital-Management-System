package org.lucas.controllers;

import com.google.gson.reflect.TypeToken;
import org.lucas.Emergency.EmergencyCase;
import org.lucas.Globals;
import org.lucas.models.Appointment;
import org.lucas.models.Patient;
import org.lucas.models.User;
import org.lucas.util.JarLocation;
import org.lucas.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmergencyController {
    private static List<EmergencyCase> emergencyCases = new ArrayList<>();
    private static final String fileName = "emergency_cases.txt";


    public void addEmergencyCases(EmergencyCase emergencyCase){
        emergencyCases.add(emergencyCase);
//        sortEmergencyCase();
    }

//    public void sortEmergencyCase(){
//        emergencyCases.sort(): //sort by triage level
//    }

    /**
     * Retrieves a list of all emergency cases associated with a specific admin, nurse or doctor.
     */
    public List<EmergencyCase> getEmergencyCases(User user){
        List<EmergencyCase> emergencyCases = getEmergencyCases();
        List<EmergencyCase> returnEmergencyList = new ArrayList<>();
        for(EmergencyCase EC: emergencyCases){
            Patient patient = EC.getPatient();
            if (patient.getId().equals(user.getId()){
                returnEmergencyList.add(EC);

            }
        }
        return returnEmergencyList;
    }

    private void loadEmergencyCaseFromFile() {
        emergencyCases.clear();
        StringBuilder sb = new StringBuilder();
        String basePath = "";

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Type listType = new TypeToken<List<Appointment>>() {
            }.getType();
            emergencyCases = Util.fromJsonString(sb.toString(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEmergencyCasesToFile() {
        String basePath = "";

        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String path = Paths.get(basePath, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = Globals.gsonPrettyPrint.toJson(emergencyCases);
            writer.write(json);
            System.out.println("Appointments saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

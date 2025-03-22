package org.lucas.controllers;

import com.google.gson.reflect.TypeToken;
import org.lucas.Emergency.EmergencyCase;
import org.lucas.Emergency.EmergencyCase_Dispatch;
import org.lucas.Emergency.EmergencySystem;
import org.lucas.Globals;
import org.lucas.models.Appointment;
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

public class ESController {
    private static List<EmergencyCase> emergencyCases = new ArrayList<>();
    private static List<EmergencyCase_Dispatch> emergencyCaseDispatch = new ArrayList<>();
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
    //    public List<EmergencyCase> getEmergencyCases(User user){
    //        List<EmergencyCase> emergencyCases = getEmergencyCases();
    //        List<EmergencyCase> returnEmergencyList = new ArrayList<>();
    //        for(EmergencyCase EC: emergencyCases){
    //            Patient patient = EC.getPatient();
    //            if (patient.getId().equals(user.getId()){
    //                returnEmergencyList.add(EC);
    //
    //            }
    //        }
    //        return returnEmergencyList;
    //    }

    //private void loadEmergencyCaseFromFile() {
    //    emergencyCases.clear();
    //    StringBuilder sb = new StringBuilder();
    //    String basePath = "";
    //
    //    // get the jar location
    //    try {
    //        basePath = JarLocation.getJarDirectory();
    //    } catch (IOException | URISyntaxException e) {
    //        e.printStackTrace();
    //        throw new RuntimeException(e);
    //    }
    //    try (BufferedReader br = Files.newBufferedReader(Paths.get(basePath, fileName))) {
    //        String line;
    //        while ((line = br.readLine()) != null) {
    //            sb.append(line);
    //        }
    //        Type listType = new TypeToken<List<Appointment>>() {
    //        }.getType();
    //        emergencyCases = Util.fromJsonString(sb.toString(), listType);
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}

    public static void saveCasesToFile(EmergencySystem emergencySystem) {
        String basePath = "";
        List<EmergencyCase> emergencyCases = emergencySystem.getEmergencyCases();
        List<EmergencyCase_Dispatch> emergencyCaseDispatch = emergencySystem.getEmergencyCaseDispatch();
        // get the jar location
        try {
            basePath = JarLocation.getJarDirectory();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String path = Paths.get(basePath, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) { // false to overwrite
            if (emergencyCases.isEmpty() && emergencyCaseDispatch.isEmpty()) {
                writer.write("No emergency cases available.\n");
            } else {
                // Write each EmergencyCase using its toString() method
                for (EmergencyCase ec : emergencyCases) {
                    writer.write(ec.toString());
                }
                // Optionally, also write the dispatch cases if needed
                for (EmergencyCase_Dispatch ecd : emergencyCaseDispatch) {
                    writer.write(ecd.toString());

                }
            }
            System.out.println("Emergency Cases saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

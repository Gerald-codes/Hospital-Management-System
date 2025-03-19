package org.lucas.pages.admin;

public class AdminPage {

}

//public class AdminPage {
//    public void startAdminFlow(HealthcareProfessional admin, AuditManager auditManager) {
//        List<Alert> alerts = admin.getAlertList();  // Retrieve list of alerts associated with the nurse
//        List<ClinicalGuideline> clinicalGuidelines = admin.getClinicalGuidelines();  // Retrieve clinical guidelines
//
//        while (true) {
//            MenuOptions.showAdminOption();  // Display the admin options
//            int choice = InputValidator.getValidIntInput("Enter your choice: ");  // Validate input from the nurse
//            if (choice == 1) {
//               continue; // WALK IN
//            }else if (choice == 2){
//                MenuOptions.showEmergencyWalkInOptions();
//                int emergency_walkIn_choice = InputValidator.getValidIntInput("Enter your choice: ");
//
////                switch(emergency_walkIn_choice) {
////                    switch (choice) {
////                        case 1:
////                            registerNewEmergencyCase();
////                            break;
////                        case 2:
////                            updateEmergencyCaseStatus();
////                            break;
////                        case 3:
////                            updateScreeningByDoctor();
////                            break;
////                        case 4:
////                            addEmergencyProcedures();
////                            break;
////                        case 5:
////                            updatePatientLocation();
////                            break;
////                        case 6:
////                            printCurrentCaseInfo();
////                            break;
////                    }
////                }
//            }else if (choice == 3) {
//                //continue; // DISPATCH CALL
//                MenuOptions.showDispatchManagementOptions();
//                int dispatch_choice = InputValidator.getValidIntInput("Enter your choice: ");
//
////                switch (dispatch_choice) {
////                    case 1:
////                        //createNewDispatch();
////                        break;
////                    case 2:
////                        //updateDispatchStatus();
////                        break;
////                    case 3:
////                        //updateScreeningByDoctorDispatch();
////                        break;
////                    case 4:
////                        //updatePatientLocationDispatch();
////                        break;
////                    case 5:
////                        //addEmergencyProceduresDispatch();
////                        break;
////                    case 6:
////                        //viewDispatchCase();
////                        break;
////                    case 7:
////                        //viewActiveDispatches();
////                        break;
////                    case 0:
////                        break;
////            }
//            }else if (choice == 0){
//                auditManager.logAction(admin.getId(), "END SESSION", "ADMIN PORTAL", "SUCCESS");  // Log session end
//                break;  // Exit the workflow
//            }
//        }
//    }
//}

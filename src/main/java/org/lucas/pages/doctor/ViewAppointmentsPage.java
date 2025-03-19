package org.lucas.pages.doctor;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.enums.AppointmentStatus;
import org.lucas.models.enums.UserType;
import org.lucas.ui.framework.*;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.ZoomCreator;
import org.lucas.util.ZoomOAuth;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewAppointmentsPage extends UiBase {
    ListView listView;
    List<Appointment> appointments;
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.GREEN
        );
        if(UserController.getActiveUserType() != UserType.DOCTOR){
            this.OnBackPressed();
            return lv;
        }
        lv.setTitleHeader(" View Appointments | " + UserController.getActiveDoctor().getName());
        this.listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
       ListView listView = (ListView) parentView;
        appointments = Globals.appointmentController.getAppointments()
                .stream()
                .filter(x->x.getAppointmentStatus() != AppointmentStatus.COMPLETED
                        && x.getAppointmentStatus() != AppointmentStatus.EMERGENCY)
                .toList();

       // filter the appointments list and only show the appointments that are not completed or non-emergency.
       listView.attachUserInput("Select Patient index", str-> selectAppointmentPrompt(appointments));
       refreshUi();
    }

    private void selectAppointmentPrompt(List<Appointment> appointments) {
        int selectedIndex = InputHelper.getValidIndex("Select Patient index", appointments);
        Appointment selectedAppointment = appointments.get(selectedIndex);

        int selectedIndex1;
        if(selectedAppointment.getAppointmentStatus() == AppointmentStatus.ACCEPTED){
                System.out.println("1. Approve appointment | 2. Reject appointment | 3. View Patient Info | 4. Start Appointment");
                selectedIndex1 = InputHelper.getValidIndex("Select An Option", 1, 4);
        }else{
                System.out.println("1. Approve appointment | 2. Reject appointment | 3. View Patient Info");
                selectedIndex1 = InputHelper.getValidIndex("Select An Option", 1, 3);
        }

        switch (selectedIndex1){
            case 1:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.ACCEPTED);
                System.out.println("Accepted, generating zoomlink...");
                try {
                    // Get the OAuth access token
                    String accessToken = ZoomOAuth.getAccessToken();

                    // Call the method to create a Zoom meeting
                    String joinUrl = ZoomCreator.createZoomMeeting(
                            accessToken,
                            "Zoom Meeting",
                            1, // Duration in minutes
                            "UTC" // Timezone
                    );
                    selectedAppointment.approveAppointment(UserController.getActiveDoctor(), joinUrl);
                    Globals.appointmentController.saveAppointmentsToFile();
                }catch (IOException e){
                    System.out.println("Error generating zoom link");
                }
                break;
            case 2:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.DECLINED);
                Globals.appointmentController.saveAppointmentsToFile();
                break;
            case 3:
                PatientInfoPage.patient = selectedAppointment.getPatient();
                ToPage(Globals.patientInfoPage);
                break;
            case 4:
                TeleconsultPage.setAppointment(selectedAppointment);
                ToPage(Globals.teleconsultPage);
                break;
        }

        refreshUi();
    }

    private void refreshUi() {
        listView.clear();
        // loop through the appointments and display them
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientName = appointment.getPatient().getName();
            String consultReason = appointment.getReason();
            String formattedTime = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String formattedDate = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            // error handling for dirty json data
            if(appointment.getAppointmentStatus() == null){
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
            }
            final int indexWidth = 3;
            final int nameWidth = 20;
            final int reasonWidth = 45;
            final int dateWidth = 10;
            final int timeWidth = 5;

            String formattedIndex = String.format("%-" + indexWidth + "d", i);
            String formattedName = String.format("%-" + nameWidth + "s", patientName.length() > nameWidth ?
                    patientName.substring(0, nameWidth - 3) + "..." :
                    patientName);
            String formattedReason = String.format("%-" + reasonWidth + "s", consultReason.length() > reasonWidth ?
                    consultReason.substring(0, reasonWidth - 3) + "..." :
                    consultReason);
            String formattedDateColumn = String.format("%-" + dateWidth + "s", formattedDate);
            String formattedTimeColumn = String.format("%-" + timeWidth + "s", formattedTime);
            String status = appointment.getAppointmentStatus().toString();

            String displayText = String.format("%s | %s | %s | %s | %s |",
                    formattedIndex, formattedName, formattedReason, formattedDateColumn, formattedTimeColumn);

//            String status = appointment.getAppointmentStatus().toString();
//
//            String displayText = String.format("%d. %s | Consult Reason: %s | Date: %s | Time: %s |",
//                    i, patientName, consultReason, formattedDate, formattedTime);

            Color itemColor = getItemColor(appointment.getAppointmentStatus());

            // add row view for horizontal separation, use two text views for two different statuses.
            ListView rowView = new ListView(this.canvas, itemColor, ListViewOrientation.HORIZONTAL);
            rowView.addItem(new TextView(this.canvas, displayText, itemColor));
            rowView.addItem(new TextView(this.canvas, status, itemColor, TextStyle.BOLD));
            listView.addItem(rowView);
        }
        this.canvas.setRequireRedraw(true);
    }

    private Color getItemColor(AppointmentStatus status) {
        switch (status) {
            case DECLINED:
                return Color.RED;
            case PENDING:
                return Color.CYAN;
            default:
                return Color.GREEN;
        }
    }
}

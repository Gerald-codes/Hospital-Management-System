package org.groupJ;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.groupJ.controllers.AppointmentController;
import org.groupJ.controllers.UserController;
import org.groupJ.pages.EntitiesPage;
import org.groupJ.pages.LandingPage;
import org.groupJ.pages.LoginPage;
import org.groupJ.pages.nurse.*;
import org.groupJ.pages.paramedic.ParamedicMenuPage;
import org.groupJ.pages.patient.PatientMainPage;
import org.groupJ.pages.pharmacy.*;
import org.groupJ.pages.doctor.*;
import org.groupJ.util.DurationTypeAdapter;
import org.groupJ.util.LocalDateTimeTypeAdapter;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Our project is far too small to warrant any complex Dependency Injection.
 * So compromise by just using static instances.
 */
public class Globals {

    // add your pages here
    public static LandingPage landingPage = new LandingPage();
//    public static LoginPage loginPage = new LoginPage();
    public static PatientMainPage patientMainPage = new PatientMainPage();
    public static PatientInfoPage patientInfoPage = new PatientInfoPage();
    public static DoctorMainPage doctorMainPage = new DoctorMainPage();
    public static DoctorLocationPage doctorLocationPage = new DoctorLocationPage();
    public static TeleconsultationPage teleconsultationPage = new TeleconsultationPage();
    public static ViewAppointmentsPage viewAppointmentsPage = new ViewAppointmentsPage();
    public static PharmacyPage pharmacyPage = new PharmacyPage();
    public static MedicationPage medicationPage = new MedicationPage();
    public static DispensaryPage dispensaryPage = new DispensaryPage();
    public static NurseMenuPage nurseMenuPage = new NurseMenuPage();
    public static NursePatientActionsPage nursePatientActionsPage = new NursePatientActionsPage();
    public static NurseLocationPage nurseLocationPage = new NurseLocationPage();
    public static NurseEmergencyMenuPage nurseEmergencyMenuPage = new NurseEmergencyMenuPage();
    public static ParamedicMenuPage paramedicMenuPage = new ParamedicMenuPage();
    public static FeedbackPage feedbackPage = new FeedbackPage();
    public static EntitiesPage entitiesPage = new EntitiesPage();
    // Controllers
    public static AppointmentController appointmentController = new AppointmentController();
    public static UserController userController = new UserController();

    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();

    public static Gson gsonPrettyPrint = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();


}
<<<<<<< HEAD:src/main/java/org/groupJ/Globals.java
package org.groupJ;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.groupJ.controllers.AppointmentController;
import org.groupJ.controllers.UserController;
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
    public static LoginPage loginPage = new LoginPage();
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
    public static NurseMainPage nurseMainPage = new NurseMainPage();
    public static NursePatientActionsPage nursePatientActionsPage = new NursePatientActionsPage();
    public static NurseLocationPage nurseLocationPage = new NurseLocationPage();
    public static NurseEmergencyMenuPage nurseEmergencyMenuPage = new NurseEmergencyMenuPage();
    public static ParamedicMenuPage paramedicMenuPage = new ParamedicMenuPage();
    public static FeedbackPage feedbackPage = new FeedbackPage();

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


=======
package org.lucas;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lucas.controllers.AppointmentController;
import org.lucas.controllers.ESController;
import org.lucas.controllers.UserController;
import org.lucas.pages.LandingPage;
import org.lucas.pages.LoginPage;
import org.lucas.pages.pharmacy.PharmacyPage;
import org.lucas.pages.doctor.*;
import org.lucas.pages.nurse.NurseMenuPage;
import org.lucas.pages.nurse.NursePatientActionsPage;
import org.lucas.pages.pharmacy.MedicationPage;
import org.lucas.util.LocalDateTimeTypeAdapter;

import java.time.LocalDateTime;

/**
 * Our project is far too small to warrant any complex Dependency Injection.
 * So compromise by just using static instances.
 */
public class Globals {

    // add your pages here
    public static PatientInfoPage patientInfoPage = new PatientInfoPage();
    public static DoctorMainPage mainPage = new DoctorMainPage();
    public static LoginPage loginPage = new LoginPage();
    public static TeleconsultPage teleconsultPage = new TeleconsultPage();
    public static ViewAppointmentsPage viewAppointmentsPage = new ViewAppointmentsPage();
    public static LandingPage landingPage = new LandingPage();
    public static PharmacyPage pharmacyPage = new PharmacyPage();
    public static MedicationPage medicationPage = new MedicationPage();
    public static FeedbackPage feedbackPage = new FeedbackPage();
    public static NurseMenuPage nurseMenuPage = new NurseMenuPage();
    public static NursePatientActionsPage nursePatientActionsPage = new NursePatientActionsPage();

    // Controllers
    public static AppointmentController appointmentController = new AppointmentController();
    public static UserController userController = new UserController();

    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
    // used strictly for debugging, beats printOutputData for POJO classes, lol
    public static Gson gsonPrettyPrint = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();


>>>>>>> parent of 830013f (Merge pull request #6 from Gerald-codes/CDSS):src/main/java/org/lucas/Globals.java
}
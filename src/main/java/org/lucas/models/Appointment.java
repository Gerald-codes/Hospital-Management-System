package org.lucas.models;

import org.lucas.models.enums.AppointmentStatus;
import org.lucas.util.ObjectBase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.lucas.models.Symptoms;

/**
 * This class represents an appointment for a telemedicine integeration for a hospital
 * It provides functionalities to manage appointments involving patients and doctors
 * It includes setting and updating appointment time, managing appointment statuses, and handling billing procedures.
 *
 * Example Usage:
 *
 *     Appointment appointment = new Appointment(patient, "Routine check-up", time, AppointmentStatus.PENDING, "Initial notes");
 *     appointment.setDoctor(doctor);
 *     appointment.approve(doctor, "zoomLinkExample");
 */

public class Appointment implements ObjectBase {
    private Patient patient;
    private String reason;
    private String history;
    private LocalDateTime appointmentTime;
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;
    private Session session;
    private String doctorNotes;
    private Billing billing;
    private MedicalCertificate mc;
    private String diagnosis;

    /**
     * Constructs a new Appointment with the specified details.
     *
     *  @param patient the patient involved in the appointment
     *  @param reason the reason for the appointment
     *  @param appointmentTime the time the appointment is scheduled
     *  @param appointmentStatus the initial status of the appointment
     */
    public Appointment(Patient patient, String reason ,LocalDateTime appointmentTime, AppointmentStatus appointmentStatus, String diagnosis) {
            this.patient = patient;
            this.reason = reason;
            this.appointmentTime = appointmentTime;
            this.appointmentStatus = appointmentStatus;
            this.diagnosis = diagnosis;
    }


    /**
     * Retrieves the session associated with this appointment.
     *
     * @return The session object.
     */
    public Session getSession() {return session;};

    /**
     * Sets the session for this appointment.
     *
     * @param session The session object to be set.
     */
    public void setSession(Session session) {this.session = session;};

    /**
     * Gets the current status of the appointment.
     *
     * @return The appointment status.
     */
    public AppointmentStatus getAppointmentStatus() {return this.appointmentStatus;}

    /**
     * Sets the status of the appointment.
     *
     * @param appointmentStatus The status to be set.
     */
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {this.appointmentStatus= appointmentStatus;};

    /**
     * Gets the doctor assigned to the appointment.
     *
     * @return The doctor object.
     */
    public Doctor getDoctor(){return doctor;}

    /**
     * Gets the doctor's notes for the appointment.
     *
     * @return The doctor's notes.
     */
    public String getDoctorNotes(){return doctorNotes;}

    /**
     * Sets the doctor's notes for the appointment.
     *
     * @param doctorNotes The doctor's notes to be set.
     */
    public void setDoctorNotes(String doctorNotes){this.doctorNotes= doctorNotes;}

    /**
     * Sets the doctor for the appointment.
     *
     * @param doctor The doctor object to be set.
     */
    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    /**
     * Gets the patient associated with the appointment.
     *
     * @return The patient object.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the patient for the appointment.
     *
     * @param patient The patient object to be set.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Gets the reason for the appointment.
     *
     * @return The reason for the appointment.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason for the appointment.
     *
     * @param reason The reason to be set.
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the scheduled time of the appointment.
     *
     * @return The appointment time.
     */
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

   /**
     * Sets the history for the appointment.
     *
     * @param history The history to be set.
     */
    public void setHistory(String history) {
        this.history = history;
    }

    /**
     * Gets the history of the appointment.
     *
     * @return The appointment history.
     */
    public String getHistory() {
        return history;
    }

    /**
     * Sets the scheduled time of the appointment.
     *
     * @param appointmentTime The time to be set.
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Gets the billing information for the appointment.
     *
     * @return The billing object.
     */
    public Billing getBilling() {
        return billing;
    }

    /**
     * Sets the billing information for the appointment.
     *
     * @param billing The billing object to be set.
     */
    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    /**
     * Gets the diagnosis for the appointment.
     *
     * @return The diagnosis.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the diagnosis for the appointment.
     *
     * @param diagnosis The diagnosis to be set.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Approves the appointment and initiates a session with the specified details.
     * This method changes the status of the appointment to 'ACCEPTED', creates a new session object for appointments that are approved, and initializes billing procedures. It invokes the initialBill() method to process the initial bill based on the assigned doctor's rates.
     *
     * @param doctor the doctor who is taking the appointment; this doctor is assigned to the appointment and responsible for the session
     * @param zoomlink the Zoom link that will be used for the virtual meeting during the appointment; this link is stored in the session details
     */
    public void approveAppointment(Doctor doctor, String zoomlink) {
        this.doctor = doctor;
        this.appointmentStatus = AppointmentStatus.ACCEPTED;
        this.session = new Session(zoomlink);
        this.billing = new Billing();
        this.billing.initialBill(doctor);
    }

    /**
     * Completes the appointment process by ending the session, updating the doctor's notes, and triggering billing.
     * - `endSession()`: Marks the session as completed, reflecting its conclusion in the system.
     * - `setDoctorNotes(String)`: Updates the appointment record with the doctor's final observations.
     * This method ensures all aspects of the appointment are concluded and documented properly in the system.
     *
     * @param doctorNotes the doctor's final remarks for the patient, intended for medical records and follow-up care.
     */
    public void finishAppointment(String doctorNotes){
        this.session.endSession();
        this.doctorNotes = doctorNotes;
        this.appointmentStatus = AppointmentStatus.COMPLETED;
    }

    /**
     * Refers the patient to emergency services, ending the session and updating notes.
     *
     * @param doctorNotes Notes from the doctor regarding the emergency referral.
     */
    public void referEmergency(String doctorNotes){
        this.session.endSession();
        this.doctorNotes = doctorNotes;
        this.appointmentStatus = AppointmentStatus.EMERGENCY;
    }

    /**
     * Sets the medical certificate for the appointment.
     *
     * @param mc The medical certificate to be set.
     */
    public void setMedicalCertificate(MedicalCertificate mc){
        this.mc = mc;
    }

    /**
     * Gets the medical certificate associated with the appointment.
     *
     * @return The medical certificate.
     */
    public MedicalCertificate getMc() {
        return mc;
    }

    /**
     * Prints the appointment details in a pretty JSON format.
     */
    public void print() {
        System.out.println(this.toPrettyJsonString());
    }

    /**
     * Returns a string representation of the Appointment object.
     *
     * @return A string containing appointment details.
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "patient=" + patient +
                ", reason='" + reason + '\'' +
                ", appointmentTime=" + appointmentTime +
                ", doctor=" + doctor +
                ", appointmentStatus=" + appointmentStatus +
                ", session=" + session +
                ", doctorNotes='" + doctorNotes + '\'' +
                '}';
    }
}
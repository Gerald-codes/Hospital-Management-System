package org.lucas.models;
import org.lucas.models.enums.DoctorType;
import org.lucas.util.ObjectBase;
import org.lucas.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Represents a doctor in a healthcare system, extending the User class with additional medical-specific details.
 * This class includes fields for a doctor's specialisation, type (e.g., GENERAL_PRACTICE, SPECIALIST), and license number.
 * It is used throughout the application to manage doctor-specific information and ensure proper access and functionality
 * based on their credentials and roles.
 */
public class Doctor extends User implements ObjectBase {
    private String specialisation;
    private DoctorType type;
    private String licenseNumber;
    private DoctorType doctorType;
    private final List<Alert> alertList;
    private final List<ClinicalGuideline> clinicalGuidelines;
    /**
     * Constructs a new Doctor with specified details.
     * @param id Unique identifier for the doctor.
     * @param loginName Doctor's login username.
     * @param name Doctor's full name.
     * @param password Doctor's login password.
     * @param email Doctor's email address.
     * @param gender Doctor's gender.
     * @param specialisation Doctor's area of medical expertise.
     * @param type The type of medical practice the doctor is involved in.
     * @param licenseNumber Doctor's medical license number.
     */
    public Doctor(String id, String loginName, String name, String password, String email, String gender, String specialisation, DoctorType type, String licenseNumber, String phoneNumber) {
        super(id, loginName, name, password, email, gender, phoneNumber);
        this.specialisation = specialisation;
        this.type = type;
        this.licenseNumber = licenseNumber;
        this.alertList = new ArrayList<>();
        this.clinicalGuidelines = new ArrayList<>();
    }

    @Override
    public String toString() {
        return super.toString()+ " Doctor{" +
                "specialisation='" + specialisation + '\'' +
                ", type=" + type +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }

    public String getSpecialisation() {return specialisation;}

    public void setSpecialisation(String specialisation) {this.specialisation = specialisation;}

    public DoctorType getType() {return type;}

    public void setType(DoctorType type) {this.type = type;}

    public String getLicenseNumber() {return licenseNumber;}

    public void setLicenseNumber(String licenseNumber) {this.licenseNumber = licenseNumber;}

    public List<ClinicalGuideline> getClinicalGuidelines() { return new ArrayList<>(this.clinicalGuidelines); }

    public void setClinicalGuidelines(List<ClinicalGuideline> clinicalGuidelines) {
        this.clinicalGuidelines.clear();
        this.clinicalGuidelines.addAll(clinicalGuidelines);
    }

    public void diagnosePatient(Patient patient, String diagnosis) {
        patient.getEHR().setDiagnosis(diagnosis);
    }

    @Override
    public void setPatientSymptoms(Symptoms symptoms, Patient patient) {
        patient.getEHR().addSymptom(symptoms);
    }

    @Override
    public void prescribeMedication(Patient patient, Medication medicine){
        patient.getEHR().addCurrentMedications(medicine);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(specialisation, doctor.specialisation) && type == doctor.type && Objects.equals(licenseNumber, doctor.licenseNumber) && doctorType == doctor.doctorType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialisation, type, licenseNumber, doctorType);
    }
}
package org.groupJ.models;
import org.groupJ.models.enums.DoctorType;
import org.groupJ.util.ObjectBase;
import java.util.Objects;
/**
 * Represents a doctor in a healthcare system, extending the User class with additional medical-specific details.
 * This class includes fields for a doctor's specialisation, type (e.g., GENERAL_PRACTICE, SPECIALIST), and license number.
 * It is used throughout the application to manage doctor-specific information and ensure proper access and functionality
 * based on their credentials and roles.
 */
public class Doctor extends User implements ObjectBase {
    private String specialisation;
    private final String licenseNumber;
    private DoctorType doctorType;
    private boolean isSurgicalApproved;           // Indicates if the doctor is approved to perform surgeries
    private boolean canPrescribeMedication;             // Indicates if the doctor can prescribe medications

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
    public Doctor(String id, String loginName, String name, String password, String email, String gender, String specialisation,
                  DoctorType type, String licenseNumber, String phoneNumber, boolean isSurgicalApproved,boolean canPrescribeMedication) {
        super(id, loginName, name, password, email, gender, phoneNumber);
        this.specialisation = specialisation;
        this.doctorType = type;
        this.licenseNumber = licenseNumber;
        this.isSurgicalApproved = isSurgicalApproved;
        this.canPrescribeMedication = canPrescribeMedication;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public DoctorType getDoctorType() {return doctorType;}

    public void setDoctorType(DoctorType doctorType) {this.doctorType = doctorType;}

    public boolean isCanPrescribeMedication() {
        return canPrescribeMedication;
    }

    public void setCanPrescribeMedication(boolean canPrescribeMedication ) {
        this.canPrescribeMedication = canPrescribeMedication;
    }

    public boolean isSurgicalApproved() {
        return isSurgicalApproved;
    }

    public void setSurgicalApproved(boolean surgicalApproved) {
        isSurgicalApproved = surgicalApproved;
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
        return Objects.equals(specialisation, doctor.specialisation) && Objects.equals(licenseNumber, doctor.licenseNumber) && doctorType == doctor.doctorType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialisation, doctorType, licenseNumber, doctorType);
    }

    @Override
    public String toString() {
        return super.toString() + "\nDoctor {" +
                "\n  Specialisation         : '" + specialisation + '\'' +
                ",\n  Doctor Type            : " + doctorType +
                ",\n  License Number         : '"+ licenseNumber + '\'' +
                "\n}";
    }
}
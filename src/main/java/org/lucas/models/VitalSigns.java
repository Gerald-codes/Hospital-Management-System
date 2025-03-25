package org.lucas.models;

/**
 * The VitalSigns class represents a set of vital measurements recorded for a patient,
 * including temperature, heart rate, blood pressure, and respiratory rate.
 */
public class VitalSigns {

    // Private fields for storing vital sign measurements
    private double temperature;                  // Body temperature in degrees Celsius
    private int heartRate;                       // Heart rate in beats per minute (bpm)
    private int bloodPressureSystolic;           // Systolic blood pressure in mmHg
    private int bloodPressureDiastolic;          // Diastolic blood pressure in mmHg
    private int respiratoryRate;                 // Respiratory rate in breaths per minute

    /**
     * Default constructor initializing all vital signs to 0.
     */
    public VitalSigns() {
        this.temperature = 0;
        this.heartRate = 0;
        this.bloodPressureSystolic = 0;
        this.bloodPressureDiastolic = 0;
        this.respiratoryRate = 0;
    }

    /**
     * Parameterized constructor to initialize vital signs with provided values.
     *
     * @param temperature Body temperature in degrees Celsius.
     * @param heartRate Heart rate in beats per minute.
     * @param systolic Systolic blood pressure in mmHg.
     * @param diastolic Diastolic blood pressure in mmHg.
     * @param respiratoryRate Respiratory rate in breaths per minute.
     */
    public VitalSigns(double temperature, int heartRate, int systolic, int diastolic, int respiratoryRate) {
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.bloodPressureSystolic = systolic;
        this.bloodPressureDiastolic = diastolic;
        this.respiratoryRate = respiratoryRate;
    }

    /**
     * Sets the body temperature.
     *
     * @param temperature the body temperature to set, in degrees Celsius.
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Sets the heart rate.
     *
     * @param heartRate the heart rate to set, in beats per minute (BPM).
     */
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    /**
     * Sets the systolic blood pressure.
     *
     * @param systolic the systolic blood pressure to set, in mmHg.
     */
    public void setBloodPressureSystolic(int systolic) {
        this.bloodPressureSystolic = systolic;
    }

    /**
     * Sets the diastolic blood pressure.
     *
     * @param diastolic the diastolic blood pressure to set, in mmHg.
     */
    public void setBloodPressureDiastolic(int diastolic) {
        this.bloodPressureDiastolic = diastolic;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    /**
     * Provides a formatted string representation of the vital signs.
     *
     * @return A string displaying the vital signs in a readable format.
     */
    @Override
    public String toString() {
        return String.format(
                "\n" +
                        "  - Temperature      : %.1f °C\n" +
                        "  - Heart Rate       : %d bpm\n" +
                        "  - Blood Pressure   : %d/%d mmHg\n" +
                        "  - Respiratory Rate : %d breaths/min",
                temperature, heartRate, bloodPressureSystolic, bloodPressureDiastolic, respiratoryRate
        );
    }
}


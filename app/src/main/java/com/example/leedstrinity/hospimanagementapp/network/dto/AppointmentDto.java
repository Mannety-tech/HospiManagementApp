package com.example.leedstrinity.hospimanagementapp.network.dto;

public class AppointmentDto {

    private long id;
    private String patientNhsNumber;
    private long startTime;
    private long endTime;
    private String doctorName;
    private String clinic;
    private String status;

    // --- Getters and Setters ---
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getPatientNhsNumber() {
        return patientNhsNumber;
    }
    public void setPatientNhsNumber(String patientNhsNumber) {
        this.patientNhsNumber = patientNhsNumber;
    }

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinic() {
        return clinic;
    }
    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}






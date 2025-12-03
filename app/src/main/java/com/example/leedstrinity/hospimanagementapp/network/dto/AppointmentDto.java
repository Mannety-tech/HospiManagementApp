package com.example.leedstrinity.hospimanagementapp.network.dto;

public class AppointmentDto {

    private long id;
    private String patientName;
    private String date;
    private String time;
    private String reason;
    private String doctorName;
    private String clinicLocation;
    private long startTime;
    private long endTime;

    // --- Constructors ---
    public AppointmentDto() { }

    public AppointmentDto(long id,
                          String patientName,
                          String date,
                          String time,
                          String reason,
                          String doctorName,
                          String clinicLocation,
                          long startTime,
                          long endTime) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.doctorName = doctorName;
        this.clinicLocation = clinicLocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getClinicLocation() { return clinicLocation; }
    public void setClinicLocation(String clinicLocation) { this.clinicLocation = clinicLocation; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }
}







package com.example.leedstrinity.hospimanagementapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.leedstrinity.hospimanagementapp.data.Converters;
import com.example.leedstrinity.hospimanagementapp.data.dao.PatientDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.StaffDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.VitalsDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicianDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicalRecordDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinic;
import com.example.leedstrinity.hospimanagementapp.data.entities.ClinicalRecord;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Patient.class,
                Staff.class,
                Vitals.class,
                Clinician.class,
                Clinic.class,
                ClinicalRecord.class,
                Appointment.class
        },
        version = 3,
        exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    // --- Executor for DB writes ---
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    // --- Declare DAOs ---
    public abstract PatientDao patientDao();
    public abstract StaffDao staffDao();
    public abstract VitalsDao vitalsDao();
    public abstract ClinicianDao clinicianDao();
    public abstract ClinicDao clinicDao();
    public abstract ClinicalRecordDao clinicalRecordDao();
    public abstract AppointmentDao appointmentDao();

    // --- Singleton instance ---
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "hospital_db"
                            )
                            // During development, fallback clears DB if schema changes
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}






















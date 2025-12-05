package com.example.leedstrinity.hospimanagementapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.leedstrinity.hospimanagementapp.data.Converters;
import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicalRecordDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.VitalsDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.PatientDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicianDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.StaffDao;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.entities.ClinicalRecord;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;
import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Appointment.class,
                ClinicalRecord.class,
                Vitals.class,
                Patient.class,
                Clinician.class,
                Staff.class,
                Clinic.class
        },
        version = 6, // bump version whenever schema changes
        exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    // --- Executor for DB writes (shared thread pool) ---
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    // --- Declare DAOs ---
    public abstract AppointmentDao appointmentDao();
    public abstract ClinicalRecordDao clinicalRecordDao();
    public abstract VitalsDao vitalsDao();
    public abstract PatientDao patientDao();
    public abstract ClinicianDao clinicianDao();
    public abstract StaffDao staffDao();
    public abstract ClinicDao clinicDao();

    // --- Combined migration from v4 directly to v6 ---
    static final Migration MIGRATION_4_6 = new Migration(4, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            // Only add columns that were NOT present in v4
            db.execSQL("ALTER TABLE appointments ADD COLUMN clinicLocation TEXT");
            db.execSQL("ALTER TABLE appointments ADD COLUMN startTimeMillis INTEGER NOT NULL DEFAULT 0");
            db.execSQL("ALTER TABLE appointments ADD COLUMN endTimeMillis INTEGER NOT NULL DEFAULT 0");
            // ⚠️ Do NOT add "reason" again, it already exists in the entity
        }
    };

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
                            // Register combined migration
                            .addMigrations(MIGRATION_4_6)
                            // Optional: during development you can still keep fallback
                            // .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Seed default clinics
                                    databaseWriteExecutor.execute(() -> {
                                        AppDatabase database = getInstance(context);
                                        database.clinicDao().insert(new Clinic("North Clinic", "123 North Street"));
                                        database.clinicDao().insert(new Clinic("South Clinic", "456 South Avenue"));
                                        database.clinicDao().insert(new Clinic("East Clinic", "789 East Road"));
                                        database.clinicDao().insert(new Clinic("West Clinic", "101 West Boulevard"));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}



















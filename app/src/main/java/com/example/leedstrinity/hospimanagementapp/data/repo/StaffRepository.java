package com.example.leedstrinity.hospimanagementapp.data.repo;

import android.app.NotificationManager;
import android.content.Context;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.dao.StaffDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaffRepository {

    private final StaffDao staffDao;
    private final ExecutorService executorService;
    private final Context context;

    public StaffRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        staffDao = db.staffDao();
        executorService = Executors.newSingleThreadExecutor();
        this.context = context.getApplicationContext();
    }

    // --- Insert staff and notify admin ---
    public void insertStaff(Staff staff) {
        executorService.execute(() -> {
            staffDao.insert(staff);
            sendAdminNotification("New Staff Added",
                    "Staff member " + staff.getName() + " has been registered.");
        });
    }

    public void updateStaff(Staff staff) {
        executorService.execute(() -> staffDao.update(staff));
    }

    public void deleteStaff(Staff staff) {
        executorService.execute(() -> staffDao.delete(staff));
    }

    // --- Queries ---
    public List<Staff> getAllStaff() {
        return staffDao.getAllStaff();
    }

    public LiveData<List<Staff>> getAllStaffLive() {
        return staffDao.getAllStaffLive();
    }

    public Staff getStaffById(long staffId) {
        return staffDao.findById(staffId);

    }

    public LiveData<List<String>> getAllDoctorNamesLive() {
        return staffDao.getAllSpecialistNamesLive();
    }


    // --- Local notification helper ---
    private void sendAdminNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "admin_channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // Use unique ID so multiple notifications don’t overwrite each other
        manager.notify((int) SystemClock.uptimeMillis(), builder.build());
    }
}



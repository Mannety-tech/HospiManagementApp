package com.example.leedstrinity.hospimanagementapp.security.auth;


import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.util.SessionManager;

public class RbacPolicyEvaluator {

    // ✅ ADMIN, RECEPTION, and CLINICIAN can view appointments
    public static boolean canViewAppointments(Context ctx) {
        String role = SessionManager.getCurrentRole(ctx);
        return "ADMIN".equals(role) || "RECEPTION".equals(role) || "CLINICIAN".equals(role);
    }

    // ✅ Only ADMIN and RECEPTION can book or reschedule
    public static boolean canBookOrReschedule(Context ctx) {
        String role = SessionManager.getCurrentRole(ctx);
        return "ADMIN".equals(role) || "RECEPTION".equals(role);
    }
}


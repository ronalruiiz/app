package com.example.pruebapractica.App;

import android.app.Application;
import android.os.SystemClock;

import com.example.pruebapractica.Services.AuthService;

public class MyApp extends Application {
    private AuthService authService;
    @Override
    public void onCreate() {
        super.onCreate();
        this.authService = AuthService.getAuthService(getSharedPreferences("Auth", MODE_PRIVATE));
        SystemClock.sleep(3000);
    }
}

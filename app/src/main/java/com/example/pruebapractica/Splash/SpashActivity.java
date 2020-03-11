package com.example.pruebapractica.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.example.pruebapractica.Activities.LoginActivity;
import com.example.pruebapractica.Activities.MainActivity;
import com.example.pruebapractica.Services.AuthService;

public class SpashActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    AuthService authService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = AuthService.getAuthService();
        Intent intent =  new Intent();
        if(authService.isLogged){
            intent.setClass(SpashActivity.this, MainActivity.class);
        }else{
            intent.setClass(SpashActivity.this, LoginActivity.class);
        }
            startActivity(intent);
        finish();
    }
}

package com.example.pruebapractica.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pruebapractica.Models.Authenticated;
import com.example.pruebapractica.R;
import com.example.pruebapractica.Services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    private AuthService authService;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.authService = AuthService.getAuthService();
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(v -> LoginActivity.this.authService.login(etUsername.getText().toString(),
                etPassword.getText().toString()).enqueue(new Callback<Authenticated>() {
            @Override
            public void onResponse(Call<Authenticated> call, Response<Authenticated> response) {
                LoginActivity me = LoginActivity.this;
                if (response.code() == 200) {
                    me.authService.setUser(response.body());
                    Intent intent = new Intent(me, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                } else {
                    Toast.makeText(me,"Emal or Password incorrect" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Authenticated> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error" + t, Toast.LENGTH_LONG).show();
                Log.e("error", t.getMessage());
            }
        }));

    }
}

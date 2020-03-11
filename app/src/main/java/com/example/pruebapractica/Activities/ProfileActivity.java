package com.example.pruebapractica.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pruebapractica.Models.Authenticated;
import com.example.pruebapractica.Models.User;
import com.example.pruebapractica.R;
import com.example.pruebapractica.Services.AuthService;
import com.example.pruebapractica.Services.UserService;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {
    EditText edtxtName;
    EditText edtxtCity;
    EditText edtxtPassword;
    AuthService authService;
    ImageButton btnEdit;
    ImageButton btnSave;
    ImageButton btnDelete;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.userService = UserService.getUserService();
        edtxtName = findViewById(R.id.edtxtName);
        edtxtCity = findViewById(R.id.edtxtCity);
        edtxtPassword = findViewById(R.id.edtxtPassword);

        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave.setEnabled(false);
        btnSave.setColorFilter(getResources().getColor(R.color.colorDisable));

        authService = AuthService.getAuthService();
        getEditextData();
        disableEditText(edtxtName);
        disableEditText(edtxtCity);
        disableEditText(edtxtPassword);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnEdit.setOnClickListener((e) -> this.editData());
        btnSave.setOnClickListener((e) -> this.saveProfile());
        btnDelete.setOnClickListener((e) -> this.deleteProfile());

    }

    void deleteProfile() {
        ProfileActivity me = this;
        this.userService.destroyUser(this.authService.getAuthenticad().user.id).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {

                Log.d("Code", "" + response.code());
                if (response.code() == 200 && response.body().get("status").equals("ok")) {
                    Boolean isReset = me.authService.resetAll();
                    Log.d("Reset", "" + isReset);
                    if (isReset) {
                        Intent intent = new Intent(me, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });
    }

    void saveProfile() {
        HashMap<String, String> userUpdate = new HashMap<>();
        userUpdate.put("name", this.edtxtName.getText().toString());
        userUpdate.put("city", this.edtxtCity.getText().toString());
        if (!this.edtxtPassword.getText().toString().isEmpty()) {
            userUpdate.put("password", this.edtxtPassword.getText().toString());
        }
        ProfileActivity me = this;
        this.userService.updateUser(authService.getAuthenticad().user.id, userUpdate).enqueue(new Callback<Authenticated>() {
            @Override
            public void onResponse(Call<Authenticated> call, Response<Authenticated> response) {
                if (response.code() == 200) {
                    Log.d("Correct", response.body().toString());
                    User user = response.body().user;
                    Authenticated authenticated = me.authService.getAuthenticad();
                    authenticated.user = user;
                    me.authService.setUser(authenticated);

                    Intent intent = new Intent(me, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Authenticated> call, Throwable t) {
                Log.e("OnFailure", t.getMessage());
            }
        });

    }

    void editData() {
        this.btnDisable(this.btnSave, false);
        this.btnDisable(this.btnEdit, true);
        this.btnDisable(this.btnDelete, true);

        enableEditText(this.edtxtName);
        enableEditText(this.edtxtCity);
        enableEditText(this.edtxtPassword);
        this.edtxtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    void btnDisable(ImageButton btn, Boolean disable) {
        if (disable) {
            btn.setEnabled(false);
            btn.setColorFilter(getResources().getColor(R.color.colorDisable));
        } else {
            btn.setEnabled(true);
            btn.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
    }

    void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    void enableEditText(EditText editText) {
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    void getEditextData() {
        this.edtxtName.setText(authService.getAuthenticad().user.name);
        this.edtxtCity.setText(authService.getAuthenticad().user.city);
    }
}

package com.example.pruebapractica.Services;

import android.content.SharedPreferences;

import com.example.pruebapractica.IServices.IAuth;
import com.example.pruebapractica.Models.Authenticated;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AuthService {
    private SharedPreferences preferences;
    private static AuthService authServices;
    private IAuth iAuth;
    private Authenticated authenticated =  null;
    public Boolean isLogged = false;

    private AuthService(SharedPreferences preferences) {
        Retrofit httpClient;
        this.preferences = preferences;
        httpClient = APIServices.HttpClient();
        this.iAuth = httpClient.create(IAuth.class);
        getSharedPreferences();
    }

    public static AuthService getAuthService(SharedPreferences preferences) {
        if (authServices == null && preferences != null) {
            authServices = new AuthService(preferences);
            return  authServices;
        }else {
            return AuthService.authServices;
        }
    }

    public Authenticated getAuthenticad(){
        return this.authenticated;
    }

    public static AuthService getAuthService() {
        return AuthService.getAuthService(AuthService.authServices.preferences);
    }

    private void getSharedPreferences() {
        if(this.preferences.contains("user") && this.preferences.contains("logged")){
            this.authenticated = new Gson().fromJson(this.preferences.getString("user",""),Authenticated.class);
            this.isLogged = this.preferences.getBoolean("logged",false);
        }
    }


    public Call<Authenticated> login(String email, String password) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("email", email);
        hashMap.put("password", password);
        return this.iAuth.login(hashMap);
    }

    public void setUser(Authenticated user) {
        if (user != null) {
            this.isLogged = true;
            this.authenticated = user;
            this.preferences.edit().putString("user", new Gson().toJson(user))
                    .putBoolean("logged",this.isLogged).apply();
        }
    }

    public Call<HashMap<String, String>> logout() {
        return this.iAuth.logout("Bearer "+this.authenticated.token);
    }

    public boolean resetAll(){
        this.isLogged = false;
        this.authenticated = null;
        this.preferences.edit().clear().apply();
        return true;
    }

}

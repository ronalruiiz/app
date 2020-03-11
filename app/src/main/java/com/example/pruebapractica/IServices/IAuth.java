package com.example.pruebapractica.IServices;

import com.example.pruebapractica.Models.Authenticated;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IAuth {
    @POST("/api/login")
    Call<Authenticated> login(@Body HashMap<String,String> body);

    @POST("/api/logout")
    Call<HashMap<String,String>> logout(@Header("Authorization") String authHeader);
}

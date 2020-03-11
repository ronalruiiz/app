package com.example.pruebapractica.IServices;

import com.example.pruebapractica.Models.Authenticated;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUser {
    @PUT("/api/user/{id}")
    Call<Authenticated> editUser(@Path("id") String id, @Header("Authorization") String token, @Body HashMap<String,String> body);
    @DELETE("/api/user/{id}")
    Call<HashMap<String,String>> deleteUser(@Path("id") String id, @Header("Authorization") String token);
}

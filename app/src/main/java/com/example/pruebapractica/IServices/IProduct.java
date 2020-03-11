package com.example.pruebapractica.IServices;

import com.example.pruebapractica.Models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IProduct {
    @GET("/api/product")
    Call<ArrayList<Product>> getAllProducts(@Header("Authorization") String token);
}

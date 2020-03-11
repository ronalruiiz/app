package com.example.pruebapractica.Models;

import com.google.gson.Gson;

public class Authenticated {
    public String token;
    public User user;

    public static User fromJson(String response){
        Gson gson = new Gson();
        return gson.fromJson(response,User.class);
    }
}

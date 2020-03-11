package com.example.pruebapractica.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServices {
    private static Retrofit retrofit;
    private static String BASE_URL = " http://192.168.1.10:8000";

    private APIServices(){
    }

    public static Retrofit HttpClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            return retrofit;
        }
        return retrofit;
    }


}

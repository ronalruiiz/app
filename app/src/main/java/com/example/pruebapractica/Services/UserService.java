package com.example.pruebapractica.Services;

import com.example.pruebapractica.IServices.IUser;
import com.example.pruebapractica.Models.Authenticated;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UserService {
    private static UserService userService;
    private IUser iUser;
    private AuthService authService;

    private UserService() {
        Retrofit httpClient;
        httpClient = APIServices.HttpClient();
        this.iUser = httpClient.create(IUser.class);
        this.authService = AuthService.getAuthService();
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
            return userService;
        }else {
            return UserService.userService;
        }
    }

    public Call<Authenticated> updateUser(int id, HashMap<String,String> body){
        return this.iUser.editUser(""+id, "Bearer "+ this.authService.getAuthenticad().token,body);
    }
    public Call<HashMap<String,String>> destroyUser(int id){
        return this.iUser.deleteUser(""+id, "Bearer "+ this.authService.getAuthenticad().token);
    }
}

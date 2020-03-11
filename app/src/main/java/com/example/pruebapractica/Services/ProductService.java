package com.example.pruebapractica.Services;

import com.example.pruebapractica.IServices.IProduct;
import com.example.pruebapractica.Models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ProductService {
    private static ProductService productService;
    private IProduct iProduct;
    private AuthService authService;
    private ProductService() {
        Retrofit httpClient;
        httpClient = APIServices.HttpClient();
        this.iProduct = httpClient.create(IProduct.class);
        this.authService = AuthService.getAuthService();
    }

    public static ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService();
            return  productService;
        }else {
            return ProductService.productService;
        }
    }

    public Call<ArrayList<Product>> getAll(){
        return this.iProduct.getAllProducts("Bearer "+this.authService.getAuthenticad().token);
    }

}

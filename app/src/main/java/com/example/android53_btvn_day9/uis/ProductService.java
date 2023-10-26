package com.example.android53_btvn_day9.uis;

import com.example.android53_btvn_day9.models.objects.Product;
import com.example.android53_btvn_day9.models.objects.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products?limit=0")
    Call<ProductResponse> getProducts();
    @GET("products/{idProduct}")
    Call<Product> getProductById(@Path("idProduct") String idProduct);
}

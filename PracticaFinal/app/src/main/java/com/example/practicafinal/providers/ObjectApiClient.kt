package com.example.practicafinal.providers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ObjectApiClient {
    private val retrofit2 = Retrofit.Builder()
        .baseUrl("https://api.rawg.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiClient = retrofit2.create(ApiInterfaz::class.java)
}
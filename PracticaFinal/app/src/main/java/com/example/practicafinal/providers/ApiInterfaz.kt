package com.example.practicafinal.providers

import com.example.practicafinal.models.ListadoApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaz {
    @GET("api/games")
    suspend fun getApi(@Query("key")key:String): Response<ListadoApi>

}
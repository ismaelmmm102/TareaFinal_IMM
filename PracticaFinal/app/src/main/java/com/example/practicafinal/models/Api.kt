package com.example.practicafinal.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Api(
    @SerializedName("name") val nombre: String,
    @SerializedName("released") val fecha: String,
    @SerializedName("background_image") val imagen: String,
    @SerializedName("rating") val puntuacion: Float

): Serializable
data class ListadoApi(
    @SerializedName("results") val listadoApi: ArrayList<Api>
)

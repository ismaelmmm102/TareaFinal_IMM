package com.example.practicafinal.models

import java.io.Serializable

data class JuegoModel(
    val id: Int,
    val nombre: String,
    val duracion: String,
    val genero: String
): Serializable

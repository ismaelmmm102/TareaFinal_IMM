package com.example.practicafinal

import android.app.Application
import android.content.Context
import com.example.practicafinal.providers.db.MyDatabase

class Aplicacion : Application() {
    companion object {
        const val VERSION = 2
        const val DB = "Base_2"
        const val TABLA = "juegos"
        lateinit var appContext: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        llave = MyDatabase()
    }
}

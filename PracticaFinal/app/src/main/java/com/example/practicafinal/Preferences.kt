package com.example.practicafinal

import android.content.Context
import android.content.Context.MODE_PRIVATE
//shared preferences

class Preferences(c: Context) {
    val storage = c.getSharedPreferences("USER_DATOS", MODE_PRIVATE)
    public fun setEmail(email: String){
        storage.edit().putString("EMAIL", email).apply()
    }
    public fun setFondo(vip: Boolean){
        storage.edit().putBoolean("FONDO", vip).apply()
    }
    public fun getEmail(): String {
        return storage.getString("EMAIL", "").toString()
    }
    public fun getFondo(): Boolean {
        return storage.getBoolean("FONDO", false)
    }
    public fun borrarTodo(){
        storage.edit().clear().apply()
    }
}
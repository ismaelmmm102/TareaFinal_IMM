package com.example.practicafinal.providers.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.practicafinal.Aplicacion
import com.example.practicafinal.models.JuegoModel

class CrudJuegos {

    fun create(c: JuegoModel): Long {
        val con = Aplicacion.llave.writableDatabase
        return try {
            con.insertWithOnConflict(
                Aplicacion.TABLA,
                null,
                c.toContentValues(),
                SQLiteDatabase.CONFLICT_IGNORE
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            -1L
        } finally {
            con.close()
        }
    }

    fun read(): MutableList<JuegoModel> {
        val lista = mutableListOf<JuegoModel>()
        val con = Aplicacion.llave.readableDatabase
        try {
            val cursor = con.query(
                Aplicacion.TABLA,
                arrayOf("id", "nombre", "duracion", "genero"),
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                val contacto = JuegoModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
                lista.add(contacto)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            con.close()
        }
        return lista
    }

    fun borrar(id: Int): Boolean {
        val con = Aplicacion.llave.writableDatabase
        val juegoBorrado = con.delete(Aplicacion.TABLA, "id=?", arrayOf(id.toString()))
        con.close()
        return juegoBorrado > 0
    }

    fun update(c: JuegoModel): Boolean {
        val con = Aplicacion.llave.writableDatabase
        val values = c.toContentValues()
        var filasAfectadas = 0
        val q = "select id from ${Aplicacion.TABLA} where nombre=? AND id <> ?"
        val cursor = con.rawQuery(q, arrayOf(c.nombre, c.id.toString()))
        val existeNombre = cursor.moveToFirst()
        cursor.close()
        if (!existeNombre) {
            filasAfectadas = con.update(Aplicacion.TABLA, values, "id=?", arrayOf(c.id.toString()))
        }
        con.close()
        return filasAfectadas > 0
    }

    private fun JuegoModel.toContentValues(): ContentValues {
        return ContentValues().apply {
            put("nombre", nombre)
            put("duracion", duracion)
            put("genero", genero)
        }
    }
}

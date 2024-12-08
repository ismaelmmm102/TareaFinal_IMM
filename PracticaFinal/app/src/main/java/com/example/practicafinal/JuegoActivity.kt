package com.example.practicafinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicafinal.databinding.ActivityJuegoBinding
import com.example.practicafinal.models.JuegoModel
import com.example.practicafinal.providers.db.CrudJuegos

class JuegoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJuegoBinding
    private var nombre = ""
    private var duracion = ""
    private var genero = ""
    private var id = -1
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJuegoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recogerContacto()
        setListeners()
        if (isUpdate) {
            binding.etTitle2.text = "Editar Juego"
            binding.btn2Enviar.text = "EDITAR"
        }
    }

    private fun recogerContacto() {
        val datos = intent.extras
        if (datos != null) {
            val c = datos.getSerializable("JUEGO") as JuegoModel
            isUpdate = true
            nombre = c.nombre
            duracion = c.duracion
            genero = c.genero
            id = c.id
            pintarDatos()
        }
    }

    private fun pintarDatos() {
        binding.etNombre.setText(nombre)
        binding.etDuracion.setText(duracion)
        binding.etGenero.setText(genero)
    }

    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btn2Reset.setOnClickListener {
            limpiar()
        }
        binding.btn2Enviar.setOnClickListener {
            guardarRegistro()
        }
    }

    private fun guardarRegistro() {
        if (datosCorrectos()) {
            val c = JuegoModel(id, nombre, duracion, genero)
            if (!isUpdate) {
                if (CrudJuegos().create(c) != -1L) {
                    Toast.makeText(
                        this,
                        "Se ha añadido un juego a la agenda",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } else {
                if (CrudJuegos().update(c)) {
                    Toast.makeText(this, "Juego Editado", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun datosCorrectos(): Boolean {
        nombre = binding.etNombre.text.toString().trim()
        duracion = binding.etDuracion.text.toString().trim()
        genero = binding.etGenero.text.toString().trim()
        if (nombre.length < 3) {
            binding.etNombre.error = "El campo nombre debe tener al menos 3 caracteres"
            return false
        }
        if (!duracion.matches("\\d+".toRegex())) {
            binding.etDuracion.error = "La duración debe ser un número"
            return false
        }
        return true
    }

    private fun limpiar() {
        binding.etNombre.setText("")
        binding.etDuracion.setText("")
        binding.etGenero.setText("")
    }
}

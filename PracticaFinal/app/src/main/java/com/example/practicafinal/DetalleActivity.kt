package com.example.practicafinal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicafinal.databinding.ActivityDetalleBinding
import com.example.practicafinal.models.Api
import com.squareup.picasso.Picasso

class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Base_Theme_PracticaFinal_NoActionBar)
        binding= ActivityDetalleBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recuperaryPintarApi()
        setListeners()
    }

    private fun setListeners() {
        binding.button.setOnClickListener {
            finish()
        }
    }

    private fun recuperaryPintarApi() {
        val datos=intent.extras
        val api:Api = datos?.getSerializable("API") as Api
        binding.tvTituloDetalle.text=api.nombre
        binding.tvPuntuacion.text=api.puntuacion.toString()
        binding.tvFecha.text=api.fecha
        Picasso.get().load(api.imagen).into(binding.ivPoster)
    }
}
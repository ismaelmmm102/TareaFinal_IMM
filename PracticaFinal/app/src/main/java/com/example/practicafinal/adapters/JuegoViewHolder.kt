package com.example.practicafinal.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinal.databinding.JuegoLayoutBinding
import com.example.practicafinal.models.JuegoModel

class JuegoViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = JuegoLayoutBinding.bind(v)

    fun render(
        c: JuegoModel,
        borrarJuegos: (Int) -> Unit,
        updateJuegos: (JuegoModel) -> Unit
    ) {
        binding.tvNombre.text = c.nombre
        binding.tvDuracion.text = c.duracion
        binding.tvGenero.text = c.genero

        binding.btnBorrar.setOnClickListener {
            borrarJuegos(adapterPosition)
        }

        binding.btnUpdate.setOnClickListener {
            updateJuegos(c)
        }
    }
}

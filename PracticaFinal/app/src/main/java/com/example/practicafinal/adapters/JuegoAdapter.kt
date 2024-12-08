package com.example.practicafinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinal.R
import com.example.practicafinal.models.JuegoModel

class JuegoAdapter(
    var lista: MutableList<JuegoModel>,
    private val borrarJuego: (Int) -> Unit,
    private val updateJuego: (JuegoModel) -> Unit
) : RecyclerView.Adapter<JuegoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuegoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.juego_layout, parent, false)
        return JuegoViewHolder(v)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: JuegoViewHolder, position: Int) {
        val juego = lista[position]
        holder.render(juego, borrarJuego, updateJuego)
    }
}

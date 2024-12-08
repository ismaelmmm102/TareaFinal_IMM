package com.example.practicafinal.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinal.databinding.ApiLayoutBinding
import com.example.practicafinal.models.Api
import com.squareup.picasso.Picasso

class ApiViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val binding = ApiLayoutBinding.bind(v)
    fun render(api: Api, onItemClick: (Api)->Unit){
        binding.tvTitulo.text=api.nombre
        Picasso.get().load(api.imagen).into(binding.ivCaratula)
        itemView.setOnClickListener{
            onItemClick(api)
        }
    }

}

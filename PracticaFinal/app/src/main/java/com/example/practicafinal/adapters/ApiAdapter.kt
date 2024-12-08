package com.example.practicafinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafinal.R
import com.example.practicafinal.models.Api

class ApiAdapter(var lista : MutableList<Api>, private var onDetalleClick: (Api)->Unit) : RecyclerView.Adapter<ApiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.api_layout,parent,false)
        return ApiViewHolder(v)
    }

    override fun getItemCount()=lista.size


    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        val api=lista[position]
        holder.render(api, onDetalleClick)
    }
}
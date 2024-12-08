package com.example.practicafinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practicafinal.adapters.ApiAdapter
import com.example.practicafinal.databinding.ActivityApiBinding
import com.example.practicafinal.models.Api
import com.example.practicafinal.providers.ObjectApiClient.apiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiBinding
    val lista= mutableListOf<Api>()
    val adapter = ApiAdapter(lista,{api->verDetalle(api)})
    var api=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        api=getString(R.string.api)
        setRecycler()
    }

    private fun setRecycler() {
        val layoutManager= GridLayoutManager(this,2)
        binding.recApi.layoutManager=layoutManager
        binding.recApi.adapter=adapter
        traerPeliculas()
    }

    private fun traerPeliculas() {
        lifecycleScope.launch (Dispatchers.IO){
            val datos= apiClient.getApi(api)
            val listaA=datos.body()?.listadoApi?: emptyList<Api>().toMutableList()
            //Como lo vamos a modificar en pantalla lo hacemos en main no io
            withContext(Dispatchers.Main){
                adapter.lista=listaA
                adapter.notifyDataSetChanged()
            }
        }
    }
    private fun verDetalle(api: Api){
        val i= Intent(this,DetalleActivity::class.java).apply{
            putExtra("API",api)
        }
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activities, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_volver -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.example.practicafinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicafinal.adapters.JuegoAdapter
import com.example.practicafinal.databinding.ActivityCrudactivityBinding
import com.example.practicafinal.models.JuegoModel
import com.example.practicafinal.providers.db.CrudJuegos

class CRUDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudactivityBinding
    var lista = mutableListOf<JuegoModel>()
    private lateinit var adapter: JuegoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrudactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        setRecycler()
        title = "Juegos"
    }

    private fun setRecycler() {
        val layoutManger = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManger
        traerRegistros()
        adapter =
            JuegoAdapter(lista, { position -> borrarJuego(position) }, { c -> update(c) })
        binding.recyclerView.adapter = adapter
    }

    private fun update(c: JuegoModel) {
        val i = Intent(this, JuegoActivity::class.java).apply {
            putExtra("JUEGO", c)
        }
        startActivity(i)
    }

    private fun borrarJuego(p: Int) {
        val id = lista[p].id
        lista.removeAt(p)
        if (CrudJuegos().borrar(id)) {
            adapter.notifyItemRemoved(p)
        } else {
            Toast.makeText(this, "No se eliminó ningún juego", Toast.LENGTH_SHORT).show()
        }
    }

    private fun traerRegistros() {
        lista = CrudJuegos().read()
        if (lista.size > 0) {
            binding.ivJuegos.visibility = View.INVISIBLE
        } else {
            binding.ivJuegos.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, JuegoActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        setRecycler()
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

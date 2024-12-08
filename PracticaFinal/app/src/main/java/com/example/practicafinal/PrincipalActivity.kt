package com.example.practicafinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicafinal.databinding.ActivityPrincipalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "VideoJuegos"
        auth = Firebase.auth
        binding.tvEmail.text = auth.currentUser?.email.toString()
        preferences = Preferences(this)
        recuperarPreferencias()
        setListeners()
    }

    private fun setListeners() {
        binding.btnEmpresas.setOnClickListener {
            val i = Intent(this, FragmentsMainActivity::class.java)
            startActivity(i)
        }
        binding.btnLocalizacion.setOnClickListener {
            val i = Intent(this, LocalizacionActivity::class.java)
            startActivity(i)
        }
        binding.btnBuscar.setOnClickListener {
            val i = Intent(this, BuscarActivity::class.java)
            startActivity(i)
        }
        binding.btnJugados.setOnClickListener {
            val i = Intent(this, CRUDActivity::class.java)
            startActivity(i)
        }
        binding.btnJuegos.setOnClickListener {
            val i = Intent(this, ApiActivity::class.java)
            startActivity(i)
        }
    }

    private fun recuperarPreferencias() {
        val email = preferences.getEmail()
        binding.tvEmail.text = email
        val fondoSel = preferences.getFondo()
        if (fondoSel) {
            binding.main.setBackgroundColor(getColor(R.color.dark_gray))
            binding.tvEmail.setTextColor(getColor(R.color.white))
            binding.tvUsuario.setTextColor(getColor(R.color.white))
        } else {
            binding.main.setBackgroundColor(getColor(R.color.white))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_salir -> {
                finishAffinity()
            }

            R.id.item_logout -> {
                auth.signOut()
                preferences.borrarTodo()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

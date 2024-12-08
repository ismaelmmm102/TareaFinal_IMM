package com.example.practicafinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.practicafinal.fragments.InicioFragment
import com.example.practicafinal.fragments.MenuFragment

class FragmentsMainActivity : AppCompatActivity(), OnFragmentActionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragments_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "Empresas Famosas"
        cargarFragments()
    }

    private fun cargarFragments() {
        val fgMenu = MenuFragment()
        val fgInicio = InicioFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_menu, fgMenu)
            add(R.id.fcv_principal, fgInicio)
        }
    }

    override fun onClickImagenMenu(btn: Int) {
        val i = Intent(this, FragmentsDosActivity::class.java).apply {
            putExtra("BOTONPULSADO", btn)
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

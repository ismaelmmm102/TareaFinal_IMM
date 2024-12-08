package com.example.practicafinal

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.practicafinal.fragments.BethesdaFragment
import com.example.practicafinal.fragments.MenuFragment
import com.example.practicafinal.fragments.MojangFragment
import com.example.practicafinal.fragments.RiotFragment

class FragmentsDosActivity : AppCompatActivity(), OnFragmentActionListener {
    private val fragments = arrayOf(
        BethesdaFragment(),
        MojangFragment(),
        RiotFragment()
    )
    private var boton = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragments_dos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "Empresas Famosas"
        cargarDatos()
    }

    private fun cargarDatos() {
        val datos = intent.extras
        boton = datos?.getInt("BOTONPULSADO") ?: 0
        val fragmentMenu = MenuFragment()
        cargarFragments(fragmentMenu, fragments[boton])
    }

    private fun cargarFragments(menu: MenuFragment, fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fcv_menu2, menu)
            replace(R.id.fcv_contenido, fragment)
        }
    }

    override fun onClickImagenMenu(btn: Int) {
        cargarFragments(MenuFragment(), fragments[btn])
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

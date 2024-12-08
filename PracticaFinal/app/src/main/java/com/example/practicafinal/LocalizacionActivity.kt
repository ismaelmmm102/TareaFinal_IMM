package com.example.practicafinal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocalizacionActivity : AppCompatActivity(), OnMapReadyCallback {
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
            if (
                permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true
                ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                gestionarLocalizacion()
            } else {
                Toast.makeText(this, "El usuario denegó los permisos....", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localizacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "Localización Empresas"
        iniciarFragment()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun iniciarFragment() {
        val fragment = SupportMapFragment()
        fragment.getMapAsync(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fm_maps, fragment)
        }
    }

    private fun gestionarLocalizacion() {
        if (!::map.isInitialized) return
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
            obtenerUbicacionActual()
        } else {
            pedirPermisos()
        }
    }

    private fun obtenerUbicacionActual() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    ponerMarcador(latLng, "Ubicación Actual")
                    mostrarAnimacion(latLng, 15f)
                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun pedirPermisos() {
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            mostrarExplicacion()
        } else {
            escogerPermisos()
        }
    }

    private fun escogerPermisos() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun mostrarExplicacion() {
        AlertDialog.Builder(this)
            .setTitle("Permisos de Ubicación")
            .setMessage("Para el uso adecuado de esta increíble aplicación necesitamos los permisos de ubicación")
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun ponerMarcador(coordenadas: LatLng, titulo: String) {
        val marker = MarkerOptions().position(coordenadas).title(titulo)
        map.addMarker(marker)
    }

    private fun mostrarAnimacion(coordenadas: LatLng, zoom: Float) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, zoom),
            4500,
            null
        )
    }

    override fun onRestart() {
        super.onRestart()
        gestionarLocalizacion()
    }

    private fun marcadorBethesda() {
        val latLngSegundoMarcador = LatLng(38.98472, -77.09472)
        ponerMarcador(latLngSegundoMarcador, "Bethesda")
    }

    private fun marcadorMojang() {
        val latLngSegundoMarcador = LatLng(59.3154, 18.0655)
        ponerMarcador(latLngSegundoMarcador, "Mojang")
    }

    private fun marcadorRiot() {
        val latLngSegundoMarcador = LatLng(34.031872, -118.457475)
        ponerMarcador(latLngSegundoMarcador, "Riot")
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.isZoomControlsEnabled = true
        gestionarLocalizacion()
        marcadorBethesda()
        marcadorMojang()
        marcadorRiot()
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

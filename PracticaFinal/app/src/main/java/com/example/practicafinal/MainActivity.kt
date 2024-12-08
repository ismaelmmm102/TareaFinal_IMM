package com.example.practicafinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicafinal.Utilidades.niveles
import com.example.practicafinal.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferences: Preferences
    private var email = ""
    private var password = ""
    private var fondo = false
    private var edad=0
    private var nivel=""
    private lateinit var adapterNivel: ArrayAdapter<CharSequence>

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val datos = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val cuenta = datos.getResult(ApiException::class.java)
                if (cuenta != null) {
                    val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credenciales)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                irActivityApp()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                }
            } catch (e: ApiException) {
                Log.d("error", e.message.toString())
            }
        }
        if (it.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Base_Theme_PracticaFinal_NoActionBar)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        preferences = Preferences(this)
        comprobarSesion()
        auth = Firebase.auth
        pintarEtiquetas("Edad")
        adapterNivel = ArrayAdapter(this,  android.R.layout.simple_spinner_item, niveles)
        binding.spNivel.adapter=adapterNivel
        setListeners()
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            recogerDatos()
            login()
        }
        binding.btnRegister.setOnClickListener {
            recogerDatos()
            registrar()
        }
        binding.btnGoogle.setOnClickListener {
            loginGoogle()
        }
        binding.sbEdad.setOnSeekBarChangeListener(this)

        binding.spNivel.onItemSelectedListener=this
    }

    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        googleClient.signOut()

        responseLauncher.launch(googleClient.signInIntent)
    }

    private fun recogerDatos() {
        email = binding.etEmail.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Se esperaba un email valido"
            return
        }
        preferences.setEmail(email)
        fondo = binding.cbFondo.isChecked
        preferences.setFondo(fondo)
    }

    private fun registrar() {
        if (!datosCorrectos()) return
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                login()
            }
        }
            .addOnFailureListener { exception ->
                if (exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(this, "El correo electrónico ya está registrado.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al registrar: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun login() {
        if (!datosCorrectos()) return
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                irActivityApp()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun datosCorrectos(): Boolean {
        email = binding.etEmail.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email no valido"
            return false
        }
        password = binding.etPassword.text.toString().trim()
        if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }
        if(binding.spNivel.selectedItemId.toInt()==0){
            Toast.makeText(this, "Debe seleccionar un nivel", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun comprobarSesion() {
        email = preferences.getEmail()
        if (email.isNotEmpty()) {
            irActivityApp()
        }
    }

    private fun irActivityApp() {
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        if (usuario != null) {
            irActivityApp()
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        when(p0?.id) {
            R.id.sbEdad -> {
                edad = p1
                pintarEtiquetas("Edad")
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when(p0?.id){
            R.id.spNivel->{
                nivel= p0.getItemAtPosition(p2).toString()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun pintarEtiquetas(campo: String) {
        when(campo){
            "Edad"->{
                binding.tvEdad.text=String.format(getString(R.string.tv_edad), edad)
            }
        }

    }
}

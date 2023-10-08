package com.example.suitcase.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.example.suitcase.R
import com.example.rblibrary.repository.UserRepo
import com.example.rblibrary.api.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvForgotPass: TextView
    private lateinit var tvSignup: TextView
    private lateinit var rootLayout: LinearLayout
    private lateinit var checkBox: CheckBox
    var isRemembered = false
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (!hasPermission()) {
            requestPermission()
        }

        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        tvForgotPass = findViewById(R.id.tvForgotPass)
        tvSignup = findViewById(R.id.tvSignup)
        rootLayout = findViewById(R.id.rootLayout)
        checkBox = findViewById(R.id.checkBox)

        sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        isRemembered = sharedPref.getBoolean("checked", false)


        btnLogin.setOnClickListener {
            if (validate())
                login()
        }

        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    private fun passData(){

    }
    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            permissions, 123
        )
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepo()
                val response = repository.loginUser(email, password)
                if (response.success == true) {
                    val id = response.data?._id

                saveEmailPassword()
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.id = id
                    startActivity(
                            Intent(
                                    this@LoginActivity,
                                    DashboardActivity::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        rootLayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK") {
                            snack.dismiss()
                        }
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@LoginActivity,
                            ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveEmailPassword() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val checked = checkBox.isChecked
        val editor:SharedPreferences.Editor = sharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putBoolean("checked", checked)
        editor.apply()
    }

    private fun validate(): Boolean {
        if(etEmail.text.toString().isEmpty()){
            etEmail.error = "Blank Email"
            etEmail.requestFocus()
            return false
        }
        else if(etPassword.text.toString().isEmpty()){
            etPassword.error = "Blank Password"
            etPassword.requestFocus()
            return false
        }
        return true
    }

}
package com.saugat.finalassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.saugat.finalassignment.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }

        //getSavedPref()

    }

    private fun getSavedPref() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val email = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        val checked = sharedPref.getBoolean("checked",false)
    }
}
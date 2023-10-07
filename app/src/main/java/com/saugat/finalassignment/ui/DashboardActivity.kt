package com.saugat.finalassignment.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saugat.finalassignment.R
import com.saugat.finalassignment.fragments.*
import java.lang.Exception

class DashboardActivity : AppCompatActivity() {

    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var botomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        botomNav = findViewById(R.id.botomNav)

        currentFragment(HomeFragment())

        botomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> currentFragment(HomeFragment())
                R.id.account -> currentFragment(ProfileFragment())
                R.id.basket -> currentFragment(AddToCartFragment())
                R.id.notification -> startActivity(Intent(this,InsertFoodActivity::class.java))

            }
            true
        }

        if (!hasPermission()) {
            requestPermission()
        }
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

    private fun currentFragment(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, fragment)
                addToBackStack(null)
                commit()
            }
        }
        catch (ex:Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
}
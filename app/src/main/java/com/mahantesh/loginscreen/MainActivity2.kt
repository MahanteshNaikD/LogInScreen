package com.mahantesh.loginscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {


    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        bottomNavigationView = findViewById(R.id.bttm_nav);


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragMent_host) as NavHostFragment

        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomNavigationView, navController)



    }
}
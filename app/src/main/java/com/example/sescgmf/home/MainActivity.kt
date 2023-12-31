package com.example.sescgmf.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // STATUSBAR TRANSPARENTE
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        // NAVEGACAO FRAGMENTO
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame_layout) as NavHostFragment
        navController = navHostFragment.findNavController()

        // MENU BOTAO
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }

    // Main bottomNavigationView
    fun hideBottomNavigationView() {
        // DESATIVA
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        // HABILITA
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    // Start Training bottomNavigationView
    fun hideBottomNavigationViewTraining() {
        // HABILITA
        binding.bottomNavigationView2.visibility = View.GONE
    }

    fun showBottomNavigationViewTraining() {
        // DESATIVA
        binding.bottomNavigationView2.visibility = View.VISIBLE
    }
    
}

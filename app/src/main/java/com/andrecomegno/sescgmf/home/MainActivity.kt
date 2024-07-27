package com.andrecomegno.sescgmf.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

        subscribeToTopic()
    }

    // MAIN MENU
    fun hideBottomNavigationView() {
        // DESATIVA
        binding.coordinatorLayout.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        // HABILITA
        binding.coordinatorLayout.visibility = View.VISIBLE
    }

    private fun subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("students")
            .addOnCompleteListener { task ->
                var msg = "Subscribed to topic"
                if (!task.isSuccessful) {
                    msg = "Subscription failed"
                }
                Log.d("Subscription", msg)
            }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "FCM Registration Token: $token")
        }

    }
    
}

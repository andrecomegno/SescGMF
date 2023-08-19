package com.example.sescgmf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Training : Fragment(R.layout.fragment_training)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        view.findViewById<AppCompatButton>(R.id.bt_training).setOnClickListener{
            val action = TrainingDirections.actionTrainingToHome()
            findNavController().navigate(action)
        }
    }

}
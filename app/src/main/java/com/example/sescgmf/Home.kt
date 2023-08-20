package com.example.sescgmf

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : Fragment(R.layout.fragment_home)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       view.findViewById<AppCompatButton>(R.id.bt_training).setOnClickListener{
           val action = HomeDirections.actionHomeToTraining()
           findNavController().navigate(action)
       }
    }
}
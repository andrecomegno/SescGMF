package com.example.sescgmf.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R

class Home : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        btTraining(view)
        return view
    }

    // BOTÃO TREINAR
    private fun btTraining(view: View)
    {
        view.findViewById<AppCompatButton>(R.id.bt_training).setOnClickListener{
            val action = HomeDirections.actionHomeToTraining()
            findNavController().navigate(action)
        }
    }
}


package com.example.sescgmf.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.MainActivity
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentHomeBinding

class Home : Fragment()
{
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO TREINAR
        binding.btTraining.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_training)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // HABILITA O BottomNavigationView
        (activity as MainActivity).showBottomNavigationView()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}


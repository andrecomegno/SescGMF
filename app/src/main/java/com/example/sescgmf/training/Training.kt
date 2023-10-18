package com.example.sescgmf.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentTrainingBinding
import com.example.sescgmf.home.MainActivity

class Training : Fragment(R.layout.fragment_training)
{
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO TREINO A
        binding.btTrainingA.setOnClickListener{
            findNavController().navigate(R.id.action_training_to_myTraining)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // HABILITA O BottomNavigationView
        (activity as MainActivity).showBottomNavigationView()
        // OCULTA O BottomNavigationView_2
        (activity as MainActivity).hideBottomNavigationViewTraining()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
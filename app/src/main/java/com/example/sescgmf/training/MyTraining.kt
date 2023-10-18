package com.example.sescgmf.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentMyTrainingBinding
import com.example.sescgmf.home.MainActivity

class MyTraining : Fragment()
{
    private var _binding: FragmentMyTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTrainingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO VOLTAR
        binding.btBackTraining.setOnClickListener{
            findNavController().navigate(R.id.action_myTraining_to_training)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
        // HABILITA O BottomNavigationView_2
        (activity as MainActivity).showBottomNavigationViewTraining()
    }

    private fun initRecyclerView()
    {

    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
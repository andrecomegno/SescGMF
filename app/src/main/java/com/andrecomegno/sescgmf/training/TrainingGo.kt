package com.andrecomegno.sescgmf.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrecomegno.sescgmf.databinding.FragmentTrainingGoBinding

class TrainingGo : Fragment()
{
    private var _binding: FragmentTrainingGoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingGoBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
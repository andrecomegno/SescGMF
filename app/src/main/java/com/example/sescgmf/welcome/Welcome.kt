package com.example.sescgmf.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentEventEditBinding
import com.example.sescgmf.databinding.FragmentWelcomeBinding


class Welcome : Fragment()
{
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater,container, false)
        return binding.root
    }
}
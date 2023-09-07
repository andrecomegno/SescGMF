package com.example.sescgmf.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentLessonBinding
import com.example.sescgmf.databinding.FragmentWelcomeBinding

class Login : Fragment()
{
    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLessonBinding.inflate(inflater,container, false)
        return binding.root
    }
}
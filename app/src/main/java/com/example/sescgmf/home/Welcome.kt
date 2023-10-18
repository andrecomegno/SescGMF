package com.example.sescgmf.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Welcome : Fragment()
{
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, 3000)
    }

    private fun checkAuth()
    {
        auth = Firebase.auth
        if(auth.currentUser == null)
        {
            findNavController().navigate(R.id.action_welcome_to_login)
        }
        else
        {
            findNavController().navigate(R.id.action_welcome_to_home)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
        // OCULTA O BottomNavigationView_2
        (activity as MainActivity).hideBottomNavigationViewTraining()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.sescgmf.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Profile : Fragment()
{
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO LOGOUT
        binding.btLogout.setOnClickListener{ logoutUser() }

        // BOTÃO DETELAR CONTA
        binding.btDeleteAccount.setOnClickListener{

        }
    }

    private fun logoutUser()
    {
        auth.signOut()
        findNavController().navigate(R.id.action_profile_to_authentication)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
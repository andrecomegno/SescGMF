package com.example.sescgmf.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.MainActivity
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentLoginBinding

class Login : Fragment()
{
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO LOGIN
        binding.btEnterLogin.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_home)
        }

        // BOTÃO CADASTRO
        binding.btRegister.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_newRegister)
        }

        // BOTÃO ESQUECI SENHA
        binding.btRecoverPassword.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_recoverPassword)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}


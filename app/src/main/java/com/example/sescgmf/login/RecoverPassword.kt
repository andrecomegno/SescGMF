package com.example.sescgmf.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentRecoverPasswordBinding

class RecoverPassword : Fragment()
{
    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverPasswordBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO ESQUECI SENHA
        binding.btSendRecover.setOnClickListener{
            findNavController().navigate(R.id.action_recoverPassword_to_login)
        }

        // BOTÃO VOLTAR
        binding.btBackLogin.setOnClickListener{
            findNavController().navigate(R.id.action_recoverPassword_to_login)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
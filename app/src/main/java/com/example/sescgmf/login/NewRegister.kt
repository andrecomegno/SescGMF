package com.example.sescgmf.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentNewRegisterBinding

class NewRegister : Fragment()
{
    private var _binding: FragmentNewRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewRegisterBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO CADASTRO
        binding.btNewRegister.setOnClickListener{
            findNavController().navigate(R.id.action_newRegister_to_home)
        }

        // BOTÃO VOLTAR
        binding.btBackLogin.setOnClickListener{
            findNavController().navigate(R.id.action_newRegister_to_login)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
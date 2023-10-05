package com.example.sescgmf.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentRecoverPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverPassword : Fragment()
{
    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO ESQUECI SENHA
        binding.btSendRecover.setOnClickListener{ validateDate() }

        // BOTÃO VOLTAR
        binding.btBackLogin.setOnClickListener{
            findNavController().navigate(R.id.action_recoverPassword_to_login)
        }
    }

    private fun validateDate()
    {
        val email = binding.txtRecoverEmail.text.toString().trim()

        if(email.isNotEmpty())
        {
            binding.progressBar.isVisible = true
            recoverPassword(email)
        }
        else
        {
            Toast.makeText(requireContext(), getString(R.string.lb_enter_your_email), Toast.LENGTH_SHORT).show()
        }
    }

    private fun recoverPassword(email: String)
    {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(requireContext(), getString(R.string.lb_send_email), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
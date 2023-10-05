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
import com.example.sescgmf.databinding.FragmentNewRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NewRegister : Fragment()
{
    private var _binding: FragmentNewRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO CADASTRO
        binding.btNewRegister.setOnClickListener{ validateDate() }

        // BOTÃO VOLTAR
        binding.btBackLogin.setOnClickListener{
            findNavController().navigate(R.id.action_newRegister_to_login)
        }
    }

    private fun validateDate()
    {
        val email = binding.txtCreatorEmail.text.toString().trim()
        val password = binding.txtCreatorPassword.text.toString().trim()

        if(email.isNotEmpty())
        {
            if(password.isNotEmpty())
            {
                binding.progressBar.isVisible = true
                registerUser(email,password)
            }
            else
            {
                Toast.makeText(requireContext(), getString(R.string.lb_enter_your_password), Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(requireContext(), getString(R.string.lb_enter_your_email), Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful)
                {
                    findNavController().navigate(R.id.action_newRegister_to_home)
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
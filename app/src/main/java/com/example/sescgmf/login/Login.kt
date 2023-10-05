package com.example.sescgmf.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.MainActivity
import com.example.sescgmf.R
import com.example.sescgmf.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : Fragment()
{
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks()
    {
        // BOTÃO LOGIN
        binding.btEnterLogin.setOnClickListener{ validateDate() }

        // BOTÃO CADASTRO
        binding.btRegister.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_newRegister)
        }

        // BOTÃO ESQUECI SENHA
        binding.btRecoverPassword.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_recoverPassword)
        }
    }

    private fun validateDate()
    {
        val email = binding.txtEmail.text.toString().trim()
        val password = binding.txtPassword.text.toString().trim()

        if(email.isNotEmpty())
        {
            if(password.isNotEmpty())
            {
                binding.progressBar.isActivated = true
                loginEnter(email,password)
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

    private fun loginEnter(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful)
                {
                    findNavController().navigate(R.id.action_login_to_home)
                }
                else
                {
                    binding.progressBar.isActivated = false
                }
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


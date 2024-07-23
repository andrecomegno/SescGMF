package com.andrecomegno.sescgmf.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentRecoverPasswordBinding
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.toast.ToastUtils

class RecoverPassword : Fragment() {
    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverPasswordBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks() {
        binding.btSendRecover.setOnClickListener{ validateDate() }

        binding.btBack.setOnClickListener{
            findNavController().navigate(R.id.action_recoverPassword_to_login)
        }
    }

    private fun validateDate() {
        val email = binding.txtRecoverEmail.text.toString().takeIf { it.isNotEmpty() }

        when {
            email == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_email)
            else -> {
                binding.progressBar.isVisible = true
                recoverPassword(email)
            }
        }
    }

    private fun recoverPassword(email: String) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), getString(R.string.alert_notification_sent_successfully), Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBar.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.andrecomegno.sescgmf.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentLoginBinding
import com.andrecomegno.sescgmf.helper.BaseFragment
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.home.MainActivity
import com.andrecomegno.sescgmf.message.NotificationSender.sendLogoutNotification
import com.andrecomegno.sescgmf.toast.ToastUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    private fun initClicks() {
        binding.btEnterLogin.setOnClickListener{ validateDate() }

        binding.btRegister.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_newRegister)
        }

        binding.btRecoverPassword.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_recoverPassword)
        }
    }

    private fun validateDate() {
        val email = binding.txtEmail.text.toString().takeIf { it.isNotEmpty() }
        val password = binding.txtPassword.text.toString().takeIf { it.isNotEmpty() }

        when {
            email == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_email)
            password == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_password)
            else -> {
                binding.progressBar.isVisible = true
                loginEnter(email, password)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loginEnter(email: String, password: String) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    hideKeyboard()
                    val userId = FirebaseHelper.getIdUser()

                    if (userId != null) {
                        val studentRef = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child(userId)
                        studentRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val storedToken = snapshot.child("token").getValue(String::class.java)
                                    FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
                                        if (tokenTask.isSuccessful) {
                                            val newToken = tokenTask.result

                                            GlobalScope.launch(Dispatchers.Main) {
                                                if (storedToken != null && storedToken != newToken) {
                                                    // LOGOUT DO DISPOSITIVO ANTERIOR
                                                    withContext(Dispatchers.IO) {
                                                        sendLogoutNotification(requireContext(), storedToken)
                                                    }
                                                    // APOS NOTIFICAR, FAZ A ATUALIZAÇÃO DO TOKEN DO NOVO DISPOSITIVO
                                                    studentRef.child("token").setValue(newToken).addOnCompleteListener { updateTask ->
                                                        if (updateTask.isSuccessful) {
                                                            findNavController().navigate(R.id.action_login_to_home)
                                                        } else {
                                                            Toast.makeText(
                                                                requireContext(),
                                                                getString(R.string.alert_error_save_register),
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            binding.progressBar.isVisible = false
                                                        }
                                                    }
                                                } else {
                                                    // ATUALIZA O TOKEN NORMAMENTE
                                                    studentRef.child("token").setValue(newToken).addOnCompleteListener { updateTask ->
                                                        if (updateTask.isSuccessful) {
                                                            findNavController().navigate(R.id.action_login_to_home)
                                                        } else {
                                                            Toast.makeText(
                                                                requireContext(),
                                                                getString(R.string.alert_error_save_register),
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            binding.progressBar.isVisible = false
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                getString(R.string.alert_error_permission_access),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.progressBar.isVisible = false
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.alert_error_permission_access),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    FirebaseHelper.getAuth().signOut()
                                    binding.progressBar.isVisible = false
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(
                                    requireContext(),
                                    FirebaseHelper.validError(task.exception?.message ?: ""),
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.progressBar.isVisible = false
                            }
                        })
                    } else {
                        Toast.makeText(
                            requireContext(),
                            FirebaseHelper.validError(task.exception?.message ?: ""),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.isVisible = false
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onResume() {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


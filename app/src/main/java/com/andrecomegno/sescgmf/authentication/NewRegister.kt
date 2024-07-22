package com.andrecomegno.sescgmf.authentication

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentNewRegisterBinding
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.model.DataUser
import com.andrecomegno.sescgmf.toast.ToastUtils
import com.google.firebase.messaging.FirebaseMessaging

class NewRegister : Fragment() {
    private var _binding: FragmentNewRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewRegisterBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        keyboard()
        setupKeyboardVisibilityListener()
    }

    private fun initClicks() {
        binding.btNewRegister.setOnClickListener{ validateDate() }

        binding.btBack.setOnClickListener{
            findNavController().navigate(R.id.action_newRegister_to_login)
        }
    }

    private fun validateDate() {
        val name = binding.txtCreatorUsername.text.toString().takeIf { it.isNotEmpty() }
        val email = binding.txtCreatorEmail.text.toString().takeIf { it.isNotEmpty() }
        val password = binding.txtCreatorPassword.text.toString().takeIf { it.isNotEmpty() }
        val confirmPassword = binding.txtConfirmPassword.text.toString().takeIf { it.isNotEmpty() }

        when{
            name == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_name)
            email == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_email)
            password == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_password)
            password != confirmPassword -> ToastUtils.showToast( requireContext(), R.string.alert_different_password )
            else ->{
                binding.progressBar.isVisible = true
                registerUser(name, email, password)
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful){
                    val userId = FirebaseHelper.getIdUser()
                    userId?.let {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
                            if(tokenTask.isSuccessful){
                                val token = tokenTask.result
                                val databaseReference = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child((userId))
                                val userData = DataUser(id = userId, name = name, email = email, token = token )

                                databaseReference.setValue(userData)
                                    .addOnCompleteListener{ saveTask ->
                                        if(saveTask.isSuccessful){
                                            findNavController().navigate(R.id.action_newRegister_to_home)
                                        }
                                        else{
                                            ToastUtils.showToast(requireContext(), getString(R.string.alert_error_save_register))
                                            binding.progressBar.isVisible = false
                                        }
                                    }
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.isVisible = false
                }
            }
    }

    private fun keyboard(){
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.root.getWindowVisibleDisplayFrame(r)
            val heightDiff = binding.root.height - (r.bottom - r.top)
            val isVisible = heightDiff > binding.root.height * 0.15
            binding.linearLayoutContent.fitsSystemWindows = isVisible
            if (!isVisible) {
                binding.linearLayoutContent.fitsSystemWindows = false
                binding.btNewRegister.visibility = View.VISIBLE
            }
            else{
                binding.btNewRegister.visibility = View.GONE
            }
        }
    }

    private fun setupKeyboardVisibilityListener() {
        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                scrollToFocusedEditText()
            }
        }
    }

    private fun scrollToFocusedEditText() {
        val focusedView = activity?.currentFocus
        focusedView?.let {
            val scrollY = it.top - resources.getDimensionPixelSize(R.dimen.scroll_padding)
            binding.scrollView.smoothScrollTo(0, scrollY)
        }
    }
}
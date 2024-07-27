package com.andrecomegno.sescgmf.profile

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentSettingBinding
import com.andrecomegno.sescgmf.helper.BaseFragment
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.helper.initToolbar
import com.andrecomegno.sescgmf.helper.showBottomSheet
import com.andrecomegno.sescgmf.home.MainActivity
import com.andrecomegno.sescgmf.model.DataUser
import com.andrecomegno.sescgmf.toast.ToastUtils
import com.andrecomegno.sescgmf.training.MyTrainingArgs
import com.google.firebase.auth.EmailAuthProvider

class Setting : BaseFragment() {
    private val args: SettingArgs by navArgs()
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataUser: DataUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        initToolbar(binding.toolbar)
        getArgs()
        keyboard()
        setupKeyboardVisibilityListener()
    }

    private fun getArgs(){
        args.user.let {
            if(it != null) {
                dataUser = it
                binding.txtName.setText(it.name)
                binding.txtEmail.setText(it.email)
            }
        }
    }

    private fun initClicks() {
        binding.btDeleteAccount.setOnClickListener{ validateDelete() }
        binding.btSave.setOnClickListener{ validateEdit() }
    }

    private fun validateEdit(){
        val name = binding.txtName.text.toString().takeIf { it.isNotEmpty() }
        val email = binding.txtEmail.text.toString().takeIf { it.isNotEmpty() }
        val password = binding.txtPassword.text.toString().takeIf { it.isNotEmpty() }

        when {
            name == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_name)
            email == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_email)
            else -> {
                if(dataUser.name != name || dataUser.email != email){
                    if (password == null) {
                        ToastUtils.showToast(requireContext(), R.string.alert_password_required_to_change_your_registration)
                    } else {
                        binding.progressBar.isVisible = true
                        editUser(name, email, password)
                    }
                }
                else{
                    Toast.makeText(requireContext(), getString(R.string.alert_edit_to_be_able_to_save), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateDelete(){
        val email = binding.txtEmail.text.toString().takeIf { it.isNotEmpty() }
        val password = binding.txtPassword.text.toString().takeIf { it.isNotEmpty() }

        when {
            email == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_email)
            password == null -> ToastUtils.showToast(requireContext(), R.string.alert_enter_your_password)
            else -> {
                if(email.isNotEmpty()){
                    binding.progressBar.isVisible = true
                    deleteAccount(password)
                }
                else{
                    Toast.makeText(requireContext(), getString(R.string.alert_edit_to_be_able_to_save), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun editUser(name: String, email: String, password: String) {
        val user = FirebaseHelper.getAuth().currentUser

        user?.let {
            // REAUTENTICAÇÃO DO USUARIO
            val credential = EmailAuthProvider.getCredential(user.email!!, password)
            it.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("EditUser", "Reautenticação bem-sucedida")

                        // ATUALIZAÇÃO DO EMAIL
                        user.updateEmail(email)
                            .addOnCompleteListener { updateEmailTask ->
                                if (updateEmailTask.isSuccessful) {
                                    // ATUALIZA OS DADOS NO AUTHENTICATION E NO REALTIME DATABASE
                                    val userId = FirebaseHelper.getIdUser().toString()
                                    val databaseReference = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child(userId)
                                    val userData = DataUser(id = userId, name = name, email = email, token = dataUser.token)

                                    // DEFINE OS NOVOS DADOS EDITADOS
                                    databaseReference.setValue(userData)
                                        .addOnCompleteListener { saveTask ->
                                            if (saveTask.isSuccessful) {
                                                Toast.makeText(requireContext(), getString(R.string.alert_edit_success), Toast.LENGTH_SHORT).show()
                                                binding.progressBar.isVisible = false
                                                binding.txtPassword.text.clear()
                                            } else {
                                                Toast.makeText(requireContext(), getString(R.string.alert_error_save_edit), Toast.LENGTH_SHORT).show()
                                                binding.progressBar.isVisible = false
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(requireContext(), getString(R.string.alert_error_save_edit), Toast.LENGTH_SHORT).show()
                                            binding.progressBar.isVisible = false
                                        }
                                } else {
                                    Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                                    binding.progressBar.isVisible = false
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                                binding.progressBar.isVisible = false
                            }
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                    }
                }
                .addOnFailureListener { error ->
                    Toast.makeText(requireContext(), FirebaseHelper.validError(error.message ?: ""), Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
        } ?: run {
            Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
            binding.progressBar.isVisible = false
        }
    }

    private fun deleteAccount(password: String) {
        showBottomSheet(
            titleButton = R.string.alert_confirm,
            message = R.string.alert_delete_account,
            onClick = {
                val user = FirebaseHelper.getAuth().currentUser
                user?.let {
                    val credential = EmailAuthProvider.getCredential(user.email!!, password)
                    it.reauthenticate(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                hideKeyboard()

                            // REMOVE OS DADOS DO FIREBASE REALTIME
                            val databaseReference = FirebaseHelper
                                .getDatabase()
                                .child("academy")
                                .child("gym_user")
                                .child("user_data")
                                .child(it.uid)

                            databaseReference.removeValue().addOnCompleteListener { removeTask ->
                                if (removeTask.isSuccessful) {
                                    // EXCLUIR O USUÁRIO DO FIREBASE AUTHENTICATION
                                    it.delete().addOnCompleteListener { deleteTask ->
                                        if (deleteTask.isSuccessful) {
                                            Toast.makeText(requireContext(), getString(R.string.alert_delete_success), Toast.LENGTH_SHORT).show()
                                            findNavController().navigate(R.id.action_setting_to_authentication)
                                        } else {
                                            Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                                            binding.progressBar.isVisible = false
                                        }
                                    }
                                } else {
                                    Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                                    binding.progressBar.isVisible = false
                                }
                            }
                            } else {
                                Toast.makeText(requireContext(), getString(R.string.alert_error_unexpected), Toast.LENGTH_SHORT).show()
                                binding.progressBar.isVisible = false
                            }
                        }
                }
            },
            onCancel = {
                binding.progressBar.isVisible = false
            }
        )
    }

    private fun keyboard(){
        // OBTÉM AS DIMENSÕES DA ÁREA VISÍVEL DA JANELA
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            binding.root.getWindowVisibleDisplayFrame(r)
            // CALCULA A ALTURA DA JANELA VISÍVEL
            val heightDiff = binding.root.height - (r.bottom - r.top)
            // VERIFICA SE A ALTURA DA JANELA VISÍVEL É MENOR DO QUE UM DETERMINADO LIMITE
            val isVisible = heightDiff > binding.root.height * 0.15
            // DEFINE FITSSYSTEMWINDOWS NO LAYOUT PRINCIPAL DO FRAGMENTO COM BASE NA VISIBILIDADE DO TECLADO
            binding.linearLayoutContent.fitsSystemWindows = isVisible
            // SE O TECLADO NÃO ESTIVER VISÍVEL, DEFINE FITSSYSTEMWINDOWS COMO FALSE
            if (!isVisible) {
                binding.linearLayoutContent.fitsSystemWindows = false
                binding.btSave.visibility = View.VISIBLE
            }
            else{
                binding.btSave.visibility = View.GONE
            }
        }
    }

    private fun setupKeyboardVisibilityListener() {
        // OBTÉM A RAIZ DO LAYOUT ATRAVÉS DO BINDING
        val rootView = binding.root
        // ADICIONA UM OUVINTE PARA MUDANÇAS GLOBAIS NO LAYOUT
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            // CRIA UM OBJETO RECT PARA ARMAZENAR OS LIMITES VISÍVEIS DA JANELA
            val rect = Rect()
            // OBTÉM O QUADRO VISÍVEL DA JANELA E O ARMAZENA NO OBJETO RECT
            rootView.getWindowVisibleDisplayFrame(rect)
            // OBTÉM A ALTURA TOTAL DA RAIZ DO LAYOUT
            val screenHeight = rootView.height
            // CALCULA A ALTURA DO TECLADO SUBTRAINDO A PARTE VISÍVEL DA TELA DA ALTURA TOTAL
            val keypadHeight = screenHeight - rect.bottom
            // SE A ALTURA DO TECLADO FOR MAIOR QUE 15% DA ALTURA DA TELA, O TECLADO ESTÁ VISÍVEL
            if (keypadHeight > screenHeight * 0.15) {
                scrollToFocusedEditText()
            }
        }
    }

    private fun scrollToFocusedEditText() {
        // FOCUS EDITVIEW
        val focusedView = activity?.currentFocus
        focusedView?.let {
            // CALCULA A POSIÇÃO Y PARA ROLAR, SUBTRAINDO O PADDING DE ROLAGEM DA POSIÇÃO SUPERIOR DA VIEW FOCADA
            val scrollY = it.top - resources.getDimensionPixelSize(R.dimen.scroll_padding)
            // ROLA SUAVEMENTE O SCROLLVIEW ATÉ A POSIÇÃO CALCULADA
            binding.scrollView.smoothScrollTo(0, scrollY)
        }
    }

    override fun onResume() {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
    }
}
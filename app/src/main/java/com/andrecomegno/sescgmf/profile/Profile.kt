package com.andrecomegno.sescgmf.profile

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentProfileBinding
import com.andrecomegno.sescgmf.helper.BaseFragment
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.helper.showBottomSheet
import com.andrecomegno.sescgmf.model.DataUser
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Profile : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataUser: DataUser
    private lateinit var sharedPreferences: SharedPreferences
    private var profileImagePath: String? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openImagePicker()
            }
        }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    profileImagePath = fileUri.toString()
                    binding.imageUser.setImageURI(fileUri)
                    sharedPreferences.edit().putString("profileImagePath", profileImagePath).apply()
                    binding.btAvatarDelete.isVisible = true
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImagePrefs()
        initClicks()
        loadData()
    }

    private fun initClicks() {
        binding.btLogout.setOnClickListener{ logoutUser() }

        binding.btAvatarDelete.setOnClickListener { deleteProfileImage() }

        binding.btEdit.setOnClickListener{
            val action = ProfileDirections.actionProfileToSetting(dataUser)
            findNavController().navigate(action)
        }

        binding.btAvatar.setOnClickListener {
            checkAndRequestPermission()
        }
    }

    private fun loadData() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { currentUser ->
            val userId = currentUser.uid
            val databaseReference = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child(userId)

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val student = snapshot.getValue(DataUser::class.java)
                        student?.let {
                            // INICIAR O DATASTUDENT
                            dataUser = it
                            binding.txtUser.text = it.name
                            binding.txtUsername.text = it.name
                            binding.txtUserEmail.text = it.email
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    private fun profileImagePrefs() {
        sharedPreferences = requireContext().getSharedPreferences("ProfileImagePrefs", Context.MODE_PRIVATE)
        profileImagePath = sharedPreferences.getString("profileImagePath", null)
        binding.btAvatarDelete.isVisible = false
        if (profileImagePath != null) {
            binding.imageUser.setImageURI(Uri.parse(profileImagePath))
        } else {
            binding.imageUser.setImageResource(R.drawable.im_profile)
        }
    }

    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                openImagePicker()
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .cropSquare()
            .maxResultSize(1080, 1080)
            .compress(1024)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun deleteProfileImage() {
        // VOLTA A IMAGEM PADRAO
        binding.imageUser.setImageResource(R.drawable.im_profile)

        // REMOVE CAMINHO DA IMAGEM
        sharedPreferences.edit().remove("profileImagePath").apply()
        profileImagePath = null
        binding.btAvatarDelete.isVisible = false

        Toast.makeText(requireContext(), getString(R.string.alert_image_removed), Toast.LENGTH_SHORT).show()
    }

    private fun logoutUser() {
        showBottomSheet(
            titleButton = R.string.alert_confirm,
            message = R.string.alert_logout,
            onClick = {
                FirebaseHelper.getAuth().signOut()
                findNavController().navigate(R.id.action_profile_to_authentication)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
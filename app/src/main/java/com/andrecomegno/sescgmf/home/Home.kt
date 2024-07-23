package com.andrecomegno.sescgmf.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.FragmentHomeBinding
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        readUserName()
    }

    private fun initClicks() {
        // BOTÃO TREINAR
        binding.btTraining.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_training)
        }
    }

    private fun readUserName() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = it.uid
            // CAMINHO DO USUARIO NO Realtime Database
            val databaseReference = FirebaseHelper.getDatabase().child("academy").child("gym_user").child("user_data").child(userId)

            // OUVIR MUDANÇAS NOS DADOS DO USUARIO
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // OBTER DADOS DO SNAPSHOT
                        val name = snapshot.child("name").value.toString()
                        binding.lbUsername.text = name
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigationView()

        if(!FirebaseHelper.isAuthenticated()) {
            FirebaseHelper.getAuth().signOut()
            findNavController().navigate(R.id.action_home_to_authentication)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


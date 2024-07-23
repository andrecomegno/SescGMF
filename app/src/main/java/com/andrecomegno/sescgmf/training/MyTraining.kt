package com.andrecomegno.sescgmf.training

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.adapter.VideoAdapter
import com.andrecomegno.sescgmf.databinding.FragmentMyTrainingBinding
import com.andrecomegno.sescgmf.helper.FirebaseHelper
import com.andrecomegno.sescgmf.home.MainActivity
import com.andrecomegno.sescgmf.model.DataVideo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyTraining : Fragment() {
    private var _binding: FragmentMyTrainingBinding? = null
    private val binding get() = _binding!!
    private val videoList = mutableListOf<DataVideo>()
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTrainingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        initRecyclerView()
        getVideo()
    }

    private fun initClicks() {
        // BOTÃO VOLTAR
        binding.btBack.setOnClickListener{
            findNavController().navigate(R.id.action_myTraining_to_training)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        videoAdapter = VideoAdapter(videoList)
        binding.recyclerView.adapter = videoAdapter
    }

    private fun getVideo(){
        binding.progressBar.isVisible = true
        FirebaseHelper
            .getDatabase()
            .child("academy")
            .child("videos")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        videoList.clear()
                        binding.alertDatabase.isVisible = videoList.isEmpty()
                        videoAdapter.notifyDataSetChanged()
                    }
                    else{
                        binding.alertDatabase.isVisible = true
                    }
                    binding.progressBar.isVisible = false
                }
                override fun onCancelled(error: DatabaseError) {
                    binding.progressBar.isVisible = false
                }
            })
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
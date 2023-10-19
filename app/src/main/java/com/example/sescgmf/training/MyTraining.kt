package com.example.sescgmf.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sescgmf.R
import com.example.sescgmf.adapter.TrainingAdapter
import com.example.sescgmf.databinding.FragmentMyTrainingBinding
import com.example.sescgmf.home.MainActivity

class MyTraining : Fragment()
{
    private var _binding: FragmentMyTrainingBinding? = null
    private val binding get() = _binding!!
    private lateinit var trainingAdapter: TrainingAdapter
    private val trainingList: MutableList<TrainingClasses> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTrainingBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        initRecyclerView()
    }

    private fun initClicks()
    {
        // BOTÃO VOLTAR
        binding.btBackTraining.setOnClickListener{
            findNavController().navigate(R.id.action_myTraining_to_training)
        }
    }

    override fun onResume()
    {
        super.onResume()
        // OCULTA O BottomNavigationView
        (activity as MainActivity).hideBottomNavigationView()
        // HABILITA O BottomNavigationView_2
        (activity as MainActivity).showBottomNavigationViewTraining()
    }

    // CARREGA O RECYCLERVIEW
    private fun initRecyclerView()
    {
        val recyclerViewTraining = binding.recyclerView
        recyclerViewTraining.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewTraining.setHasFixedSize(true)
        trainingAdapter = TrainingAdapter(requireActivity(), trainingList)
        recyclerViewTraining.adapter = trainingAdapter

        getTrainingClass()
    }

    // AULAS CRIADAS
    private fun getTrainingClass()
    {
        val trainingClass1 = TrainingClasses(
            imgTraining = R.drawable.im_training_01,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass1)

        val trainingClass2 = TrainingClasses(
            imgTraining = R.drawable.im_training_02,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass2)

        val trainingClass3 = TrainingClasses(
            imgTraining = R.drawable.im_training_03,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass3)

        val trainingClass4 = TrainingClasses(
            imgTraining = R.drawable.im_training_04,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass4)

        val trainingClass5 = TrainingClasses(
            imgTraining = R.drawable.im_training_01,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass5)

        val trainingClass6 = TrainingClasses(
            imgTraining = R.drawable.im_training_02,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass6)

        val trainingClass7 = TrainingClasses(
            imgTraining = R.drawable.im_training_03,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass7)

        val trainingClass8 = TrainingClasses(
            imgTraining = R.drawable.im_training_04,
            description = "Generic description, fitness training and sport exercise",
            repetitions = "3x 15a20"
        )
        trainingList.add(trainingClass8)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
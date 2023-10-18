package com.example.sescgmf.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.databinding.TrainingItemBinding
import com.example.sescgmf.training.TrainingClasses

class Adapter(private val context: Context, private val trainingList: MutableList<TrainingClasses>) :
    RecyclerView.Adapter<Adapter.TrainingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class TrainingViewHolder(binding: TrainingItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

}
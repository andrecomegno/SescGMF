package com.example.sescgmf.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.databinding.TrainingItemBinding
import com.example.sescgmf.training.TrainingClasses

class TrainingAdapter(private val context: Context, private val trainingList: MutableList<TrainingClasses>) : RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder
    {
        val listItem :TrainingItemBinding = TrainingItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return TrainingViewHolder(listItem)
    }

    override fun getItemCount() = trainingList.size

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int)
    {
        holder.imTraining.setBackgroundResource(trainingList[position].imgTraining!!)
        holder.lbDescription.text = trainingList[position].description
        holder.lbRepetitions.text = trainingList[position].repetitions
    }

    inner class TrainingViewHolder(binding: TrainingItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        val imTraining = binding.imTraining
        val lbDescription = binding.lbDescription
        val lbRepetitions = binding.lbRepetitions
    }

}
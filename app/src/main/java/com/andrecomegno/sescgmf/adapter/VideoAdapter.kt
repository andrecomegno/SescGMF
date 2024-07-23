package com.andrecomegno.sescgmf.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrecomegno.sescgmf.databinding.ItemsVideoBinding
import com.andrecomegno.sescgmf.model.DataVideo
import java.util.regex.Pattern

class VideoAdapter(
    private val videoList: List<DataVideo>,
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemsVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videos = videoList[position]

        holder.binding.title.text = videos.title
        holder.binding.number.text = videos.number
        holder.binding.reps.text = videos.reps
        //holder.binding.image.text = videos.image
    }

    override fun getItemCount() = videoList.size

    inner class VideoViewHolder(val binding: ItemsVideoBinding): RecyclerView.ViewHolder(binding.root)
}
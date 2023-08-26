package com.example.sescgmf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener) :
    RecyclerView.Adapter<CalendarViewHolder>()
{
    interface OnItemListener
    {
        fun onItemClick(position: Int, dayText: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int)
    {
        holder.dayOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount(): Int
    {
        return daysOfMonth.size
    }

}
package com.example.sescgmf.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.R
import com.example.sescgmf.event.Event
import java.time.LocalDate

class CalendarAdapter(private val days: ArrayList<LocalDate>, private val onItemListener: OnItemListener) :
RecyclerView.Adapter<CalendarViewHolder>()
{
    interface OnItemListener
    {
        fun onItemClick(position: Int, date: LocalDate?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        return CalendarViewHolder(view, onItemListener, days)
    }

    private fun hasEvent(date: LocalDate): Boolean
    {
        return Event.eventsList.any { it.date == date }
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int)
    {
        val date = days[position]
        holder.dayOfMonth.text = date.dayOfMonth.toString()

        if (date == CalendarUtils.selectedDate)
            holder.itemView.setBackgroundColor(Color.rgb(255,250,225))
        else if (hasEvent(date))
            holder.itemView.setBackgroundColor(Color.rgb(255,219,206))
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun getItemCount(): Int
    {
        return days.size
    }
}

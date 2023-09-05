package com.example.sescgmf.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.R
import java.time.LocalDate

class CalendarAdapter(private val days: ArrayList<LocalDate>, private val onItemListener: OnItemListener) :
RecyclerView.Adapter<CalendarViewHolder>()
{
    private val datesWithEvents = HashSet<LocalDate>()

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

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int)
    {
        val date = days[position]
        holder.dayOfMonth.text = date.dayOfMonth.toString()
        if (date == CalendarUtils.selectedDate) {
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getItemCount(): Int
    {
        return days.size
    }
}

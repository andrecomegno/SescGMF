package com.example.sescgmf.calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.R
import java.time.LocalDate

class CalendarViewHolder(itemView: View, private val onItemListener: CalendarAdapter.OnItemListener, private val days: ArrayList<LocalDate>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener
{
    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)

    init
    {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View)
    {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }
}
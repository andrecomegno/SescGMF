package com.example.sescgmf.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarUtils.formattedTime

class EventAdapter(context: Context, events: List<Event?>) :
    ArrayAdapter<Event?>(context, 0, events)
{
    private class ViewHolder
    {
        lateinit var eventCell: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var convertedView = convertView
        val event = getItem(position)
        val viewHolder: ViewHolder

        if (convertedView == null)
        {
            viewHolder = ViewHolder()
            convertedView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
            viewHolder.eventCell = convertedView.findViewById(R.id.eventCell)
            convertedView.tag = viewHolder
        } else
        {
            viewHolder = convertedView.tag as ViewHolder
        }

        val eventTitle = event!!.name + " " + formattedTime(event.time)
        viewHolder.eventCell.text = eventTitle

        return convertedView!!
    }
}
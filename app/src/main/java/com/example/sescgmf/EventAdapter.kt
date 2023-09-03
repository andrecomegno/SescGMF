package com.example.sescgmf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sescgmf.CalendarUtils.formattedTime


class EventAdapter(context: Context, events: List<Event?>?) :
    ArrayAdapter<Event?>(context, 0, events!!)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val event = getItem(position)
        if (convertView == null) convertView =
            LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        val eventCell = convertView!!.findViewById<TextView>(R.id.eventCell)
        val eventTitle = event!!.name + " " + formattedTime(
            event.time
        )
        eventCell.text = eventTitle
        return convertView
    }
}
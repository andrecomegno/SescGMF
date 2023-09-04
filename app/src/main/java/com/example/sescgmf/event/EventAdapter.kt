package com.example.sescgmf.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarUtils.formattedTime

class EventAdapter(context: Context, events: List<Event?>) :
    ArrayAdapter<Event?>(context, 0, events)
{
    private class ViewHolder
    {
        lateinit var cardView: CardView
        lateinit var eventName: TextView
        lateinit var eventTime: TextView
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
            viewHolder.cardView = convertedView.findViewById(R.id.card_view)
            viewHolder.eventName = convertedView.findViewById(R.id.event_name)
            viewHolder.eventTime = convertedView.findViewById(R.id.event_time)
            convertedView.tag = viewHolder
        } else
        {
            viewHolder = convertedView.tag as ViewHolder
        }

        // CARDVIEW COM OS ATRIBUTOS DO EVENTO
        viewHolder.eventName.text = event!!.name
        viewHolder.eventTime.text = formattedTime(event.time)

        return convertedView!!
    }
}
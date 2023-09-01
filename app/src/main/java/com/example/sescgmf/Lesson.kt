package com.example.sescgmf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.CalendarUtils.daysInWeekArray
import com.example.sescgmf.CalendarUtils.monthYearFromDate
import com.example.sescgmf.CalendarUtils.selectedDate
import java.time.LocalDate

class Lesson : Fragment(), CalendarAdapter.OnItemListener
{
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lesson, container, false)
        initWidgets(view)
        return view
    }

    private fun initWidgets(view: View)
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.tile_month)

        btPreviousMonthAction(view)
        btNextMonthAction(view)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
        setWeekView()
    }

    private fun setWeekView()
    {
        monthYearText.text = monthYearFromDate(selectedDate ?: LocalDate.now())
        val days = daysInWeekArray(selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        //setEventAdapter()
    }

    override fun onItemClick(position: Int, date: LocalDate?)
    {
        selectedDate = date
        setWeekView()
    }

    /*
        override fun onResume()
        {
            super.onResume()
            //setEventAdapter()
        }


        private fun setEventAdapter()
        {
            val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
            val eventAdapter = EventAdapter(requireContext(), dailyEvents)
            eventListView.adapter = eventAdapter
        }


        fun newEventAction(view: View)
        {
            //startActivity(Intent(requireContext(), EventEditActivity::class.java))
        }
         */

    private fun btPreviousMonthAction(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_month_left)
        button.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.minusMonths(1)
                selectedDate = newDate
                setWeekView()
            }
        }
    }

    private fun btNextMonthAction(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_month_right)
        button.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.plusMonths(1)
                selectedDate = newDate
                setWeekView()
            }
        }
    }



}
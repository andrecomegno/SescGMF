package com.example.sescgmf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.CalendarUtils.daysInWeekArray
import com.example.sescgmf.CalendarUtils.monthYearFromDate
import java.time.LocalDate

class WeekViewActivity : Fragment(), CalendarAdapter.OnItemListener
{
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var eventListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_week_view_activity, container, false)
        initWidgets(view)
        return view
    }

    private fun initWidgets(view: View)
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.calendar_month)
        //eventListView = view.findViewById(R.id.eventListView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setWeekView()
    }

    private fun setWeekView()
    {
        monthYearText.text = monthYearFromDate(CalendarUtils.selectedDate ?: LocalDate.now())
        val days = daysInWeekArray(CalendarUtils.selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        setEventAdapter()
    }

    fun previousWeekAction(view: View)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate?.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekAction(view: View)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate?.plusWeeks(1)
        setWeekView()
    }

    override fun onItemClick(position: Int, date: LocalDate?)
    {
        CalendarUtils.selectedDate = date
        setWeekView()
    }

    override fun onResume()
    {
        super.onResume()
        setEventAdapter()
    }

    private fun setEventAdapter()
    {
        //val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
        //val eventAdapter = EventAdapter(requireContext(), dailyEvents)
        //eventListView.adapter = eventAdapter
    }

    fun newEventAction(view: View)
    {
        //startActivity(Intent(requireContext(), EventEditActivity::class.java))
    }

}
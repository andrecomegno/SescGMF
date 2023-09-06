package com.example.sescgmf.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarAdapter
import com.example.sescgmf.calendar.CalendarUtils.daysInWeekArray
import com.example.sescgmf.calendar.CalendarUtils.monthYearFromDate
import com.example.sescgmf.calendar.CalendarUtils.selectedDate
import com.example.sescgmf.event.Event
import com.example.sescgmf.event.EventAdapter
import java.time.LocalDate

class Lesson : Fragment(), CalendarAdapter.OnItemListener
{
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_lesson, container, false)
        initWidgets(view)
        return view
    }

    private fun initWidgets(view: View)
    {
        // CALENDARIO
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.title_month)

        // BOTÕES
        btPreviousWeek(view)
        btNextWeek(view)
        btEvent(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
        setWeekView()
    }

    // CALENDARIO POR SEMANA
    private fun setWeekView()
    {
        // CALENDARIO SEMANAS
        monthYearText.text = monthYearFromDate(selectedDate ?: LocalDate.now())
        val days = daysInWeekArray(selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        setEventAdapter()
    }

    override fun onItemClick(position: Int, date: LocalDate?)
    {
        selectedDate = date
        setWeekView()
    }

    override fun onResume()
    {
        super.onResume()
        setEventAdapter()
    }

    // LISTA DE EVENTOS
    private fun setEventAdapter()
    {
        val eventListView: ListView = requireView().findViewById(R.id.eventListView)
        val dailyEvents = Event.eventsForDate(selectedDate?: LocalDate.now())
        val eventAdapter = EventAdapter(requireContext(), dailyEvents)
        eventListView.adapter = eventAdapter
    }

    // BOTÃO CALENDARIO VOLTAR SEMANA
    private fun btPreviousWeek(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_week_left)
        button.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.minusWeeks(1)
                selectedDate = newDate
                setWeekView()
            }
        }
    }

    // BOTÃO CALENDARIO PROXIMA SEMANA
    private fun btNextWeek(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_week_right)
        button.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.plusWeeks(1)
                selectedDate = newDate
                setWeekView()
            }
        }
    }

    // BOTÃO NOVO EVENTO
    private fun btEvent(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_new_event)
        button.setOnClickListener{
            val action = LessonDirections.actionLessonToEventEdit()
            findNavController().navigate(action)
        }
    }

}
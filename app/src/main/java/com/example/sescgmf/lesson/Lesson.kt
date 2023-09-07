package com.example.sescgmf.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarAdapter
import com.example.sescgmf.calendar.CalendarUtils.daysInWeekArray
import com.example.sescgmf.calendar.CalendarUtils.monthYearFromDate
import com.example.sescgmf.calendar.CalendarUtils.selectedDate
import com.example.sescgmf.databinding.FragmentLessonBinding
import com.example.sescgmf.event.Event
import com.example.sescgmf.event.EventAdapter
import java.time.LocalDate

class Lesson : Fragment(), CalendarAdapter.OnItemListener
{
    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentLessonBinding.inflate(inflater,container, false)
        initWidgets()
        return binding.root
    }

    private fun initWidgets()
    {
        // CALENDARIO
        calendarRecyclerView = binding.calendarRecyclerView
        monthYearText = binding.titleMonth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
        setWeekView()
        initClicks()
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

    private fun initClicks()
    {
        // BOTÃO CALENDARIO VOLTAR SEMANA
        binding.btWeekLeft.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.minusWeeks(1)
                selectedDate = newDate
                setWeekView()
            }
        }

        // BOTÃO CALENDARIO PROXIMA SEMANA
        binding.btWeekRight.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.plusWeeks(1)
                selectedDate = newDate
                setWeekView()
            }
        }

        // BOTÃO NOVO EVENTO
        binding.btNewEvent.setOnClickListener{
            findNavController().navigate(R.id.action_lesson_to_eventEdit)
        }
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

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
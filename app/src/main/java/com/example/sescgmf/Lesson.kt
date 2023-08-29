package com.example.sescgmf

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sescgmf.CalendarUtils.daysInMonthArray
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
        monthYearText = view.findViewById(R.id.calendar_month)

        btPreviousMonthAction(view)
        btNextMonthAction(view)
        weeklyAction(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
        setMonthView()
    }

    private fun setMonthView()
    {
        val selectedDate = selectedDate ?: LocalDate.now()
        monthYearText.text = monthYearFromDate(selectedDate)

        val daysInMonthNullable = daysInMonthArray(selectedDate)
        val daysInMonth = daysInMonthNullable.filterNotNull()

        val calendarAdapter = CalendarAdapter(ArrayList(daysInMonth), this)

        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(requireContext(), 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    override fun onItemClick(position: Int, date: LocalDate?)
    {
        selectedDate = date
        setMonthView()
    }

    private fun weeklyAction(view: View)
    {
        view.findViewById<AppCompatButton>(R.id.bt_weekly).setOnClickListener{
            val action = LessonDirections.actionLessonToWeekViewActivity()
            findNavController().navigate(action)
        }
    }

    private fun btPreviousMonthAction(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_month_left)
        button.setOnClickListener {
            selectedDate?.let { nonNullSelectedDate ->
                val newDate = nonNullSelectedDate.minusMonths(1)
                selectedDate = newDate
                setMonthView()
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
                setMonthView()
            }
        }
    }

}
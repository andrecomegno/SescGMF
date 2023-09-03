package com.example.sescgmf.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarUtils.formattedDate
import com.example.sescgmf.calendar.CalendarUtils.selectedDate
import java.time.LocalDate
import java.time.LocalTime

class EventEdit : Fragment()
{
    private var time : LocalTime = LocalTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_event_edit, container, false)
        eventViewDate(view)
        btBackLesson(view)
        btSaveEvent(view)
        return view
    }

    // BOTÃO SALVAR NOVO EVENTO
    private fun btSaveEvent(view: View)
    {
        // ID EDITTEXT
        val txtEventName = view.findViewById<EditText>(R.id.txt_event_name)
        // ID BOTAO SALVAR
        val button: Button = view.findViewById(R.id.bt_save_event)

        button.setOnClickListener{
            // PEGA O QUE FOI DIGITADO
            val eventName: String = txtEventName.text.toString()

            // VERIFICACAO SE ESTA EM BRANCO
            if(txtEventName.text.isNotBlank())
            {
                // ATRIBUIU AO NOVO EVENTO NOME, DATA E HORA
                val newEvent = Event( eventName, selectedDate?: LocalDate.now(), time )
                // TROCA DE FRAGMENT EVENTEDIT PARA LESSON
                val action = EventEditDirections.actionEventEditToLesson()

                // FRAGMENT LESSON
                findNavController().navigate(action)
                // CRIA UM NOVO EVENTO NO LESSON
                Event.eventsList.add(newEvent)
            }
            else
            {
                // ERRO ALERTA
                txtEventName.error = getString(R.string.enter_name_event)
            }
        }
    }

    // BOTAO VOLTAR
    private fun btBackLesson(view: View)
    {
        val button: Button = view.findViewById(R.id.bt_back_lesson)
        button.setOnClickListener{
            val action = EventEditDirections.actionEventEditToLesson()
            findNavController().navigate(action)
        }
    }

    // MOSTRA A DATA SELECIONADA NA TELA
    private fun eventViewDate(view: View)
    {
        val eventDate: TextView = view.findViewById(R.id.event_date)
        eventDate.text = formattedDate(selectedDate?: LocalDate.now())
    }

}
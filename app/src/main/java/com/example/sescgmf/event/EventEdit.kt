package com.example.sescgmf.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sescgmf.R
import com.example.sescgmf.calendar.CalendarUtils.formattedDate
import com.example.sescgmf.calendar.CalendarUtils.selectedDate
import com.example.sescgmf.databinding.FragmentEventEditBinding
import java.time.LocalDate
import java.time.LocalTime

class EventEdit : Fragment()
{
    private var _binding: FragmentEventEditBinding? = null
    private val binding get() = _binding!!
    private var time : LocalTime = LocalTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentEventEditBinding.inflate(inflater,container, false)
        initClicks()
        return binding.root
    }

    private fun initClicks()
    {
        // BOTÃO SALVAR NOVO EVENTO
        binding.btSaveEvent.setOnClickListener{
            // PEGA O QUE FOI DIGITADO
            val eventName: String = binding.txtEventName.text.toString()

            // VERIFICACAO SE ESTA EM BRANCO
            if(binding.txtEventName.text.isNotBlank())
            {
                // ATRIBUIU AO NOVO EVENTO NOME, DATA E HORA
                val newEvent = Event( eventName, selectedDate?: LocalDate.now(), time )
                // VOLTAR PARA O FRAGMENT ANTERIOR
                findNavController().popBackStack()
                // CRIA UM NOVO EVENTO NO LESSON
                Event.eventsList.add(newEvent)
            }
            else
            {
                // ERRO ALERTA
                binding.txtEventName.error = getString(R.string.lb_enter_name_event)
            }
        }

        // BOTAO VOLTAR
        binding.btBackLesson.setOnClickListener{
            findNavController().navigate(R.id.action_eventEdit_to_lesson)
        }

        eventViewDate()
    }

    // MOSTRA A DATA SELECIONADA NA TELA
    private fun eventViewDate()
    {
        val eventDate: TextView =  binding.eventDate
        eventDate.text = formattedDate(selectedDate?: LocalDate.now())
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
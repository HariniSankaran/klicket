package com.example.civil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.date_picker.*
import java.util.*

class DatePickerDialog(val delegate : ISelectedDate) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.date_picker,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        submitButton.setOnClickListener {
            delegate.selectedDate(Date(datePicker.year,datePicker.month,  datePicker.dayOfMonth).time)
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
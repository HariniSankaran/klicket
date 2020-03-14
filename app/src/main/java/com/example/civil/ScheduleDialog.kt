package com.example.civil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.civil.ObjectBox.ScheduleModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleDialog(val delegate: IDateScheduler) : DialogFragment() {

    private lateinit var startCalendarLayout: ConstraintLayout
    private lateinit var endCalendarLayout: ConstraintLayout
    private lateinit var startDate: TextView
    private lateinit var endDate: TextView
    private lateinit var datePicker: DatePicker
    private var setStartDate: Boolean = true
    private lateinit var submitButton: Button
    private lateinit var backButton : ImageView
    private lateinit var detailReport : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.schedule_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startCalendarLayout = view.findViewById(R.id.startDateLayout)
        endCalendarLayout = view.findViewById(R.id.endDateLayout)
        startDate = view.findViewById(R.id.selectedStartDate)
        endDate = view.findViewById(R.id.selectedEndDate)
        datePicker = view.findViewById(R.id.datePicker)
        submitButton = view.findViewById(R.id.submitButton)
        backButton = view.findViewById(R.id.backButton)
        detailReport = view.findViewById(R.id.detailReport)
        setUpOnClickListener()
        super.onViewCreated(view, savedInstanceState)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view) {
            startCalendarLayout -> showDatePicker(true)
            endCalendarLayout -> showDatePicker(false)
            submitButton -> validateData()
            backButton -> dialog.dismiss()
        }
    }

    private fun validateData() {
        if (startDate.text == getString(R.string.select_date) || endDate.text == getString(R.string.select_date)) {
            Toast.makeText(context, "Select a Date", Toast.LENGTH_SHORT).show()
        } else {
            val dateSchedule = ScheduleModel()
            dateSchedule.startDate = startDate.text.toString()
            dateSchedule.endDate = endDate.text.toString()
            dateSchedule.detail = detailReport.text.toString()
            delegate.newDateScheduler(dateSchedule)
            dialog.dismiss()
        }
    }

    private fun showDatePicker(isStart: Boolean) {
        setStartDate = isStart
        if (isStart) {
            startCalendarLayout.background = resources.getDrawable(R.color.lightBlue)
            endCalendarLayout.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
        } else {
            endCalendarLayout.background = resources.getDrawable(R.color.lightBlue)
            startCalendarLayout.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
        }
        datePicker.visibility = View.VISIBLE
    }

    private fun setUpOnClickListener() {
        startCalendarLayout.setOnClickListener(onClickListener)
        endCalendarLayout.setOnClickListener(onClickListener)
        submitButton.setOnClickListener(onClickListener)
        setUpDatePicker()
    }

    private fun setUpDatePicker(){
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) { datePicker, year, month, dayOfMonth ->
            val dateFormatter = SimpleDateFormat("MM-dd-yyyy")
            val d = Date(year - 1900, month, dayOfMonth)
            if (setStartDate) {
                startDate.text = dateFormatter.format(d)

            } else {
                endDate.text = dateFormatter.format(d)
            }
        }
    }
}
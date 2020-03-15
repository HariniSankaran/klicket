package com.example.civil;

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ReportModel
import com.example.civil.ObjectBox.ReportModel_
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_onclickviewreport.*

class viewReportSummary : AppCompatActivity(),ISelectedDate {

    private lateinit var reportBox : Box<ReportModel>
    private var projectId : Long = 0L
    private lateinit var reportModel: List<ReportModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onclickviewreport)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()
        reportBox.query().equal(ReportModel_.projectId,projectId).build().findFirst()!!

        choseDate.setOnClickListener {
            val dialog = DatePickerDialog(this)
            dialog.show(supportFragmentManager,"datePicker")
        }
    }

    override fun selectedDate(date: Long) {
        date
//        reportModel = reportBox.all
    }
}
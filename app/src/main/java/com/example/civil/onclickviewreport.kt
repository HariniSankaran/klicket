package com.example.civil;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ReportModel
import com.example.civil.ObjectBox.ReportModel_
import com.example.civil.Util.Constants
import com.example.civil.Util.Utils
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_onclickviewreport.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import java.io.File.separator
import android.os.Environment.getExternalStorageDirectory
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File


class ViewReportSummary : AppCompatActivity(),ISelectedDate {

    private lateinit var reportBox : Box<ReportModel>
    private var projectId : Long = 0L
    private lateinit var reportModel: List<ReportModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onclickviewreport)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()
        reportModel = reportBox.query().equal(ReportModel_.projectId,projectId).build().find()

        choseDate.setOnClickListener {
            val dialog = DatePickerDialog(this)
            dialog.show(supportFragmentManager,"datePicker")
        }

        doneButton.setOnClickListener {
            startActivity(Intent(this, NewPage::class.java))
        }
    }

    override fun selectedDate(date: Long) {
        val reports = reportModel.filter { it.selectedDate == date }
        if(reports.isEmpty()){
            viewReportList.visibility = View.GONE
            noReports.visibility = View.VISIBLE
        }else {
            noReports.visibility = View.GONE
            viewReportList.adapter = ViewReportListAdapter(baseContext,reportModel.sortedByDescending { it.id })
            viewReportList.visibility = View.VISIBLE
        }
    }
}

class ViewReportListAdapter(ctx : Context, private val reportList : List<ReportModel>) : ArrayAdapter<ReportModel>(ctx,R.layout.view_report_list,reportList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_report_list,parent,false)

        val labour = view.findViewById<TextView>(R.id.labourExpense)
        val material = view.findViewById<TextView>(R.id.materialExpense)
        val total = view.findViewById<TextView>(R.id.totalExpense)
        val viewReport = view.findViewById<TextView>(R.id.viewReport)

        labour.text = "Labour Expense : ${reportList[position].Sum}"
        material.text = "Material Expense : ${reportList[position].sumTotal}"
        total.text = "Total Expense : ${reportList[position].totalExpense}"

        viewReport.setOnClickListener {
            val filePath = Utils.createHtmlFile(reportList[position])
            val uri2 = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", File(filePath))
            val intent = Intent(Intent.ACTION_VIEW, uri2)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        return view
    }
}
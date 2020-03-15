package com.example.civil;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.Util.Constants
import kotlinx.android.synthetic.main.activity_viewreport.*

class ViewReport : AppCompatActivity() {

    lateinit var addReport: Button
    private var projectId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewreport)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        addReport = findViewById(R.id.addReport)

        addReport.setOnClickListener {
            val intent = Intent(this,Report::class.java)
            intent.putExtra(Constants.PROJECT_ID, projectId.toString())
            startActivity(intent)
        }

        viewReport.setOnClickListener {
            val intent = Intent(this,viewReportSummary::class.java)
            intent.putExtra(Constants.PROJECT_ID, projectId.toString())
            startActivity(intent)
        }
    }

}

package com.example.civil;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle
import com.example.civil.ObjectBox.*
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_finishproject.*

class FinishProject : AppCompatActivity(){

    private lateinit var reportBox : Box<ReportModel>
    private lateinit var projectBox : Box<ProjectModel>
    private var projectId : Long = 0L
    private lateinit var reportModel: List<ReportModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finishproject)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()
        projectBox = ObjectBox.boxStore.boxFor()
        reportModel = reportBox.query().equal(ReportModel_.projectId,projectId).build().find()

        appValue.text = reportModel.sumByDouble { it.Sum + it.sumTotal }.toString()
        completed.setOnClickListener{
            projectBox.query().equal(ProjectModel_.id,projectId).build().remove()
            reportBox.query().equal(ReportModel_.projectId,projectId).build().remove()
            startActivity(Intent(this,NewPage::class.java))
        }

    }
}

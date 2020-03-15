package com.example.civil;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ProjectModel
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class NewProjectDetail : AppCompatActivity() {

    lateinit var nextButton : Button
    lateinit var ownerName : EditText
    lateinit var siteLocation : EditText
    lateinit var emailId : EditText
    lateinit var mobileNumber : EditText
    lateinit var areaOfPlot : EditText
    lateinit var unit : EditText
    lateinit var projectBox : Box<ProjectModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newprojectdetail)
        setUpUI()
        projectBox = ObjectBox.boxStore.boxFor()
        nextButton.setOnClickListener {
            if(ownerName.text.toString() != "" && siteLocation.text.toString() != "") {
                val newProject = ProjectModel()
                newProject.ownerName = ownerName.text.toString()
                newProject.siteLocation = siteLocation.text.toString()
                newProject.emailId = emailId.text.toString()
                newProject.mobileNumber = mobileNumber.text.toString()
                newProject.areaPlot = areaOfPlot.text.toString()
                newProject.unit = unit.text.toString()
                projectBox.put(newProject)
                val intent = Intent(this, Schedule::class.java)
                intent.putExtra(Constants.PROJECT_ID, newProject.id.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this.applicationContext,"Provide Details",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpUI(){
        nextButton = findViewById(R.id.nextButton)
        ownerName = findViewById(R.id.et_ownername)
        siteLocation = findViewById(R.id.et_sitelocation)
        emailId = findViewById(R.id.et_owneremail)
        mobileNumber = findViewById(R.id.et_ownernumber)
        areaOfPlot = findViewById(R.id.et_area)
        unit = findViewById(R.id.et_unit)
    }

}
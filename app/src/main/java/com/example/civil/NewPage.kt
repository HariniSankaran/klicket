package com.example.civil;

import android.content.Context
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ListView
import android.widget.TextView
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ProjectModel
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.BoxStore.context
import io.objectbox.kotlin.boxFor

class NewPage : AppCompatActivity(),IProjectSelected{

    private lateinit var newProjectButton : Button
    private lateinit var projectListView : ListView
    private lateinit var projectBox : Box<ProjectModel>
    private lateinit var listAdapter: ProjectListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpage)
        newProjectButton = findViewById(R.id.btn_newproject)
        projectListView = findViewById(R.id.projectList)
        projectBox = ObjectBox.boxStore.boxFor()

        newProjectButton.setOnClickListener{ view ->
            startActivity(Intent(this,NewProjectDetail::class.java))
        }
    }

    override fun onResume() {
        setUpListView()
        super.onResume()
    }

    private fun setUpListView(){
        listAdapter = ProjectListAdapter(this,projectBox.all,this)
        projectListView.adapter = listAdapter
    }

    override fun selectedProject(projectId: Long) {
        val intent = Intent(this, Schedule::class.java)
        intent.putExtra(Constants.PROJECT_ID, projectId.toString())
        startActivity(intent)
    }
}

class ProjectListAdapter(context : Context, private val projectList: List<ProjectModel>, private val delegate : IProjectSelected) :ArrayAdapter<ProjectModel>(context,R.layout.project_list_card,projectList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.project_list_card,parent,false)

        val name = view.findViewById<TextView>(R.id.et_name)
        val location = view.findViewById<TextView>(R.id.et_location)

        name.text = "Name  : " + projectList[position].ownerName
        location.text= "Location : " + projectList[position].siteLocation

        view.setOnClickListener { view ->
            delegate.selectedProject(projectList[position].id)
        }
        return view
    }

}

package com.example.civil;


import android.os.Bundle;
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ScheduleModel
import com.example.civil.ObjectBox.ScheduleModel_
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class Schedule : AppCompatActivity(),IDateScheduler {

    lateinit var addButton : ImageView
    lateinit var scheduleRecyclerView : RecyclerView
    private lateinit var  listAdapter : ScheduleListAdapter
    private lateinit var scheduleBox : Box<ScheduleModel>
    private var projectId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        scheduleBox = ObjectBox.boxStore.boxFor()
        addButton = findViewById(R.id.addButton)
        scheduleRecyclerView = findViewById(R.id.scheduleList)
        addButton.setOnClickListener(clickListener)
    }

    override fun onResume() {
        super.onResume()
        setUpList()
    }

    private fun setUpList(){
        scheduleRecyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        listAdapter = ScheduleListAdapter(getScheduleList())
        scheduleRecyclerView.adapter = listAdapter
    }

    private val clickListener = View.OnClickListener { view ->
        when(view){
            addButton -> openScheduleCalendarDialog()
        }
    }

    override fun newDateScheduler(schedule: ScheduleModel) {
        schedule.projectId = projectId
        scheduleBox.put(schedule)
        listAdapter.updateList(getScheduleList())
        listAdapter.notifyItemInserted(scheduleBox.count().toInt())
        listAdapter.notifyItemRangeInserted(scheduleBox.count().toInt(),scheduleBox.count().toInt())
        listAdapter.notifyDataSetChanged()
    }

    private fun openScheduleCalendarDialog(){
        val dialog = ScheduleDialog(this)
        dialog.show(supportFragmentManager,"scheduleDialog")
    }

    private fun getScheduleList() : List<ScheduleModel>{
        return scheduleBox.query().equal(ScheduleModel_.projectId, projectId).build().find()
    }
}

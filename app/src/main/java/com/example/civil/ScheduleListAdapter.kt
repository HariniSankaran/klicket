package com.example.civil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.civil.ObjectBox.ScheduleModel

class ScheduleListAdapter(private var scheduleList : List<ScheduleModel>) : RecyclerView.Adapter<ScheduleListViewHolder>() {

    fun updateList(scheduleList: List<ScheduleModel>){
        this.scheduleList = scheduleList
    }
    override fun getItemCount(): Int = scheduleList.size

    override fun onBindViewHolder(holder: ScheduleListViewHolder, position: Int) {
        holder.bindView(scheduleList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleListViewHolder {
        return ScheduleListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.schedule_list_card,parent,false))
    }
}


class ScheduleListViewHolder(view : View) : RecyclerView.ViewHolder(view){

    var startDate = view.findViewById(R.id.startDate) as TextView
    var endDate = view.findViewById(R.id.endDate) as TextView
    var details = view.findViewById(R.id.details) as TextView
    
    fun bindView( schedule : ScheduleModel){
        startDate.text = schedule.startDate
        endDate.text = schedule.endDate
        if(schedule.detail != ""){
            details.text = "Details : " + schedule.detail
            details.visibility = View.VISIBLE
        }
    }
}
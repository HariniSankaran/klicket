package com.example.civil

import com.example.civil.ObjectBox.ScheduleModel
import java.io.FileDescriptor

class DateSchedule{
     var startDate : String = ""
     var endDate : String = ""
     var description : String = ""
}

interface IDateScheduler{
     fun newDateScheduler(schedule: ScheduleModel)
}

interface IProjectSelected{
     fun selectedProject(projectId : Long)
}
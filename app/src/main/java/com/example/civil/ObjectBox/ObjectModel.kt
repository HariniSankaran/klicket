package com.example.civil.ObjectBox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Uid

@Entity
data class ScheduleModel(@Id var id  : Long = 0L,
                         var projectId : Long = 0L,
                         var startDate : String = "",
                         var endDate : String = "",
                         var detail: String = "")

@Entity
data class ProjectModel(@Id var id : Long = 0L,
                        var ownerName : String = "",
                        var siteLocation : String = "",
                        var emailId : String? = "",
                        @Uid(3980140657724044662L) var mobileNumber : String? = "",
                        var areaPlot : String? = "",
                        var unit : String? = "")
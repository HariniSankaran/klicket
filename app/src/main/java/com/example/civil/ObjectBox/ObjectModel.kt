package com.example.civil.ObjectBox

import android.hardware.camera2.TotalCaptureResult
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

@Entity
data class ReportModel(@Id var id : Long = 0L,
                        var projectId : Long = 0L,
                        var svNo : Long = 0L,
                        var snWage : Double = 0.0,
                       var svTotal : Double = 0.0,

                       var coolieCount : Long = 0L,
                       var Cooliewage : Double = 0.0,
                       var CoolieTotal : Double = 0.0,

                       var bbCount : Long = 0L,
                       var bbWage : Double = 0.0,
                       var bbTotal : Double = 0.0,

                       var eeCount : Long = 0L,
                       var eeWage : Double = 0.0,
                       var eeTotal : Double = 0.0,

                       var carCount : Long = 0L,
                       var carWage : Double = 0.0,
                       var carTotal : Double = 0.0,

                       var otherTotal : Double = 0.0,

                       var sumTotal : Double = 0.0,

                       var CementQty : Double = 0.0,
                       var CementRate : Double = 0.0,
                       var CementTotal : Double = 0.0,

                       var GravelQty : Double = 0.0,
                       var GravelRate : Double = 0.0,
                       var GravelTotal : Double = 0.0,

                       var waterQty : Double = 0.0,
                       var WaterRate : Double = 0.0,
                       var WaterTotal : Double = 0.0,

                       var RiQty : Double = 0.0,
                       var RiRate : Double = 0.0,
                       var RiTotal : Double = 0.0,

                       var BrickQty : Double = 0.0,
                       var BrickRate : Double = 0.0,
                       var BrickTotal : Double = 0.0,

                       var Other : Double = 0.0,
                       var Sum : Double = 0.0,
                       var totalExpense : String = "",
                       var nextPlan : String = "",
                       var reportDate : String = "",
                       var selectedDate : Long = 0L

)
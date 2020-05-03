package com.example.civil.Util

import android.os.Environment
import android.provider.Settings.Global.getString
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ReportModel
import com.example.civil.R
import java.io.File
import java.io.PrintWriter

object Utils {

     fun createHtmlFile(reportModel: ReportModel) : String {
        val htmlData = constructHtml(reportModel)

        val storageDirectory = File(Environment.getExternalStorageDirectory().toString() + "/Klicket")
        var success = true
        if (!storageDirectory.exists()) {
            success = storageDirectory.mkdir()
        }
        if (success) {
            val dest = File(storageDirectory, "report.html")
            if (dest.exists()) {
                dest.delete()
                PrintWriter(dest).use { out -> out.println(htmlData) }
            } else {
                try {
                    PrintWriter(dest).use { out -> out.println(htmlData) }
                } catch (e: Exception) {
                    // handle the exception
                }
            }
        }
        return "${Environment.getExternalStorageDirectory()}/Klicket/report.html"
    }

    private fun constructHtml(reportModel : ReportModel) : String {
        return  "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  width:100%;\n" +
                "}\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "  padding: 15px;\n" +
                "  text-align: left;\n" +
                "}\n" +
                "table tr:nth-child(even) {\n" +
                "  background-color: #eee;\n" +
                "}\n" +
                "table tr:nth-child(odd) {\n" +
                " background-color: #fff;\n" +
                "}\n" +
                "table th {\n" +
                "  background-color: black;\n" +
                "  color: white;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<img width=\"300px\" height=\"300px\" src ='${reportModel.imageLocalPath}'>" +
                "\n" +
                "\n" +
                "<h2>Labour Table</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <th>Role</th>\n" +
                "    <th>Numbers</th> \n" +
                "    <th>Wage</th>\n" +
                "    <th>Total</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Supervisor</td>\n" +
                "    <td>${reportModel.svNo}</td>\n" +
                "    <td>${reportModel.snWage}</td>\n" +
                "    <td>${reportModel.svTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Coolie</td>\n" +
                "    <td>${reportModel.coolieCount}</td>\n" +
                "    <td>${reportModel.Cooliewage}</td>\n" +
                "    <td>${reportModel.CoolieTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Bar Bender</td>\n" +
                "    <td>${reportModel.bbCount}</td>\n" +
                "    <td>${reportModel.bbWage}</td>\n" +
                "    <td>${reportModel.bbTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Electrician</td>\n" +
                "    <td>${reportModel.eeCount}</td>\n" +
                "    <td>${reportModel.eeWage}</td>\n" +
                "    <td>${reportModel.eeTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Carpenter</td>\n" +
                "    <td>${reportModel.carCount}</td>\n" +
                "    <td>${reportModel.carWage}</td>\n" +
                "    <td>${reportModel.carTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Others</td>\n" +
                "    <td></td>\n" +
                "    <td></td>\n" +
                "    <td>${reportModel.otherTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total</td>\n" +
                "    <td></td>\n" +
                "    <td></td>\n" +
                "    <td>${reportModel.sumTotal}</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "<br/><br/>\n" +
                "\n" +
                "<h2>Material Table</h2>\n" +
                "\n" +
                "<table>\n" +
                "  <tr>\n" +
                "    <th>Item</th>\n" +
                "    <th>Qty</th> \n" +
                "    <th>Rate</th>\n" +
                "    <th>Total</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Cement</td>\n" +
                "    <td>${reportModel.CementQty}</td>\n" +
                "    <td>${reportModel.CementRate}</td>\n" +
                "    <td>${reportModel.CementTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Gravel</td>\n" +
                "    <td>${reportModel.GravelQty}</td>\n" +
                "    <td>${reportModel.GravelRate}</td>\n" +
                "    <td>${reportModel.GravelTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Water</td>\n" +
                "    <td>${reportModel.waterQty}</td>\n" +
                "    <td>${reportModel.WaterRate}</td>\n" +
                "    <td>${reportModel.WaterTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>RI Bars</td>\n" +
                "    <td>${reportModel.RiQty}</td>\n" +
                "    <td>${reportModel.RiRate}</td>\n" +
                "    <td>${reportModel.RiTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Brick</td>\n" +
                "    <td>${reportModel.BrickQty}</td>\n" +
                "    <td>${reportModel.BrickRate}</td>\n" +
                "    <td>${reportModel.BrickTotal}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Others</td>\n" +
                "    <td></td>\n" +
                "    <td></td>\n" +
                "    <td>${reportModel.Other}</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Total</td>\n" +
                "    <td></td>\n" +
                "    <td></td>\n" +
                "    <td>${reportModel.Sum}</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n"
    }
}
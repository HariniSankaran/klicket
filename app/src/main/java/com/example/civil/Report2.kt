package com.example.civil;

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ReportModel
import com.example.civil.ObjectBox.ReportModel_
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_report2.*

class Report2 : AppCompatActivity() {

    private lateinit var reportBox : Box<ReportModel>
    private var reportId : Long = 0L
    private lateinit var reportModel: ReportModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report2)
        reportId = intent.getStringExtra(Constants.REPORT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()
        reportModel = reportBox.query().equal(ReportModel_.id,reportId).build().findFirst()!!

        textChangeListener()
        nextButton.setOnClickListener {
            saveAndNextActivity()
        }
    }

    private fun saveAndNextActivity(){
        reportModel.CementQty = checkDoubleNumber(cementqty.text.toString())
        reportModel.CementRate = checkDoubleNumber(cementrate.text.toString())
        reportModel.CementTotal = checkDoubleNumber(cementtotal.text.toString())

        reportModel.GravelQty = checkDoubleNumber(gravelqty.text.toString())
        reportModel.GravelRate = checkDoubleNumber(gravelrate.text.toString())
        reportModel.GravelTotal = checkDoubleNumber(graveltotal.text.toString())

        reportModel.waterQty = checkDoubleNumber(waterqty.text.toString())
        reportModel.WaterRate = checkDoubleNumber(waterrate.text.toString())
        reportModel.WaterTotal = checkDoubleNumber(watertotal.text.toString())

        reportModel.RiQty = checkDoubleNumber(riqty.text.toString())
        reportModel.RiRate = checkDoubleNumber(rirate.text.toString())
        reportModel.RiTotal = checkDoubleNumber(ritotal.text.toString())

        reportModel.BrickQty = checkDoubleNumber(brickqty.text.toString())
        reportModel.BrickRate = checkDoubleNumber(brickrate.text.toString())
        reportModel.BrickTotal = checkDoubleNumber(bricktotal.text.toString())

        reportModel.otherTotal = checkDoubleNumber(othertotal.text.toString())

        reportModel.sumTotal = checkDoubleNumber(totalsum.text.toString())

        reportBox.put(reportModel)
        val intent = Intent(this,ReportSubmit::class.java)
        intent.putExtra(Constants.REPORT_ID, reportId.toString())
        startActivity(intent)
    }

    private fun checkLongNumber(number : String) : Long {
        return if(number == "")
            0L
        else
            number.toLong()
    }


    private fun checkDoubleNumber(number : String) : Double {
        return if(number == "")
            0.0
        else
            number.toDouble()
    }

    private fun textChangeListener(){
        cementqty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            changeTotal("cement")
            }
        })

        cementrate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("cement")
            }
        })


        gravelqty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("gravel")
            }
        })

        gravelrate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("gravel")
            }
        })

        waterqty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("water")
            }
        })

        waterrate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("water")
            }
        })


        riqty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("ri")
            }
        })

        rirate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("ri")
            }
        })

        brickqty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("brick")
            }
        })

        brickrate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("brick")
            }
        })


        othertotal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeTotal("others")
            }
        })
    }

    private fun changeTotal(item : String){
        if(item == "cement"){
            if(cementqty.text.toString() != "" && cementrate.text.toString() != ""){
                cementtotal.setText((cementqty.text.toString().toDouble() * cementrate.text.toString().toDouble()).toString())
            }else {
                cementtotal.setText("")
            }
        }else if(item == "gravel"){
            if(gravelqty.text.toString() != "" && gravelrate.text.toString() != ""){
                graveltotal.setText((gravelrate.text.toString().toDouble() * gravelqty.text.toString().toDouble()).toString())
            }else {
                graveltotal.setText("")
            }
        }else if(item == "water"){
            if(waterqty.text.toString() != "" && waterrate.text.toString() != ""){
                watertotal.setText((waterqty.text.toString().toDouble() * waterrate.text.toString().toDouble()).toString())
            }else {
                watertotal.setText("")
            }
        }else if(item == "ri"){
            if(riqty.text.toString() != "" && rirate.text.toString() != ""){
                ritotal.setText((rirate.text.toString().toDouble() * riqty.text.toString().toDouble()).toString())
            }else {
                ritotal.setText("")
            }
        }else if(item == "brick"){
            if(brickqty.text.toString() != "" && brickrate.text.toString() != ""){
                bricktotal.setText((brickrate.text.toString().toDouble() * brickqty.text.toString().toDouble()).toString())
            }else {
                bricktotal.setText("")
            }
        }
        updateTotal()
    }

    private fun updateTotal(){
        val cement = if(cementtotal.text.toString() != "") cementtotal.text.toString().toDouble() else 0.0
        val gravel = if(graveltotal.text.toString() != "") graveltotal.text.toString().toDouble() else 0.0
        val water = if(watertotal.text.toString() != "") watertotal.text.toString().toDouble() else 0.0
        val ri = if(ritotal.text.toString() != "") ritotal.text.toString().toDouble() else 0.0
        val brick = if(bricktotal.text.toString() != "") bricktotal.text.toString().toDouble() else 0.0
        val others = if(othertotal.text.toString() != "") othertotal.text.toString().toDouble() else 0.0
        totalsum.setText((cement+gravel+water+ri+brick+others).toString())
    }
}

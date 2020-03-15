package com.example.civil;

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ReportModel
import com.example.civil.ObjectBox.ScheduleModel
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_report2.*
import kotlinx.android.synthetic.main.activity_report2.othertotal

class Report : AppCompatActivity() {

    private lateinit var svno : EditText
    private lateinit var svwage : EditText
    private lateinit var svtotal : EditText


    private lateinit var coolieno : EditText
    private lateinit var cooliewage : EditText
    private lateinit var coolietotal : EditText

    private lateinit var bbcount : EditText
    private lateinit var bbwage : EditText
    private lateinit var bbtotal : EditText

    private lateinit var eecount : EditText
    private lateinit var eewage : EditText
    private lateinit var eetotal : EditText

    private lateinit var carcount : EditText
    private lateinit var carwage : EditText
    private lateinit var cartotal : EditText

    private lateinit var othersTotal : EditText
    private lateinit var sumtotal : TextView
    private lateinit var next : TextView
    private var projectId: Long = 0L
    private lateinit var reportBox : Box<ReportModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        projectId = intent.getStringExtra(Constants.PROJECT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()


        svno = findViewById(R.id.svno)
        svwage = findViewById(R.id.svwage)
        svtotal = findViewById(R.id.svtotal)

        coolieno = findViewById(R.id.coolieno)
        cooliewage = findViewById(R.id.cooliewage)
        coolietotal = findViewById(R.id.coolietotal)

        bbcount = findViewById(R.id.bbcount)
        bbwage = findViewById(R.id.bbwage)
        bbtotal = findViewById(R.id.bbtotal)

        eecount = findViewById(R.id.eecount)
        eewage = findViewById(R.id.eewage)
        eetotal = findViewById(R.id.eetotal)

        carcount = findViewById(R.id.carcount)
        carwage = findViewById(R.id.carwage)
        cartotal = findViewById(R.id.cartotal)

        othersTotal = findViewById(R.id.othertotal)
        sumtotal = findViewById(R.id.sumtotal)
        next = findViewById(R.id.nextButton)

        next.setOnClickListener {
            saveAndStartActivity()
        }
        textChangeListener()
    }

    private fun textChangeListener(){
        svno.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("sv")
            }
        })

        svwage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("sv")
            }
        })

        coolieno.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("coolie")
            }
        })

        cooliewage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("coolie")
            }
        })

        bbcount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("bb")
            }
        })

        bbwage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("bb")
            }
        })

        eecount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("ee")
            }
        })

        eewage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("ee")
            }
        })

        carcount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("car")
            }
        })

        carwage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("car")
            }
        })

        othersTotal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                changeTotal("others")
            }
        })
    }

    fun changeTotal(role : String){
        if(role == "sv"){
            if(svno.text.toString() != "" && svwage.text.toString() != ""){
                svtotal.setText((svno.text.toString().toLong() * svwage.text.toString().toDouble()).toString())
            }else {
                svtotal.setText("")
            }
        }else if(role == "coolie"){
            if(coolieno.text.toString() != "" && cooliewage.text.toString() != ""){
                coolietotal.setText((coolieno.text.toString().toLong() * cooliewage.text.toString().toDouble()).toString())
            } else {
                coolietotal.setText("")
            }
        }else if(role == "bb"){
            if(bbcount.text.toString() != "" && bbwage.text.toString() != ""){
                bbtotal.setText((bbcount.text.toString().toLong() * bbwage.text.toString().toDouble()).toString())
            } else{
                bbtotal.setText("")
            }
        }else if(role == "ee"){
            if(eecount.text.toString() != "" && eewage.text.toString() != ""){
                eetotal.setText((eecount.text.toString().toLong() * eewage.text.toString().toDouble()).toString())
            }else {
                eetotal.setText("")
            }
        }else if(role == "car") {
            if (carcount.text.toString() != "" && carwage.text.toString() != "") {
                cartotal.setText((carcount.text.toString().toLong() * carwage.text.toString().toDouble()).toString())
            } else {
                cartotal.setText("")
            }
        }
        calculateTotal()
    }

    private fun calculateTotal(){
        val svTotal = if(svtotal.text.toString() != "") svtotal.text.toString().toDouble() else 0.0
        val coolieTotal = if(coolietotal.text.toString() != "") coolietotal.text.toString().toDouble() else 0.0
        val bbTotal = if(bbtotal.text.toString() != "") bbtotal.text.toString().toDouble() else 0.0
        val eeTotal = if(eetotal.text.toString() != "") eetotal.text.toString().toDouble() else 0.0
        val carTotal = if(cartotal.text.toString() != "") cartotal.text.toString().toDouble() else 0.0
        val otherTotal = if(othersTotal.text.toString() != "") othersTotal.text.toString().toDouble() else 0.0
        sumtotal.text = (svTotal+coolieTotal+bbTotal+eeTotal+carTotal+otherTotal).toString()
    }


    private fun saveAndStartActivity(){
        val newReport = ReportModel()
        newReport.projectId = projectId
        newReport.svNo = checkLongNumber(svno.text.toString())
        newReport.snWage = checkDoubleNumber(svwage.text.toString())
        newReport.svTotal= checkDoubleNumber(svtotal.text.toString())

        newReport.coolieCount = checkLongNumber(coolieno.text.toString())
        newReport.Cooliewage = checkDoubleNumber(cooliewage.text.toString())
        newReport.CoolieTotal = checkDoubleNumber(coolietotal.text.toString())

        newReport.bbCount = checkLongNumber(bbcount.text.toString())
        newReport.bbWage = checkDoubleNumber(bbwage.text.toString())
        newReport.bbTotal = checkDoubleNumber(bbtotal.text.toString())

        newReport.eeCount = checkLongNumber(eecount.text.toString())
        newReport.eeWage = checkDoubleNumber(eewage.text.toString())
        newReport.eeTotal = checkDoubleNumber(eetotal.text.toString())

        newReport.carCount = checkLongNumber(carcount.text.toString())
        newReport.carWage = checkDoubleNumber(carwage.text.toString())
        newReport.carTotal = checkDoubleNumber(cartotal.text.toString())

        newReport.otherTotal = checkDoubleNumber(othersTotal.text.toString())

        newReport.Sum = checkDoubleNumber(sumtotal.text.toString())

        reportBox.put(newReport)
        val intent = Intent(this,Report2::class.java)
        intent.putExtra(Constants.REPORT_ID, newReport.id.toString())
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
}

package com.example.civil;

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.civil.ObjectBox.ObjectBox
import com.example.civil.ObjectBox.ProjectModel
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_newprojectdetail.*
import java.io.File


class NewProjectDetail : AppCompatActivity() {

    lateinit var projectBox : Box<ProjectModel>
    private var selectPdf = 7
    private val requestPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newprojectdetail)
        projectBox = ObjectBox.boxStore.boxFor()

        pdfIcon.setOnClickListener(clickListener)
        removePdf.setOnClickListener(clickListener)
        nextButton.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view ->
        when(view){
            removePdf -> {
                selectedPdf.text = ""
                selectedPdfVisibility(false)
            }
            nextButton -> saveDetails()
            pdfIcon -> chosePdf()
        }
    }

    private fun chosePdf(){
        if(checkPermission()) {
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select a PDF "), selectPdf)
        }
    }

    private fun saveDetails(){
        if(et_ownername.text.toString() != "" && et_sitelocation.text.toString() != "") {
            val newProject = ProjectModel()
            newProject.ownerName = et_ownername.text.toString()
            newProject.siteLocation = et_sitelocation.text.toString()
            newProject.emailId = et_owneremail.text.toString()
            newProject.mobileNumber = et_ownernumber.text.toString()
            newProject.areaPlot = et_area.text.toString()
            newProject.unit = et_unit.text.toString()
            newProject.pdfName = selectedPdf.text.toString()
            projectBox.put(newProject)
            val intent = Intent(this, Schedule::class.java)
            intent.putExtra(Constants.PROJECT_ID, newProject.id.toString())
            startActivity(intent)
        } else {
            Toast.makeText(this.applicationContext,"Provide Details",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(): Boolean {
        val permissionDenied = requestPermission.any { permission ->
            ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED
        }
        return if (permissionDenied) {
            showAlertToGivePermission()
            false
        } else {
            true
        }
    }

    private fun showAlertToGivePermission(){
        val listener = DialogInterface.OnClickListener { dialog, id ->
            startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
        }
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.grant_access)
                .setCancelable(false)
                .setPositiveButton("Settings", listener)
                .setNegativeButton("Not now"){dialog, id ->
                    dialog.dismiss()
                }
                .show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == selectPdf){
                val uri = data!!.data
                val uriString = uri.toString()
                val file = File(uriString)
                if(uriString.startsWith("content://")){
                    var cursor: Cursor? = null
                    try {
                        cursor =this.contentResolver.query(uri!!, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            selectedPdfName(cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)))
                        }
                    } finally {
                        cursor!!.close()
                    }

                }else if(uriString.startsWith("file://")){
                    selectedPdfName(file.name)
                }
            }
        }
    }

    private fun selectedPdfVisibility(visibility: Boolean){
        if(visibility){
            selectedPdf.visibility = View.VISIBLE
            removePdf.visibility = View.VISIBLE
            pdfIcon.visibility = View.GONE
            uploadText.visibility = View.GONE
        } else {
            selectedPdf.visibility = View.GONE
            removePdf.visibility = View.GONE
            pdfIcon.visibility = View.VISIBLE
            uploadText.visibility = View.VISIBLE
        }
    }

    private fun selectedPdfName (string: String){
        selectedPdf.text = string
        selectedPdfVisibility(true)
    }

}
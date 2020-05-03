package com.example.civil;

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.civil.ObjectBox.*
import com.example.civil.Util.Constants
import com.example.civil.Util.Utils
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_reportsubmit.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class ReportSubmit : AppCompatActivity() {

    private lateinit var reportBox: Box<ReportModel>
    private lateinit var projectBox: Box<ProjectModel>
    private var reportId: Long = 0L
    private lateinit var reportModel: ReportModel
    private lateinit var projectModal: ProjectModel
    private var selectedDate: Long = 0L
    private var imageFile: File? = null
    private var cameraRequestCode = 101
    private var imageBase64: String = ""
    private var mCurrentPhotoPath: String? = null

    private val requestPermission = arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val permissionRequestCode = 108
    private val emailRequestCode = 103


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportsubmit)
        reportId = intent.getStringExtra(Constants.REPORT_ID)?.toLong() ?: 0L
        reportBox = ObjectBox.boxStore.boxFor()
        projectBox = ObjectBox.boxStore.boxFor()
        reportModel = reportBox.query().equal(ReportModel_.id, reportId).build().findFirst()!!
        projectModal = projectBox.query().equal(ProjectModel_.id, reportModel.projectId).build().findFirst()!!
        totalExpense.setText((reportModel.Sum + reportModel.sumTotal).toString())

        val cal = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        selectedDate = Date(cal.year+1900, cal.month, cal.date).time
        val formattedDate = df.format(cal)
        dateText.setText(formattedDate)

        submitButton.setOnClickListener {
            if(imageBase64 != ""){
                saveAndMoveToNextPage()
            } else {
                Toast.makeText(baseContext,"Capture the image",Toast.LENGTH_SHORT).show()
            }
        }

        captureImage.setOnClickListener {
            takePicture()
        }
    }

    private fun takePicture(){
        if(checkPermission()){
            startCamera()
        }
    }

    private fun startCamera(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1)
            startActivityForResult(takePictureIntent, cameraRequestCode)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == permissionRequestCode){
            if(grantResults.all { it == 0 }){
                startCamera()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? { // Create an image file name
        val timeStamp = SimpleDateFormat("ddMMyyyy_hhmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
        )
        mCurrentPhotoPath = imageFile!!.absolutePath
        return imageFile
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            cameraRequestCode -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val extras = data!!.extras
                        val imageBitmap = extras!!["data"] as Bitmap?
                        captureImage.setImageBitmap(imageBitmap!!)
                        captureImage.setPadding(2, 2, 2, 2)
                        saveImage(imageBitmap)
                    }
                }
            }
            emailRequestCode -> {
                        startActivity(Intent(this, NewPage::class.java))
            }
        }
    }

    private fun saveImage(bitmapImage: Bitmap) {
        var file: File? = null
        try {
            file = createImageFile()
        } catch (ex: IOException) { // Error occurred while creating the File
            Toast.makeText(this, "Error occurred while creating the File", Toast.LENGTH_SHORT)
                    .show()
        }
        if (file != null) {
            try {
                val out = FileOutputStream(file)
                val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
                val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, out)
                imageBase64 = "data:image/png;base64, ${getBase64String(scaled)}"
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getBase64String(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        Log.e("getBase64String", imageString.replace("\n", ""))
        return imageString
    }


    private fun saveAndMoveToNextPage() {
        reportModel.totalExpense = totalExpense.text.toString()
        reportModel.reportDate = dateText.text.toString()
        reportModel.nextPlan = nextProjectPlan.text.toString()
        reportModel.selectedDate = selectedDate
        reportModel.imageLocalPath = imageBase64
        reportBox.put(reportModel)
        sendMail(Utils.createHtmlFile(reportModel))

    }

    private fun sendMail(filePath : String){
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                File(filePath))
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/html"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(projectModal.emailId))
        i.putExtra(Intent.EXTRA_SUBJECT, "report")
        i.putExtra(Intent.EXTRA_STREAM, uri)
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        i.putExtra(Intent.EXTRA_TEXT, "Dear ${projectModal.ownerName} ,\n" +
                "Please find the attachment for today's report")
        try {
            startActivityForResult(Intent.createChooser(i, "Send mail..."),emailRequestCode)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
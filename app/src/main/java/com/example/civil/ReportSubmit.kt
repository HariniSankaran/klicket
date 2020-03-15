package com.example.civil;

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.civil.ObjectBox.*
import com.example.civil.Util.Constants
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_reportsubmit.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReportSubmit : AppCompatActivity() {

    private lateinit var reportBox: Box<ReportModel>
    private lateinit var projectBox : Box<ProjectModel>
    private var reportId: Long = 0L
    private lateinit var reportModel: ReportModel
    private lateinit var projectModal : ProjectModel
    private var selectedDate: Long = 0L
    private var CAMERA_REQUEST = 1888
    private var imageBase64 : String = ""
    private var mCurrentPhotoPath : String? =null

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
        selectedDate = Date(cal.year, cal.month, cal.day).time
        val formattedDate = df.format(cal)
        dateText.setText(formattedDate)

        submitButton.setOnClickListener {
            saveAndMoveToNextPage()
        }

        captureImage.setOnClickListener {


            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//             Create the File where the photo should go
            var photoFile : File? =null
            try {
                photoFile = createImageFile();
            } catch (e : java.lang.Exception){

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                var photoURI : Uri? = null
                if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)) {
                    photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                            photoFile);
                    //FAApplication.setPhotoUri(photoURI);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }

                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, 101);

//
//                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////            photoURI = FileProvider.getUriForFile(baseContext,
////                    "com.example.civil",
////                    photoFile);
//                val file = File(Environment.getExternalStorageDirectory().toString(), "test.jpeg")
//                val outputFileUri = Uri.fromFile(file)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }
    }

     fun createImageFile(): File? {
         // Create an image file name
         val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
         val imageFileName = "JPEG_" + timeStamp + "_"
         val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)


         /*File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + File.separator
                + imageFileName);
        if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
//            mCurrentPhotoPath = file.getAbsolutePath();
        }*/



         // Save a file: path for use with ACTION_VIEW intents
         val file = File.createTempFile(
                 imageFileName,  //prefix
                 ".jpg",  //suffix
                 storageDir //directory
         )
         mCurrentPhotoPath = file.absolutePath
         return file
     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
//            val photo = data!!.extras!!.get("data") as Bitmap
//            captureImage.setImageBitmap(photo)
//            imageBase64 = "data:image/jpeg:base64,"+encodeImage(photo)!!
//            Log.d("base",imageBase64)
//        }

        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            val file = File(Environment.getExternalStorageDirectory().toString(), "test.jpeg")

        }
        if (requestCode === 101) {
            val contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", File(mCurrentPhotoPath)) //You wll get the proper image uri here.

//            val outputFileUri = Uri.fromFile(contentUri)
            val imageUri: Uri = contentUri
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage = encodeImage(selectedImage)
            captureImage.setImageBitmap(selectedImage)
            Log.d("base",encodedImage)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun encodeImage(bm: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
//        val baos = ByteArrayOutputStream()
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val b = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }


    private fun saveAndMoveToNextPage() {
        reportModel.totalExpense = totalExpense.text.toString()
        reportModel.reportDate = dateText.text.toString()
        reportModel.nextPlan = nextProjectPlan.text.toString()
        reportModel.selectedDate = selectedDate
        reportBox.put(reportModel)
        startActivity(Intent(this, NewPage::class.java))


        val main = "<!DOCTYPE html>\n" +
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
                "<h2>Labour Table</h2>\n" +
                "\n" +
                "\n" +
                "<img width=\"100px\" height=\"100px\" src ='${imageBase64}'>"+
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
                "    <td>${reportModel.carCount }</td>\n" +
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
                "<br/><br/>\n"+
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
                "    <td>${reportModel.BrickQty }</td>\n" +
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

        val sd_main = File(Environment.getExternalStorageDirectory().toString()+"/report")
        var success = true
        if (!sd_main.exists()) {
            success = sd_main.mkdir()
        }
        if (success) {
                val dest = File(sd_main, "report.html")
                if(dest.exists()){
                    dest.delete()
                    try {
                        // response is the data written to file
                        PrintWriter(dest).use { out -> out.println(main) }
                    } catch (e: Exception) {
                        // handle the exception
                    }
                }else {
                    try {
                        // response is the data written to file
                        PrintWriter(dest).use { out -> out.println(main) }
                    } catch (e: Exception) {
                        // handle the exception
                    }
                }
            }
        val list : ArrayList<String>  = arrayListOf()
        list.add(Environment.getExternalStorageDirectory().toString()+"/report/report.html")
        val fileUris: ArrayList<Uri> = ArrayList<Uri>()
        for (file in list) {
            val fileIn = File(file)
            val uri: Uri = Uri.fromFile(fileIn)
            fileUris.add(uri)
        }
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                File(Environment.getExternalStorageDirectory().toString()+"/report/report.html"))


        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/html"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(projectModal.emailId))
        i.putExtra(Intent.EXTRA_SUBJECT, "report")
        i.putExtra(Intent.EXTRA_STREAM,uri)
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        i.putExtra(Intent.EXTRA_TEXT, "Dear ${projectModal.ownerName} ,\n" +
                "Please find the attachment for today's report")

        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
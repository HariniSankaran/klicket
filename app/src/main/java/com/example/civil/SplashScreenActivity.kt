package com.example.civil;

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class SplashScreenActivity : AppCompatActivity() {

    private val requestPermission = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionRequestCode = 108

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_fullscreen)
        checkPermission()
        super.onCreate(savedInstanceState)
    }

    private fun checkPermission(){
        val permissionGranted = requestPermission.all { permision ->
            ActivityCompat.checkSelfPermission(this,permision) == PackageManager.PERMISSION_GRANTED
        }
        if(!permissionGranted){
            ActivityCompat.requestPermissions(this,requestPermission,permissionRequestCode)
        } else {
            Handler().postDelayed({
                startLoginActivity()
            },2000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == permissionRequestCode){
            startLoginActivity()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startLoginActivity(){
        startActivity(Intent(this,MainActivity::class.java))
    }
}
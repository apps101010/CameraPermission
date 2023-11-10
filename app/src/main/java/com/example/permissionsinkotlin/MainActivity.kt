package com.example.permissionsinkotlin

import android.Manifest
import android.app.Dialog

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
            if (isGranted){
                Toast.makeText(this,"Permission granted for camera",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted){
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission granted for location",Toast.LENGTH_SHORT).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission granted for Coarse location",Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this,"Permission granted for camera",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission not granted for location",Toast.LENGTH_SHORT).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission not granted for Coarse location",Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this,"Permission not granted for camera",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private val dialog: Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPermission: Button = findViewById(R.id.permissionbtn)

        btnPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                 showRationaleDialog("Permission Demo requires camera access","Camera cannot be used because Camera access is denied")
            }else{
                cameraAndLocationResultLauncher.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    private fun showRationaleDialog(title: String, message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message)
            .setPositiveButton("Cancel"){dialog, _->
                dialog.dismiss()
            }
        builder.create().show()
    }

}
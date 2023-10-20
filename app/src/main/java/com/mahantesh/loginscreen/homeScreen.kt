package com.mahantesh.loginscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.mahantesh.loginscreen.BarCodeScanner.BarCodeAndQrCodeScannerActivity
import com.mahantesh.loginscreen.NetWorkCheck.NetWorkCheckActivity
import com.mahantesh.loginscreen.MapModule.PhotoCaptureWithCoordinates.PhotoCaptureActivity

class homeScreen : AppCompatActivity() {


    lateinit var barCodeScanner: AppCompatButton;
    lateinit var networkCheck: AppCompatButton;
    lateinit var photoCaptureActivity: AppCompatButton;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        barCodeScanner = findViewById(R.id.barCodeScanner);
        networkCheck = findViewById(R.id.netWorkCheck);
        photoCaptureActivity = findViewById(R.id.photoCaptureActivity);



        barCodeScanner.setOnClickListener {
            startActivity(Intent(this, BarCodeAndQrCodeScannerActivity::class.java));
        }

        networkCheck.setOnClickListener {
            startActivity(Intent(this, NetWorkCheckActivity::class.java));
        }


        photoCaptureActivity.setOnClickListener {
            startActivity(Intent(this, PhotoCaptureActivity::class.java))
        }

    }
}
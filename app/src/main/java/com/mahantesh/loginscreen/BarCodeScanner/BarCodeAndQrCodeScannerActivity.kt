package com.mahantesh.loginscreen.BarCodeScanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.mahantesh.loginscreen.databinding.ActivityBarCodeAndQrCodeScannerBinding;
import java.io.IOException

class BarCodeAndQrCodeScannerActivity : AppCompatActivity() {


    private  var camaraPermissionCode = 1001;
    private lateinit var camaraResouce : CameraSource
    private lateinit var barcodeDetector: BarcodeDetector;
    private var scannedValue = ""
    private lateinit var binding: ActivityBarCodeAndQrCodeScannerBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_bar_code_and_qr_code_scanner)

        binding = ActivityBarCodeAndQrCodeScannerBinding.inflate(layoutInflater)

        setContentView(binding.root);

        setupControls();

    }

    private fun setupControls() {
        barcodeDetector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        camaraResouce = CameraSource.Builder(this,barcodeDetector)
            .setRequestedPreviewSize(1920,1080)
            //.setAutoFocusEnabled(true)
            .build();

        runOnUiThread {
            println("insdie bar code ||||||||||||||||||||||||||||||||||")
            binding.camaraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    try {
                        //Start preview after 1s delay

                        if (ActivityCompat.checkSelfPermission(
                                this@BarCodeAndQrCodeScannerActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {

                            askForPermission();
                        }else{
                            println("iside coma")
                            camaraResouce.start(holder)
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                    try {
                        if (ActivityCompat.checkSelfPermission(
                                this@BarCodeAndQrCodeScannerActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            askForPermission()
                        }
//                        askForPermission()
                        camaraResouce.start(holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    camaraResouce.stop()
                }

            })
        }




      barcodeDetector.setProcessor(object : Detector.Processor<Barcode>{
          override fun release() {
              Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                  .show()
          }

          override fun receiveDetections(detections: Detector.Detections<Barcode>) {
              val barcodes = detections.detectedItems
              if (barcodes.size() == 1) {
                  scannedValue = barcodes.valueAt(0).rawValue


                  //Don't forget to add this line printing value or finishing activity must run on main thread
                  runOnUiThread {
                      camaraResouce.stop()
                      Toast.makeText(
                          this@BarCodeAndQrCodeScannerActivity,
                          "value- $scannedValue",
                          Toast.LENGTH_SHORT
                      ).show()
                      finish()
                  }
              }else {
                      Toast.makeText(this@BarCodeAndQrCodeScannerActivity, "value- else", Toast.LENGTH_SHORT).show()

              }
          }

      })



    }


    override fun onResume() {
        super.onResume()
        setupControls();
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(
            this@BarCodeAndQrCodeScannerActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            camaraPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == camaraPermissionCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        camaraResouce.stop()
    }
}
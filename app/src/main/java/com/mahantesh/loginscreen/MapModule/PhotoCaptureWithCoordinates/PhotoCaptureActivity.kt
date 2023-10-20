package com.mahantesh.loginscreen.MapModule.PhotoCaptureWithCoordinates

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.mahantesh.loginscreen.R
import com.mahantesh.loginscreen.databinding.ActivityPhotoCaptureBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PhotoCaptureActivity : AppCompatActivity() {

    private var REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var read_file_permission = 112
    private var write_file_storage_permission = 113;

    private lateinit var binding: ActivityPhotoCaptureBinding;


    private var TAG: String = "PHOTO ACTIVITY"
    private lateinit var imageCapture: ImageCapture;

    private lateinit var cameraExecutor: ExecutorService


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var latitude: Double = 0.0;
    private var longitude: Double = 0.0;


    private var timeStamp: Long = 0;


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timeStamp = System.currentTimeMillis()

        binding = ActivityPhotoCaptureBinding.inflate(layoutInflater);

        setContentView(binding.root)
        requestPermissions();
        writeFilePermission();
        //  askPermissionForLocation()
        read_file_permission();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
            setMinUpdateDistanceMeters(0.1F)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let {

                    for (location in it.locations) {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            }
        }


        cameraExecutor = Executors.newSingleThreadExecutor()




        binding.startCamaraButton.setOnClickListener {

            binding.ivCapture.visibility = View.GONE
            binding.viewFinder.visibility = View.VISIBLE
            startCamara();

        }


        binding.takePhotoButton.setOnClickListener {
            takePhoto();
        }

    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {


            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback, Looper.getMainLooper()
            )

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun onPause() {
        super.onPause()
        cameraExecutor.shutdown()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun startCamara() {

        val camaraResourceFuture = ProcessCameraProvider.getInstance(this);

        camaraResourceFuture.addListener({
            val camaraProvider: ProcessCameraProvider = camaraResourceFuture.get();

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }




            imageCapture = ImageCapture.Builder().build()
            val camaraSelctor = CameraSelector.DEFAULT_BACK_CAMERA;

            try {
                camaraProvider.unbindAll();

                camaraProvider.bindToLifecycle(this, camaraSelctor, preview, imageCapture);


            } catch (exe: java.lang.Exception) {
                Log.e(TAG, "startCamara: " + exe)
            }
        }, ContextCompat.getMainExecutor(this));

    }


    private fun getOutputFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return File(mediaDir, "${timeStamp}.jpg")
    }


    private fun formatLatitudeLongitude(value: Double): String {
        val absValue = kotlin.math.abs(value)
        val degrees = kotlin.math.floor(absValue.toDouble()).toInt()
        val minutesDecimal = (absValue - degrees) * 60
        val minutes = kotlin.math.floor(minutesDecimal.toDouble()).toInt()
        val seconds = (minutesDecimal - minutes) * 60

        return "$degrees/1,$minutes/1,$seconds/1"
    }


    private fun saveImageToMediaStoreLegacy(imageFile: File) {
        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageToMediaStoreQ(imageFile: File) {
        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${timeStamp}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera")
        }


        binding.viewFinder.visibility = View.GONE
        binding.ivCapture.visibility = View.VISIBLE
        binding.ivCapture.setImageURI(imageFile.absolutePath.toUri());







        try {

            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            val outputStream = uri?.let { resolver.openOutputStream(it) }
            val filePath = uri?.let { getFilePathFromContentUri(applicationContext, it) }

            val file = filePath?.let { File(it) }

            if (file != null) {
                println("file exits" + file.exists())
            }





            outputStream?.use {
                val inputStream = imageFile.inputStream()

                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888

                val bitmap = BitmapFactory.decodeFile(imageFile.path, options)
                if (bitmap == null) {
                    Log.e(TAG, "Failed to decode image file: $imageFile.path")
                }


                val workingBitmap: Bitmap = Bitmap.createBitmap(bitmap)
                val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)
                val canvas = Canvas(mutableBitmap)

                val dateText =
                    SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.forLanguageTag("en-IN"))
                        .format(System.currentTimeMillis())


                val paint = Paint().apply {
                    color = Color.WHITE
                    textSize = 70f
                    isAntiAlias = true
                    style = Paint.Style.FILL
                }

                canvas.drawBitmap(bitmap, 0f, 0f, paint)
                val textWidth = paint.measureText(dateText)
                val textX = textWidth
                val textY = bitmap.height - 100f

                canvas.drawText(
                    "$dateText\n   latitue:$latitude \n  logitude: $longitude",
                    textX,
                    textY,
                    paint
                )

                mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                val exif = filePath?.let { ExifInterface(it) }
                exif?.setAttribute(
                    ExifInterface.TAG_GPS_LATITUDE,
                    formatLatitudeLongitude(latitude)
                )
                exif?.setAttribute(
                    ExifInterface.TAG_GPS_LONGITUDE,
                    formatLatitudeLongitude(longitude)
                )
                exif?.setAttribute(
                    ExifInterface.TAG_GPS_LATITUDE_REF,
                    if (latitude >= 0) "N" else "S"
                )
                exif?.setAttribute(
                    ExifInterface.TAG_GPS_LONGITUDE_REF,
                    if (longitude >= 0) "E" else "W"
                )

                exif?.saveAttributes()

                inputStream.copyTo(it)
//                it.flush()
                inputStream.close()
            }
            Toast.makeText(this, "Stored in $filePath", Toast.LENGTH_LONG).show()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        imageFile.delete() // Optionally, delete the original file if you don't need it anymore.
    }

    private fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val resolver: ContentResolver = context.contentResolver
        val cursor = resolver.query(contentUri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val dataIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(dataIndex)
            }
        }
        return null
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun takePhoto() {

        val name = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
                .format(System.currentTimeMillis())
        } else {
            TODO("VERSION.SDK_INT < N")
        }


        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onCaptureSuccess(image: ImageProxy) {
                    val imageFile = File(getOutputFile().absoluteFile.toURI())
                    saveImageWithLocationData(image, imageFile)

                }


                override fun onError(exception: ImageCaptureException) {
                    // Handle the error if capturing the image fails
                    Log.e(TAG, "Error capturing image: ${exception.message}", exception)
                }
            })

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageWithLocationData(image: ImageProxy, imageFile: File) {
        // Process the image to save it to the file and then set EXIF data
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        FileOutputStream(imageFile).use { outputStream ->
            outputStream.write(bytes)
        }

        println("in saveImageWithLocationData" + latitude + longitude)
//        val exif = ExifInterface(imageFile)
//        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, formatLatitudeLongitude(latitude))
//        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, formatLatitudeLongitude(longitude))
//        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, if (latitude >= 0) "N" else "S")
//        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, if (longitude >= 0) "E" else "W")
//
//        exif.saveAttributes()

        // Save the image to the MediaStore
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            println("inside version" + imageFile)
            saveImageToMediaStoreQ(imageFile)
        } else {
            saveImageToMediaStoreLegacy(imageFile)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        println("permission approved" + requestCode)
        if (requestCode == write_file_storage_permission || requestCode == read_file_permission && grantResults.isNotEmpty()) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                println("permission denied" + requestCode)
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun writeFilePermission() {
        println("write permission")
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(
                    this@PhotoCaptureActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //startCamara();
            } else {
                ActivityCompat.requestPermissions(
                    this@PhotoCaptureActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    write_file_storage_permission
                )
            }
        }

    }


    private fun read_file_permission() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(
                    this@PhotoCaptureActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //startCamara();
            } else {
                println("read persmission")
                ActivityCompat.requestPermissions(
                    this@PhotoCaptureActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    read_file_permission
                )
            }
        }
    }


    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
                println("persmission" + it.key + "   " + it.value)

            }
            if (!permissionGranted) {
                Toast.makeText(
                    baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }


}
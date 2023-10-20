package com.mahantesh.loginscreen.NetWorkCheck

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mahantesh.loginscreen.R

class NetWorkCheckActivity : AppCompatActivity() {

    private var read_sms_code = 101;
    private var read_phone_number = 102;
    private var read_phone_state = 103;


    lateinit var networkState: TextView;
    lateinit var networkDeatils: TextView;
    lateinit var simNumber: TextView;
    lateinit var simDeatils: TextView;

    private var REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.READ_PHONE_NUMBERS
    )


    @SuppressLint("HardwareIds")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work_check)

        requestPermissions();
        askForPermission()


        networkState = findViewById(R.id.real_network_state);
        networkDeatils = findViewById(R.id.network_details);

        simNumber = findViewById(R.id.sim_number);

        simDeatils = findViewById(R.id.sim_details);


        val connectivityManager = getSystemService(ConnectivityManager::class.java);

        val connectManager = connectivityManager.activeNetwork

//        val defaultNetWork = connectivityManager.isActiveNetworkMetered
//
//        println("defaulut"+defaultNetWork)

        //  networkState.text = NetworkCapabilities.NET_CAPABILITY_INTERNET.toString()
        val caps = connectivityManager.getNetworkCapabilities(connectManager);
        networkDeatils.text = caps.toString();

        if (caps != null) {

            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                networkState.text =
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR).toString()
            } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                networkState.text = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI).toString()
            } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                networkState.text =
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET).toString()
            }

        } else {
            networkState.text = false.toString()
        }


        val telephoneyManger = getSystemService(TelephonyManager::class.java);


//        val subscribeManager = getSystemService(SubscriptionManager::class.java);
//
//        subscribeManager.getPhoneNumber(100);
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            askForPermission()
        }
        simNumber.text = telephoneyManger.line1Number
        simDeatils.text = telephoneyManger.simOperatorName;
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun askForPermission() {


        ActivityCompat.requestPermissions(
            this@NetWorkCheckActivity,
            arrayOf(
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_PHONE_NUMBERS
            ),
            read_phone_number
        )

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == read_phone_number && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //askForPermission();
            } else {
                println("permission denied" + requestCode)
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
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
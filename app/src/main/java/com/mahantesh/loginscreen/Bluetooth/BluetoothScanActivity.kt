package com.mahantesh.loginscreen.Bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import com.mahantesh.loginscreen.R
import java.util.*


class BluetoothScanActivity : AppCompatActivity() {

    private var REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.BLUETOOTH
    )


    val bluetoothListAdapter:BluetoothListAdapter = BluetoothListAdapter()
    var bluetoothService : BluetoothService? = null

    var recyclerView :RecyclerView? = null


    lateinit var  turnOnButton:AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_scan)

        turnOnButton = findViewById(R.id.turnOnBluetoothButton);
        requestPermissions()

        val config = BluetoothConfiguration()
        config.context = applicationContext
        config.bluetoothServiceClass = BluetoothClassicService::class.java
        config.bufferSize = 1024
        config.characterDelimiter = '\n';
        config.deviceName = "LogInScreen"
        config.callListenersInMainThread = true






        BluetoothService.init(config)
        bluetoothService = BluetoothService.getDefaultInstance()

        val bluetoothManager :BluetoothManager = getSystemService(BluetoothManager::class.java)
       val bluetoothAdapter :BluetoothAdapter = bluetoothManager.adapter

        if (bluetoothManager == null){
           //device does not support

            println("bluetooth does not support")
        }else{
           if(!bluetoothAdapter.isEnabled){
               bluetoothAdapter.enable()
           }



            println("bluetooth enalbe")
        }


        recyclerView = findViewById(R.id.bluetooth_recycleView);

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager  =  LinearLayoutManager(this)
        recyclerView?.adapter = bluetoothListAdapter





        turnOnButton.setOnClickListener {
            bluetoothService?.startScan()
        }




        bluetoothService?.setOnScanCallback(object:BluetoothService.OnBluetoothScanCallback{
            override fun onDeviceDiscovered(device: BluetoothDevice?, rssi: Int) {
                println("device"+device)
                if (device != null) {
                    bluetoothListAdapter.addDevice(device)
                }
            }

            override fun onStartScan() {
                println("bluetooth started scanning")
            }

            override fun onStopScan() {
                println("bluetooth stop scanning")
            }


        })

    }


    override fun onDestroy() {
        super.onDestroy()
        bluetoothService?.stopScan()
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
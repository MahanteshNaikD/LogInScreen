package com.mahantesh.loginscreen.Bluetooth

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mahantesh.loginscreen.R



class BluetoothListAdapter : RecyclerView.Adapter<BluetoothListAdapter.BluetoothListViewHolder>() {

      private val mBlueToothDevices: ArrayList<BluetoothDevice> = ArrayList<BluetoothDevice>()

      private var OnConnectClickListner : onConnectClickListner? = null

     interface onConnectClickListner{
       fun onConnectClick(bleDevice : BluetoothDevice)
     }

    
    
    public fun setOnClickListner(OnConnectClickListner: onConnectClickListner) {
        this.OnConnectClickListner = OnConnectClickListner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_card, parent, false)
        return BluetoothListViewHolder(view, OnConnectClickListner!!)
    }

    override fun onBindViewHolder(holder: BluetoothListViewHolder, position: Int) {
      val bluetoothDevice:BluetoothDevice= mBlueToothDevices[position];
         holder.bluetoothName.text  = bluetoothDevice.name.toString()
    }

    override fun getItemCount(): Int {
        return mBlueToothDevices.size
    }


    fun addDevice(bluetoothDevice: BluetoothDevice){
        mBlueToothDevices.add(bluetoothDevice)
        notifyDataSetChanged()
    }



    class BluetoothListViewHolder(itemView: View, private val onConnectClickListener: onConnectClickListner) : RecyclerView.ViewHolder(itemView) {


        private var bluetoothListAdapter = BluetoothListAdapter()

        var bluetoothName : AppCompatTextView = itemView.findViewById(R.id.bluetooth_name)
        private val bluetoothConnectButton : AppCompatButton = itemView.findViewById(R.id.bluetooth_connect)

        init {
            bluetoothConnectButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedDevice: BluetoothDevice = bluetoothListAdapter.mBlueToothDevices[position]
                    onConnectClickListener.onConnectClick(clickedDevice)
                }
            }
        }
    }





}
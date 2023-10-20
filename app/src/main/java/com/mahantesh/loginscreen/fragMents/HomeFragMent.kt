package com.mahantesh.loginscreen.fragMents

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.mahantesh.loginscreen.Graphs.GraphActivity
import com.mahantesh.loginscreen.BarCodeScanner.BarCodeAndQrCodeScannerActivity
import com.mahantesh.loginscreen.Bluetooth.BluetoothScanActivity
import com.mahantesh.loginscreen.NetWorkCheck.NetWorkCheckActivity
import com.mahantesh.loginscreen.MapModule.PhotoCaptureWithCoordinates.PhotoCaptureActivity
import com.mahantesh.loginscreen.PaginationActivity
import com.mahantesh.loginscreen.R
import com.mahantesh.loginscreen.carousel.ImageSliderActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragMent.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragMent : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var barCodeScanner: AppCompatButton;
    private lateinit var networkCheck: AppCompatButton;
    private lateinit var photoCaptureActivity : AppCompatButton;
    private lateinit var lineGraphActivity: AppCompatButton
    lateinit var paginationActivity: AppCompatButton;
    lateinit var caroseulAcivity :AppCompatButton
    private lateinit var bluetoothActivity :AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  =  inflater.inflate(R.layout.activity_home_screen, container, false)
        barCodeScanner = view.findViewById(R.id.barCodeScanner)!!;
        networkCheck = view.findViewById(R.id.netWorkCheck)!!;
        photoCaptureActivity = view.findViewById(R.id.photoCaptureActivity)!!;
        lineGraphActivity = view.findViewById(R.id.lineGraphActivity)
        paginationActivity = view.findViewById(R.id.paginationActivity)
        caroseulAcivity = view.findViewById(R.id.carousel_activity)
        bluetoothActivity = view.findViewById(R.id.bluetooth_activity)

        barCodeScanner.setOnClickListener {
            activity?.startActivity(Intent(activity, BarCodeAndQrCodeScannerActivity::class.java))
        }

        networkCheck.setOnClickListener {
            activity?.startActivity(Intent(activity, NetWorkCheckActivity::class.java))
        }


        photoCaptureActivity.setOnClickListener {
            activity?.startActivity(Intent(activity, PhotoCaptureActivity::class.java))
        }

        lineGraphActivity.setOnClickListener {
            activity?.startActivity(Intent(activity, GraphActivity::class.java))
        }


        paginationActivity.setOnClickListener {
            activity?.startActivity(Intent(activity, PaginationActivity::class.java))
        }


        caroseulAcivity.setOnClickListener {
            activity?.startActivity(Intent(activity, ImageSliderActivity::class.java))
        }


        bluetoothActivity.setOnClickListener {
            activity?.startActivity(Intent(activity, BluetoothScanActivity::class.java))
        }








        return view
    }

}
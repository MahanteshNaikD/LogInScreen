package com.mahantesh.loginscreen.MapModule

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.mahantesh.loginscreen.R


class MapsFragment : Fragment() {


    private lateinit var mMap: GoogleMap
    private lateinit var search_button: ImageButton;
    private lateinit var search_text: TextInputEditText

    private lateinit var locationTextView: AppCompatTextView

    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap
        val Brigosha = LatLng(12.913, 77.634)
        googleMap.addMarker(MarkerOptions().position(Brigosha).title("Brigosha Technology"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Brigosha))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        search_button = view.findViewById(R.id.search_button);

        search_text = view.findViewById(R.id.searchView1)

        locationTextView = view.findViewById(R.id.latlongLocation)
        search_button.setOnClickListener {

            println("inside button")
            val location: String = search_text.text.toString()

            val geocoder: Geocoder? = activity?.let { it1 -> Geocoder(it1.applicationContext) }

            var address: List<Address>? = null;

            try {
                address = geocoder?.getFromLocationName(search_text.text.toString(), 3)
                if (address != null && !address.equals("")) {
                    search(address);
                }
            } catch (e: Exception) {

            }

        }

        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        mMap.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun search(addresses: List<Address>) {
        val address: Address = addresses[0];


        val latlang = LatLng(address.latitude, address.longitude);

        val addreeText = String.format(
            "%s ,%s", if (address.maxAddressLineIndex > 0) address.getAddressLine(0) else "",
            address.countryName
        )


        val markerOptions = MarkerOptions();

        markerOptions.position(latlang)

        markerOptions.title(search_text.text.toString());

        mMap.clear();

        mMap.addMarker(markerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15F));

        locationTextView.text = ("Latitude:" + address.latitude.toString() + ", Longitude:"
                + address.longitude)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
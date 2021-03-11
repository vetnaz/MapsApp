package com.application.mapsaplication

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var searchView: SearchView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView = findViewById(R.id.location_search);

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {

              val location:String = searchView.query.toString();
               var list: ArrayList<Address>? = null

               if(location!=null||!(location=="")){
                   val geocoder:Geocoder = Geocoder(this@MapsActivity)
                   try {
                       list = geocoder.getFromLocationName(location,1) as ArrayList<Address>;
                   }catch (e:Exception){
                        e.printStackTrace();
                   }
                   val address: Address? = list?.get(0)
                   val latLng: LatLng?
                   latLng = address?.latitude?.let { LatLng(it, address.longitude) }

                   mMap.addMarker(latLng?.let { MarkerOptions().position(it).title(location) })
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
               }

               return true
           }

           override fun onQueryTextChange(newText: String?): Boolean {
               return false;
           }
       })

        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val kryvonuly = LatLng(48.78364944458008, 24.858089447021484)
        mMap.addMarker(MarkerOptions().position(kryvonuly).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kryvonuly))
    }
}


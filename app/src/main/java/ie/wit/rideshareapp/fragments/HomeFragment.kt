package ie.wit.rideshareapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import ie.wit.rideshareapp.R
import java.lang.Exception


class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback {


    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    // user location
    lateinit var locationRequest:LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        childFragmentManager.findFragmentById(R.id.map)



    }

    private fun init() {
       locationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setFastestInterval(3000)
        locationRequest.setSmallestDisplacement(10f)
        locationRequest.interval = 5000

        locationCallback = object: LocationCallback(){

            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val newPos = LatLng(p0!!.lastLocation.latitude,p0!!.lastLocation.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos,18f))
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0!!

        Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object:PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return
                        }
                        googleMap.isMyLocationEnabled = true
                        googleMap.uiSettings.isMyLocationButtonEnabled = true
                        googleMap.setOnMyLocationButtonClickListener {
                            fusedLocationProviderClient.lastLocation
                            .addOnFailureListener { e->
                            Snackbar.make(requireView(), e.message!!,
                                    Snackbar.LENGTH_LONG).show()
                        }
                                .addOnSuccessListener { location ->
                                    val userLatLng = LatLng(location.latitude,location.longitude)
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,18f))

                                }
                            true
                        }

                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Snackbar.make(requireView(), p0!!.permissionName+ "needed for run app" ,
                       Snackbar.LENGTH_LONG).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {

                    }

                })
                .check()

        //Button and Layout
        val locationButton = (mapFragment.requireView()!!.findViewById<View>("1".toInt())!!.parent!! as View )
                .findViewById<View>("2".toInt())
        val params = locationButton.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_TOP, 0)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        params.bottomMargin = 50
        try{
            val success = p0!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.alternative_maps_style))
            if(!success)
                Snackbar.make(requireView(),"Load map Style Failed",
                Snackbar.LENGTH_LONG).show()

        }catch(e:Exception){
            Snackbar.make(requireView(),""+e.message,
                    Snackbar.LENGTH_LONG).show()
        }

    }


}





























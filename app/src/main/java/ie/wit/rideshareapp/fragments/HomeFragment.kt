package ie.wit.rideshareapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.view.View
import android.widget.RelativeLayout
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.lang.Exception


class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback {


    lateinit var mapFragment : SupportMapFragment
    // here we declare a variable for the google maps class in our sdk
    lateinit var googleMap: GoogleMap

    // user location
    lateinit var locationRequest:LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // creating variable for searchview
    internal lateinit var searchView:SearchView

    override fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // this assign the searchview to our searchview layout by the id
        searchView = idSearchView

        init()

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query:String):Boolean {
                // on below line we are getting the
                // location name from search view.
                val location = searchView.getQuery().toString()
                // below line is to create a list of address
                // where we will store the list of all address.
                var addressList: List<Address>? = null
                // checking if the entered location is null or not.
                if (location != null || location == "")
                {
                    // here im, creating and initializing a geo coder.
                    val geocoder = Geocoder(context)
                    try
                    {
                        //here we are getting location from the location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1)
                    }
                    catch (e: IOException) {
                        e.printStackTrace()
                    }

                    val address = addressList?.get(0)
                    //  creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    val latLng = address?.let { LatLng(it.getLatitude(), address.getLongitude()) }
                    // adding marker to that position.
                    googleMap.addMarker(latLng?.let { MarkerOptions().position(it).title(location) })
                    // here we animate the camera to that position.
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                }
                return false
            }
            override fun onQueryTextChange(newText:String):Boolean {
                return false
            }
        })

        mapFragment.getMapAsync(this)
        childFragmentManager.findFragmentById(R.id.map)



    }


    // here we find the user's current location via a locationrequest and set priorities, interval and displacement
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

        // here we ask for user permissions to aceesss location
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
                        // if the user denies permission a message that it is required to run the app is displayed
                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Snackbar.make(requireView(), p0!!.permissionName+ "needed to run the app" ,
                       Snackbar.LENGTH_LONG).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {

                    }

                })
                .check()

        //Button and Layout here is the button and layout for the geolocate button
        val locationButton = (mapFragment.requireView()!!.findViewById<View>("1".toInt())!!.parent!! as View )
                .findViewById<View>("2".toInt())
        val params = locationButton.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_TOP, 0)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        params.bottomMargin = 50
        try{
            //here we also change the map style to our custom one
            val success = p0!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.alternative_maps_style))
            if(!success)
                //here we also create a validation message but this tiome with snackbar instead of toast, they are similar but snackbar can be swiped of screen
                Snackbar.make(requireView(),"Load map Style Failed",
                Snackbar.LENGTH_LONG).show()

        }catch(e:Exception){
            Snackbar.make(requireView(),""+e.message,
                    Snackbar.LENGTH_LONG).show()
        }

    }


}





























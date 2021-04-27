package ie.wit.rideshareapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.fragment_shareride.*

class SharerideFragment : Fragment(R.layout.fragment_shareride)  {



    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("rides")


        shareRide.setOnClickListener {
            if ( pickupLocation.text.toString().isNotEmpty() || destination.text.toString().isNotEmpty() || contact.text.toString().toInt()>10000) {
                        shareRide()


                        Toast.makeText(activity, "Ride has been shared", Toast.LENGTH_LONG).show()
                    }else {
                Toast.makeText(activity, "Please provide all details", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun shareRide(){

        val currentUser = auth.currentUser
        val currentUserDB = databaseReference?.child((currentUser?.uid!!))
        currentUserDB?.child("pickupLocation")?.setValue(pickupLocation.text.toString())
        currentUserDB?.child("destination")?.setValue(destination.text.toString())
        currentUserDB?.child("contact")?.setValue(contact.text.toString().toInt())
        Log.e("Task Message", "Ride Created")

    }

}
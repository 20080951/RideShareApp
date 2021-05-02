package ie.wit.rideshareapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.activities.LoginActivity
import ie.wit.rideshareapp.activities.UpdateActivity
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment(R.layout.fragment_account) {

    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("profile")


        // this directs us to our update activity
        update.setOnClickListener {
           val intent = Intent(view.context, UpdateActivity::class.java)
        startActivity(intent)
        }
        readFireStoreData()
    }


    fun readFireStoreData() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)


        // as email is already stored elsewhere we do not pull it from the profile collection
        userEmail.text = user?.email


        // here we check if the data is changed within our relatime databse and then pull the data stored in the fields, username,firstname and lastname from the the profile collection
        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
             username.text = snapshot.child("username").value.toString()
                firstname.text = snapshot.child("firstname").value.toString()
                lastname.text = snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



    }
}
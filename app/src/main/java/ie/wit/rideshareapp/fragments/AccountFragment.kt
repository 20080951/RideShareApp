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

       // update.setOnClickListener {
         //   val intent = Intent(view.context, UpdateActivity::class.java)
           // startActivity(intent)
        //}
        readFireStoreData()
    }


    fun readFireStoreData() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userEmail.text = user?.email

        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
             username.text = snapshot.child("username").value.toString()
                fullname.text = snapshot.child("fullname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


       // val db = FirebaseFirestore.getInstance()
       // db.collection("profile")
      //      .get()
       //     .addOnCompleteListener {

         //       val result: StringBuffer = StringBuffer()
           //     val name: StringBuffer = StringBuffer()
           //     if (it.isSuccessful) {
            //        for (document in it.result!!) {
             //           result.append(document.data.getValue("username")).append("\n\n")
              //          name.append(document.data.getValue("fullName")).append("\n\n")
               //     }

                //    username.setText(result)
                 //   userFullName.setText(name)

              //  }

           // }
    }
}
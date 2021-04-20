package ie.wit.rideshareapp.activities

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.fragments.AccountFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.fragment_account.*


class UpdateActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("profile")

        save.setOnClickListener {
            if ( updateUsername.text.toString().isNotEmpty() || updatefirstName.text.toString().isNotEmpty() || updatelastName.text.toString().isNotEmpty()) {
                updateUser()


                Toast.makeText(this, "Account Details Updated", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this, "Provide new details", Toast.LENGTH_LONG).show()
            }
        }


    }

    fun updateUser() {

                        val currentUser = auth.currentUser
                        val currentUserDB = databaseReference?.child((currentUser?.uid!!))
                        currentUserDB?.child("username")?.setValue(updateUsername.text.toString())
                         currentUserDB?.child("firstname")?.setValue(updatefirstName.text.toString())
                        currentUserDB?.child("lastname")?.setValue(updatelastName.text.toString())
                        Log.e("Task Message", "Successful...")

                    }



}
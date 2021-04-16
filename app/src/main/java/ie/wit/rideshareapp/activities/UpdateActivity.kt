package ie.wit.rideshareapp.activities

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.activity_update.*


class UpdateActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

   //     save.setOnClickListener {
         //   val username = inputUsername.text.toString()
        //    val fullname =inputfullName.text.toString()

       //     saveFireStore(username, fullname)
       // }



    }
   // fun saveFireStore(username: String, fullname: String) {
    //    val db = FirebaseFirestore.getInstance()
    //    val user: MutableMap<String, Any> = HashMap()
     //   user["username"] = username
     //   user["fullName"] = fullname
//
  //      db.collection("users")
        //    .add(user)
         //   .addOnSuccessListener {

           //     Toast.makeText(this, "record added successfully", Toast.LENGTH_SHORT)
                //    .show()
            //}
          //  .addOnFailureListener {
            //    Toast.makeText(this,
             //       "record failed to add",
            //        Toast.LENGTH_SHORT
            //    ).show()

        //    }
    //}
}
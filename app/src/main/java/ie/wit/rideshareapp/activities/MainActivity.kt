package ie.wit.rideshareapp.activities
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.util.ArrayUtils.contains
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.rideshareapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("profile")

        btnRegister.setOnClickListener {
            if (editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty() || editUsername.text.toString().isNotEmpty() || editFirstName.text.toString().isNotEmpty() || editLastName.text.toString().isNotEmpty()) {
                createUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())

            Toast.makeText(this, "Input Provided", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
        tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun createUser(email:String, password:String) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    if (it.isSuccessful) {

                        sendEmailVerification()

                        val currentUser = auth.currentUser
                       val currentUserDB = databaseReference?.child((currentUser?.uid!!))
                        //insert data into the user database for the fields username, firstname, and lastname
                        currentUserDB?.child("username")?.setValue(editUsername.text.toString())
                        currentUserDB?.child("firstname")?.setValue(editFirstName.text.toString())
                        currentUserDB?.child("lastname")?.setValue(editLastName.text.toString())
                        Log.e("Task Message", "Successful...");

                        Toast.makeText(this, "Account Created Successfully, Please Login", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, LoginActivity::class.java);
                        startActivity(intent);
                    } else {
                        //Displays message notifying user that their registration was unsuccessful
                       Toast.makeText(this@MainActivity, "Unable to Create Account",Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun sendEmailVerification(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            Toast.makeText(this, "Verification Email Sent Please check Inbox", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if(user != null){
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
    }

}
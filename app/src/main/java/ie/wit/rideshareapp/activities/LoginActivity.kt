package ie.wit.rideshareapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        //directs the user to register page
        tvRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        //opens forgot password activity
        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }


        btnLogin.setOnClickListener {

            //calls sign in function if fields are not empty
            if (editTextEmail.text.trim().toString().isNotEmpty() || editTextPassword.text.trim().toString().isNotEmpty()
            ) {
                signInUser(editTextEmail.text.trim().toString(), editTextPassword.text.trim().toString())
            } else {
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }

        }
    }

            // function that logs user in by calling login function
            fun signInUser(email: String, password: String) {

                auth.signInWithEmailAndPassword(email, password)
                        
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            login(user)

                        } else {

                            login(null)

                        }

                    }

            }

private fun login(currentUser: FirebaseUser?){

    // here we check if the user has a verified email and correct email and password, allowign them to sign in if both conditions are true.
    if(currentUser != null){
        if(currentUser.isEmailVerified) {


            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else
        {
            Toast.makeText(this, "Please Verify Email", Toast.LENGTH_LONG).show()
        }
    }else {
        Toast.makeText(this, "Please Enter Correct Email/Password", Toast.LENGTH_LONG).show()
    }

}

}

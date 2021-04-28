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

        tvRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }

        btnLogin.setOnClickListener {

            if (editTextEmail.text.trim().toString().isNotEmpty() || editTextPassword.text.trim().toString().isNotEmpty()
            ) {
                signInUser(editTextEmail.text.trim().toString(), editTextPassword.text.trim().toString())
            } else {
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }

        }
    }
            fun signInUser(email: String, password: String) {

                auth.signInWithEmailAndPassword(email, password)
                        
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // task will be succesful if user provides valid email and password
                            val user = auth.currentUser
                            login(user)

                        } else {

                            login(null)

                        }

                    }

            }

private fun login(currentUser: FirebaseUser?){

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

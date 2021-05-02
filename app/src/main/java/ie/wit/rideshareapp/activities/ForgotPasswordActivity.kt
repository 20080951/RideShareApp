package ie.wit.rideshareapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //this function checks if the email field is populated and if so then sending a reset password email with firebase
        btnSubmit.setOnClickListener {
            val email: String = enterTextEmail.text.toString().trim { it <= ' ' }
            if(email.isEmpty()) {
                Toast.makeText(
                        this@ForgotPasswordActivity, "Please enter Email",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener{task ->
                            if(task.isSuccessful){
                                Toast.makeText(this@ForgotPasswordActivity,"Reset Password Email Sent!",Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }

            }
        }
        tvLogins.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
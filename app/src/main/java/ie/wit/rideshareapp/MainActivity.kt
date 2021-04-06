package ie.wit.rideshareapp
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            if (editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty()) {
                createUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())

            Toast.makeText(this, "Input Provided", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
    }

    fun createUser(email:String, password:String) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Log.e("Task Message", "Successful...");

                        val intent = Intent(this,HomeActivity::class.java);
                        startActivity(intent);
                    } else {
                        Log.e("Task Message", "Unsuccessful..."+task.exception)
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if(user != null){
            val intent = Intent(this,HomeActivity::class.java);
            startActivity(intent);
        }
    }
}
package ie.wit.rideshareapp
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister.setOnClickListener {
            if (editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty()) {


            Toast.makeText(this, "Input Provided", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
    }
}
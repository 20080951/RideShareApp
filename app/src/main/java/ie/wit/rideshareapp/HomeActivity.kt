package ie.wit.rideshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ie.wit.rideshareapp.fragments.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val myridesFragment = MyridesFragment()
    private val settingsFragment = SettingsFragment()
    private val sharerideFragment = SharerideFragment()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = FirebaseAuth.getInstance()
        replaceFragment(homeFragment)

      //  btnSignOut.setOnClickListener {
        //    auth.signOut()
          //  val intent = Intent(this, LoginActivity::class.java)
            //startActivity(intent)
        //}
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
        val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}













package ie.wit.rideshareapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.fragments.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeActivity : AppCompatActivity() {


    // declaring fragment variables
    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val myridesFragment = MyridesFragment()
    private val settingsFragment = SettingsFragment()
    private val sharerideFragment = SharerideFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replaceFragment(homeFragment)


        // adding functionality for bottom navigation by assigning layouts to icon id's
        //stating that when icon is slecetd the fragment is replace with the new fragment assigned to that icon
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_account -> replaceFragment(accountFragment)
                R.id.ic_myrides -> replaceFragment(myridesFragment)
                R.id.ic_settings -> replaceFragment(settingsFragment)
                R.id.ic_shareride -> replaceFragment(sharerideFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
        val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}













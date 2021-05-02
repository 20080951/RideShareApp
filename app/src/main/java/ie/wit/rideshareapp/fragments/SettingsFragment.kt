package ie.wit.rideshareapp.fragments

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        //signs user out and displays login activity
        btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
        }

        btnDeleteAccount.setOnClickListener{
            val mAlertDialog = AlertDialog.Builder(context)
            //creating alert dialog asking user if they are sure or weant to cancel account deletion
            mAlertDialog.setTitle("Delete Account")
            mAlertDialog.setMessage("Are you sure you want to delete account?")
            mAlertDialog.setIcon(R.mipmap.ic_launcher)

            // setting conditions for positive button and negative button
            mAlertDialog.setPositiveButton("Yes"){dialog, id ->
                val user = auth.currentUser!!

                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User account deleted.")
                            val intent = Intent(view.context, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }
            mAlertDialog.setNegativeButton("No"){dialog, id ->
                dialog.dismiss()

            }
            mAlertDialog.show()
        }
    }

}

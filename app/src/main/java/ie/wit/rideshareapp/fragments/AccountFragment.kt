package ie.wit.rideshareapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment(R.layout.fragment_account) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save.setOnClickListener {
            val username = inputUsername.text.toString()
            val fullname = fullName.text.toString()

            saveFireStore(username, fullname)
        }
        readFireStoreData()
    }

    fun saveFireStore(username: String, fullname: String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["username"] = username
        user["fullName"] = fullname

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(getActivity(), "record added successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    getActivity(),
                    "record failed to add",
                    Toast.LENGTH_SHORT
                ).show()

            }
        readFireStoreData()
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()
                val name: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("username")).append("")
                        name.append(document.data.getValue("fullName")).append("\n\n")
                    }

                    userData.setText(result)
                    userName.setText(name)

                }

            }
    }
}
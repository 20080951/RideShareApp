package ie.wit.rideshareapp.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ie.wit.rideshareapp.R
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.fragment_shareride.*
import java.util.*

class SharerideFragment : Fragment(R.layout.fragment_shareride), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0




    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pickDate()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("rides")


        btn_shareRide.setOnClickListener {
            if ( pickupLocation.text.toString().isNotEmpty() || destination.text.toString().isNotEmpty() || date.editableText.toString().isNotEmpty() || contact.text.toString().toInt()>1111111) {
                        shareRide()


                        Toast.makeText(activity, "Ride has been shared", Toast.LENGTH_LONG).show()
                    }else {
                Toast.makeText(activity, "Please provide all details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){

        btn_timePicker.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    fun shareRide(){

        val currentUser = auth.currentUser
        val currentUserDB = databaseReference?.child((currentUser?.uid!!))
        currentUserDB?.child("pickupLocation")?.setValue(pickupLocation.text.toString())
        currentUserDB?.child("destination")?.setValue(destination.text.toString())
        currentUserDB?.child("date")?.setValue(date.editableText.toString())
        currentUserDB?.child("contact")?.setValue(contact.text.toString().toInt())
        Log.e("Task Message", "Ride Created")

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(context, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        savedHour = hourOfDay
        savedMinute = minute

        date.setText("$savedDay-$savedMonth-$savedYear\n $savedHour : $savedMinute")

    }

}
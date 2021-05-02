package ie.wit.rideshareapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.adapters.MyAdapter
import ie.wit.rideshareapp.classes.Rides
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_myrides.*

class MyridesFragment : Fragment(R.layout.fragment_myrides) {


// declaring variables and assigning them to public classes from our implemented files
    private lateinit var dbref : DatabaseReference
    private lateinit var ridesRecyclerview: RecyclerView
    private lateinit var ridesArrayList : ArrayList<Rides>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        ridesRecyclerview = view.findViewById(R.id.sharedRides)
        ridesRecyclerview.layoutManager = LinearLayoutManager(context)
        ridesRecyclerview.setHasFixedSize(true)

        // lists array of all content in rides collection
        ridesArrayList = arrayListOf<Rides>()
        getRidesData()


    }

    private fun getRidesData(){
        // gettibng data from rides collection
        dbref = FirebaseDatabase.getInstance().getReference("rides")

      

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ridesSnapshot in snapshot.children){
                        // sets rides data into rides array
                        val rides = ridesSnapshot.getValue(Rides::class.java)
                        ridesArrayList.add(rides!!)
                }

                    ridesRecyclerview.adapter = MyAdapter(ridesArrayList)
            }
        }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

}
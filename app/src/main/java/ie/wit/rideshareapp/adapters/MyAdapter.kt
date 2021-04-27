package ie.wit.rideshareapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.rideshareapp.R
import ie.wit.rideshareapp.adapters.MyAdapter.MyViewHolder
import ie.wit.rideshareapp.classes.Rides
import kotlinx.android.synthetic.main.ride_item.view.*

class MyAdapter(private val rideList : ArrayList<Rides>) : RecyclerView.Adapter<MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ride_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = rideList[position]

        holder.pickupLoaction.text = currentitem.pickupLocation
        holder.destination.text = currentitem.destination
        holder.contact.text = currentitem.contact.toString()
    }

    override fun getItemCount(): Int {
    return rideList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val pickupLoaction : TextView = itemView.findViewById(R.id.tvpickupLocation)
        val destination : TextView = itemView.findViewById(R.id.tvDesination)
        val contact : TextView = itemView.findViewById(R.id.tvContact)
    }
}
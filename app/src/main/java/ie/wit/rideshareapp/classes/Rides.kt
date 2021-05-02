package ie.wit.rideshareapp.classes
// we assign all the data in rides which will be used to populate our recycler view
data class Rides(var pickupLocation : String ?= null, var destination : String ?= null,var date : String?= null, var contact : Long ?= null)


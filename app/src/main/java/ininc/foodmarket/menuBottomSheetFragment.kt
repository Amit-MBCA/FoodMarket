package ininc.foodmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
//import ininc.foodmarket.adapter.CartAdapter
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.databinding.FragmentMenuBottomSheetBinding
import ininc.foodmarket.model.MenuItem

class menuBottomSheetFragment : BottomSheetDialogFragment() {
   private lateinit var binding:FragmentMenuBottomSheetBinding
   private lateinit var database:FirebaseDatabase
   private lateinit var menuItems:MutableList<MenuItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        binding.idbackbutton.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()
//        val menuFoodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
//        val menuItemPrice= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
//        val menuImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)
//        val quantity=listOf(1,2,3,4,5,6)
//        val adapter= MenuAdapter(ArrayList(menuFoodNames),ArrayList(menuItemPrice),ArrayList(menuImages),requireContext())

        return binding.root
    }

    private fun retrieveMenuItems(){
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems= mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem.let{
                        menuItems.add(it!!)
                    }
                    //once data received, set to adapter
                    setAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setAdapter() {
        val adapter= MenuAdapter(menuItems,requireContext())
        binding.idMenuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.idMenuRecyclerView.adapter=adapter
    }

    companion object {

    }
}
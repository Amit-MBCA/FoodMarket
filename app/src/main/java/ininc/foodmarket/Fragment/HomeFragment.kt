package ininc.foodmarket.Fragment

//import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.appcompat.resources.R
//import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import ininc.foodmarket.R
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.adapter.PopularAdapter

import ininc.foodmarket.databinding.FragmentHomeBinding
import ininc.foodmarket.databinding.FragmentMenuBottomSheetBinding
import ininc.foodmarket.menuBottomSheetFragment
import ininc.foodmarket.model.MenuItem


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
//    by lazy {
//        FragmentHomeBinding.inflate(layoutInflater)
//    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = menuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"test")

        }

        //retrieve and display popular menu Item
        retrieveAndDisplayPopularItems()
        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
        //get reference to the database
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems= mutableListOf()
        //retrieve menu items from the database
        foodRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                //display random popular items
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun randomPopularItems() {
        //create a shuffle list of menu Items
        val index=menuItems.indices.toList().shuffled()
        val numItemToShow=8
        val subsetMenuItems=index.take(numItemToShow).map { menuItems[it] }
        setPopularItemsAdapter(subsetMenuItems)
    }

    private fun setPopularItemsAdapter(subsetMenuItems:List<MenuItem>) {
        val adapter=MenuAdapter(subsetMenuItems,requireContext())
        binding.idpopularrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.idpopularrecyclerview.adapter=adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList=ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.pizzaimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.sandwichimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.frenchfriesimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.kimchiimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.friedriceimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.momosimg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.burgerimg, ScaleTypes.FIT))

        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        imageSlider.setItemClickListener(object: ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position:Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_LONG).show()
            }
        })

//        val foodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
//        val price= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
//        val popularFoodImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)

        
    }
}
package ininc.foodmarket.Fragment

//import android.os.Build.VERSION_CODES.R
//import androidx.appcompat.resources.R
//import androidx.core.graphics.drawable.toDrawable

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.R
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.databinding.FragmentHomeBinding
import ininc.foodmarket.menuBottomSheetFragment
import ininc.foodmarket.model.MenuItem


class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
    private lateinit var adapter: MenuAdapter
//    private lateinit var window: Window
    private val originalMenuItems= mutableListOf<MenuItem>()
//    by lazy {
//        FragmentHomeBinding.inflate(layoutInflater)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val focusedView=window.currentFocus
//        Log.d("GetView","Focused View: + $focusedView")
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
//        val searchViewFocus=binding.searchView
//        searchViewFocus.setOnFocusChangeListener(this)
//        binding.searchView.setQuery("",false)
        binding.searchViewLayout.setOnClickListener {
            binding.cardView.visibility=View.GONE
            binding.imageSlider.visibility=View.GONE
            binding.idpopularrecyclerview.visibility= View.GONE
            binding.idpopularitemtv.visibility= View.GONE
            binding.viewAllMenu.visibility= View.GONE
            binding.idmenurv.visibility=View.VISIBLE
        }

        binding.idpopularrecyclerview.requestFocus()
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = menuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"test")

        }

        //retrieve and display popular menu Item
        retrieveAndDisplayPopularItems()
        //retrive items for search bar
        setupSearchView()
        showAllMenu()

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

    private fun showAllMenu() {
        val filterMenuItem=ArrayList(menuItems)
        setAdapter(filterMenuItem)

//        adapter.notifyDataSetChanged()
    }

    private fun setAdapter(filterMenuItem: List<MenuItem>) {
        adapter=MenuAdapter(filterMenuItem,requireContext())
        binding.idmenurv.layoutManager=LinearLayoutManager(requireContext())
        binding.idmenurv.adapter=adapter
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
//                binding.idmenurv.visibility=View.VISIBLE
                if(binding.searchView.isFocused){
                    binding.cardView.visibility=View.INVISIBLE
                    binding.idpopularrecyclerview.visibility= View.INVISIBLE
                    binding.idmenurv.visibility=View.VISIBLE
                }
                else{
                    binding.cardView.visibility=View.VISIBLE
                    binding.idpopularrecyclerview.visibility= View.VISIBLE
                    binding.idmenurv.visibility=View.INVISIBLE
                }
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                binding.idmenurv.visibility=View.VISIBLE

                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        val filteredMenuItem=menuItems.filter {
            it.foodName?.contains(query,ignoreCase = true) == true
        }
        adapter.notifyDataSetChanged()
        setAdapter(filteredMenuItem)
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

//    override fun onFocusChange(v: View?, hasFocus: Boolean) {
//        if(hasFocus){
//            Log.d("CheckFocus","Focus on search view")
//            binding.cardView.visibility=View.GONE
//            binding.idpopularrecyclerview.visibility= View.GONE
//            binding.idpopularitemtv.visibility= View.GONE
//            binding.viewAllMenu.visibility= View.GONE
//            binding.idmenurv.visibility=View.VISIBLE
//        }
//        else{
//            binding.cardView.visibility=View.VISIBLE
//            binding.idpopularrecyclerview.visibility= View.VISIBLE
//            binding.idpopularitemtv.visibility= View.VISIBLE
//            binding.viewAllMenu.visibility= View.VISIBLE
//            binding.idmenurv.visibility=View.GONE
//        }
//    }



}
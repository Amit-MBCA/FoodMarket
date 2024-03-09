package ininc.foodmarket.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ininc.foodmarket.R
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter:MenuAdapter
    private val originalMenuFoodName=listOf("Burger","Sandwich","Momos","Item","Sandwich","Momos")
    private val originalMenuItemPrice= listOf("$5","$4","$6","$5","$4","$6")
    private val originalMenuImages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filterMenuFoodNames=mutableListOf<String>()
    private val filterMenuFoodPrices=mutableListOf<String>()
    private val filterMenuFoodImages=mutableListOf<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        adapter= MenuAdapter(filterMenuFoodNames,filterMenuFoodPrices,filterMenuFoodImages,requireContext())
        binding.idMenuRecylerView.layoutManager=LinearLayoutManager(requireContext())
        binding.idMenuRecylerView.adapter=adapter

        //Setup for search view
        setupSearchView()

        //Show all menu items
        showAllMenu()

        return binding.root
    }

    private fun showAllMenu() {
        filterMenuFoodNames.clear()
        filterMenuFoodPrices.clear()
        filterMenuFoodImages.clear()
        filterMenuFoodNames.addAll(originalMenuFoodName)
        filterMenuFoodPrices.addAll(originalMenuItemPrice)
        filterMenuFoodImages.addAll(originalMenuImages)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        filterMenuFoodNames.clear()
        filterMenuFoodPrices.clear()
        filterMenuFoodImages.clear()

        originalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query,ignoreCase = true)){
                    filterMenuFoodNames.add(foodName)
                    filterMenuFoodPrices.add(originalMenuItemPrice[index])
                    filterMenuFoodImages.add(originalMenuImages[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}
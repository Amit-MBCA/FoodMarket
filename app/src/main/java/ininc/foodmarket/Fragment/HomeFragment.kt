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
import ininc.foodmarket.R
import ininc.foodmarket.adapter.PopularAdapter

import ininc.foodmarket.databinding.FragmentHomeBinding
import ininc.foodmarket.databinding.FragmentMenuBottomSheetBinding
import ininc.foodmarket.menuBottomSheetFragment


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
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
        return binding.root
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

        val foodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
        val price= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
        val popularFoodImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)
        val adapter=PopularAdapter(foodNames,price,popularFoodImages,requireContext())
        binding.idpopularrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.idpopularrecyclerview.adapter=adapter
        
    }
}
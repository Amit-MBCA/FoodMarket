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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList=ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.logowithbg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.logowithbg, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.logowithbg, ScaleTypes.FIT))

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
        val foodNames= listOf("Burger","Sandwich","Momos","item")
        val price= listOf("$5","$7","$8","$10")
        val popularFoodiImages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
        val adapter=PopularAdapter(foodNames,price,popularFoodiImages)
        binding.idpopularrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.idpopularrecyclerview.adapter=adapter
        
    }
}
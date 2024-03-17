package ininc.foodmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import ininc.foodmarket.adapter.CartAdapter
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.databinding.FragmentMenuBottomSheetBinding

class menuBottomSheetFragment : BottomSheetDialogFragment() {
   private lateinit var binding:FragmentMenuBottomSheetBinding

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
        val menuFoodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
        val menuItemPrice= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
        val menuImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)
//        val quantity=listOf(1,2,3,4,5,6)
        val adapter= MenuAdapter(ArrayList(menuFoodNames),ArrayList(menuItemPrice),ArrayList(menuImages),requireContext())
        binding.idMenuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.idMenuRecyclerView.adapter=adapter
        return binding.root
    }

    companion object {

    }
}
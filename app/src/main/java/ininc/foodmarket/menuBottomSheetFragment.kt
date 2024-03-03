package ininc.foodmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ininc.foodmarket.adapter.CartAdapter
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
        val menuFoodNames=listOf("Burger","Sandwich","Momos","Item","Sandwich","Momos")
        val menuItemPrice= listOf("$5","$4","$6","$5","$4","$6")
        val menuImages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
//        val quantity=listOf(1,2,3,4,5,6)
        val adapter= CartAdapter(ArrayList(menuFoodNames),ArrayList(menuItemPrice),ArrayList(menuImages))
        binding.idMenuRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.idMenuRecyclerView.adapter=adapter
        return binding.root
    }

    companion object {

    }
}
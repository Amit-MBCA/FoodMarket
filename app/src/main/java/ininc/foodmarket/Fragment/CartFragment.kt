package ininc.foodmarket.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ininc.foodmarket.R
import ininc.foodmarket.adapter.CartAdapter
import ininc.foodmarket.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(inflater,container,false)
        val cartFoodNames=listOf("Burger","Sandwich","Momos","Item","Sandwich","Momos")
        val cartItemPrice= listOf("$5","$4","$6","$5","$4","$6")
        val cartImages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
//        val quantity=listOf(1,2,3,4,5,6)
        val adapter=CartAdapter(ArrayList(cartFoodNames),ArrayList(cartItemPrice),ArrayList(cartImages))
        binding.idcartrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.idcartrecyclerview.adapter=adapter

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}
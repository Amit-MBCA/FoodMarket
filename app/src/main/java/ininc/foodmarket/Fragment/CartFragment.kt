package ininc.foodmarket.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ininc.foodmarket.CongratsBottomSheet
import ininc.foodmarket.PayOutActivity
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
        val cartFoodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
        val cartItemPrice= listOf("$5","$4","$6","$5","$4","$6","$2")
        val cartImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)


//        val quantity=listOf(1,2,3,4,5,6)
        val adapter=CartAdapter(ArrayList(cartFoodNames),ArrayList(cartItemPrice),ArrayList(cartImages))
        binding.idcartrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.idcartrecyclerview.adapter=adapter
        binding.idproceedbtn.setOnClickListener {
            val intent=Intent(requireContext(),PayOutActivity::class.java)
            startActivity(intent)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}
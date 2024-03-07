package ininc.foodmarket.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ininc.foodmarket.R
import ininc.foodmarket.adapter.BuyAgainAdapter
import ininc.foodmarket.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding:FragmentHistoryBinding
    private lateinit var buyAgainAdapter:BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        val buyAgainFoodName= arrayListOf("Food 1","Food 2","Food 3")
        val buyAgainFoodPrice= arrayListOf("$5","$12","$8")
        val buyAgainFoodImage= arrayListOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
        buyAgainAdapter=BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
        binding.idbuyagainrv.adapter=buyAgainAdapter
        binding.idbuyagainrv.layoutManager=LinearLayoutManager(requireContext())

    }
    companion object {


    }
}
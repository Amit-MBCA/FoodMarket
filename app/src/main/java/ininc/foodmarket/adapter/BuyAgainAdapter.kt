package ininc.foodmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ininc.foodmarket.databinding.BuyAgainItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.lang.reflect.Array

class BuyAgainAdapter(private val buyAgainFoodName:ArrayList<String>,private val buyAgainFoodPrice:ArrayList<String>,private val bugAgainFoodImage:ArrayList<Int>):
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding=BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    override fun onBindViewHolder(holder: BuyAgainViewHolder,position:Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],bugAgainFoodImage[position])
    }

    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(foodName: String, foodPrice: String, foodImage: Int) {
        binding.idbuyagainfoodname.text=foodName
        binding.idbuyagainfoodprice.text=foodPrice
        binding.idbuyagainfoodimage.setImageResource(foodImage)
    }

}

}
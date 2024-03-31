package ininc.foodmarket.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ininc.foodmarket.databinding.BuyAgainItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.lang.reflect.Array

class BuyAgainAdapter(private val buyAgainFoodName:MutableList<String>,private val buyAgainFoodPrice:MutableList<String>,private val bugAgainFoodImage:MutableList<String>,private var requireContext: Context):
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding=BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    override fun onBindViewHolder(holder: BuyAgainViewHolder,position:Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],bugAgainFoodImage[position])
    }

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(foodName: String, foodPrice: String, foodImage: String) {
        binding.idbuyagainfoodname.text=foodName
        binding.idbuyagainfoodprice.text=foodPrice
        val imgString=foodImage
        val uri= Uri.parse(imgString)
        Glide.with(requireContext).load(uri).into(binding.idbuyagainfoodimage)
    }

}

}
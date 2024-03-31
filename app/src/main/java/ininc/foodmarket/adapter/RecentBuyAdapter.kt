package ininc.foodmarket.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ininc.foodmarket.databinding.RecentBuyItemBinding

class RecentBuyAdapter(private var context:Context,private var foodNameList: ArrayList<String>,private  var foodImageList:ArrayList<String>,private var foodPriceList:ArrayList<String>,private var foodQuantityList:ArrayList<Int>):RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding=RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentViewHolder(binding)
    }



    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = foodNameList.size
    inner class RecentViewHolder(private val binding:RecentBuyItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                idfoodname.text=foodNameList[position]
                idfoodprice.text=foodPriceList[position]
                idquantitytv.text=foodQuantityList[position].toString()
//                idfoodname.text=foodNameList[position]
                val imgString=foodImageList[position]
                val uri= Uri.parse(imgString)
                Glide.with(context).load(uri).into(idfoodimage)
            }
        }

    }
//    inner class RecentViewHolder(){
//
//    }
}
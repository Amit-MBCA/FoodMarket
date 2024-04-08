package ininc.foodmarket.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ininc.foodmarket.DetailsActivity
import ininc.foodmarket.R
import ininc.foodmarket.databinding.FreshItemBinding
import ininc.foodmarket.databinding.FreshItemBinding.*
import ininc.foodmarket.databinding.MenuItemBinding
import ininc.foodmarket.model.MenuItem

class FreshItemAdapter(private val menuItems:List<MenuItem>, private val requiredContext: Context): RecyclerView.Adapter<FreshItemAdapter.FreshItemViewHolder>() {
    private lateinit var binding: FreshItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):FreshItemViewHolder{
        binding=FreshItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FreshItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FreshItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size


    inner class FreshItemViewHolder(private val binding:FreshItemBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }
        private fun openDetailsActivity(position: Int) {
            val menuItem=menuItems[position]
            //a intent to open details activity and pass data
            val intent= Intent(requiredContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName",menuItem.foodName)
                putExtra("MenuItemImage",menuItem.foodImage)
                putExtra("MenuItemPrice",menuItem.foodPrice)
                putExtra("MenuItemDesc",menuItem.foodDesc)
                putExtra("MenuItemIngredient",menuItem.foodIngred)
            }
            requiredContext.startActivity(intent)
        }
        fun bind(position: Int) {
            val menuItem=menuItems[position]
            binding.apply {
                idpopularfoodname.text=menuItem.foodName
                idpopularfoodprice.text=menuItem.foodPrice
                val imgString=menuItem?.foodImage
                val uri= Uri.parse(imgString)
                Glide.with(requiredContext).load(uri).into(idpopularfoodimage)
            }
        }
    }
}

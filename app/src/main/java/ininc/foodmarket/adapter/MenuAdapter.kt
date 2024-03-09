package ininc.foodmarket.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ininc.foodmarket.DetailsActivity
import ininc.foodmarket.databinding.MenuItemBinding

class MenuAdapter(private val menuItemsName: List<String>, private val menuItemPrice: List<String>, private val menuImage: List<Int>,private val requiredContext:Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private lateinit var binding: MenuItemBinding
    private val itemClickListener:OnClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):MenuViewHolder{
        binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItemsName.size
    inner class MenuViewHolder(private val binding:MenuItemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position=adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    itemClickListener?.onItemClick(position)
                }
                val intent= Intent(requiredContext,DetailsActivity::class.java)
                intent.putExtra("MenuItemName",menuItemsName.get(position))
                intent.putExtra("MenuImage",menuImage.get(position))
                requiredContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                idmenufoodname.text=menuItemsName[position]
                idmenuprice.text=menuItemPrice[position]
                idmenuimage.setImageResource(menuImage[position])
            }
        }

    }
    interface OnClickListener {
        fun onItemClick(position: Int) {

        }
    }

}



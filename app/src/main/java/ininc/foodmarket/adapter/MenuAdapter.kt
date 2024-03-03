package ininc.foodmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ininc.foodmarket.databinding.MenuItemBinding

class MenuAdapter(private val menuItemsName: List<String>, private val menuItemPrice: List<String>, private val menuImage: List<Int>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):MenuViewHolder{
        val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItemsName.size
    inner class MenuViewHolder(private val binding:MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                idmenufoodname.text=menuItemsName[position]
                idmenuprice.text=menuItemPrice[position]
                idmenuimage.setImageResource(menuImage[position])

            }
        }

    }

}
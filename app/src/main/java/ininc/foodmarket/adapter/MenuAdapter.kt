package ininc.foodmarket.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ininc.foodmarket.DetailsActivity
import ininc.foodmarket.databinding.MenuItemBinding
import ininc.foodmarket.model.MenuItem

class MenuAdapter(private val menuItems:List<MenuItem>,private val requiredContext:Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private lateinit var binding: MenuItemBinding
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):MenuViewHolder{
        binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItems.size
    inner class MenuViewHolder(private val binding:MenuItemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position=adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    openDetailsActivity(position)
                }
//                val intent= Intent(requiredContext,DetailsActivity::class.java)
//                intent.putExtra("MenuItemName",menuItemsName.get(position))
//                intent.putExtra("MenuImage",menuImage.get(position))
//                requiredContext.startActivity(intent)
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem=menuItems[position]
            //a intent to open details activity and pass data
            val intent=Intent(requiredContext,DetailsActivity::class.java).apply {
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
                idmenufoodname.text=menuItem.foodName
                idmenuprice.text=menuItem.foodPrice
                val uri= Uri.parse(menuItem.foodImage)
                Glide.with(requiredContext).load(uri).into(idmenuimage)
            }
        }

    }
}



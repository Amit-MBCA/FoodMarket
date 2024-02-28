package ininc.foodmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ininc.foodmarket.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>,private val prices:List<String>,private val images:List<Int>): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item=items[position]
        val price=prices[position]
        val image=images[position]
        holder.bind(item,price,image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding:PopularItemBinding) :RecyclerView.ViewHolder(binding.root){
        private val imageView=binding.idpopularfoodimage
        fun bind(item: String, price: String, image: Int) {
            binding.idfoodnamepopular.text=item
            binding.idpricepopular.text=price
            binding.idpopularfoodimage
            imageView.setImageResource(image)
        }

    }
}
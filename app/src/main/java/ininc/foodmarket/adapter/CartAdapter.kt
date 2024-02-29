package ininc.foodmarket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ininc.foodmarket.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>,private val cartItemPrices:MutableList<String>,private var cartImages:MutableList<Int>):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemQuantities=IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding=CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CartViewHolder(private val binding: CartItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply{
                val quantity=itemQuantities[position]
                idcartfoodname.text=cartItems[position]
                idcartprice.text=cartItemPrices[position]
                idCartItemImage.setImageResource(cartImages[position])
                idQuanttv.text=quantity.toString()

                idMinusButton.setOnClickListener {
                    decreaseButton(itemQuantities[position])
                }
                idPlusButton.setOnClickListener{
                    increaseButton(itemQuantities[position])
                }
                idTrashButton.setOnClickListener {
                    val itemPosition=adapterPosition
                    if(itemPosition!=RecyclerView.NO_POSITION){
                        deleteButton(itemPosition)
                    }

                }
            }
        }
        private fun decreaseButton(position: Int){
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.idQuanttv.text=itemQuantities[position].toString()
            }
        }
        private fun increaseButton(position: Int){
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.idQuanttv.text=itemQuantities[position].toString()
            }
        }
        private fun deleteButton(position: Int){

            cartItems.removeAt(position)
            cartImages.removeAt(position)
            cartItemPrices.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItems.size)
        }

    }
}
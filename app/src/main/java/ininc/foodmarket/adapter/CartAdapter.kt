package ininc.foodmarket.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.databinding.CartItemBinding

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private var cartImages: MutableList<String>,
    private var cartDesc: MutableList<String>,
    private val cartQuantity: MutableList<Int>,
    private val cartIngredients:MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val auth = FirebaseAuth.getInstance()
    init {
        val database=FirebaseDatabase.getInstance()
        val userId=auth.currentUser?.uid?:""
        val cartItemNumber=cartItems.size
        itemQuantities=IntArray(cartItemNumber){1}
        cartItemReference=database.reference.child("user").child("buyer").child(userId).child("CartItems")
    }
    companion object{
        private var itemQuantities:IntArray = intArrayOf()
        private lateinit var cartItemReference:DatabaseReference
    }
//    private val itemQuantities = IntArray(cartItems.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity= mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                idcartfoodname.text = cartItems[position]
                idcartprice.text = cartItemPrices[position]
                val uriString=cartImages[position]
                val uri= Uri.parse(uriString)
                Glide.with(context).load(uri).into(idCartItemImage)
//                idCartItemImage.setImageResource(cartImages[position])
                idQuanttv.text = quantity.toString()

                idMinusButton.setOnClickListener {
                    decreaseButton(itemQuantities[position])
                }
                idPlusButton.setOnClickListener {
                    increaseButton(itemQuantities[position])
                }
                idTrashButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteButton(itemPosition)
                    }

                }
            }
        }

        private fun decreaseButton(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position]= itemQuantities[position]
                binding.idQuanttv.text = itemQuantities[position].toString()
            }
        }

        private fun increaseButton(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                cartQuantity[position]= itemQuantities[position]
                binding.idQuanttv.text = itemQuantities[position].toString()
            }
        }

        private fun deleteButton(position: Int) {
              val positionRetrieve = position
              getUniqueKeyAtPosition(positionRetrieve){ uniqueKey ->
                    if(uniqueKey!=null){
                        removeItem(position,uniqueKey)
                    }
              }
//            cartItems.removeAt(position)
//            cartImages.removeAt(position)
//            cartItemPrices.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, cartItems.size)
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey != null){
                cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImages.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartDesc.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartIngredients.removeAt(position)
                    Toast.makeText(context,"Item removed successfully",Toast.LENGTH_SHORT).show()
                    //update item quantity
                    itemQuantities= itemQuantities.filterIndexed { index, i -> index!=position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to delete item",Toast.LENGTH_SHORT).show()
                }
            }
            else{
//                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int,onComplete:(String?) -> Unit) {
            cartItemReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
                    //loop for snapshot children
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if(index==positionRetrieve){
                            uniqueKey=dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

    }
}
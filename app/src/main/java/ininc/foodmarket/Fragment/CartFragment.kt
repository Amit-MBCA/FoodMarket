package ininc.foodmarket.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.CongratsBottomSheet
import ininc.foodmarket.PayOutActivity
import ininc.foodmarket.R
import ininc.foodmarket.adapter.CartAdapter
import ininc.foodmarket.databinding.FragmentCartBinding
import ininc.foodmarket.model.CartItems


class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames:MutableList<String>
    private lateinit var foodPrices:MutableList<String>
    private lateinit var foodDesc:MutableList<String>
    private lateinit var foodImagesUri:MutableList<String>
    private lateinit var foodIngredients:MutableList<String>
    private lateinit var quantity:MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(inflater,container,false)

        auth=FirebaseAuth.getInstance()
        retrieveCartItems()
//        val cartFoodNames=listOf("Pizza","Sandwich","French Fries","Kimchi","Fried Rice","Momos","Burger")
//        val cartItemPrice= listOf("₹199","₹40","₹60","₹150","₹40","₹60","₹35")
//        val cartImages= listOf(R.drawable.pizzaimg,R.drawable.sandwichimg,R.drawable.frenchfriesimg,R.drawable.kimchiimg,R.drawable.friedriceimg,R.drawable.momosimg,R.drawable.burgerimg)


//        val quantity=listOf(1,2,3,4,5,6)

        binding.idproceedbtn.setOnClickListener {
            //get ordered item details before proceeding to check out
            getOrderItemsDetail()

        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference:DatabaseReference=database.reference.child("user").child(userId).child("CartItems")
        val foodName= mutableListOf<String>()
        val foodPrice= mutableListOf<String>()
        val foodImage= mutableListOf<String>()
        val foodDesc= mutableListOf<String>()
        val foodIngredients= mutableListOf<String>()
        //get items quantity
        val foodQuantities=cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    //get the cartItems to the respective list
                    val orderItems=foodSnapshot.getValue(CartItems::class.java)
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodDesc?.let { foodDesc.add(it) }
                    orderItems?.foodIngredients?.let { foodIngredients.add(it) }
                }
                orderNow(foodName,foodPrice,foodDesc,foodImage,foodIngredients,foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Order Failed, Please try again",Toast.LENGTH_SHORT).show()

            }

        })

    }

    private fun orderNow(foodName: MutableList<String>, foodPrice: MutableList<String>, foodDesc: MutableList<String>, foodImage: MutableList<String>, foodIngredients: MutableList<String>, foodQuantities: MutableList<Int>) {
        if (isAdded && context!=null){
            val intent=Intent(requireContext(),PayOutActivity::class.java)
            intent.putExtra("FoodItemName",foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice",foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemDesc",foodDesc as ArrayList<String>)
            intent.putExtra("FoodItemImage",foodImage as ArrayList<String>)
            intent.putExtra("FoodItemIngredients",foodIngredients as ArrayList<String>)
            intent.putExtra("FoodItemQuantities",foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        //database reference to the database
        database=FirebaseDatabase.getInstance()
        userId=auth.currentUser?.uid?:""
        val foodReference:DatabaseReference=database.reference.child("user").child(userId).child("CartItems")
        //list to store cart items
        foodNames= mutableListOf()
        foodPrices= mutableListOf()
        foodImagesUri= mutableListOf()
        foodDesc= mutableListOf()
        foodIngredients= mutableListOf()
        quantity= mutableListOf()

        //fetch data from the database
        foodReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    //get the cartItems object from the child node
                    val cartItems=foodSnapshot.getValue(CartItems::class.java)
                    //add cart items details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDesc?.let { foodDesc.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredients?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"data not fetch",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        cartAdapter=CartAdapter(requireContext(),foodNames,foodPrices,foodImagesUri,foodDesc,quantity,foodIngredients)
        binding.idcartrecyclerview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.idcartrecyclerview.adapter=cartAdapter
    }

    companion object {

    }
}
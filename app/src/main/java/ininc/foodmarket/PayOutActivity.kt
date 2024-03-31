package ininc.foodmarket

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.databinding.ActivityPayOutBinding
import ininc.foodmarket.model.OrderDetails

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPayOutBinding

    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phone:String
    private lateinit var totalAmount:String
    private lateinit var foodItemName:ArrayList<String>
    private lateinit var foodItemPrice:ArrayList<String>
    private lateinit var foodItemDesc:ArrayList<String>
    private lateinit var foodItemImage:ArrayList<String>
    private lateinit var foodItemIngredients:ArrayList<String>
    private lateinit var foodItemQuantities:ArrayList<Int>
    private lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().reference
        //set User Data
        setUserData()
        //get user details from firebase
        val intent=intent
        foodItemName=intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemImage=intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDesc=intent.getStringArrayListExtra("FoodItemDesc") as ArrayList<String>
        foodItemIngredients=intent.getStringArrayListExtra("FoodItemIngredients") as ArrayList<String>
        foodItemQuantities=intent.getIntegerArrayListExtra("FoodItemQuantities") as ArrayList<Int>
        foodItemPrice=intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        totalAmount=calculateTotalAmount().toString()
//        binding.idtotal.isEnabled=false
        binding.idtotal.setText(totalAmount)

        binding.idplaceorderbtn.setOnClickListener {
            //get data from textViews
            name=binding.idname.text.toString().trim()
            address=binding.idaddress.text.toString().trim()
            phone=binding.idmobileno.text.toString().trim()
            if(!(name.isBlank()&&address.isBlank()&&phone.isBlank())){
                placeOrder()
            }
            else{
                Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show()
            }

        }
        binding.idbackbtn.setOnClickListener {
            finish()
        }

    }

    private fun placeOrder() {
        userId=auth.currentUser?.uid?:""
        val time=System.currentTimeMillis()
        val itemPushKey=databaseReference.child("OrderDetails").push().key
        val orderDetails= OrderDetails(userId,name,foodItemName,foodItemImage,foodItemPrice,foodItemQuantities,address,totalAmount,phone,false,false,itemPushKey,time)
        val orderReference=databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog=CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            addOrderToHistory(orderDetails)
            removeItemFromCart()

//            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed to take order",Toast.LENGTH_SHORT).show()
            }



    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemReference=databaseReference.child("user").child(userId).child("CartItems")
        cartItemReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmt=0
        for(i in 0 until  foodItemPrice.size){
            var price:String=foodItemPrice[i]
            val lastChar=price.last()
            val priceIntValue=if(lastChar == '$'){
                price.dropLast(1)
                price.toInt()
            }else{
                price.toInt()
            }
            var quantity=foodItemQuantities[i]
            totalAmt+=priceIntValue*quantity
            Log.d("PriceValue","priceValue + $totalAmt")
        }
        return totalAmt
    }

    private fun setUserData(){
        val user=auth.currentUser
        if(user != null){
            val userId=user.uid
            val userReference=databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val name=snapshot.child("name").getValue(String::class.java)?:""
                        val address=snapshot.child("address").getValue(String::class.java)?:""
                        val phone=snapshot.child("phone").getValue(String::class.java)?:""
//                        val address=snapshot.child("").getValue(String::class.java)?:""
//                        val address=snapshot.child("address").getValue(String::class.java)?:""
                        binding.apply {
                            idname.setText(name)
                            idaddress.setText(address)
                            idmobileno.setText(phone)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}
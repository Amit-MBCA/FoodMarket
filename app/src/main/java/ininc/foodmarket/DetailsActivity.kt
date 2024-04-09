package ininc.foodmarket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ininc.foodmarket.Fragment.CartFragment
import ininc.foodmarket.Fragment.HomeFragment
import ininc.foodmarket.databinding.ActivityDetailsBinding
import ininc.foodmarket.model.CartItems

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding
    private var foodName:String?=null
    private var foodImage:String?=null
    private var foodPrice:String?=null
    private var foodDesc:String?=null
    private var foodIngred:String?=null

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        foodName=intent.getStringExtra("MenuItemName")
        foodPrice=intent.getStringExtra("MenuItemPrice")
        foodDesc=intent.getStringExtra("MenuItemDesc")
        foodIngred=intent.getStringExtra("MenuItemIngredient")
        foodImage=intent.getStringExtra("MenuItemImage")
        with(binding){
            idtvfoodname.text=foodName
//            iddetailfoodimage.setImageURI(Uri.parse(foodImage))
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(iddetailfoodimage)
            iddesctv.text=foodDesc
            idingredientstv.text=foodIngred

        }
        binding.idimgbtndetail.setOnClickListener {
            finish()
        }
        binding.addtocartbtn.setOnClickListener {
                addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database:DatabaseReference=FirebaseDatabase.getInstance().reference
        val userId=auth.currentUser?.uid?:""
        //Create a cart item object
        val cartItem=CartItems(foodName.toString(),foodPrice.toString(),foodDesc.toString(),foodImage.toString(),1)
        //save data to cart items to database
        database.child("user").child("buyer").child("CartItems").child(userId).push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Item Added To Cart",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Failed to adding cart item",Toast.LENGTH_SHORT).show()
        }
        val intent=Intent(this@DetailsActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
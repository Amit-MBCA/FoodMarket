package ininc.foodmarket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ininc.foodmarket.adapter.FreshItemAdapter
import ininc.foodmarket.adapter.MenuAdapter
import ininc.foodmarket.databinding.ActivityFreshItemsBinding
import ininc.foodmarket.databinding.FragmentHomeBinding
import ininc.foodmarket.model.MenuItem

class FreshItemsActivity : AppCompatActivity() {
    private val binding:ActivityFreshItemsBinding by lazy{
        ActivityFreshItemsBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
    private lateinit var adapter: MenuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        retrieveAndDisplayFreshItems()

    }
    private fun retrieveAndDisplayFreshItems() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu").child("fresh")
        menuItems= mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem.let{
                        menuItems.add(it!!)
                    }
                    //once data received, set to adapter
                    setFreshItemAdapter(menuItems)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setFreshItemAdapter(menuItems: List<MenuItem>) {
        val adapter=FreshItemAdapter(menuItems,this)
        binding.idpopularrecyclerview.layoutManager= LinearLayoutManager(this)
        binding.idpopularrecyclerview.adapter=adapter
    }
}
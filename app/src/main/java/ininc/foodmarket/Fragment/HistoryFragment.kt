package ininc.foodmarket.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import ininc.foodmarket.R
import ininc.foodmarket.RecentOrderItems
import ininc.foodmarket.adapter.BuyAgainAdapter
import ininc.foodmarket.databinding.FragmentHistoryBinding
import ininc.foodmarket.model.OrderDetails
import java.io.Serializable


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItems: ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //retrieve and display the user data
        retrieveBuyHistory()

        binding.idrecentbuyitem.setOnClickListener {
            seeRecentBuyItems()
        }
        binding.receivedbtn.setOnClickListener {
            UpdateOrderStatus()
        }
//        setUpRecyclerView()
        return binding.root
    }

    private fun UpdateOrderStatus() {
        val itemPushKey=listOfOrderItems[0].itemPushKey
        val completeOrderReference=database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
        binding.idorderstatus.background.setTint(Color.GREEN)
        binding.receivedbtn.visibility = View.INVISIBLE
    }

    private fun seeRecentBuyItems() {
        listOfOrderItems.firstOrNull()?.let { recentBuy ->
            val intent= Intent(requireContext(),RecentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem",listOfOrderItems)
            startActivity(intent)
        }
    }

    private fun retrieveBuyHistory() {
        binding.idrecentbuyitem.isVisible = false
        binding.idrecentbuytv.isVisible = false
        userId = auth.currentUser?.uid ?: ""
        val buyItemReference: DatabaseReference =
            database.reference.child("user").child("BuyHistory")
        val sortingQuery: Query = buyItemReference.orderByChild("currentTime")
        sortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItems.add(it)
                    }
                }
                listOfOrderItems.reverse()
                if (listOfOrderItems.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setDataInPreviousBuyItem()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun setDataInRecentBuyItem() {
        binding.idrecentbuyitem.visibility = View.VISIBLE
        binding.idrecentbuytv.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItems.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                idbuyagainfoodname.text = it.foodNames?.firstOrNull() ?: ""
                idbuyagainfoodprice.text = it.foodPrices?.firstOrNull() ?: ""
//                idbuyagainfoodname.text=it.foodNames?.firstOrNull()?:""
                val imageString = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(imageString)
                Glide.with(requireContext()).load(uri).into(idbuyagainfoodimage)
                val isOrderAccepted=listOfOrderItems[0].orderAccepted
                Log.d("order","accepted: $isOrderAccepted")
//                if (isOrderAccepted!!){
//                    idorderstatus.background.setTint(Color.GREEN)
                    receivedbtn.visibility=View.VISIBLE
//                }

                if (listOfOrderItems.isNotEmpty()) {
                    listOfOrderItems.reverse()
                }
            }
        }
    }

    private fun setDataInPreviousBuyItem() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItems.size) {
            listOfOrderItems[i].foodNames?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfOrderItems[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrice.add(it) }
            listOfOrderItems[i].foodImages?.firstOrNull()?.let { buyAgainFoodImage.add(it) }
        }
        val rv=binding.idbuyagainrv
        rv.layoutManager=LinearLayoutManager(requireContext())
        buyAgainAdapter=BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage,requireContext())
        rv.adapter=buyAgainAdapter
    }
}

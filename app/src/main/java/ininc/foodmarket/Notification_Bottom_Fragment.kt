package ininc.foodmarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ininc.foodmarket.adapter.NotificationAdapter
import ininc.foodmarket.databinding.FragmentNotificationBottomBinding

class Notification_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotificationBottomBinding.inflate(inflater,container,false)
        val notifications= listOf("Your order has been cancelled successfully","Order has been taken by the driver","Congrats your order placed")
        val notiimages= listOf(R.drawable.logowithbg,R.drawable.logowithbg,R.drawable.logowithbg)
        val adapter=NotificationAdapter(ArrayList(notifications), ArrayList(notiimages))
        binding.idnotifirv.layoutManager=LinearLayoutManager(requireContext())
        binding.idnotifirv.adapter=adapter
        return binding.root
    }

    companion object {

    }
}
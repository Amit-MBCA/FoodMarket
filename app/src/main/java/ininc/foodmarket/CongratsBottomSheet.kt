package ininc.foodmarket

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ininc.foodmarket.Fragment.HomeFragment
import ininc.foodmarket.databinding.FragmentCongratsBottomSheetBinding


class CongratsBottomSheet : BottomSheetDialogFragment() {
   private lateinit var binding: FragmentCongratsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCongratsBottomSheetBinding.inflate(layoutInflater,container,false)
//        return inflater.inflate(R.layout.fragment_congrats_bottom_sheet, container, false)
        binding.idgohomebtn.setOnClickListener {
            val intent=Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
//            dismiss()

        }
        return binding.root
    }

    companion object {

    }
}
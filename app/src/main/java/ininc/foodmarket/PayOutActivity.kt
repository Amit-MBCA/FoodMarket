package ininc.foodmarket

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ininc.foodmarket.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idplaceorderbtn.setOnClickListener {
            val bottomSheetDialog=CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
    }
}
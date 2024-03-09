package ininc.foodmarket

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ininc.foodmarket.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val foodName=intent.getStringExtra("MenuItemName")
        val foodImage=intent.getIntExtra("MenuImage",0)
        binding.idtvfoodname.text=foodName
        binding.iddetailfoodimage.setImageResource(foodImage)
        binding.idimgbtndetail.setOnClickListener {
//            val intent=Intent(this,MainActivity::class.java)
//            startActivity(intent)
            finish()

        }

    }
}
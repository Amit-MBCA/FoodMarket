package ininc.foodmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ininc.foodmarket.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private val binding:ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.alreadyaccountbtn.setOnClickListener {
            val it= Intent(this,LoginActivity::class.java)
            startActivity(it)
            finish()
        }
    }
}
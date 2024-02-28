package ininc.foodmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ininc.foodmarket.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.donothavebutton.setOnClickListener {
            val it=Intent(this,SigninActivity::class.java)
            startActivity(it)
        }
        binding.loginButton.setOnClickListener {
            val it=Intent(this,ChooseLocationActivity::class.java)
            startActivity(it)
            finish()
        }
    }
}

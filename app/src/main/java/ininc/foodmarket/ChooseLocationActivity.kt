package ininc.foodmarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import ininc.foodmarket.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding:ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationlist= arrayOf("Jind","Kaithal","Karnal","Hisar","Rohtak")
        val adapter=ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,locationlist);
        val autoCompleteTextView=binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)

        binding.iddesignedbytv.setOnClickListener {
            val it=Intent(this,MainActivity::class.java)
            startActivity(it)
        }
    }
}
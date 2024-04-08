package ininc.foodmarket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import ininc.foodmarket.databinding.ActivitySigninBinding
import ininc.foodmarket.model.UserModel

class SigninActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference  
    private lateinit var googleSignInClient:GoogleSignInClient

    private val binding:ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //initialize firebase database
        auth= Firebase.auth
        database= Firebase.database.reference
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions)
        binding.createaccountbtn.setOnClickListener {
            username=binding.idusername.text.toString()
            email=binding.idemail.text.toString().trim()
            password=binding.idpassword.text.toString().trim()
            if(!(email.isBlank()||username.isBlank()||password.isEmpty())){
                createAccount(email,password)
            }
            else{
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            }

        }
        binding.alreadyaccountbtn.setOnClickListener {
            val it= Intent(this,LoginActivity::class.java)
            startActivity(it)
            finish()
        }
        binding.idgooglebtn.setOnClickListener {
            val signInIntent=googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }
    //Launcher for google sign in
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode== Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account:GoogleSignInAccount?=task.result
                val credential=GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this,"SignedIn Successfully",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Sign-in Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else{
            Toast.makeText(this,"Sign-in Failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                saveUserData()
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }

        }
    }

    private fun saveUserData() {
        username=binding.idusername.text.toString()
        email=binding.idemail.text.toString().trim()
        password=binding.idpassword.text.toString().trim()

        val user=UserModel(username,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        //Save data to firebase database
        database.child("user").child("buyer").child(userId).setValue(user)
    }
}
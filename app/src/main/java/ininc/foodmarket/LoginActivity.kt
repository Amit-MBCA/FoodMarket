package ininc.foodmarket

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import ininc.foodmarket.databinding.ActivityLoginBinding
import ininc.foodmarket.model.UserModel

class LoginActivity : AppCompatActivity() {
    private var userName:String?=null
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        auth= Firebase.auth
        database= Firebase.database.reference
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions)
        binding.donothavebutton.setOnClickListener {
            val it=Intent(this,SigninActivity::class.java)
            startActivity(it)
        }
//        binding.idpassword.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_UP) {
//                // Calculate the position of right drawable
//                val drawableRightEnd = binding.idpassword.compoundDrawablesRelative[2]?.bounds?.right ?: 0
//                // Check if touch event is within the bounds of the right drawable
//                if (event.rawX >= (drawableRightEnd - binding.idpassword.totalPaddingEnd)
//                    && event.rawX <= (drawableRightEnd)) {
//                    // Toggle password visibility
//                    if (binding.idpassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//                        // Hide the password
//                        binding.idpassword.inputType =
//                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//                        binding.idpassword.setSelection(binding.idpassword.text.length)
//                        // Change right drawable to hideicon
//                        binding.idpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                            ContextCompat.getDrawable(this, R.drawable.passicon),
//                            null,
//                            ContextCompat.getDrawable(this, R.drawable.hideicon),
//                            null
//                        )
//                    } else {
//                        // Show the password
//                        binding.idpassword.inputType =
//                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                        binding.idpassword.setSelection(binding.idpassword.text.length)
//                        // Change right drawable to showicon
//                        binding.idpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                            ContextCompat.getDrawable(this, R.drawable.passicon),
//                            null,
//                            ContextCompat.getDrawable(this, R.drawable.hideicon),
//                            null
//                        )
//                    }
//                    return@setOnTouchListener true
//                }
//            }
//            return@setOnTouchListener false
//        }
        binding.idgooglebtn.setOnClickListener {
            val signInIntent=googleSignInClient.signInIntent
            launcher.launch(signInIntent)

        }
        binding.loginButton.setOnClickListener {
            email=binding.idmail.text.toString().trim()
            password=binding.idpassword.toString().trim()
            if(email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                createUser(email,password)
            }

        }
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode == Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account: GoogleSignInAccount?=task.result
                val credential= GoogleAuthProvider.getCredential(account?.idToken,null)
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
    private fun createUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->
            if(task.isSuccessful){
                val user=auth.currentUser
                updateUI(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        saveUserData()
                        val user=auth.currentUser
                        updateUI(user)
                    }
                    else{
                        Toast.makeText(this,"Log-in Failed",Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun saveUserData() {
        email=binding.idmail.text.toString().trim()
        password=binding.idpassword.toString().trim()

        val user= UserModel(userName,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        //save data into database
        database.child("user").child("buyer").child(userId).setValue(user)
    }

    private fun updateUI(user: FirebaseUser?) {
        val it=Intent(this,MainActivity::class.java)
        startActivity(it)
        finish()
    }

    //Check if user if already logged in
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}

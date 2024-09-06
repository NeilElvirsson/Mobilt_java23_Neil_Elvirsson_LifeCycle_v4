package com.example.lifecyclev4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginEmail: String
    private val sharedPreFile = "com.example.lifecyclev4.PREFERENCE_FILE_KEY"
    /*
    private val hardcodedUsername = "admin";
    private val hardcodedPassword = "1234";


     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // write()

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.email);
        val passwordEditText = findViewById<EditText>(R.id.password);
        val loginButton = findViewById<Button>(R.id.registerButton);

        val sharedPreferences = getSharedPreferences(sharedPreFile, MODE_PRIVATE)
        this.loginEmail = sharedPreferences.getString("email", "") ?: ""

        emailEditText.setText(this.loginEmail)

        loginButton.setOnClickListener {
            this.loginEmail = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(this.loginEmail, password)
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

   private fun loginUser(email: String, password: String) {
       if(email.isNotEmpty() && password.isNotEmpty()) {
           auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {task ->
               if(task.isSuccessful) {

                   Toast.makeText(this, "Login succeeded!", Toast.LENGTH_SHORT).show()
                   val intent = Intent(this, MainActivity2::class.java)
                   startActivity(intent)
               } else {
                   Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show()
               }

           }

       } else {
           Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_SHORT).show()
       }
   }


}

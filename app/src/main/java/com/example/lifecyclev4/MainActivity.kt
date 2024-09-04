package com.example.lifecyclev4

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private val hardcodedUsername = "admin";
    private val hardcodedPassword = "1234";





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // write()

        val usernameField = findViewById<EditText>(R.id.userName);
        val passwordField = findViewById<EditText>(R.id.password);
        val loginButton = findViewById<Button>(R.id.registerButton);

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if(username == hardcodedUsername && password == hardcodedPassword) {

                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            } else {
                Log.d("Main activity", "Fel användarnamn eller lösenord")
            }
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Create a new user with a first and last name


}

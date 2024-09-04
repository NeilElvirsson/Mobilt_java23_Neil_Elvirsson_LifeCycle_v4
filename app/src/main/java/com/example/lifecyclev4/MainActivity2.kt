package com.example.lifecyclev4

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity2 : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var name: String
    private lateinit var lastName: String
    private lateinit var phone: String
    private lateinit var radioGrop: RadioGroup
    private lateinit var selectedRadioButton: RadioButton


    private val sharedPreFile = "com.example.lifecyclev4.PREFERENCE_FILE_KEY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        //-------------Top--------------------

        val homeButton = findViewById<Button>(R.id.homebutton)

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }





        val sharedPreferences = getSharedPreferences(sharedPreFile, MODE_PRIVATE)

        this.name = sharedPreferences.getString("name", "") ?: ""
        this.lastName = sharedPreferences.getString("lastName", "") ?: ""
        this.phone = sharedPreferences.getString("phone", "") ?:""
        val savedRadioButtonId = sharedPreferences.getInt("selectedRadioButton", -1)


        val nameEditText = findViewById<TextView>(R.id.name)
        val lastNameEditText = findViewById<TextView>(R.id.lastName)
        val phoneEditText = findViewById<TextView>(R.id.phone)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val registerButton = findViewById<Button>(R.id.registerButton)


        nameEditText.text = this.name
        lastNameEditText.text = this.lastName
        phoneEditText.text = this.phone

        if(savedRadioButtonId != 1) {
            radioGroup.check(savedRadioButtonId)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedRadioButton = findViewById(R.id.radioGroup)
            val selectedText = selectedRadioButton.text.toString()
            Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()


        }

        //------Registerbutton below-------------------

        registerButton.setOnClickListener {

            this.name = nameEditText.text.toString()
            this.lastName = lastNameEditText.text.toString()
            this.phone = phoneEditText.text.toString()

            if (this.name.isEmpty() || this.lastName.isEmpty() || this.phone.isEmpty() || radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please fill in all fields and select a radio button", Toast.LENGTH_SHORT).show()

            } else {
                with(sharedPreferences.edit()) {

                    putString("name", this@MainActivity2.name)
                    putString("lastName", this@MainActivity2.lastName)
                    putString("phone", this@MainActivity2.phone)
                    putInt("selectedRadioButtonID", radioGroup.checkedRadioButtonId)
                    apply()
                }
                write()

            }
        }

        //-------------------------------------Bottom-------------------
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun write() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to this.name,
            "last" to this.lastName,
            "phone" to this.phone

        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", this.name)
        outState.putString("lastName", this.lastName)
        outState.putString("phone", this.phone)
        outState.putInt("selectedRadioButton", findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId)

    }




}
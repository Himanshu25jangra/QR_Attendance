package com.example.qrattendance

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nameEditText = findViewById<TextInputEditText>(R.id.userNameRegistrationActivity)
        val emailEditText = findViewById<TextInputEditText>(R.id.userEmailRegistrationActivity)
        val passwordEditText = findViewById<TextInputEditText>(R.id.userPassRegistrationActivity)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            var name = nameEditText.text.toString()
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !isValidEmail(email)) {
                Toast.makeText(this, "Please Verify all fields", Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.registerButton).visibility = View.VISIBLE
            } else {
                //Registeration function call
                val dbHelper = DBHelper(this)
                dbHelper.registerUser(name, email, password)
                //Setting vTextInputEditText Values to null after clicking on register button
                nameEditText.setText("")
                emailEditText.setText("")
                passwordEditText.setText("")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val uptoin = findViewById<TextView>(R.id.signInText)
        uptoin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        }
    }

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return emailRegex.matches(email)
}
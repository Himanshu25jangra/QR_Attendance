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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<TextView>(R.id.register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        //login
        var emailEditText = findViewById<TextInputEditText>(R.id.emailLoginActivity)
        var passwordEditText = findViewById<TextInputEditText>(R.id.passwordLoginActivity)
        var emaill = emailEditText.text.toString()
        var passwordd = passwordEditText.text.toString()
        if (emaill.isEmpty() || passwordd.isEmpty() || !isValidEmail(emaill)) {
            Toast.makeText(this, "Please Verify all fields", Toast.LENGTH_SHORT).show()
        }
            findViewById<Button>(R.id.loginButton).setOnClickListener {
                val dbHelper = DBHelper(this)
                var email = emailEditText.text.toString()
                var password = passwordEditText.text.toString()
                val loggedin: Boolean = dbHelper.loginUser(email, password)
                if (loggedin) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("key_email", email)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
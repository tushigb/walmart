package com.miu.wallmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        button_register.setOnClickListener {
            if (isValidated()) {
                Toast.makeText(
                    applicationContext,
                    "Account created successfully",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent()
                intent.putExtra("user", user)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun setInputValues() {
        user.email = email.text.toString()
        user.firstname = firstname.text.toString()
        user.lastname = lastname.text.toString()
        user.password = password.text.toString()
    }

    private fun isValidated(): Boolean {
        setInputValues()
        if (user.firstname.isEmpty() || user.lastname.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please fill all required fields",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}
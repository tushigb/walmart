package com.miu.wallmart

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var users = ArrayList<User>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        textView2.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        initializeData()

        button_signin.setOnClickListener {
            if (email.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Email & password cannot be blank!",
                    Toast.LENGTH_LONG
                ).show()
            } else if (validate(email.text.toString(), password.text.toString())) {
                val shoppingIntent = Intent(this, ShoppingCategory::class.java)
                shoppingIntent.putExtra("email", email.text.toString())
                startActivity(shoppingIntent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Username or password invalid",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        button2.setOnClickListener {
            val signUpIntent = Intent(this, RegisterActivity::class.java)
            resultLauncher.launch(signUpIntent)
        }
    }

    private fun validate(email: String, password: String): Boolean {
        return users.find { user -> user.email == email && user.password == password } != null
    }

    private fun findUser(email: String): User {
        val user = users.find { user -> user.email == email }
        return if (user != null) return user
        else User()
    }

    fun forgotPassword(view: View) {
        val inputEmail = email.text.toString()
        val user = findUser(inputEmail)

        if (user.password.isNotEmpty()) {
            val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(inputEmail))
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Recover your password")
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Your password is: ${user.password}")
            startActivity(Intent.createChooser(sendIntent, "Send Email Using: "))
        } else {
            Toast.makeText(
                applicationContext,
                "Can not found user with email: $inputEmail",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val serUser = data!!.getSerializableExtra("user")
                val newUser = serUser as User
                users.add(newUser)
            }
        }

    private fun initializeData() {
        val user1 = User("Tushig", "Battumur", "tbattumur@miu.edu", "tushig123")
        val user2 = User("Test1", "Test1", "Test1@miu.edu", "test1")
        val user3 = User("Test2", "Test2", "Test2@miu.edu", "test2")
        val user4 = User("Test3", "Test3", "Test3@miu.edu", "test3")
        val user5 = User("Test4", "Test4", "Test4@miu.edu", "test4")

        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)
        users.add(user5)
    }
}
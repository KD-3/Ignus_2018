package org.ignus.ignus18.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.ignus.ignus18.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = Color.parseColor("#37474F")


        login.setOnClickListener({ login() })
        forgotPass.setOnClickListener({ forgotPass() })
    }

    private fun login() {
        val usrName = username.text.toString()
        val password = password.text.toString()

        Toast.makeText(this, "Already Logged In : )", Toast.LENGTH_SHORT).show()
    }

    private fun forgotPass() {
        Toast.makeText(this, "Throw user to website : (", Toast.LENGTH_SHORT).show()
    }
}

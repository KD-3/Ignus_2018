package org.ignus.ignus18.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_on_start.*
import org.ignus.ignus18.R
import java.util.*
import kotlin.concurrent.timerTask

class OnStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_start)

        splashScreen()
    }

    private fun splashScreen(){
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        layout_login_screen.visibility = View.GONE
        layout_splash_screen.visibility = View.VISIBLE
        layout_notification_access.visibility = View.GONE

        val t = Timer()
        t.schedule(timerTask {
            runOnUiThread({ notificationAccess() })
        },3000)
    }

    private fun notificationAccess(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#FFE737") }

        layout_splash_screen.visibility = View.GONE
        layout_login_screen.visibility = View.GONE
        layout_notification_access.visibility = View.VISIBLE

        a1_yes.setOnClickListener({
            loginScreen()
        })
    }

    private fun loginScreen(){
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        layout_splash_screen.visibility = View.GONE
        layout_login_screen.visibility = View.VISIBLE
        layout_notification_access.visibility = View.GONE

        a1_login.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })

        a1_skip.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        })

    }
}

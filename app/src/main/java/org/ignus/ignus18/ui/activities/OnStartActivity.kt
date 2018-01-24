package org.ignus.ignus18.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_on_start.*
import org.ignus.ignus18.R

class OnStartActivity : AppCompatActivity() {

    companion object {
        var splashScreen = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_start)

        controller()

    }

    private fun controller() {
        if (splashScreen) splashScreen()
        else if (showNotificationWindow()) notificationAccess()
        else if (!isLoggedIn()) loginScreen()
        else startActivity(Intent(this, MainActivity::class.java))
    }

    private fun splashScreen() {
        toggleLayout('S')
        window.statusBarColor = Color.parseColor("#212121")

        splashScreen = false
        Handler().postDelayed({ controller() }, 3000)
    }

    private fun notificationAccess() {
        toggleLayout('N')
        window.statusBarColor = Color.parseColor("#FBC02D")

        a1_yes.setOnClickListener({
            controller()
        })

        a1_no.setOnClickListener({
            controller()
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("showNotification", false).apply()
        })

        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("showNotificationWindow", false).apply()
    }

    private fun loginScreen() {
        toggleLayout()
        window.statusBarColor = Color.parseColor("#00ACC1")


        a1_register.setOnClickListener({
            startActivity(Intent(this, RegisterActivity::class.java))
        })

        a1_login.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })

        a1_skip.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        })

    }

    private fun showNotificationWindow(): Boolean = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("showNotificationWindow", true)

    private fun isLoggedIn(): Boolean = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isLoggedIn", false)

    private fun toggleLayout(char: Char = 'L') {

        layout_splash_screen.visibility = View.GONE
        layout_login_screen.visibility = View.GONE
        layout_notification_access.visibility = View.GONE

        when (char) {
            'S' -> layout_splash_screen.visibility = View.VISIBLE
            'N' -> layout_notification_access.visibility = View.VISIBLE
            else -> layout_login_screen.visibility = View.VISIBLE
        }
    }
}

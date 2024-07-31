package com.rakibofc.lifeplustask.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.util.AppPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashUiActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_ui)

        Handler(Looper.getMainLooper()).postDelayed({

            // Get values from shared preference
            val isLoggedIn = AppPreference.with(applicationContext)
                .getBoolean(UserEntity.IS_LOGGED_IN_KEY, false)
            val userID = AppPreference.with(applicationContext).getLong(UserEntity.USER_ID_KEY, 0L)

            if (isLoggedIn) {
                startActivity(Intent(this@SplashUiActivity, DashboardActivity::class.java).apply {
                    putExtra(UserEntity.USER_ID_KEY, userID)
                })
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()

        }, SPLASH_DELAY)
    }
}

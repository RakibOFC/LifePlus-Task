package com.rakibofc.lifeplustask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup listeners
        setupListener()
    }

    private fun setupListener() {

        with(binding) {

            btnLogin.setOnClickListener {
                // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

            btnRegistration.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }
        }
    }
}
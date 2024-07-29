package com.rakibofc.lifeplustask.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.databinding.ActivityLoginBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup observer
        setupObserver()

        // Setup listeners
        setupListener()
    }

    private fun setupObserver() {
        viewModel.loginUser.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    binding.btnLogin.isEnabled = false
                }

                is UiState.Error -> {
                    showToast(it.message)
                    binding.btnLogin.isEnabled = true
                }

                is UiState.Success -> {
                    binding.btnLogin.isEnabled = true
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java).apply {
                        putExtra(UserEntity.USER_ID, it.data.id)
                    })
                    finish()
                }
            }
        }
    }

    private fun setupListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                userLogin()
            }

            btnRegistration.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            }
        }
    }

    private fun userLogin() {

        val userName = binding.inputUserName.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()

        if (userName.isEmpty()) {
            showError(binding.inputUserName, R.string.enter_user_name_text)
            return
        }

        if (password.isEmpty()) {
            showError(binding.inputPassword, R.string.enter_password_text)
            return
        }

        lifecycleScope.launch {
            viewModel.loginUser(userName, password)
        }
    }
}
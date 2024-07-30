package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.databinding.ActivityRegistrationBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup observer
        setupObserver()

        // Setup listeners
        setupListener()
    }

    private fun setupObserver() {

        viewModel.userNameValidation.observe(this) {
            binding.apply {
                when (it) {
                    is UiState.Error -> {
                        binding.inputUserNameLayout.endIconMode = TextInputLayout.END_ICON_NONE
                        inputUserName.error = it.message
                    }

                    is UiState.Loading -> {
                        btnRegister.isEnabled = false
                    }

                    is UiState.Success -> {
                        inputUserName.error = null
                        validInputUserNameUi(inputUserNameLayout)
                        btnRegister.isEnabled = true
                    }
                }
            }
        }

        viewModel.registerUser.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    binding.btnRegister.isEnabled = false
                }

                is UiState.Error -> {
                    showToast(it.message)
                    binding.btnRegister.isEnabled = true
                }

                is UiState.Success -> {
                    showToast(it.data)
                    // After register success back to the previous activity
                    finish()
                }
            }
        }
    }

    private fun validInputUserNameUi(inputUserNameLayout: TextInputLayout) {
        inputUserNameLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        inputUserNameLayout.setEndIconDrawable(R.drawable.baseline_check_circle_24)
        inputUserNameLayout.setEndIconTintList(
            ContextCompat.getColorStateList(
                applicationContext,
                R.color.green_tint
            )
        )
    }

    private fun setupListener() {

        binding.inputUserName.doAfterTextChanged {
            userNameValidation(it.toString())
        }

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun userNameValidation(userName: String) {
        lifecycleScope.launch {
            viewModel.userNameValidation(userName)
        }
    }

    private fun registerUser() {

        val name = binding.inputName.text.toString().trim()
        val userName = binding.inputUserName.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val phone = binding.inputPhone.text.toString().trim()

        if (name.isEmpty()) {
            showError(binding.inputName, R.string.enter_name_text)
            return
        }

        if (userName.isEmpty()) {
            showError(binding.inputUserName, R.string.enter_user_name_text)
            return
        }

        if (password.isEmpty()) {
            showError(binding.inputPassword, R.string.enter_password_text)
            return
        }

        if (phone.isEmpty()) {
            showError(binding.inputPhone, R.string.enter_phone_text)
            return
        }

        lifecycleScope.launch {
            val user =
                UserEntity(name = name, userName = userName, password = password, phone = phone)
            viewModel.registerUser(user)
        }
    }
}
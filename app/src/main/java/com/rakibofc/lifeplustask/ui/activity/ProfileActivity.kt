package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.databinding.ActivityProfileBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup observer
        setupObserver()

        // Load user data
        loadViewModelData()

        binding.btnGoBack.setOnClickListener {
            finish()
        }
    }

    private fun setupObserver() {

        viewModel.user.observe(this) {
            handleUserData(it)
        }
    }

    private fun handleUserData(user: UiState<UserEntity>) {
        when (user) {
            is UiState.Loading -> {
                // Nothing todo..
            }

            is UiState.Error -> {
                showToast(user.message)
            }

            is UiState.Success -> {
                binding.tvName.text = user.data.name
                binding.tvUserName.text = user.data.userName
                binding.tvPhone.text = user.data.phone
            }
        }
    }

    private fun loadViewModelData() {

        // Get user id from intent
        val userID = intent.getLongExtra(UserEntity.USER_ID, 0)
        lifecycleScope.launch {
            viewModel.loadUser(userID)
        }
    }
}
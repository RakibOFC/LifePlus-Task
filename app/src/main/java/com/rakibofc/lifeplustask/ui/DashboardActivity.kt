package com.rakibofc.lifeplustask.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.databinding.ActivityDashboardBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityDashboardBinding

    private var userID: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get user id from intent
        userID = intent.getLongExtra(UserEntity.USER_ID, 0)

        // Setup observer
        setupObserver()

        // Load user data
        loadViewModelData()

        // Setup listeners
        setupListener()
    }

    private fun setupListener() {

        binding.cvName.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java).apply {
                putExtra(UserEntity.USER_ID, userID)
            })
        }
    }

    private fun setupObserver() {

        viewModel.user.observe(this) {

            when (it) {
                is UiState.Loading -> {
                    // Nothing todo..
                }

                is UiState.Error -> {
                    showToast(it.message)
                }

                is UiState.Success -> {
                    binding.userName.text = it.data.name
                }
            }
        }
    }

    private fun loadViewModelData() {
        lifecycleScope.launch {
            viewModel.loadUser(userID)
        }
    }
}
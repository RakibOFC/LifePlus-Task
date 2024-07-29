package com.rakibofc.lifeplustask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.databinding.ActivityDashboardBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
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
            handleUserData(it)
        }

        viewModel.searchResult.observe(this) {
            handleSearchResult(it)
        }
    }

    private fun handleUserData(user: UiState<UserEntity>) {
        when (user) {
            is UiState.Loading -> {
                // Nothing todo..
                Log.e("TAG", "User: Loading...")
            }

            is UiState.Error -> {
                showToast(user.message)
            }

            is UiState.Success -> {
                binding.userName.text = user.data.name
            }
        }
    }

    private fun handleSearchResult(searchResult: UiState<List<SearchResult>>) {
        when (searchResult) {
            is UiState.Loading -> {
                Log.e("TAG", "SearchResult: Loading...")
            }

            is UiState.Error -> {
                Log.e("TAG", "Error: ${searchResult.message}")
            }

            is UiState.Success -> {
                Log.e("TAG", "Success: ${Gson().toJson(searchResult.data)}")
            }
        }
    }

    private fun loadViewModelData() {
        lifecycleScope.launch {
            viewModel.loadUser(userID)

            viewModel.getSearchResult("girls")
        }
    }
}
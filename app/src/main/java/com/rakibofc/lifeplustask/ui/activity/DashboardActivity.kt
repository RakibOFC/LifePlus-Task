package com.rakibofc.lifeplustask.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.local.UserEntity
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.databinding.ActivityDashboardBinding
import com.rakibofc.lifeplustask.ui.adapter.SearchResultAdapter
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

        binding.llcName.setOnClickListener {
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
                binding.rvSearchResult.visibility = View.GONE
                binding.tvStatus.visibility = View.GONE
                binding.loadingEffect.visibility = View.VISIBLE
            }

            is UiState.Error -> {
                binding.tvStatus.text = searchResult.message

                binding.rvSearchResult.visibility = View.GONE
                binding.loadingEffect.visibility = View.GONE
                binding.tvStatus.visibility = View.VISIBLE
            }

            is UiState.Success -> {
                binding.loadingEffect.visibility = View.GONE

                if (searchResult.data.isNotEmpty()) {

                    binding.rvSearchResult.adapter =
                        SearchResultAdapter(applicationContext, searchResult.data)

                    binding.tvStatus.visibility = View.GONE
                    binding.rvSearchResult.visibility = View.VISIBLE

                } else {
                    binding.tvStatus.text = getString(R.string.no_result_text)

                    binding.rvSearchResult.visibility = View.GONE
                    binding.tvStatus.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadViewModelData() {
        lifecycleScope.launch {
            viewModel.loadUser(userID)

            viewModel.getSearchResult("man")
        }
    }
}
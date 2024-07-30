package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.databinding.ActivityDetailsBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup observer
        setupObserver()

        // Load live data
        loadLiveData()
    }

    private fun setupObserver() {
        viewModel.showDetails.observe(this) {
            handleShowDetails(it)
        }
    }

    private fun handleShowDetails(showUiState: UiState<Show>) {

        when (showUiState) {
            is UiState.Loading -> {
                Log.e("TAG", "Loading: ")
            }

            is UiState.Error -> {
                Log.e("TAG", "Error: ${showUiState.message}")
            }

            is UiState.Success -> {
                Log.e("TAG", "Success: ${Gson().toJson(showUiState.data)}")
            }
        }
    }

    private fun loadLiveData() {
        val showDetails = intent.serializable<Show>(Show.SHOW_KEY)

        lifecycleScope.launch {
            viewModel.setShowDetails(showDetails)
        }
    }
}
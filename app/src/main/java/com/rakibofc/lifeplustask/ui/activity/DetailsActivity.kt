package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.databinding.ActivityDetailsBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the status bar translucent
        /*val window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )*/

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val decorView = window.decorView
        decorView.setOnApplyWindowInsetsListener { v: View, insets: WindowInsets ->
            v.setPadding(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }*/

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
                handleShowDetailsSuccess(showUiState.data)
            }
        }
    }

    private fun loadLiveData() {
        val showDetails = intent.serializable<Show>(Show.SHOW_KEY)

        lifecycleScope.launch {
            viewModel.setShowDetails(showDetails)
        }
    }

    private fun handleShowDetailsSuccess(show: Show) {

        val genres = show.genres.joinToString(", ")
        val schedule =
            getStringFormat(listOf(show.schedule.days.joinToString(", "), show.schedule.time))
        // Load show image
        loadShowThumbnail(show.image?.medium)

        // Set show details
        binding.tvShowName.text = show.name
        binding.tvLangGenTime.text =
            getStringFormat(
                listOf(
                    show.language, genres.ifBlank { "" },
                    if (show.runtime != null) "${show.runtime} mins" else ""
                )
            )
        if (schedule.isNotBlank())
            binding.tvShowSchedule.text = schedule
        else
            binding.tvShowSchedule.visibility = View.GONE

        val summary = show.summary ?: ""
        val spannedSummary: Spanned = Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
        binding.tvShowSummary.text = spannedSummary
    }

    private fun getStringFormat(strings: List<String>): String {
        var langGenTime = ""
        strings.indices.forEach {
            val string = strings[it]
            if (string.isNotBlank()) {
                langGenTime += string
                if (it < strings.size - 1) {
                    langGenTime += " • "
                }
            }
        }

        return langGenTime
    }

    private fun loadShowThumbnail(imageUrl: String?) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.ivShowImage)
    }
}
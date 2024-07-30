package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
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
                // Nothing to do..
            }

            is UiState.Error -> {
                showToast(showUiState.message)
            }

            is UiState.Success -> {
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
        loadShowThumbnail(show.image?.original)

        // Set show details
        binding.apply {
            tvShowName.text = show.name
            tvLangGenTime.text = getStringFormat(
                listOf(
                    show.language,
                    genres.ifBlank { "" },
                    show.runtime?.let { "$it mins" } ?: ""
                )
            )
            tvShowSchedule.apply {
                text = schedule
                visibility = if (schedule.isNotBlank()) View.VISIBLE else View.GONE
            }
            tvShowSummary.text = Html.fromHtml(show.summary ?: "", Html.FROM_HTML_MODE_COMPACT)
        }
    }

    private fun getStringFormat(strings: List<String>): String {
        return strings.filter { it.isNotBlank() }.joinToString(" • ")
    }

    private fun loadShowThumbnail(imageUrl: String?) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.ivShowImage)
    }
}
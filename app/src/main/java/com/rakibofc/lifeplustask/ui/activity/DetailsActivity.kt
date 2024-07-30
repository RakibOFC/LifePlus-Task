package com.rakibofc.lifeplustask.ui.activity

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.remote.Show
import com.rakibofc.lifeplustask.databinding.ActivityDetailsBinding
import com.rakibofc.lifeplustask.util.UiState
import com.rakibofc.lifeplustask.viewmodel.MainViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

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

        binding.ivBackArrow.setOnClickListener {
            finish()
        }
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
        loadShowThumbnail(show.image?.medium, show.image?.original)

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

    private fun loadShowThumbnail(lowQualityUrl: String?, highQualityUrl: String?) {

        // Show the progress bar
        binding.pbImageLoading.visibility = View.VISIBLE

        // Define a function to load the high-quality image
        fun loadHighQualityImage() {
            highQualityUrl?.let {

                val loadedDrawable = binding.ivShowImage.drawable

                Picasso.get()
                    .load(it)
                    .placeholder(loadedDrawable)
                    .error(loadedDrawable)
                    .into(binding.ivShowImage, object : Callback {
                        override fun onSuccess() {
                            binding.pbImageLoading.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            binding.pbImageLoading.visibility = View.GONE
                        }
                    })
            } ?: run {
                binding.pbImageLoading.visibility = View.GONE
            }
        }

        // Load the low-quality image first
        Picasso.get()
            .load(lowQualityUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.ivShowImage, object : Callback {
                override fun onSuccess() {
                    // Low-quality image loaded successfully, now load the high-quality image
                    loadHighQualityImage()
                }

                override fun onError(e: Exception?) {
                    // Failed to load low-quality image, try to load high-quality image directly
                    loadHighQualityImage()
                }
            })
    }
}
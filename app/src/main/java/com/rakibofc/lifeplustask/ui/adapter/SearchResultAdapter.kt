package com.rakibofc.lifeplustask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rakibofc.lifeplustask.R
import com.rakibofc.lifeplustask.data.remote.SearchResult
import com.rakibofc.lifeplustask.databinding.ItemSearchResultBinding
import com.squareup.picasso.Picasso

class SearchResultAdapter(
    private val context: Context,
    private val searchResultList: List<SearchResult>
) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class SearchResultViewHolder(binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val ivShowImage = binding.ivShowImage
        private val tvShowName = binding.tvShowName
        private val tvShowLanguage = binding.tvShowLanguage
        private val tvShowGenres = binding.tvShowGenres
        private val tvShowRuntime = binding.tvShowRuntime
        private val tvShowRating = binding.tvShowRating
        private val tvShowSchedule = binding.tvShowSchedule

        fun bind(position: Int) {

            val searchResult = searchResultList[position]
            val genres = searchResult.show.genres.joinToString(", ")
            val runtime = searchResult.show.runtime
            val rating = searchResult.show.rating?.average
            var schedule = searchResult.show.schedule.days.joinToString(", ")
            schedule += " " + searchResult.show.schedule.time

            // Load show image
            loadShowThumbnail(searchResult.show.image?.medium)

            tvShowName.text = searchResult.show.name
            tvShowLanguage.text = searchResult.show.language
            tvShowGenres.text = if (genres.isNotBlank()) "Genres: $genres" else ("Genres: N/A")

            tvShowRuntime.text = if (runtime != null) "Runtime: $runtime mins" else "Runtime: N/A"
            tvShowRating.text = if (rating != null) "Rating: $rating" else "Rating: N/A"
            tvShowSchedule.text =
                if (schedule.isNotBlank()) "Schedule: $schedule" else "Schedule: N/A"
        }

        private fun loadShowThumbnail(imageUrl: String?) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(ivShowImage)
        }
    }
}
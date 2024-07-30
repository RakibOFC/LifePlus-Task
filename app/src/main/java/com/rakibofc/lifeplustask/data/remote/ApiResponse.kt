package com.rakibofc.lifeplustask.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResult(
    val score: Double,
    val show: Show
)

data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: String?,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule,
    val rating: Rating?,
    val weight: Int,
    val network: Network?,
    val webChannel: Any?, // Can be modified to a specific type if known
    val dvdCountry: Any?, // Can be modified to a specific type if known
    val externals: Externals,
    val image: Image?,
    val summary: String?,
    val updated: Long,
    @SerializedName("_links") val links: Links
) : Serializable {
    companion object {
        const val SHOW_KEY = "show"
    }
}

data class Schedule(
    val time: String,
    val days: List<String>
) : Serializable

data class Rating(
    val average: Double?
) : Serializable

data class Network(
    val id: Int,
    val name: String,
    val country: Country,
    val officialSite: String?
) : Serializable

data class Country(
    val name: String,
    val code: String,
    val timezone: String
) : Serializable

data class Externals(
    val tvrage: Int?,
    val thetvdb: Int?,
    val imdb: String?
) : Serializable

data class Image(
    val medium: String,
    val original: String
) : Serializable

data class Links(
    val self: SelfLink,
    val previousepisode: PreviousEpisodeLink?
) : Serializable

data class SelfLink(
    val href: String
) : Serializable

data class PreviousEpisodeLink(
    val href: String,
    val name: String?
) : Serializable
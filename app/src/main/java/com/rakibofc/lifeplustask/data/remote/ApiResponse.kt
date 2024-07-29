package com.rakibofc.lifeplustask.data.remote

import com.google.gson.annotations.SerializedName

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
)

data class Schedule(
    val time: String,
    val days: List<String>
)

data class Rating(
    val average: Double?
)

data class Network(
    val id: Int,
    val name: String,
    val country: Country,
    val officialSite: String?
)

data class Country(
    val name: String,
    val code: String,
    val timezone: String
)

data class Externals(
    val tvrage: Int?,
    val thetvdb: Int?,
    val imdb: String?
)

data class Image(
    val medium: String,
    val original: String
)

data class Links(
    val self: SelfLink,
    val previousepisode: PreviousEpisodeLink?
)

data class SelfLink(
    val href: String
)

data class PreviousEpisodeLink(
    val href: String,
    val name: String?
)
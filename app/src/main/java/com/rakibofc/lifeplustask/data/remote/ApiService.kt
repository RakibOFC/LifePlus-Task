package com.rakibofc.lifeplustask.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/shows")
    suspend fun getSearchResult(@Query("q") query: String): List<SearchResult>
}
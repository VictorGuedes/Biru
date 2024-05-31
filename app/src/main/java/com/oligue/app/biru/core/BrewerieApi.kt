package com.oligue.app.biru.core

import retrofit2.http.GET
import retrofit2.http.Query

interface BrewerieApi {

    @GET("breweries")
    suspend fun getBreweries(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<BrewerieApi>

}
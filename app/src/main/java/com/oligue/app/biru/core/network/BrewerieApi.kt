package com.oligue.app.biru.core.network

import com.oligue.app.biru.core.model.Brewerie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BrewerieApi {

    @GET("breweries")
    suspend fun getBreweries(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<Brewerie>

    @GET("breweries/search")
    suspend fun getBreweriesBySearch(
        @Query("query") query: String,
    ) : Response<List<Brewerie>>

}
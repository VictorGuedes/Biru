package com.oligue.app.biru.core.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.oligue.app.biru.core.network.BrewerieApi
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.core.network.BaseApiResponse
import com.oligue.app.biru.core.network.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrewerieRepository @Inject constructor(
    private val brewerieApi: BrewerieApi
): BaseApiResponse() {

    fun getBreweries(): LiveData<PagingData<Brewerie>>{
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 5
            ),
            pagingSourceFactory = {
                BrewerieDataSource(brewerieApi)
            }
        ).liveData
    }

    suspend fun getBreweriesBySeach(query: String): NetworkResult<List<Brewerie>> {
        return ResponseManager {
            brewerieApi.getBreweriesBySearch(query)
        }
    }
}
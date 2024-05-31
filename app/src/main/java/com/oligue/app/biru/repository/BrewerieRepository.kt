package com.oligue.app.biru.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oligue.app.biru.core.BrewerieApi
import com.oligue.app.biru.core.model.Brewerie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrewerieRepository @Inject constructor(
    private val brewerieApi: BrewerieApi
) {

    fun getBreweroes(): Flow<PagingData<Brewerie>>{
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 10
            ),
            pagingSourceFactory = {
                BrewerieDataSource(brewerieApi)
            }
        ).flow
    }
}
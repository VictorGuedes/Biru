package com.oligue.app.biru.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oligue.app.biru.core.BrewerieApi
import com.oligue.app.biru.core.model.Brewerie
import retrofit2.HttpException
import java.io.IOException

class BrewerieDataSource(
    private val brewerieApi: BrewerieApi
): PagingSource<Int, Brewerie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Brewerie> {
        val page = params.key ?: START_PAGE
        return try {
            val response = brewerieApi.getBreweries(
                page = page,
                perPage = params.loadSize,
            )

            LoadResult.Page(
                data = response,
                prevKey = if (page == START_PAGE) null else page - 1,
                nextKey = if (page > TOTAL_PAGES) null else page + 1
            )

        } catch (exception: IOException){
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Brewerie>): Int? {
        return state.anchorPosition
    }

    companion object{
        private const val START_PAGE = 1
        private const val TOTAL_PAGES = 8257
    }
}
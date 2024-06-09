package com.oligue.app.biru.app.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.maps.model.LatLng
import com.oligue.app.biru.app.main.adapter.BrewerieMapper
import com.oligue.app.biru.app.main.model.BrewerieUI
import com.oligue.app.biru.base.BaseViewModel
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.core.network.NetworkResult
import com.oligue.app.biru.core.repository.BrewerieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mapper: BrewerieMapper,
    private val repository: BrewerieRepository
): BaseViewModel<ListBreweriesUiState>() {

    private val _brewerieList = MutableLiveData<PagingData<BrewerieUI>>()
    private val brewerieHashMap = HashMap<LatLng, BrewerieUI>()

    private val _brewerieSearh = MutableLiveData<ListBreweriesUiState>()
    override val uiState: LiveData<ListBreweriesUiState>
        get() = _brewerieSearh

    fun getBreweries(): LiveData<PagingData<BrewerieUI>>{
        val response = repository.getBreweries().cachedIn(viewModelScope)
        _brewerieList.value = response.value?.map { mapper.mapModel(it) }

        return response.map { it.map { mapper.mapModel(it) } }
    }

    fun getBreweriesBySearch(query: String){
        viewModelScope.launch {
            val response = repository.getBreweriesBySeach(query)

            when(response){
                is NetworkResult.Success -> {
                    _brewerieSearh.value = ListBreweriesUiState.Success(
                        mapper.map(response.data ?: emptyList())
                    )
                }

                is NetworkResult.Error -> {
                    _brewerieSearh.value = ListBreweriesUiState.Error(
                        response.message ?: ""
                    )
                }
            }
        }
    }


    fun putBrewerieHashMap(latLng: LatLng, data: BrewerieUI){
        brewerieHashMap[latLng] = data
    }

    fun getBrewerieDataFromHashMap(latLng: LatLng): BrewerieUI? = brewerieHashMap[latLng]
}
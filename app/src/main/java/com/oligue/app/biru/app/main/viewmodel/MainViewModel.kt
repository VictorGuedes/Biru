package com.oligue.app.biru.app.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.gms.maps.model.LatLng
import com.oligue.app.biru.base.BaseViewModel
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.core.network.NetworkResult
import com.oligue.app.biru.core.repository.BrewerieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BrewerieRepository
): BaseViewModel<ListBreweriesUiState>() {

    private val _brewerieList = MutableLiveData<PagingData<Brewerie>>()
    private val brewerieHashMap = HashMap<LatLng, Brewerie?>()

    private val _brewerieSearh = MutableLiveData<ListBreweriesUiState>()
    override val uiState: LiveData<ListBreweriesUiState>
        get() = _brewerieSearh

    fun getBreweries(): LiveData<PagingData<Brewerie>>{
        val response = repository.getBreweries().cachedIn(viewModelScope)
        _brewerieList.value = response.value

        return response
    }

    fun getBreweriesBySearch(query: String){
        viewModelScope.launch {
            val response = repository.getBreweriesBySeach(query)

            when(response){
                is NetworkResult.Success -> {
                    _brewerieSearh.value = ListBreweriesUiState.Success(
                        response.data ?: emptyList()
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


    fun putBrewerieHashMap(latLng: LatLng, data: Brewerie?){
        brewerieHashMap[latLng] = data
    }

    fun getBrewerieDataFromHashMap(latLng: LatLng): Brewerie? = brewerieHashMap[latLng]
}
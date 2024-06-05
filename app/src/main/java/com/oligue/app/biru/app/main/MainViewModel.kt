package com.oligue.app.biru.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.gms.maps.model.LatLng
import com.oligue.app.biru.core.model.Brewerie
import com.oligue.app.biru.core.repository.BrewerieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BrewerieRepository
): ViewModel() {

    private val _brewerieList = MutableLiveData<PagingData<Brewerie>>()
    private val brewerieHashMap = HashMap<LatLng, Brewerie?>()

    /*val brewerieList: LiveData<PagingData<Brewerie>>
        get() = _brewerieList*/
    /*fun getBreweries() =
        viewModelScope.launch {
            val response = repository.getBreweries()

            _brewerieList.value = response.value
        }*/

    fun getBreweries(): LiveData<PagingData<Brewerie>>{
        val response = repository.getBreweries().cachedIn(viewModelScope)
        _brewerieList.value = response.value

        return response
    }

    fun putBrewerieHashMap(latLng: LatLng, data: Brewerie?){
        brewerieHashMap[latLng] = data
    }

    fun getBrewerieDataFromHashMap(latLng: LatLng): Brewerie? = brewerieHashMap[latLng]
}
package com.oligue.app.biru

import com.google.android.gms.maps.model.LatLng
import com.oligue.app.biru.app.main.adapter.BrewerieMapper
import com.oligue.app.biru.app.main.model.BrewerieUI
import com.oligue.app.biru.app.main.viewmodel.MainViewModel
import com.oligue.app.biru.core.repository.BrewerieRepository
import org.junit.Test
import org.mockito.Mockito.mock

class BrewerieHashMapTest {

    private val viewModel = MainViewModel(
        mock(BrewerieMapper::class.java),
        mock(BrewerieRepository::class.java)
    )

    @Test
    fun `putBrewerieHashMap should add data to the hashmap`(){
        val key = LatLng(1.0, 2.0)
        val brewerie = mock(BrewerieUI::class.java)

        viewModel.putBrewerieHashMap(key, brewerie)

        assert(viewModel.getBrewerieDataFromHashMap(key) == brewerie)
    }

    @Test
    fun `getBrewerieDataFromHashMap should return null for a non-existent key`() {
        val noExistent = LatLng(2.0, 2.0)
        assert(viewModel.getBrewerieDataFromHashMap(noExistent) == null)
    }



}
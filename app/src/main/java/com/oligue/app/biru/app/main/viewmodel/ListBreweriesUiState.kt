package com.oligue.app.biru.app.main.viewmodel

import com.oligue.app.biru.core.model.Brewerie
sealed class ListBreweriesUiState {
    object Loading : ListBreweriesUiState()

    data class Success(
        val model: List<Brewerie>
    ): ListBreweriesUiState()

    data class Error(
        val errorMessage: String
    ): ListBreweriesUiState()

}

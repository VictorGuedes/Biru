package com.oligue.app.biru.app.main.viewmodel

import com.oligue.app.biru.app.main.model.BrewerieUI

sealed class ListBreweriesUiState {
    object Loading : ListBreweriesUiState()

    data class Success(
        val model: List<BrewerieUI>
    ): ListBreweriesUiState()

    data class Error(
        val errorMessage: String
    ): ListBreweriesUiState()

}

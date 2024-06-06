package com.oligue.app.biru.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<uiState>: ViewModel() {

    abstract val uiState: LiveData<uiState>
}
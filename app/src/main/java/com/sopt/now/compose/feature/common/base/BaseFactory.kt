package com.sopt.now.compose.feature.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseFactory<T>(
    private val creator: () -> T,
) : ViewModelProvider.Factory where T : ViewModel {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = creator() as T
}

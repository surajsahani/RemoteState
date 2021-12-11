package com.martialcoder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martialcoder.repository.Repository
import com.martialcoder.viewmodel.MainViewModel

class ViewModelFactory(private val repo: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }

}
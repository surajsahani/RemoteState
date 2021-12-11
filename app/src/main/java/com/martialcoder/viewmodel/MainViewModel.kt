package com.martialcoder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martialcoder.StateLiveData
import com.martialcoder.model.TrucksMapResponse
import com.martialcoder.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainViewModel(private val repo: Repository) : ViewModel() {

    private lateinit var viewModelJob: Job

    fun getTRucksData(
        authCompany: String,
        companyID: Int,
        deactivated: Boolean,
        key: String,
        qExpand: Boolean,
        qInclude: String
    ): StateLiveData<TrucksMapResponse> {
        val data = StateLiveData<TrucksMapResponse>()
        data.postLoading()
        viewModelJob = CoroutineScope(Job() + Dispatchers.Main).launch {
            try {
                val response =
                    repo.getTrucksData(authCompany, companyID, deactivated, key, qExpand, qInclude)
                response.value?.let { data.postSuccess(it) }
            } catch (e: Exception) {
                data.postError(e)
            }
        }
        return data
    }

    @Suppress("UNCHECKED_CAST")
    class Factory
    constructor(



        private val repo: Repository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java))
                return MainViewModel(
                    repo
                ) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
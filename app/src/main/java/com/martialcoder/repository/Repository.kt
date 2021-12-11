package com.martialcoder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.martialcoder.api.TrucksDataService
import com.martialcoder.model.TrucksMapResponse

class Repository(private val trucksService: TrucksDataService) {

    suspend fun getTrucksData(authCompany: String, companyId:Int, deactivated: Boolean, key:  String,  qExpand: Boolean, qInclude : String): LiveData<TrucksMapResponse> {
        val data = MutableLiveData<TrucksMapResponse>()
        val response = trucksService.truckInstance.getTrucksData(
            authCompany,
            companyId,
            deactivated,
            key,
            qExpand,
            qInclude
        ).await()
        data.value = response
        return data
    }
}
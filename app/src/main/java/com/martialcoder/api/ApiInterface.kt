package com.martialcoder.api

import android.companion.CompanionDeviceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.martialcoder.model.TrucksMapResponse
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.mystral.in/"
interface ApiInterface {

    @GET("tt/mobile/logistics/searchTrucks")
    fun getTrucksData(@Query("auth-company") authCompany: String,
                      @Query("companyId") companyId: Int,
                      @Query("deactivated") deactivated: Boolean,
                      @Query("key") key: String,
        @Query("q-expand") qExpand: Boolean,
        @Query("q-include") qInclude : String
    ): Deferred<TrucksMapResponse>
}

object TrucksDataService {
     var truckInstance : ApiInterface
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        truckInstance = retrofit.create(ApiInterface::class.java)
    }
}
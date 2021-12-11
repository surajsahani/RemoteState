package com.martialcoder

import androidx.lifecycle.MutableLiveData

class StateLiveData<T> : MutableLiveData<StateData<T>?>() {
    fun postLoading() {
        postValue(StateData<T>().loading())
    }

    fun postError(throwable: Throwable?) {
        postValue(throwable?.let { StateData<T>().error(it) })
    }

    fun postSuccess(data: T) {
        postValue(StateData<T>().success(data))
    }
}
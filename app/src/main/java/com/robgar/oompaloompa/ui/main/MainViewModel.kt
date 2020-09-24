package com.robgar.oompaloompa.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.repository.MainRepository
import com.robgar.oompaloompa.utils.Resource
import kotlinx.coroutines.*

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val oompaLoompaWorkers = MutableLiveData<Resource<OompaLoompaWorkers>>()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var page: Int = 1
    var totalPages: Int = 1

    fun getOompaLoompaWorkers() {
        coroutineScope.launch {
            val workers = mainRepository.getOompaLoompaWorkers(page)
            oompaLoompaWorkers.postValue(Resource.loading(data = null))
            try {
                oompaLoompaWorkers.postValue(Resource.success(data = workers))
            } catch (exception: Exception) {
                oompaLoompaWorkers.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    fun nextPage() {
        page += 1
        if (totalPages >= page) {
            getOompaLoompaWorkers()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}
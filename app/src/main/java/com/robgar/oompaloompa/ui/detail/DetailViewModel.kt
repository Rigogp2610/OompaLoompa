package com.robgar.oompaloompa.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.data.repository.DetailRepository
import com.robgar.oompaloompa.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    val worker = MutableLiveData<Resource<Worker>>()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getWorker(id: Int) {
        coroutineScope.launch {
            val oompaLoompaWorker = detailRepository.getWorker(id)
            worker.postValue(Resource.loading(data = null))
            try {
                worker.postValue(Resource.success(data = oompaLoompaWorker))
            } catch (exception: Exception) {
                worker.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }

}
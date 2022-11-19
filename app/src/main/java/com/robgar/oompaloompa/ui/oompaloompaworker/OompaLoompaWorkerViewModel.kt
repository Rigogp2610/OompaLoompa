package com.robgar.oompaloompa.ui.oompaloompaworker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.data.repository.DetailRepository
import com.robgar.oompaloompa.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OompaLoompaWorkerViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    val worker = MutableLiveData<Result<Worker>>()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getWorker(id: Int) {
        coroutineScope.launch {
            val oompaLoompaWorker = detailRepository.getWorker(id)
            worker.postValue(Result.Loading(_data = null))
            try {
                worker.postValue(Result.Success(_data = oompaLoompaWorker))
            } catch (exception: Exception) {
                worker.postValue(Result.Error(_data = null, _message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }

}
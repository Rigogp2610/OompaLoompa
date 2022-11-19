package com.robgar.oompaloompa.ui.oompaloompaworker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkerRepository
import com.robgar.oompaloompa.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OompaLoompaWorkerViewModel(private val oompaLoompaWorkerRepository: OompaLoompaWorkerRepository) : ViewModel() {

    private val _oompaLoompaWorker = MutableLiveData<Result<OompaLoompaWorker>>()
    val oompaLoompaWorker : LiveData<Result<OompaLoompaWorker>>
        get() = _oompaLoompaWorker
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getWorker(id: Int) {
        coroutineScope.launch {
            val oompaLoompaWorker = oompaLoompaWorkerRepository.getWorker(id)
            this@OompaLoompaWorkerViewModel._oompaLoompaWorker.postValue(Result.Loading(_data = null))
            try {
                this@OompaLoompaWorkerViewModel._oompaLoompaWorker.postValue(Result.Success(_data = oompaLoompaWorker))
            } catch (exception: Exception) {
                this@OompaLoompaWorkerViewModel._oompaLoompaWorker.postValue(Result.Error(_data = null, _message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }

}
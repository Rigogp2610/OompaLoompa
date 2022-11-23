package com.robgar.oompaloompa.ui.oompaloompaworker

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkerRepository
import com.robgar.oompaloompa.utils.Result
import com.robgar.oompaloompa.utils.hasInternetConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OompaLoompaWorkerViewModel(
    private val oompaLoompaWorkerRepository: OompaLoompaWorkerRepository,
    private val context: Context
) : ViewModel() {

    private val _oompaLoompaWorker = MutableLiveData<Result<OompaLoompaWorker>>()
    val oompaLoompaWorker : LiveData<Result<OompaLoompaWorker>>
        get() = _oompaLoompaWorker
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getOompaLoompaWorker(id: Int) {
        if (hasInternetConnection(context)) {
            _oompaLoompaWorker.postValue(Result.Loading)
            coroutineScope.launch {
                val oompaLoompaWorker = oompaLoompaWorkerRepository.getWorker(id)
                try {
                    _oompaLoompaWorker.postValue(Result.Success(data = oompaLoompaWorker))
                } catch (exception: Exception) {
                    _oompaLoompaWorker.postValue(Result.Error(
                        exception.message ?: context.getString(R.string.error_get_content)))
                }
            }
        } else {
            _oompaLoompaWorker.postValue(Result.Error(context.getString(R.string.error_internet)))
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }

}
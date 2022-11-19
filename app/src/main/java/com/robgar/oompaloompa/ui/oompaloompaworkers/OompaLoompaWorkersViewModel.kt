package com.robgar.oompaloompa.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.utils.ConsumableValue
import com.robgar.oompaloompa.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OompaLoompaWorkersViewModel(private val oompaLoompaWorkersRepository: OompaLoompaWorkersRepository) : ViewModel() {

    private val _oompaLoompaWorkers = MutableLiveData<ConsumableValue<Result<OompaLoompaWorkers>>>()
    val oompaLoompaWorkers: LiveData<ConsumableValue<Result<OompaLoompaWorkers>>>
        get() = _oompaLoompaWorkers
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val errorOcurred = "Error Occurred!"

    private var nextPage: Int = 1
    private var currentPage: Int = 0
    var totalPages: Int = 1
    private var filter: Int = 0

    fun nextPage() {
        nextPage += 1
        if (totalPages >= nextPage && nextPage > currentPage) {
            getOompaLoompaWorkers()
        }
    }

    fun getOompaLoompaWorkers() {
        if (nextPage > currentPage) {
            currentPage = nextPage
            getOompaLoompaWorkersByPage()
        }
    }

    private fun getOompaLoompaWorkersByPage() {
        _oompaLoompaWorkers.postValue(ConsumableValue(Result.Loading(_data = null)))
        coroutineScope.launch {
            try {
                val workers = oompaLoompaWorkersRepository.getOompaLoompaWorkers(nextPage)
                _oompaLoompaWorkers.postValue(ConsumableValue(Result.Success(_data = workers)))
            } catch (exception: Exception) {
                _oompaLoompaWorkers.postValue(ConsumableValue(Result.Error(_data = null, _message = exception.message ?: errorOcurred)))
            }
        }
    }

    fun getFilteredWorkers(oompaLoompaWorkers: List<OompaLoompaWorker>, filter: Int = 0): List<OompaLoompaWorker> {
        var sortedList: List<OompaLoompaWorker> = oompaLoompaWorkers
        if (filter != 0) { this.filter = filter }
        when (this.filter) {
            R.id.filter_profession -> sortedList = oompaLoompaWorkers.sortedBy { it.profession }
            R.id.filter_gender -> sortedList = oompaLoompaWorkers.sortedBy { it.gender }
            R.id.filter_profession_gender -> sortedList = oompaLoompaWorkers.sortedWith(compareBy({ it.profession }, { it.gender }))
        }

        return sortedList
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}
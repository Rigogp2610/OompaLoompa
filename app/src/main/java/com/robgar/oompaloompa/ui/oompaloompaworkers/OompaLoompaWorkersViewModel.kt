package com.robgar.oompaloompa.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OompaLoompaWorkersViewModel(private val oompaLoompaWorkersRepository: OompaLoompaWorkersRepository) : ViewModel() {

    val oompaLoompaWorkers = MutableLiveData<Result<OompaLoompaWorkers>>()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var page: Int = 1
    var totalPages: Int = 1
    private var filter: Int = 0

    fun getOompaLoompaWorkers() {
        oompaLoompaWorkers.postValue(Result.Loading(_data = null))
        coroutineScope.launch {
            try {
                val workers = oompaLoompaWorkersRepository.getOompaLoompaWorkers(page)
                oompaLoompaWorkers.postValue(Result.Success(_data = workers))
            } catch (exception: Exception) {
                oompaLoompaWorkers.postValue(Result.Error(_data = null, _message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    fun nextPage() {
        page += 1
        if (totalPages >= page) {
            getOompaLoompaWorkers()
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
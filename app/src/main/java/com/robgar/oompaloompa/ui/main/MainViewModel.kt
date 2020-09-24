package com.robgar.oompaloompa.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.R
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.data.repository.MainRepository
import com.robgar.oompaloompa.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val oompaLoompaWorkers = MutableLiveData<Resource<OompaLoompaWorkers>>()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var page: Int = 1
    var totalPages: Int = 1
    private var filter: Int = 0

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

    fun getFilteredWorkers(workers: List<Worker>, filter: Int = 0): List<Worker> {
        var sortedList: List<Worker> = workers
        if (filter != 0) { this.filter = filter }
        when (this.filter) {
            R.id.filter_profession -> sortedList = workers.sortedBy { it.profession }
            R.id.filter_gender -> sortedList = workers.sortedBy { it.gender }
            R.id.filter_profession_gender -> sortedList = workers.sortedWith(compareBy({ it.profession }, { it.gender }))
        }

        return sortedList
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}
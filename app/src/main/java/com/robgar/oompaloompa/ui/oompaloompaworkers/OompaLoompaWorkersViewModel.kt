package com.robgar.oompaloompa.ui.oompaloompaworkers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.ui.filter_dialog.FilterType
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

    private lateinit var oompaLoompaWorkerList: OompaLoompaWorkers

    private val errorGetOompaLoompaWorkers = "An error has occured. Try it again later"
    val firstPage = 1

    private var page: Int = 1
    private var currentPage: Int = 0

    private var filterList = listOf<Pair<FilterType, String>>()

    fun nextPage() {
        page += 1
        if (oompaLoompaWorkerList.totalPages >= page && page > currentPage) {
            getOompaLoompaWorkers()
        }
    }

    fun getOompaLoompaWorkers() {
        if (page > currentPage) {
            currentPage = page
            getOompaLoompaWorkersByPage()
        }
    }

    private fun getOompaLoompaWorkersByPage() {
        _oompaLoompaWorkers.postValue(ConsumableValue(Result.Loading))
        coroutineScope.launch {
            try {
                val workers = oompaLoompaWorkersRepository.getOompaLoompaWorkers(page)
                if (page == firstPage) {
                    oompaLoompaWorkerList = workers
                } else {
                    oompaLoompaWorkerList.oompaLoompaWorkers += workers.oompaLoompaWorkers
                }

                updateOompaLoompaWorkers()
            } catch (exception: Exception) {
                _oompaLoompaWorkers.postValue(ConsumableValue(Result.Error(message = exception.message ?: errorGetOompaLoompaWorkers)))
            }
        }
    }

    fun updateOompaLoompaWorkers() {
        val filterOompaLoompaWorkerList = oompaLoompaWorkerList.copy(oompaLoompaWorkers = getFilteredOompaLoompaWorkers())

        _oompaLoompaWorkers.postValue(ConsumableValue(Result.Success(data = filterOompaLoompaWorkerList)))
    }

    private fun getFilteredOompaLoompaWorkers() : List<OompaLoompaWorker> {
        var filterOompaLoompaWorkers: List<OompaLoompaWorker> = oompaLoompaWorkerList.oompaLoompaWorkers

        filterList.forEach { filter ->
            filterOompaLoompaWorkers = when (filter.first) {
                FilterType.GENDER -> filterOompaLoompaWorkers.filter { it.gender.uppercase() == filter.second.first().uppercase() }
                FilterType.PROFESSION -> filterOompaLoompaWorkers.filter { it.profession == filter.second }
            }
        }

        return filterOompaLoompaWorkers
    }

    fun setFilterList(filterList: List<Pair<FilterType, String>>) {
        this.filterList = filterList
    }

    fun getCurrentPage() = currentPage

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}
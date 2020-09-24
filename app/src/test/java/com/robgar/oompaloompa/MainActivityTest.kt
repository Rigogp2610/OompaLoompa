package com.robgar.oompaloompa

import com.robgar.oompaloompa.data.api.ApiHelper
import com.robgar.oompaloompa.data.api.RetrofitBuilder
import com.robgar.oompaloompa.data.model.Worker
import com.robgar.oompaloompa.data.model.WorkerFavorite
import com.robgar.oompaloompa.data.repository.MainRepository
import com.robgar.oompaloompa.ui.main.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MainActivityTest {

    private lateinit var viewModel: MainViewModel
    private var apiHelper: ApiHelper = ApiHelper(RetrofitBuilder.getApiService())
    private var mainRepository = MainRepository(apiHelper)
    private var workers: List<Worker> = listOf(
        Worker("","","", WorkerFavorite("","","",""),"F","",
            "Developer","","",24,"",40,1),
        Worker("","","", WorkerFavorite("","","",""),"M","",
        "Brewer","","",24,"",40,2),
        Worker("","","", WorkerFavorite("","","",""),"F","",
            "Brewer","","",24,"",40,3),
        Worker("","","", WorkerFavorite("","","",""),"F","",
            "Metalworker","","",24,"",40,4),
        Worker("","","", WorkerFavorite("","","",""),"M","",
            "Medic","","",24,"",40,5)
    )

    @Test
    fun filteredWorkersSameWorkers() {
        viewModel = MainViewModel(mainRepository)

        val result = viewModel.getFilteredWorkers(workers)

        assertEquals(workers, result)
    }

    @Test
    fun lastWorkerFilteredByProfession() {
        viewModel = MainViewModel(mainRepository)

        val result = viewModel.getFilteredWorkers(workers, R.id.filter_profession)

        assertEquals(result.last().profession, "Metalworker")
    }

    @Test
    fun laastWorkerFilteredByProfession() {
        viewModel = MainViewModel(mainRepository)
        var workers: List<Worker> = listOf(
            Worker("","","", WorkerFavorite("","","",""),"M","",
                "Metalworker","","",24,"",40,6),
            Worker("","","", WorkerFavorite("","","",""),"M","",
                "Gemcutter","","",24,"",40,7)
        )

        val filteredWorkers = viewModel.getFilteredWorkers(this.workers, R.id.filter_profession)
        val result = viewModel.getFilteredWorkers(filteredWorkers + workers, R.id.filter_profession_gender)

        assertEquals(result.last().id, 6)
    }
}
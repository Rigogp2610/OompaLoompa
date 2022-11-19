package com.robgar.oompaloompa

import com.robgar.oompaloompa.data.api.ApiHelper
import com.robgar.oompaloompa.data.api.RetrofitBuilder
import com.robgar.oompaloompa.data.model.Favorite
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.ui.main.OompaLoompaWorkersViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MainActivityTest {

    private lateinit var viewModel: OompaLoompaWorkersViewModel
    private var apiHelper: ApiHelper = ApiHelper(RetrofitBuilder.getApiService())
    private var oompaLoompaWorkersRepository = OompaLoompaWorkersRepository(apiHelper)
    private var oompaLoompaWorkers: List<OompaLoompaWorker> = listOf(
        OompaLoompaWorker("","","", Favorite("","","",""),"F","",
            "Developer","","",24,"",40,1),
        OompaLoompaWorker("","","", Favorite("","","",""),"M","",
        "Brewer","","",24,"",40,2),
        OompaLoompaWorker("","","", Favorite("","","",""),"F","",
            "Brewer","","",24,"",40,3),
        OompaLoompaWorker("","","", Favorite("","","",""),"F","",
            "Metalworker","","",24,"",40,4),
        OompaLoompaWorker("","","", Favorite("","","",""),"M","",
            "Medic","","",24,"",40,5)
    )

    @Test
    fun filteredWorkersSameWorkers() {
        viewModel = OompaLoompaWorkersViewModel(oompaLoompaWorkersRepository)

        val result = viewModel.getFilteredWorkers(oompaLoompaWorkers)

        assertEquals(oompaLoompaWorkers, result)
    }

    @Test
    fun lastWorkerFilteredByProfession() {
        viewModel = OompaLoompaWorkersViewModel(oompaLoompaWorkersRepository)

        val result = viewModel.getFilteredWorkers(oompaLoompaWorkers, R.id.filter_profession)

        assertEquals(result.last().profession, "Metalworker")
    }

    @Test
    fun laastWorkerFilteredByProfession() {
        viewModel = OompaLoompaWorkersViewModel(oompaLoompaWorkersRepository)
        var oompaLoompaWorkers: List<OompaLoompaWorker> = listOf(
            OompaLoompaWorker("","","", Favorite("","","",""),"M","",
                "Metalworker","","",24,"",40,6),
            OompaLoompaWorker("","","", Favorite("","","",""),"M","",
                "Gemcutter","","",24,"",40,7)
        )

        val filteredWorkers = viewModel.getFilteredWorkers(this.oompaLoompaWorkers, R.id.filter_profession)
        val result = viewModel.getFilteredWorkers(filteredWorkers + oompaLoompaWorkers, R.id.filter_profession_gender)

        assertEquals(result.last().id, 6)
    }
}
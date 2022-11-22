package com.robgar.oompaloompa

import com.robgar.oompaloompa.data.api.ApiHelper
import com.robgar.oompaloompa.data.api.RetrofitBuilder
import com.robgar.oompaloompa.data.model.Favorite
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.ui.filter_dialog.FilterType
import com.robgar.oompaloompa.ui.main.OompaLoompaWorkersViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class OompaLoompaWorkersFragmentTest {

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

    private var filterList = listOf<Pair<FilterType, String>>()

    fun setFilterList(filterList: List<Pair<FilterType, String>>) {
        this.filterList = filterList
    }

    private fun getFilteredOompaLoompaWorkers() : List<OompaLoompaWorker> {
        var filterOompaLoompaWorkers: List<OompaLoompaWorker> = oompaLoompaWorkers

        filterList.forEach { filter ->
            filterOompaLoompaWorkers = when (filter.first) {
                FilterType.GENDER -> filterOompaLoompaWorkers.filter { it.gender.uppercase() == filter.second.first().uppercase() }
                FilterType.PROFESSION -> filterOompaLoompaWorkers.filter { it.profession == filter.second }
            }
        }

        return filterOompaLoompaWorkers
    }

    @Test
    fun filterByGenderAndProfession() {
        val filterList = mutableListOf<Pair<FilterType, String>>()
        filterList.add(Pair(FilterType.GENDER, "Male"))
        filterList.add(Pair(FilterType.PROFESSION, "Medic"))
        setFilterList(filterList)
        val result = getFilteredOompaLoompaWorkers()

        assertEquals(result.get(0).profession, "Medic")
    }

    @Test
    fun filterByProfession() {
        val filterList = mutableListOf<Pair<FilterType, String>>()
        filterList.add(Pair(FilterType.PROFESSION, "Metalworker"))
        setFilterList(filterList)
        val result = getFilteredOompaLoompaWorkers()

        assertEquals(result.size, 1)
    }

    @Test
    fun filterByGender() {
        val filterList = mutableListOf<Pair<FilterType, String>>()
        filterList.add(Pair(FilterType.GENDER, "Female"))
        setFilterList(filterList)
        val result = getFilteredOompaLoompaWorkers()

        assertEquals(result.size, 3)
    }

    @Test
    fun verifyGetOompaLoompaWorkers() {
        val mock = mock(OompaLoompaWorkersViewModel::class.java)

        mock.getOompaLoompaWorkers()
        verify(mock).getOompaLoompaWorkers()
    }
}
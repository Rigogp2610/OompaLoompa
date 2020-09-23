package com.robgar.oompaloompa.data.repository

import com.robgar.oompaloompa.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getOompaLoompaWorkers(page: Int) = apiHelper.getOompaLoompaWorkers(page)
}
package com.robgar.oompaloompa.data.repository

import com.robgar.oompaloompa.data.api.ApiHelper

class OompaLoompaWorkerRepository(private val apiHelper: ApiHelper) {

    suspend fun getWorker(id: Int) = apiHelper.getWorker(id)
}
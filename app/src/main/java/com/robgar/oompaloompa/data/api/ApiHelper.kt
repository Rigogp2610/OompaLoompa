package com.robgar.oompaloompa.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getOompaLoompaWorkers(page: Int) = apiService.getOompaLoompaWorkers(page)

    suspend fun getWorker(id: Int) = apiService.getWorker(id)
}
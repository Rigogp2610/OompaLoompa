package com.robgar.oompaloompa.data.api

import com.robgar.oompaloompa.data.model.OompaLoompaWorkers
import com.robgar.oompaloompa.data.model.OompaLoompaWorker
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("oompa-loompas")
    suspend fun getOompaLoompaWorkers(@Query("page") page: Int): OompaLoompaWorkers

    @GET("oompa-loompas/{id}")
    suspend fun getWorker(@Path("id") id: Int): OompaLoompaWorker
}
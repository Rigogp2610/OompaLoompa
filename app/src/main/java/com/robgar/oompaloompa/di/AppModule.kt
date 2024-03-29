package com.robgar.oompaloompa.di

import com.robgar.oompaloompa.data.api.ApiHelper
import com.robgar.oompaloompa.data.api.RetrofitBuilder
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkerRepository
import com.robgar.oompaloompa.data.repository.OompaLoompaWorkersRepository
import com.robgar.oompaloompa.ui.oompaloompaworker.OompaLoompaWorkerViewModel
import com.robgar.oompaloompa.ui.oompaloompaworkers.OompaLoompaWorkersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitBuilder.getApiService() }
    single { ApiHelper(get()) }
    single { OompaLoompaWorkersRepository(get()) }
    single { OompaLoompaWorkerRepository(get()) }
    viewModel { OompaLoompaWorkersViewModel(get(), androidContext()) }
    viewModel { OompaLoompaWorkerViewModel(get(), androidContext()) }
}
package com.robgar.oompaloompa.di

import com.robgar.oompaloompa.data.api.ApiHelper
import com.robgar.oompaloompa.data.api.ApiService
import com.robgar.oompaloompa.data.api.RetrofitBuilder
import com.robgar.oompaloompa.data.repository.DetailRepository
import com.robgar.oompaloompa.data.repository.MainRepository
import com.robgar.oompaloompa.ui.detail.DetailViewModel
import com.robgar.oompaloompa.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitBuilder.getApiService() }
    single { ApiHelper(get()) }
    single { MainRepository(get()) }
    single { DetailRepository(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
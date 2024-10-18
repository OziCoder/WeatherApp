package com.example.weather.di

import com.example.weather.base.PreferencesManager
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.network.api.provideApiService
import com.example.weather.network.api.provideOkHttpClient
import com.example.weather.network.api.provideRetrofit
import com.example.weather.network.service.APIService
import com.example.weather.ui.landing.LandingViewModel
import com.example.weather.ui.search.SearchViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<PreferencesManager> { PreferencesManager(androidContext()) }
    single<OkHttpClient> { provideOkHttpClient() }
    single<Retrofit> { provideRetrofit(get()) }
    factory<APIService> { provideApiService(get()) }
    single { WeatherRepository(get()) }
    viewModel<LandingViewModel> { LandingViewModel(get()) }
    viewModel<SearchViewModel> { SearchViewModel(get(), get()) }
}
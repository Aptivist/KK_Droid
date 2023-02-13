package com.kk.presentation.di

import com.kk.presentation.home.HomeVieModel
import com.kk.presentation.host.creategame.CreateRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


val viewModelModule = module {

    viewModelOf(::CreateRoomViewModel)
    viewModelOf(::HomeVieModel)
}
package com.example.sandboxbankapp.authorize.di

import com.example.sandboxbankapp.authorize.presentation.viewmodel.AuthorizeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizeViewModelModule = module {
    viewModel<AuthorizeViewModel> {
        AuthorizeViewModel()
    }
}
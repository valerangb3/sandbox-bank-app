package com.example.sandboxbankapp.authorize.presentation

data class FieldState(
    val text: String = "",
    val errorText: String = "",
)

data class AuthorizeUiState(
    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState()
)
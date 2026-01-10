package com.example.sandboxbankapp.authorize.presentation.state

sealed interface FieldType {
    object Email : FieldType
    class Password(val isVisible: Boolean = false) : FieldType
}


data class FieldState(
    val fieldType: FieldType,
    val text: String = "",
    val isValid: Boolean = false,
    val errorText: String? = null,
)

data class AuthorizeUiState(
    val emailState: FieldState = FieldState(fieldType = FieldType.Email),
    val passwordState: FieldState = FieldState(fieldType = FieldType.Password(isVisible = false)),
    val success: Boolean = false,

    val isLoading: Boolean = false,
    val isAuthorizeButtonEnabled: Boolean = false,
)
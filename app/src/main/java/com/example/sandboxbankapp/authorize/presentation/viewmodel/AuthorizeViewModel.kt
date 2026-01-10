package com.example.sandboxbankapp.authorize.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeAction
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeUiState
import com.example.sandboxbankapp.authorize.presentation.state.FieldType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizeViewModel : ViewModel(
    //TODO inject dependency
) {
    private val _uiState = MutableStateFlow(AuthorizeUiState())
    val uiState = _uiState.asStateFlow()

    fun reduce(action: AuthorizeAction) {
        viewModelScope.launch {
            when (action) {
                is AuthorizeAction.EmailInput -> {
                    _uiState.update { authorizeUiState ->
                        authorizeUiState.copy(
                            emailState = authorizeUiState.emailState.copy(text = action.text)
                        )
                    }
                }
                is AuthorizeAction.PasswordInput -> {
                    _uiState.update { authorizeUiState ->
                        authorizeUiState.copy(
                            passwordState = authorizeUiState.passwordState.copy(text = action.text)
                        )
                    }
                }
                is AuthorizeAction.TogglePasswordIcon -> {
                    _uiState.update { authorizeUiState ->
                        val passwordField = authorizeUiState.passwordState.fieldType as FieldType.Password
                        authorizeUiState.copy(
                            passwordState = authorizeUiState.passwordState.copy(
                                fieldType = FieldType.Password(
                                    isVisible = !passwordField.isVisible
                                )
                            )
                        )
                    }
                }
                is AuthorizeAction.Authorize -> {
                    withContext(Dispatchers.IO) {

                    }
                }
                is AuthorizeAction.MoveToRegister -> {}
            }
        }
    }
}
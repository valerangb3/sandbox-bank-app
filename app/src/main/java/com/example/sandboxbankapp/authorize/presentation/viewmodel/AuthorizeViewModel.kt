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

    private fun validatePassword() {

    }

    private fun validateEmail() {
        _uiState.update { authorizeUiState ->
            val emailState = authorizeUiState.emailState
            val email = emailState.text
            if (email.length < MIN_NUM_SYMBOL) {
                authorizeUiState.copy(
                    emailState = emailState.copy(
                        errorText = "Минимально 4 символа",
                        isValid = false
                    )
                )
            } else {
                authorizeUiState.copy(
                    emailState = emailState.copy(
                        errorText = null,
                        isValid = true
                    )
                )
            }
        }
    }

    private fun validateField(fieldType: FieldType) {
        when (fieldType) {
            is FieldType.Email -> validateEmail()
            is FieldType.Password -> {}
        }
    }

    fun reduce(action: AuthorizeAction) {
        viewModelScope.launch {
            when (action) {
                is AuthorizeAction.ValidateField -> validateField(action.field)
                is AuthorizeAction.EmailInput -> {
                    _uiState.update { authorizeUiState ->
                        authorizeUiState.copy(
                            emailState = authorizeUiState.emailState.copy(
                                text = action.text,
                                errorText = null,
                                isValid = false
                            )
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

    companion object {
        private const val MIN_NUM_SYMBOL = 4
    }
}
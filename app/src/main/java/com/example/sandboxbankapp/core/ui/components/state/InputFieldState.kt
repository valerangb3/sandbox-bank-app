package com.example.sandboxbankapp.core.ui.components.state

enum class FieldType {
    TEXT,
    EMAIL,
    PASSWORD
}

sealed interface InputFieldState {
    object Common : InputFieldState
    object Success : InputFieldState
    class Error(val text: String) : InputFieldState
}
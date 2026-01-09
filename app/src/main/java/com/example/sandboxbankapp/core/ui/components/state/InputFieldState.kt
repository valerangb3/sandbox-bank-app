package com.example.sandboxbankapp.core.ui.components.state

enum class FieldType {
    TEXT,
    EMAIL,
    PASSWORD
}


// этот state чисто для input'a
sealed interface InputFieldState {
    object Common : InputFieldState
    object Success : InputFieldState
    class Error(val errorText: String) : InputFieldState
}
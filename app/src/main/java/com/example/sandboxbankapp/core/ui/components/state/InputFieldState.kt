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

/* TODO скорее всего подлежит удалению
sealed interface InputFieldType {
    data class TextField(val text: String = "") : InputFieldType
    data class EmailField(
        val email: String = "",
        val errorText: String = "",
        val validField: Boolean = true,
    ) : InputFieldType
    data class PasswordField(
        val password: String = "",
        val errorText: String = "",
        val validField: Boolean = true
    ) : InputFieldType
}*/

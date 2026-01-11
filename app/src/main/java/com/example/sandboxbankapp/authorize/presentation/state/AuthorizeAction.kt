package com.example.sandboxbankapp.authorize.presentation.state


sealed interface AuthorizeAction {
    object Authorize : AuthorizeAction
    object MoveToRegister : AuthorizeAction
    class EmailInput(val text: String) : AuthorizeAction
    class PasswordInput(val text: String) : AuthorizeAction
    class ValidateField(val field: FieldType) : AuthorizeAction
    object TogglePasswordIcon : AuthorizeAction
}
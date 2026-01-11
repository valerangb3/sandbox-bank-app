package com.example.sandboxbankapp.authorize.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeUiState
import com.example.sandboxbankapp.authorize.presentation.state.FieldState
import com.example.sandboxbankapp.authorize.presentation.state.FieldType
import com.example.sandboxbankapp.ui.theme.SandboxBankAppTheme

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AuthorizationScreenPreview() {
    SandboxBankAppTheme(dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) { innerPadding ->
            val formState = AuthorizeUiState(
                emailState = FieldState(
                    fieldType = FieldType.Email,
                    text = "vgb3@gmail.com",
                    errorText = "Что-то пошло не так (email)",
                    isValid = true
                ),
                passwordState = FieldState(
                    fieldType = FieldType.Password(isVisible = true),
                    text = "foo-bar",
                    // errorText = "Что-то пошло не так (password)"
                ),
                isAuthorizeButtonEnabled = true
            )
            AuthorizationForm(
                modifier = Modifier.padding(innerPadding),
                formState = formState,
                onLoginInput = {},
                onPasswordInput = {},
                onShowPassword = {},
                onAuthorize = {},
                moveToNextStep = {},
                onRegisterMove = {},
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AuthorizationScreenDarkPreview() {
    SandboxBankAppTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            val formState = AuthorizeUiState(
                passwordState = FieldState(
                    fieldType = FieldType.Password(isVisible = false),
                    text = "foo-bar",
                    isValid = true
                    // errorText = "Что-то пошло не так (password)"
                ),
                isAuthorizeButtonEnabled = true
            )
            AuthorizationForm(
                modifier = Modifier.padding(innerPadding),
                formState = formState,
                onLoginInput = {},
                onPasswordInput = {},
                onShowPassword = {},
                onAuthorize = {},
                moveToNextStep = {},
                onRegisterMove = {},
            )
        }
    }
}
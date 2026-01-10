package com.example.sandboxbankapp.authorize.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeAction
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeUiState
import com.example.sandboxbankapp.authorize.presentation.viewmodel.AuthorizeViewModel
import com.example.sandboxbankapp.core.ui.components.FormState
import com.example.sandboxbankapp.core.ui.components.InputField
import com.example.sandboxbankapp.core.ui.components.state.FieldType
import com.example.sandboxbankapp.core.ui.components.state.InputFieldState
import com.example.sandboxbankapp.ui.theme.Outline
import org.koin.androidx.compose.koinViewModel
import com.example.sandboxbankapp.authorize.presentation.state.FieldType.Password

@Composable
private fun AuthorizationContent(
    authorizeViewModel: AuthorizeViewModel,

    onLoginInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onShowPassword: () -> Unit,
    onAuthorize: () -> Unit,

    moveToNextStep: () -> Unit,
    onRegisterMove: () -> Unit,

    modifier: Modifier = Modifier,
) {
    val formState by authorizeViewModel.uiState.collectAsStateWithLifecycle()

    AuthorizationForm(
        modifier = modifier,
        formState = formState,
        onLoginInput = onLoginInput,
        onPasswordInput = onPasswordInput,
        onShowPassword = onShowPassword,
        onAuthorize = onAuthorize,
        moveToNextStep = moveToNextStep,
        onRegisterMove = onRegisterMove,
    )
}

@Composable
fun AuthorizationForm(
    formState: AuthorizeUiState,
    onLoginInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onShowPassword: () -> Unit,
    onAuthorize: () -> Unit,
    moveToNextStep: () -> Unit,
    onRegisterMove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordField = formState.passwordState.fieldType as Password
    // var formState by remember { mutableStateOf(FormState("vgb3@gmail.com")) }
    val emailFieldState = remember(formState.emailState.errorText) {
        formState.emailState.errorText?.let { error ->
            InputFieldState.Error(error)
        } ?: InputFieldState.Common
        // mutableStateOf<InputFieldState>(InputFieldState.Error("Что-то пошло не так"))
    }

    val passwordFieldState = remember(
        formState.passwordState.errorText,
        passwordField.isVisible
    ) {
        // val passwordField = formState.passwordState.fieldType as Password
        formState.passwordState.errorText?.let { error ->
            InputFieldState.Error(error)
        } ?: InputFieldState.Common
    }

    Column(
        modifier = modifier
            .padding(top = 160.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Авторизация",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(80.dp))

        /*
        TODO для тестов
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            label = {
                Text("Input email")
            },
            value = text,
            onValueChange = {text = it}
        )

         */

        InputField(
            text = formState.emailState.text,
            modifier = Modifier.fillMaxWidth(),
            fieldType = FieldType.EMAIL,
            state = emailFieldState,
            onValueChange = onLoginInput,
            labelText = "Введите e-mail",
        )

        Spacer(modifier = Modifier.height(24.dp))
        // val fieldState by remember { mutableStateOf<InputFieldState>(InputFieldState.Error("Что-то пошло не так")) }
        InputField(
            text = formState.passwordState.text,
            modifier = Modifier.fillMaxWidth(),
            fieldType = FieldType.PASSWORD,
            state = passwordFieldState,
            onValueChange = onPasswordInput,
            labelText = "Введите пароль",
            onTrailingClick = onShowPassword,
            showPassword = passwordField.isVisible
        )

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            enabled = formState.isAuthorizeButtonEnabled,
            onClick = {
                if (formState.success)
                    moveToNextStep()
                else
                    onAuthorize()
            }
        ) {
            Text(
                text = "Вход",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Outline,
                    shape = ButtonDefaults.shape
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = onRegisterMove,
        ) {
            Text(
                text = "Регистрация",
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}


//moveToNextStep - по идее там может быть как переход на экран pin кода, так и главный экран приложения
//пока не понятно...
//onRegisterMove - переход на экран регистрации
@Composable
fun AuthorizationScreen(
    onAuthorize: () -> Unit,
    onRegisterMove: () -> Unit,
    modifier: Modifier = Modifier,
    authorizeViewModel: AuthorizeViewModel = koinViewModel(),
) {
    AuthorizationContent(
        modifier = modifier,
        authorizeViewModel = authorizeViewModel,
        onLoginInput = { email ->
            authorizeViewModel
                .reduce(
                    action = AuthorizeAction.EmailInput(
                        text = email
                    )
                )
        },
        onPasswordInput = { password ->
            authorizeViewModel
                .reduce(
                    action = AuthorizeAction.PasswordInput(
                        text = password
                    )
                )
        },
        onShowPassword = {
            authorizeViewModel
                .reduce(
                    action = AuthorizeAction.TogglePasswordIcon
                )
        },
        onAuthorize = onAuthorize,
        moveToNextStep = {},
        onRegisterMove = onRegisterMove
    )
}


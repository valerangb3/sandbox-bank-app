package com.example.sandboxbankapp.authorize.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sandboxbankapp.authorize.presentation.state.AuthorizeAction
import com.example.sandboxbankapp.authorize.presentation.viewmodel.AuthorizeViewModel
import com.example.sandboxbankapp.core.ui.components.FormState
import com.example.sandboxbankapp.core.ui.components.InputField
import com.example.sandboxbankapp.core.ui.components.state.FieldType
import com.example.sandboxbankapp.core.ui.components.state.InputFieldState
import com.example.sandboxbankapp.ui.theme.Outline
import org.koin.androidx.compose.koinViewModel

@Composable
private fun AuthorizationForm(
    authorizeViewModel: AuthorizeViewModel,

    onLoginInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onAuthorize: () -> Unit,

    moveToNextStep: () -> Unit,
    onRegisterMove: () -> Unit,

) {
    val formState by authorizeViewModel.uiState.collectAsStateWithLifecycle()

    // var formState by remember { mutableStateOf(FormState("vgb3@gmail.com")) }
    var fieldState by remember { mutableStateOf<InputFieldState>(InputFieldState.Error("Error")) }

    Column(
        modifier = Modifier
            .padding(top = 160.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Авторизация",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(80.dp))

        InputField(
            text = formState.emailState.text,
            modifier = Modifier.fillMaxWidth(),
            fieldType = FieldType.EMAIL,
            state = fieldState,
            onValueChange = onLoginInput,
            label = { Text("Введите e-mail") },
        )

        Spacer(modifier = Modifier.height(24.dp))

        InputField(
            text = formState.passwordState.text,
            modifier = Modifier.fillMaxWidth(),
            fieldType = FieldType.PASSWORD,
            state = fieldState,
            onValueChange = onPasswordInput,
            label = { Text("Введите пароль") },
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
    authorizeViewModel: AuthorizeViewModel = koinViewModel(),
) {

    AuthorizationForm(
        authorizeViewModel = authorizeViewModel,

        onLoginInput = { email ->
            authorizeViewModel
                .reduce(
                    action = AuthorizeAction.EmailInput(
                        text = email
                    )
                )
        },
        onPasswordInput = {},
        onAuthorize = onAuthorize,

        moveToNextStep = {},
        onRegisterMove = onRegisterMove

    )
}


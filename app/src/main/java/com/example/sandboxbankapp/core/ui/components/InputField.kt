package com.example.sandboxbankapp.core.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color
import android.graphics.drawable.PaintDrawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sandboxbankapp.R
import com.example.sandboxbankapp.core.ui.components.state.FieldType
import com.example.sandboxbankapp.core.ui.components.state.InputFieldState
import com.example.sandboxbankapp.ui.theme.Green


private enum class FieldState {
    COMMON, ERROR, SUCCESS
}

/*private fun getIconInfo(fieldState: InputFieldState): Pair<Int, String?> {
    @DrawableRes var iconRes: Int
    var contentDescription: String? = null
    when (fieldState) {
        is InputFieldState.Common -> {
            iconRes = R.drawable.ic_password_danger
        }

        is InputFieldState.Error -> {
            iconRes = R.drawable.ic_field_error
            contentDescription = fieldState.text
        }

        is InputFieldState.Success -> {
            iconRes = R.drawable.ic_field_success
        }
    }
    return Pair(iconRes, contentDescription)
}*/

@Composable
private fun PasswordField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: InputFieldState = InputFieldState.Common,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    var isVisible by remember { mutableStateOf(false) }

    val trailingIcon: @Composable (() -> Unit)? = remember(state, isVisible) {
        val drawableRes = when (state) {
            is InputFieldState.Success -> R.drawable.ic_field_success
            is InputFieldState.Common -> {
                if (isVisible) {
                    R.drawable.ic_password_danger
                } else {
                    R.drawable.ic_password_save
                }
            }
            is InputFieldState.Error -> R.drawable.ic_field_error
        }
        {
            IconButton(
                onClick = { isVisible = !isVisible }
            ) {
                Icon(
                    painter = painterResource(drawableRes),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = state is InputFieldState.Error,
        supportingText = if (state is InputFieldState.Error) {
            {
                Text(state.errorText)
            }
        } else null,
        colors = fieldColor(state = state)
    )
}

@Composable
private fun EmailField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: InputFieldState = InputFieldState.Common,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    //TODO на примере PasswordField пофиксить стабильность для данного поля
    val trailingIcon: @Composable (() -> Unit)? = when(state) {
        is InputFieldState.Error -> {
            {
                Image(
                    painter = painterResource(R.drawable.ic_field_error),
                    contentDescription = null
                )
            }
        }
        is InputFieldState.Success -> {
            {
                Image(
                    painter = painterResource(R.drawable.ic_field_success),
                    contentDescription = null
                )
            }
        }
        is InputFieldState.Common -> null
    }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label,
        isError = state is InputFieldState.Error,
        supportingText = if (state is InputFieldState.Error) {
            {
                Text(state.errorText)
            }
        } else null,
        colors = fieldColor(state = state)
    )
}

@Composable
private fun TextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: InputFieldState = InputFieldState.Common,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
) {
    val trailingIcon: @Composable (() -> Unit)? = when(state) {
        is InputFieldState.Error -> {
            {
                Image(
                    painter = painterResource(R.drawable.ic_field_error),
                    contentDescription = null
                )
            }
        }
        is InputFieldState.Success -> {
            {
                Image(
                    painter = painterResource(R.drawable.ic_field_success),
                    contentDescription = null
                )
            }
        }
        is InputFieldState.Common -> null
    }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label,
        isError = state is InputFieldState.Error,
        supportingText = if (state is InputFieldState.Error) {
            {
                Text(state.errorText)
            }
        } else null,
        colors = fieldColor(state = state)
    )
}


@Composable
private fun fieldColor(state: InputFieldState): TextFieldColors {
    if (state is InputFieldState.Success) return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(Green.value),
        unfocusedBorderColor = Color(Green.value)
    )
    return OutlinedTextFieldDefaults.colors()
}

@Composable
fun InputField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fieldType: FieldType = FieldType.TEXT,
    state: InputFieldState = InputFieldState.Common,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    formState: FormState
) {
    when (fieldType) {
        FieldType.TEXT -> TextField(
            text = formState.passwordField,
            onValueChange = onValueChange,
            modifier = modifier,
            state = state,
            leadingIcon = leadingIcon,
            label = label
        )
        FieldType.PASSWORD -> PasswordField(
            text = formState.passwordField,
            onValueChange = onValueChange,
            modifier = modifier,
            state = state,
            leadingIcon = leadingIcon,
            label = label
        )
        FieldType.EMAIL -> EmailField(
            text = formState.passwordField,
            onValueChange = onValueChange,
            modifier = modifier,
            state = state,
            leadingIcon = leadingIcon,
            label = label
        )
    }
}

data class FormState(
    val passwordField: String = ""
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InputFieldPreview() {
    val errorText = "Опаньки, ошибка вышла"
    var fieldState by remember { mutableStateOf<InputFieldState>(InputFieldState.Common) }
    var formState by remember { mutableStateOf(FormState("qwerty")) }
    val fieldType = FieldType.PASSWORD
    Column(
        modifier = Modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        InputField(
            formState = formState,
            modifier = Modifier.fillMaxWidth(),
            fieldType = fieldType,
            state = fieldState,
            onValueChange = {
                formState = formState.copy(passwordField = it)
            },
            label = { Text("Введите пароль") },
        )
    }
}
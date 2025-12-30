package com.example.sandboxbankapp.core.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color
import android.graphics.drawable.PaintDrawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sandboxbankapp.R
import com.example.sandboxbankapp.core.ui.components.state.FieldType
import com.example.sandboxbankapp.core.ui.components.state.InputFieldState
import com.example.sandboxbankapp.ui.theme.Green


private enum class FieldState {
    COMMON, ERROR, SUCCESS
}

private fun getIconInfo(fieldState: InputFieldState): Pair<Int, String?> {
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
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fieldType: FieldType = FieldType.TEXT,
    state: InputFieldState = InputFieldState.Common,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null
) {
    var fieldState1 by remember { mutableStateOf(InputFieldState.Common) }
    var colors = OutlinedTextFieldDefaults.colors()

    val fieldState = when (state) {
        is InputFieldState.Common -> FieldState.COMMON
        is InputFieldState.Error -> FieldState.ERROR
        is InputFieldState.Success -> {
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(Green.value),
                unfocusedBorderColor = Color(Green.value)
            )
            FieldState.SUCCESS
        }
    }

    val supportingText: (@Composable (() -> Unit))? = if (fieldState == FieldState.ERROR) {
        {
            val errorText = (state as InputFieldState.Error).text
            Text(errorText)
        }
    } else null

    val (iconRes, contentDescription) = getIconInfo(fieldState1)
    val trailingIcon: (@Composable (() -> Unit))? = when (fieldType) {
        //TODO Email
        FieldType.PASSWORD -> {
            {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = contentDescription
                )
            }
        }
        FieldType.EMAIL -> {
            when (state) {
                is InputFieldState.Success -> {
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_field_success),
                            contentDescription = null
                        )
                    }
                }

                is InputFieldState.Error -> {
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_field_error),
                            contentDescription = null
                        )
                    }
                }

                else -> { null }
            }
        }
        FieldType.TEXT -> null
    }

    val visualTransformation = when (fieldType) {
        FieldType.TEXT, FieldType.EMAIL -> VisualTransformation.None
        FieldType.PASSWORD -> PasswordVisualTransformation()
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label,
        visualTransformation = visualTransformation,
        isError = fieldState == FieldState.ERROR,
        supportingText = supportingText,
        colors = colors
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InputFieldPreview() {
    var text by remember { mutableStateOf("vgb3@gmail.com") }
    Column(
        modifier = Modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        val errorText = "Опаньки, ошибка вышла"
        InputField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            fieldType = FieldType.EMAIL,
            state = InputFieldState.Success,
            onValueChange = { text = it },
            label = { Text("Введите e-mail") },
        )
    }
}
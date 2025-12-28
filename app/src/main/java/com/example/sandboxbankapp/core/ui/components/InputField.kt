package com.example.sandboxbankapp.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class FieldType {
    TEXT,
    PASSWORD
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    fieldType: FieldType = FieldType.TEXT,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null
) {
    val trailingIcon: (@Composable (() -> Unit))? = trailingIcon?.let {
        if (value.isNotEmpty()) {
            {
                trailingIcon()
            }
        } else {
            null
        }
    }

    val visualTransformation = when (fieldType) {
        FieldType.TEXT -> VisualTransformation.None
        FieldType.PASSWORD -> PasswordVisualTransformation()
    }


    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        label = label,
        visualTransformation = visualTransformation
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InputFieldPreview() {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        InputField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            trailingIcon = { Text("baz") },
            label = { Text("foo") },
        )
    }
}
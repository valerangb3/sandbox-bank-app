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
import com.example.sandboxbankapp.ui.theme.SandboxBankAppTheme

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AuthorizationScreenPreview() {
    SandboxBankAppTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                AuthorizationScreen(
                    onAuthorize = {},
                    onRegisterMove = {}
                )
            }
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
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                AuthorizationScreen(
                    onAuthorize = {},
                    onRegisterMove = {}
                )
            }
        }
    }
}
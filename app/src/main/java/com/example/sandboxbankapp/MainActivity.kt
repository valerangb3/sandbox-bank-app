package com.example.sandboxbankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.sandboxbankapp.authorize.presentation.ui.AuthorizationScreen
import com.example.sandboxbankapp.ui.theme.SandboxBankAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SandboxBankAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) { innerPadding ->
                    AuthorizationScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        onRegisterMove = {},
                        onAuthorize = {}
                    )
                }
            }
        }
    }
}
package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.ninenox.kotlinlocalemanager.ApplicationLocale
import io.github.ninenox.kotlinlocalemanager.LocaleManager
import io.github.ninenox.kotlinlocalemanager.localeAsState

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LocaleDemo()
                }
            }
        }
    }
}

@Composable
fun LocaleDemo() {
    val localeManager = ApplicationLocale.localeManager ?: return
    val context = LocalContext.current

    val language by localeManager.localeAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.hello_world),
            fontSize = 28.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Language: $language",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            localeManager.setNewLocale(context, LocaleManager.LANGUAGE_THAI)
        }) {
            Text("Thai")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            localeManager.setNewLocale(context, LocaleManager.LANGUAGE_ENGLISH)
        }) {
            Text("English")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            localeManager.setNewLocale(context, LocaleManager.LANGUAGE_JAPANESE)
        }) {
            Text("日本語")
        }
    }
}

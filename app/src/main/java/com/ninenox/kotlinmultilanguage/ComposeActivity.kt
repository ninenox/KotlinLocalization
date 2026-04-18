package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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

class ComposeActivity : AppCompatActivity() {
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

private val languages = listOf(
    "English" to LocaleManager.LANGUAGE_ENGLISH,
    "ภาษาไทย" to LocaleManager.LANGUAGE_THAI,
    "日本語" to LocaleManager.LANGUAGE_JAPANESE,
    "한국어" to LocaleManager.LANGUAGE_KOREAN,
    "中文" to LocaleManager.LANGUAGE_CHINESE_SIMPLIFIED,
    "العربية" to LocaleManager.LANGUAGE_ARABIC,
    "Español" to LocaleManager.LANGUAGE_SPANISH,
    "Français" to LocaleManager.LANGUAGE_FRENCH,
    "Deutsch" to LocaleManager.LANGUAGE_GERMAN,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LocaleDemo() {
    val localeManager = ApplicationLocale.localeManager ?: return
    val context = LocalContext.current
    val language by localeManager.localeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
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
        Spacer(modifier = Modifier.height(24.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            languages.forEach { (label, code) ->
                Button(onClick = { localeManager.setNewLocale(context, code) }) {
                    Text(label)
                }
            }
        }
    }
}

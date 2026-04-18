package io.github.ninenox.kotlinlocalemanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import java.util.Locale

val LocalLocaleManager: CompositionLocal<LocaleManager?> = compositionLocalOf { null }

@Composable
fun LocaleManager.localeAsState(): State<String> = localeFlow.collectAsState()

@Composable
fun LocaleManager.currentLocaleAsState(): State<Locale> = currentLocaleFlow.collectAsState()

@Composable
fun rememberLocaleManager(): LocaleManager? = remember { ApplicationLocale.localeManager }

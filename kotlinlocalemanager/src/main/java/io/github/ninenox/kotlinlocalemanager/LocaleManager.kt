package io.github.ninenox.kotlinlocalemanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class LocaleManager(context: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val _localeFlow = MutableStateFlow(language)
    val localeFlow: StateFlow<String> = _localeFlow.asStateFlow()

    private val _currentLocaleFlow = MutableStateFlow(getLocale(language))
    val currentLocaleFlow: StateFlow<Locale> = _currentLocaleFlow.asStateFlow()

    fun setLocale(c: Context): Context = updateResources(c, language)

    fun setNewLocale(
        c: Context,
        language: String,
    ): Context {
        val lang = language.lowercase(Locale.ROOT)
        persistLanguage(lang)
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
        _localeFlow.value = lang
        _currentLocaleFlow.value = getLocale(lang)
        return updateResources(c, lang)
    }

    val language: String
        get() {
            if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
                val delegateLocales = AppCompatDelegate.getApplicationLocales()
                if (!delegateLocales.isEmpty) {
                    delegateLocales[0]?.let { return it.toLanguageTag().lowercase() }
                }
            }
            return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH
        }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // commit() instead of apply() — process may be killed immediately after this call
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(
        ctx: Context,
        language: String,
    ): Context {
        var context = ctx
        val locale =
            if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                Locale.forLanguageTag(language)
            } else {
                val parts = language.split("-")
                if (parts.size > 1) Locale(parts[0], parts[1]) else Locale(parts[0])
            }
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            @Suppress("DEPRECATION")
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_THAI = "th"
        const val LANGUAGE_JAPANESE = "ja"
        const val LANGUAGE_KOREAN = "ko"
        const val LANGUAGE_CHINESE_SIMPLIFIED = "zh-CN"
        const val LANGUAGE_CHINESE_TRADITIONAL = "zh-TW"
        const val LANGUAGE_ARABIC = "ar"
        const val LANGUAGE_SPANISH = "es"
        const val LANGUAGE_FRENCH = "fr"
        const val LANGUAGE_GERMAN = "de"
        const val LANGUAGE_PORTUGUESE = "pt"
        const val LANGUAGE_PORTUGUESE_BRAZIL = "pt-BR"
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_ITALIAN = "it"
        const val LANGUAGE_HINDI = "hi"
        const val LANGUAGE_INDONESIAN = "id"
        const val LANGUAGE_VIETNAMESE = "vi"
        const val LANGUAGE_MALAY = "ms"
        const val LANGUAGE_TURKISH = "tr"
        const val LANGUAGE_DUTCH = "nl"
        const val LANGUAGE_POLISH = "pl"
        const val LANGUAGE_UKRAINIAN = "uk"
        const val LANGUAGE_BENGALI = "bn"
        const val LANGUAGE_FARSI = "fa"

        private const val LANGUAGE_KEY = "language_key"

        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= VERSION_CODES.N) config.locales[0] else config.locale
        }

        fun getLocale(languageTag: String): Locale =
            if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                Locale.forLanguageTag(languageTag)
            } else {
                val parts = languageTag.split("-")
                if (parts.size > 1) Locale(parts[0], parts[1]) else Locale(parts[0])
            }
    }
}

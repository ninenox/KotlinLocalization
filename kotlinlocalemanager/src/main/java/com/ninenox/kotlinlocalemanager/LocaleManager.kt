package com.ninenox.kotlinlocalemanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import android.preference.PreferenceManager
import java.util.*

class LocaleManager(context: Context?) {
    private val prefs: SharedPreferences
    fun setLocale(c: Context): Context {
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    val language: String
        get() = prefs.getString(
            LANGUAGE_KEY,
            LANGUAGE_ENGLISH
        ) ?: LANGUAGE_ENGLISH

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) { // use commit() instead of apply(), because sometimes we kill the application process immediately
// which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(
        context: Context,
        language: String
    ): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    companion object {
        //const val LANGUAGE_FARSI = "fa"
        const val LANGUAGE_ENGLISH = "en"
        private const val LANGUAGE_KEY = "language_key"
        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= VERSION_CODES.N) config.locales[0] else config.locale
        }
    }

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }
}
package com.ninenox.kotlinlocalemanager

import android.app.Application
import android.content.Context
import android.content.res.Configuration

open class ApplicationLocale : Application() {

    override fun attachBaseContext(base: Context) {
        initializeLocaleManager(base)
        super.attachBaseContext(localeManager?.setLocale(base) ?: base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager?.setLocale(this)
    }

    companion object {
        // for the sake of simplicity. use DI in real apps instead
        private var _localeManager: LocaleManager? = null
        val localeManager: LocaleManager?
            get() = _localeManager

        fun initializeLocaleManager(context: Context) {
            if (_localeManager == null) {
                _localeManager = LocaleManager(context)
            }
        }
    }
}
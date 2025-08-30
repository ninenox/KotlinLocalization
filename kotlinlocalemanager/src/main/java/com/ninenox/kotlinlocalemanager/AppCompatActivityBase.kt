package com.ninenox.kotlinlocalemanager

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AppCompatActivityBase : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        val context = ApplicationLocale.localeManager?.setLocale(base) ?: base
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    fun setNewLocale(language: String): Boolean {
        ApplicationLocale.localeManager?.setNewLocale(this, language)
        recreate()
        return true
    }
}
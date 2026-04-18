package io.github.ninenox.kotlinlocalemanager

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

object LocaleHelper {
    /**
     * Call this inside [AppCompatActivity.attachBaseContext] when you cannot extend
     * [AppCompatActivityBase]:
     *
     * ```kotlin
     * override fun attachBaseContext(newBase: Context) {
     *     super.attachBaseContext(LocaleHelper.wrap(newBase))
     * }
     * ```
     */
    fun wrap(context: Context): Context = ApplicationLocale.localeManager?.setLocale(context) ?: context

    /**
     * Call this inside [AppCompatActivity.applyOverrideConfiguration] to preserve dark mode
     * when locale changes:
     *
     * ```kotlin
     * override fun applyOverrideConfiguration(config: Configuration?) {
     *     LocaleHelper.applyOverrideConfiguration(baseContext, config)
     *     super.applyOverrideConfiguration(config)
     * }
     * ```
     */
    fun applyOverrideConfiguration(
        baseContext: Context,
        overrideConfiguration: Configuration?,
    ) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
    }

    /**
     * Call this to switch language from any Activity that does not extend
     * [AppCompatActivityBase]:
     *
     * ```kotlin
     * LocaleHelper.setNewLocale(this, LocaleManager.LANGUAGE_THAI)
     * ```
     */
    fun setNewLocale(
        activity: AppCompatActivity,
        language: String,
    ) {
        ApplicationLocale.localeManager?.setNewLocale(activity, language)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            activity.recreate()
        }
    }
}

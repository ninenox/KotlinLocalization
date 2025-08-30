package com.ninenox.kotlinlocalemanager

import android.os.Build
import androidx.preference.PreferenceManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
class LocaleManagerTest {

    @Test
    fun setNewLocale_savesLanguageAndUpdatesConfiguration() {
        val context = RuntimeEnvironment.getApplication()
        val localeManager = LocaleManager(context)

        val updatedContext = localeManager.setNewLocale(context, LocaleManager.LANGUAGE_THAI)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        assertEquals(LocaleManager.LANGUAGE_THAI, prefs.getString("language_key", ""))

        val resLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updatedContext.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            updatedContext.resources.configuration.locale
        }
        assertEquals(Locale("th"), resLocale)
        assertEquals(Locale("th"), Locale.getDefault())
    }
}

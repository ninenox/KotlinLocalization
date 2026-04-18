package io.github.ninenox.kotlinlocalemanager

import android.os.Build
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
class LocaleManagerTest {
    private val context get() = RuntimeEnvironment.getApplication()

    @Test
    fun setNewLocale_savesLanguageAndUpdatesConfiguration() {
        val localeManager = LocaleManager(context)

        val updatedContext = localeManager.setNewLocale(context, LocaleManager.LANGUAGE_THAI)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        assertEquals(LocaleManager.LANGUAGE_THAI, prefs.getString("language_key", ""))

        val resLocale =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updatedContext.resources.configuration.locales[0]
            } else {
                @Suppress("DEPRECATION")
                updatedContext.resources.configuration.locale
            }
        assertEquals(Locale("th"), resLocale)
        assertEquals(Locale("th"), Locale.getDefault())
    }

    @Test
    fun setNewLocale_updatesLocaleFlow() =
        runTest {
            val localeManager = LocaleManager(context)

            localeManager.setNewLocale(context, LocaleManager.LANGUAGE_JAPANESE)

            assertEquals(LocaleManager.LANGUAGE_JAPANESE, localeManager.localeFlow.first())
        }

    @Test
    fun setNewLocale_updatesCurrentLocaleFlow() =
        runTest {
            val localeManager = LocaleManager(context)

            localeManager.setNewLocale(context, LocaleManager.LANGUAGE_KOREAN)

            val locale = localeManager.currentLocaleFlow.first()
            assertEquals("ko", locale.language)
        }

    @Test
    fun setNewLocale_normalizesLanguageToLowercase() {
        val localeManager = LocaleManager(context)

        localeManager.setNewLocale(context, "EN")

        assertEquals("en", localeManager.language)
    }

    @Test
    fun getLocale_fromLanguageTag_returnsCorrectLocale() {
        assertEquals("th", LocaleManager.getLocale("th").language)
        assertEquals("ja", LocaleManager.getLocale("ja").language)
        assertEquals("zh", LocaleManager.getLocale("zh-CN").language)
        assertEquals("CN", LocaleManager.getLocale("zh-CN").country)
    }

    @Test
    fun languageConstants_areValidBcp47Tags() {
        val constants =
            listOf(
                LocaleManager.LANGUAGE_ENGLISH,
                LocaleManager.LANGUAGE_THAI,
                LocaleManager.LANGUAGE_JAPANESE,
                LocaleManager.LANGUAGE_KOREAN,
                LocaleManager.LANGUAGE_CHINESE_SIMPLIFIED,
                LocaleManager.LANGUAGE_CHINESE_TRADITIONAL,
                LocaleManager.LANGUAGE_ARABIC,
                LocaleManager.LANGUAGE_SPANISH,
                LocaleManager.LANGUAGE_FRENCH,
                LocaleManager.LANGUAGE_GERMAN,
                LocaleManager.LANGUAGE_PORTUGUESE,
                LocaleManager.LANGUAGE_PORTUGUESE_BRAZIL,
                LocaleManager.LANGUAGE_RUSSIAN,
                LocaleManager.LANGUAGE_ITALIAN,
                LocaleManager.LANGUAGE_HINDI,
                LocaleManager.LANGUAGE_INDONESIAN,
                LocaleManager.LANGUAGE_VIETNAMESE,
                LocaleManager.LANGUAGE_MALAY,
                LocaleManager.LANGUAGE_TURKISH,
                LocaleManager.LANGUAGE_DUTCH,
                LocaleManager.LANGUAGE_POLISH,
                LocaleManager.LANGUAGE_UKRAINIAN,
                LocaleManager.LANGUAGE_BENGALI,
                LocaleManager.LANGUAGE_FARSI,
            )
        constants.forEach { tag ->
            val locale = LocaleManager.getLocale(tag)
            assertNotNull("getLocale($tag) should not be null", locale)
        }
    }
}

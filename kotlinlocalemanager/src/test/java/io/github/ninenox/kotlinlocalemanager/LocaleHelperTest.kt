package io.github.ninenox.kotlinlocalemanager

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class LocaleHelperTest {
    private val context get() = RuntimeEnvironment.getApplication()

    @Test
    fun wrap_returnsOriginalContext_whenLocaleManagerIsNull() {
        val wrapped = LocaleHelper.wrap(context)
        assertNotNull(wrapped)
    }

    @Test
    fun applyOverrideConfiguration_preservesUiMode() {
        val config = android.content.res.Configuration()
        config.uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
        LocaleHelper.applyOverrideConfiguration(context, config)
        assertEquals(android.content.res.Configuration.UI_MODE_NIGHT_YES, config.uiMode)
    }

    @Test
    fun applyOverrideConfiguration_doesNotCrash_whenConfigIsNull() {
        LocaleHelper.applyOverrideConfiguration(context, null)
    }
}

package io.github.ninenox.kotlinlocalemanager

import android.content.Context
import androidx.fragment.app.Fragment

abstract class FragmentBase : Fragment() {
    override fun onAttach(context: Context) {
        val localizedContext = ApplicationLocale.localeManager?.setLocale(context) ?: context
        super.onAttach(localizedContext)
    }
}

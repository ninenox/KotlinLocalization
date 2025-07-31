package com.ninenox.kotlinlocalemanager

import android.content.Context
import android.content.pm.PackageManager
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
        //resetTitle()
    }

    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(
                componentName,
                PackageManager.GET_META_DATA
            ).labelRes
            if (label != 0) {
                setTitle(label)
            }
        } catch (e: PackageManager.NameNotFoundException) {
        }
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
//        val i = Intent(this, MainActivity::class.java)
//        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
//        if (restartProcess) {
//            System.exit(0)
//        } else {
//            Toast.makeText(this, "Activity restarted", Toast.LENGTH_LONG).show()
//        }
        return true
    }
}
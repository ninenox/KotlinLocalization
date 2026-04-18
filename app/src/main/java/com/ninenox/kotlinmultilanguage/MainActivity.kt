package com.ninenox.kotlinmultilanguage

import android.content.Intent
import android.os.Bundle
import com.ninenox.kotlinmultilanguage.databinding.ActivityMainBinding
import io.github.ninenox.kotlinlocalemanager.AppCompatActivityBase
import io.github.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_ENGLISH
import io.github.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_THAI

class MainActivity : AppCompatActivityBase() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeLanguageThButton.setOnClickListener { setNewLocale(LANGUAGE_THAI) }
        binding.changeLanguageEnButton.setOnClickListener { setNewLocale(LANGUAGE_ENGLISH) }
        binding.openComposeDemoButton.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}

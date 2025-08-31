package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import io.github.ninenox.kotlinlocalemanager.AppCompatActivityBase
import io.github.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_ENGLISH
import io.github.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_THAI
import com.ninenox.kotlinmultilanguage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivityBase() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.changeLanguageThButton.setOnClickListener {
            setNewLocale(LANGUAGE_THAI)
        }
        binding.changeLanguageEnButton.setOnClickListener {
            setNewLocale(LANGUAGE_ENGLISH)
        }
    }
}

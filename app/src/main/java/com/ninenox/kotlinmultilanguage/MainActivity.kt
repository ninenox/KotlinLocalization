package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import com.ninenox.kotlinlocalemanager.AppCompatActivityBase
import com.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_ENGLISH
import com.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_THAI
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
            setNewLocale("th-TH")
        }
        binding.changeLanguageEnButton.setOnClickListener {
            setNewLocale("en-US")

        }
    }
}

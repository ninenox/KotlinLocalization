package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import com.ninenox.kotlinlocalemanager.AppCompatActivityBase
import com.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_ENGLISH
import com.ninenox.kotlinlocalemanager.LocaleManager.Companion.LANGUAGE_THAI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    private fun initView() {
        change_language_th_button.setOnClickListener {
            setNewLocale(LANGUAGE_THAI)
        }
        change_language_en_button.setOnClickListener {
            setNewLocale(LANGUAGE_ENGLISH)

        }
    }


}

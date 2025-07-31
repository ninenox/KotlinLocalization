package com.ninenox.kotlinmultilanguage

import android.os.Bundle
import com.ninenox.kotlinlocalemanager.AppCompatActivityBase
import com.ninenox.kotlinlocalemanager.ApplicationLocale.Companion.localeManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    private fun initView() {
        change_language_th_button.setOnClickListener {
            setNewLocale("th-TH")
        }
        change_language_en_button.setOnClickListener {
            setNewLocale("en-US")
        }
    }


}

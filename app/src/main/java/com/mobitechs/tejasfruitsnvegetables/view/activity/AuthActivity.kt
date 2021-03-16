package com.mobitechs.tejasfruitsnvegetables.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.utils.setStatusColor

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val window: Window = window
        setStatusColor(window,resources.getColor(R.color.colorPrimaryDark))
    }
}
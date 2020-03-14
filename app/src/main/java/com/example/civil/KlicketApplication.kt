package com.example.civil

import android.app.Application
import com.example.civil.ObjectBox.ObjectBox

class KlicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
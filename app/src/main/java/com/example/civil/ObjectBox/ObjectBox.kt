package com.example.civil.ObjectBox

import android.content.Context
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser




object ObjectBox {

    lateinit var boxStore: BoxStore private set

    fun init(context : Context) {

        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

    }
}
package com.andriod.dictionary

import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.andriod.dictionary.di.AppComponent
import com.andriod.dictionary.di.DaggerAppComponent
import com.andriod.dictionary.di.RoomModule

class DictionaryApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .roomModule(RoomModule(this))
            .build()
    }
    private val inputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        lateinit var instance: DictionaryApp
            private set
    }
}
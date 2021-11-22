package com.andriod.dictionary

import android.app.Application
import com.andriod.dictionary.di.AppComponent
import com.andriod.dictionary.di.DaggerAppComponent
import com.andriod.dictionary.di.RoomModule

class DictionaryApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .roomModule(RoomModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: DictionaryApp
            private set
    }
}
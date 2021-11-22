package com.andriod.dictionary.model

import android.os.Handler
import android.os.HandlerThread
import com.andriod.dictionary.entity.Word
import io.reactivex.Completable
import io.reactivex.Observable

abstract class DataProvider {
    protected val words = mutableListOf<Word>()

    private val handlerThread = HandlerThread("handlerThread").apply { isDaemon = true;start() }
    protected val dataHandler = Handler(handlerThread.looper)
    abstract fun search(query: String): Observable<List<Word>>
    abstract fun saveWords(words: List<Word>): Completable

    companion object {
        const val SLEEP_TIME = 1000L
    }
}
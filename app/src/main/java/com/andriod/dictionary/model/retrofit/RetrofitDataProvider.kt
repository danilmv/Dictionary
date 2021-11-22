package com.andriod.dictionary.model.retrofit

import com.andriod.dictionary.entity.Word
import com.andriod.dictionary.model.DataProvider
import io.reactivex.Completable
import io.reactivex.Observable

class RetrofitDataProvider(private val service: SkyengApi) : DataProvider() {
    override fun search(query: String): Observable<List<Word>> = service.search(query)

    override fun saveWords(words: List<Word>): Completable {
        TODO("Not yet implemented")
    }
}
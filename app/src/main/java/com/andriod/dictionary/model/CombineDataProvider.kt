package com.andriod.dictionary.model

import com.andriod.dictionary.entity.Word
import io.reactivex.Observable

class CombineDataProvider(
    private val webDataProvider: DataProvider,
    private val localDataProvider: DataProvider
) : DataProvider() {

    override fun search(query: String): Observable<List<Word>> =
        localDataProvider.search(query)
            .mergeWith(webDataProvider.search(query)
                .firstOrError()
                .doOnSuccess { saveWords(it) }
            )

    override fun saveWords(words: List<Word>) = localDataProvider.saveWords(words)
}
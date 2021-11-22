package com.andriod.dictionary.model.room

import com.andriod.dictionary.entity.Word
import com.andriod.dictionary.model.DataProvider
import io.reactivex.Observable

class RoomDataProvider(private val db: DictionaryDatabase) : DataProvider() {
    override fun search(query: String): Observable<List<Word>> =
        db.getDictionaryDao().search("$query%")

    override fun saveWords(words: List<Word>) = db.getDictionaryDao().insert(words)
}
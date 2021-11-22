package com.andriod.dictionary.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.andriod.dictionary.entity.Word
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary WHERE word like :query")
    fun search(query: String): Observable<List<Word>>

    @Insert(onConflict = IGNORE)
    fun insert(users: List<Word>): Completable
}
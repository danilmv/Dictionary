package com.andriod.dictionary.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andriod.dictionary.entity.Word

@Database(entities = [Word::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun getDictionaryDao(): DictionaryDao
}
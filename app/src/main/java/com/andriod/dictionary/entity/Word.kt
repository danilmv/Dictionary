package com.andriod.dictionary.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dictionary")
@Parcelize
data class Word(
    @PrimaryKey
    val word: String,
) : Parcelable

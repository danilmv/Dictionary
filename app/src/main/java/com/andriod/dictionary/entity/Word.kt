package com.andriod.dictionary.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dictionary")
@Parcelize
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    @SerializedName("text")
    val word: String,
) : Parcelable

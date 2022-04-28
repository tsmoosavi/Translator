package com.example.translator.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class WordEntity (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val word: String,
    val meaningOfWord: String,
    val example: String,
    val synonym: String,
    val url: String
): Parcelable
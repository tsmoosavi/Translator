package com.example.translator.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val word: String,
    val meaningOfWord: String,
    val example: String,
    val synonym: String,
    val url: String
)
package com.example.translator

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoOfWord {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: WordEntity)

    @Delete
    fun delete(word: WordEntity)

    @Update
    fun edit(word: WordEntity)

    @Query ("SELECT * FROM WordEntity WHERE word = :word")
    fun getWord(word: String): WordEntity

    @Query ("SELECT * FROM WordEntity WHERE meaningOfWord = :word")
    fun getMeaningOfWord(word: String): WordEntity

    @Query("SELECT COUNT(id) FROM WordEntity")
    fun countNumber():LiveData<Int>
}
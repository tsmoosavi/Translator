package com.example.translator.database

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


    @Query ("SELECT * FROM WordEntity")
    fun getList(): LiveData<List<WordEntity>>

    @Query ("SELECT * FROM WordEntity WHERE id = :id")
    fun findId(id: Int): WordEntity

    @Query("SELECT COUNT(id) FROM WordEntity")
    fun countNumber():LiveData<Int>

    @Query("SELECT * FROM WordEntity WHERE word= :word or meaningOfWord= :word")
    fun getWord(word:String):WordEntity
}
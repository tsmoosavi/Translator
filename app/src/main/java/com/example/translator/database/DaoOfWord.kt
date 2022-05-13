package com.example.translator.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoOfWord {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: WordEntity)

    @Delete
    suspend fun delete(word: WordEntity)

    @Update
    suspend fun edit(word: WordEntity)


    @Query ("SELECT * FROM WordEntity")
    fun getList(): LiveData<List<WordEntity>>

//    @Query ("SELECT * FROM WordEntity WHERE id = :id")
//    fun findId(id: Int): WordEntity

    @Query("SELECT COUNT(id) FROM WordEntity")
    fun countNumber():LiveData<Int>

    @Query("SELECT * FROM WordEntity WHERE word= :word or meaningOfWord= :word")
    suspend fun getWord(word:String):WordEntity
}
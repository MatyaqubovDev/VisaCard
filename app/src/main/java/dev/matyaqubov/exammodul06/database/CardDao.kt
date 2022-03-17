package dev.matyaqubov.exammodul06.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.matyaqubov.exammodul06.model.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(card: Card)

    @Query("SELECT * FROM cards")
    fun getUsers(): List<Card>

}
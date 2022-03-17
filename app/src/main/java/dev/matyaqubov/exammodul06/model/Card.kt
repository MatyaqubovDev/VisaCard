package dev.matyaqubov.exammodul06.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var full_name: String,
    var card_number: String,
    var expatered_date: String,
    var cvv: Int,
    var is_have: Boolean = false,

    )

package dev.matyaqubov.exammodul06.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.matyaqubov.exammodul06.model.Card

@Database(entities = [Card::class], version = 3)
abstract class RoomManager : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager {
            if (INSTANCE == null) {
                synchronized(RoomManager::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "card_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
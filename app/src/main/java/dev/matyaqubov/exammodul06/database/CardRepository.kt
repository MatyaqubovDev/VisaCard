package dev.matyaqubov.exammodul06.database

import android.app.Application
import dev.matyaqubov.exammodul06.model.Card

class CardRepository(application: Application) {
    private val db = RoomManager.getDatabase(application)
    private val cardDao=db.cardDao()


    fun getCardsList():List<Card>{
        return cardDao.getUsers()
    }

    fun saveCard(card: Card){
        cardDao.saveCard(card)
    }
}
package dev.matyaqubov.exammodul06.network

import dev.matyaqubov.exammodul06.model.Card
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardService {

    @GET("card")
    fun getAllCards(): Call<ArrayList<Card>>

    @GET("card/{id}")
    fun getCard(@Path("id") id: String): Call<Card>

    @POST("card")
    fun createCard(@Body card: Card): Call<Card>

}
package dev.matyaqubov.exammodul06.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHttp {
    companion object {
        const val TAG = "RetrofitHttp"
        const val IS_TESTER = true

        const val SERVER_DEVELOPMENT = "https://6232aebf8364d63035c18d39.mockapi.io/api/v1/"
        const val SERVER_PRODUCTION = "https://6232aebf8364d63035c18d39.mockapi.io/api/v1/"

        private fun server(): String {
            if (IS_TESTER) {
                return SERVER_DEVELOPMENT
            }
            return SERVER_PRODUCTION
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server())
                .build()
        }

        val cardService= getRetrofit().create(CardService::class.java)
    }
}
package dev.matyaqubov.exammodul06.ui

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.matyaqubov.exammodul06.R
import dev.matyaqubov.exammodul06.adapter.CardAdapter
import dev.matyaqubov.exammodul06.database.CardRepository
import dev.matyaqubov.exammodul06.model.Card
import dev.matyaqubov.exammodul06.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:CardAdapter
    var cards=ArrayList<Card>()
    private lateinit var iv_add:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        recyclerView=findViewById(R.id.recyclerView)
        iv_add=findViewById(R.id.iv_add)
        adapter= CardAdapter(cards)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=GridLayoutManager(this,1)

        if (isInternetAvailable()){
            getAllcards()

        } else{
            getAllcardsFromLocal()
        }

        iv_add.setOnClickListener {
            var intent= Intent(this,AddCardActivity::class.java)
            createPostLauncher.launch(intent)
        }



    }

    private fun saveAllCardsToLocal() {
        val repository=CardRepository(application)
        val items=repository.getCardsList()
        for (card in cards) {
            for (item in items) {
                if (card.card_number==item.card_number){
                    card.is_have=true
                    break
                }

            }

            if (!card.is_have) repository.saveCard(card)
        }

    }


    val createPostLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode== Activity.RESULT_OK){
            updateList()
        }
    }

    private fun updateList() {
        val repository=CardRepository(application)
        RetrofitHttp.cardService.createCard(repository.getCardsList().last()).enqueue(object :Callback<Card>{
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                getAllcards()
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Toast.makeText(this@MainActivity, "check internet", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getAllcardsFromLocal() {
        val repository=CardRepository(application)
        cards.addAll(repository.getCardsList())
    }

    private fun getAllcards() {
        RetrofitHttp.cardService.getAllCards().enqueue(object :Callback<ArrayList<Card>>{
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>
            ) {
                Log.d("responseim", response.body().toString())
                if (response.body()!=null){
                    cards.clear()
                    cards.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    saveAllCardsToLocal()
                }
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}
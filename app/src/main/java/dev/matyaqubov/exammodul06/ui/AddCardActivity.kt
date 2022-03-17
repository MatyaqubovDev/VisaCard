package dev.matyaqubov.exammodul06.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import dev.matyaqubov.exammodul06.R
import dev.matyaqubov.exammodul06.database.CardRepository
import dev.matyaqubov.exammodul06.model.Card

class AddCardActivity : AppCompatActivity() {
    private lateinit var tv_cardNumber:TextView
    private lateinit var tv_holderName:TextView
    private lateinit var tv_ex_date:TextView
    private lateinit var et_cardNumber:EditText
    private lateinit var et_ex_date:EditText
    private lateinit var et_cvv:EditText
    private lateinit var et_fullname:EditText
    private lateinit var b_add:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        initViews()
    }

    private fun initViews() {
        b_add=findViewById(R.id.b_add)
        b_add.isEnabled=false
        tv_cardNumber=findViewById(R.id.tv_card_number)
        tv_ex_date=findViewById(R.id.tv_ex_date)
        tv_holderName=findViewById(R.id.tv_card_holder)
        et_cardNumber=findViewById(R.id.et_card_number)
        et_fullname=findViewById(R.id.et_holder_name)
        et_cvv=findViewById(R.id.et_cvv)
        et_ex_date=findViewById(R.id.et_ex_date)


        et_fullname.addTextChangedListener {
            tv_holderName.text=it.toString()
            if (checkEnabled()) b_add.isEnabled=true
        }
        et_cardNumber.addTextChangedListener {
            tv_cardNumber.text=it.toString()
            if (checkEnabled()) b_add.isEnabled=true
        }
        et_ex_date.addTextChangedListener {
            tv_ex_date.text=it.toString()
            if (checkEnabled()) b_add.isEnabled=true
        }
        et_cvv.addTextChangedListener{
            if (checkEnabled()) b_add.isEnabled=true
        }


        b_add.setOnClickListener {

            val card=Card(1,full_name = et_fullname.text.toString(), card_number = et_cardNumber.text.toString(), expatered_date = et_ex_date.text.toString(), cvv =et_cvv.text.toString().toInt())
            saveData(card)

        }


    }

    private fun checkEnabled() :Boolean {
        return et_cardNumber.text.isNotEmpty() && et_cvv.text.isNotEmpty() && et_ex_date.text.isNotEmpty() && et_fullname.text.isNotEmpty()
    }

    private fun saveData(card: Card) {
        var repository=CardRepository(application)
        var list=repository.getCardsList()
        for (item in list) {
            if(item.card_number==card.card_number){
                card.is_have=true
            }
        }
        if (!card.is_have)  {
            repository.saveCard(card)
            back()
        } else{
            Toast.makeText(this, "Bunday karta avval kitilgan iltimos karta raqamini tekshiring", Toast.LENGTH_SHORT).show()
        }
    }



    fun back(){
        val intent = Intent().apply {
            setResult(RESULT_OK, this)
            finish()
        }
    }
}
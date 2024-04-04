package com.example.deltagames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEntrar = findViewById<Button>(R.id.btLogin)
        val inputEmail = findViewById<EditText>(R.id.editUsername)
        val inputSenha = findViewById<EditText>(R.id.editPassword)

        btnEntrar.setOnClickListener() {
            var Email = inputEmail.getText().toString()

            val intent = Intent(this, ListagemDeProdutos::class.java)
            intent.putExtra("usuario", Email)
            startActivity(intent)
        }    }







}
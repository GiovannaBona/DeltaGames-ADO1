package com.example.deltagames

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ListagemDeProdutos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_de_produtos)

        recyclerView = findViewById(R.id.recyclerViewProdutos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuração do Logging Interceptor
        val logging = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Configuração do OkHttpClient com o interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        // Configuração do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://4af01f27-21ff-4a35-8f58-48517ff0c74c-00-2ohswx0v4qr6t.kirk.replit.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getProdutos().enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    adapter = CustomAdapter(produtos)
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API Error", "Response not successful. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Log.e("API Failure", "Error fetching products", t)
            }
        })

    }
}
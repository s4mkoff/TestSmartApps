package com.example.testsmartapps.data.network

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.scalars.ScalarsConverterFactory

interface WebViewService {
    @GET("/v1/forecast?latitude=52.52&longitude=13.41")
    suspend fun getWebData(): String
}

object WebViewNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val webview = retrofit.create(WebViewService::class.java)
}
package com.tyshko.todoappkmp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkApi(){
    val client = provideClient()
    suspend fun getUserIP() : String{
        return client.get("https://api.ipify.org/").bodyAsText()
    }
}

fun provideClient() : HttpClient{
    return HttpClient(){
        install(ContentNegotiation){
            json(Json {
                encodeDefaults = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
}
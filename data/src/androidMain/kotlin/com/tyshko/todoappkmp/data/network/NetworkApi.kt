package com.tyshko.todoappkmp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class NetworkApi(private val client: HttpClient){
    suspend fun getUserIP() : String{
        return client.get("https://api.ipify.org/").bodyAsText()
    }
}
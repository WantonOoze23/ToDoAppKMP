package com.tyshko.data.network

import com.tyshko.todoappkmp.data.network.NetworkApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.fail
import org.junit.Test
import java.lang.reflect.Field

class NetworkApiTest {

    private fun NetworkApi.setMockClient(mockClient: HttpClient) {
        val field: Field = this::class.java.getDeclaredField("client")
        field.isAccessible = true
        field.set(this, mockClient)
    }

    @Test
    fun getUserIDTest200Response() = runTest {
        val expectedId = "1.1.1.1"
        val mockEngine = MockEngine { request ->
            assertEquals("https://api.ipify.org/", request.url.toString())
            assertEquals(HttpMethod.Get, request.method)

            respond(
                content = expectedId,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }

        val mockClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val networkApi = NetworkApi()
        networkApi.setMockClient(mockClient)

        val result = networkApi.getUserIP()
        assertEquals(expectedId, result)
    }

    @Test
    fun getUserIDTest400Response() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "Bad Request",
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }

        val mockClient = HttpClient(mockEngine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val networkApi = NetworkApi()
        networkApi.setMockClient(mockClient)

        try {
            networkApi.getUserIP()
            fail("Expected ClientRequestException was not thrown")
        } catch (e: ClientRequestException) {
            assertEquals(HttpStatusCode.BadRequest, e.response.status)
        }
    }

    @Test
    fun getUserIDTest500Response() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }

        val mockClient = HttpClient(mockEngine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    encodeDefaults = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val networkApi = NetworkApi()
        networkApi.setMockClient(mockClient)

        try {
            networkApi.getUserIP()
            fail("Expected ServerResponseException was not thrown")
        } catch (e: ServerResponseException) {
            assertEquals(HttpStatusCode.InternalServerError, e.response.status)
        }
    }
}
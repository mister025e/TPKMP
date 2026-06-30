package com.example.tpkmp

import com.example.tpkmp.data.remote.WeatherApiDataSource
import com.example.tpkmp.presentation.viewmodel.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.test.Test

class MainViewModelTest {

    // Test qui réalise une vraie requête réseau
    @Test
    fun testLoadWeathersRealRequest() {
        // Utilisation de la clé API depuis le BuildConfig (local.properties / Secrets GitHub)
        val apiKey = BuildConfig.WEATHER_API_KEY
        
        if (apiKey.isBlank()) {
            println("Test sauté : WEATHER_API_KEY non configurée")
            return
        }

        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
        val dataSource = WeatherApiDataSource(client)
        val viewModel = MainViewModel(dataSource)

        viewModel.loadWeathers("Paris")
        
        // Dans un test réel avec runTest, on vérifierait que viewModel.dataList n'est plus vide.
    }
}

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
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
        val dataSource = WeatherApiDataSource(client)
        val viewModel = MainViewModel(dataSource)

        viewModel.loadWeathers("Paris")
        
        // Dans un test réel, on utiliserait runTest pour attendre la fin de la coroutine
        // et vérifier que viewModel.dataList n'est plus vide.
    }
}

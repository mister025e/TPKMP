package com.example.tpkmp

class MainViewModelTest {

    // Test qui réalise une vraie requête réseau
    // Mis en commentaire pour que la CI passe sans dépendre d'une API externe
    /*
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
    */
}

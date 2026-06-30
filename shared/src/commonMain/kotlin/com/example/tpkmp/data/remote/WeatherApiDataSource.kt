package com.example.tpkmp.data.remote


import com.example.tpkmp.BuildConfig
import com.example.tpkmp.di.apiModule
import com.example.tpkmp.domain.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import org.koin.core.context.startKoin

suspend fun main() {
    val koin = startKoin {
        modules(apiModule)
    }.koin
    
    val dataSource = koin.get<WeatherApiDataSource>()
    val weathers = dataSource.loadWeathers("Nice")
    for(w in weathers) {
        println(
            """
        Il fait ${w.temp}° à ${w.name} (id=${w.id}) avec un vent de ${w.speed} m/s
        -Description : ${w.description}
        -Icône : ${w.icon}
    """.trimIndent()
        )
    }
}

class WeatherApiDataSource(private val client: HttpClient) {
    
    companion object {
        private const val API_URL = "https://www.amonteiro.fr/api/weather?cityname="
    }

    suspend fun loadWeathers(cityName: String): List<Weather> {
        val response = client.get("https://api.openweathermap.org/data/2.5/find") {
            parameter("q", cityName)
            parameter("appid", BuildConfig.WEATHER_API_KEY)
            parameter("units", "metric")
            parameter("lang", "fr")
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val result = response.body<WeatherResultDTO>()
        
        if (result.list.isEmpty()) {
            throw Exception("Aucun résultat trouvé pour cette ville")
        }

        val listSortie = ArrayList<Weather>()
        for (w in result.list) {
            listSortie.add(
                Weather(
                    id = w.id,
                    name = w.name,
                    temp = w.main.temp,
                    description = w.weather.firstOrNull()?.description ?: "-",
                    speed = w.wind.speed,
                    icon = "https://openweathermap.org/img/wn/${w.weather.firstOrNull()?.icon}@4x.png"
                )
            )
        }

        return listSortie
    }

    suspend fun loadWeathersVFacile(cityName: String): List<Weather> {
        val response = client.get(API_URL + cityName)
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val list = response.body<List<Weather>>()

        //Si je devais faire un Wrapper
        val listSortie = ArrayList<Weather>()
        for (w in list) {
            listSortie.add(
                Weather(
                    id = w.id,
                    name = w.name,
                    temp = w.temp,
                    description = w.description,
                    speed = w.speed,
                    icon = "https://openweathermap.org/img/wn/${w.icon}@4x.png"
                )
            )
        }

        return listSortie
    }
}

/* -------------------------------- */
// DTO
/* -------------------------------- */
@Serializable
data class WeatherResultDTO(
    val list: List<WeatherDTO>
)

@Serializable
data class WeatherDTO(
    val id: Int,
    val name: String,
    val main: TempDTO,
    val wind: WindDTO,
    val weather: List<DescriptionDTO>
)

@Serializable
data class TempDTO(val temp: Double)

@Serializable
data class WindDTO(var speed: Double)

@Serializable
data class DescriptionDTO(val icon: String, val description: String)

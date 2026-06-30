package com.example.tpkmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpkmp.data.remote.WeatherApiDataSource
import com.example.tpkmp.di.apiModule
import com.example.tpkmp.di.viewModelModule
import com.example.tpkmp.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin

suspend fun main() {
    val koin = startKoin {
        modules(apiModule, viewModelModule)
    }.koin
    
    val viewModel = koin.get<MainViewModel>()
    viewModel.loadWeathers("Paris")

    while (viewModel.runInProgress.value) {
        println("Attente....")
        delay(500)
    }

    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}")
}

class MainViewModel(private val weatherApiDataSource: WeatherApiDataSource) : ViewModel() {
    val dataList = MutableStateFlow(emptyList<Weather>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    fun loadFakeData(runInProgress :Boolean = false, errorMessage:String = "" ) {
        this.runInProgress.value = runInProgress
        this.errorMessage.value = errorMessage
        dataList.value = listOf(
            Weather(
                id = 1,
                name = "Paris",
                temp = 18.5,
                description = "ciel dégagé",
                icon = "https://picsum.photos/200",
                speed = 5.0
            ),
            Weather(
                id = 2,
                name = "Toulouse",
                temp = 22.3,
                description = "partiellement nuageux",
                icon = "https://picsum.photos/201",
                speed = 3.2
            ),
            Weather(
                id = 3,
                name = "Toulon",
                temp = 25.1,
                description = "ensoleillé",
                icon = "https://picsum.photos/202",
                speed = 6.7
            ),
            Weather(
                id = 4,
                name = "Lyon",
                temp = 19.8,
                description = "pluie légère",
                icon = "https://picsum.photos/203",
                speed = 4.5
            )
        ).shuffled()
    }


    fun loadWeathers(cityName: String) {
        errorMessage.value = ""
        if (cityName.isBlank()) {
            errorMessage.value = "Veuillez entrer une ville"
            return
        }
        runInProgress.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataList.value = weatherApiDataSource.loadWeathers(cityName)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            }
            finally {
                runInProgress.value = false
            }

        }

    }
}

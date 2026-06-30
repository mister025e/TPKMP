package com.example.tpkmp.domain.model

import kotlinx.serialization.Serializable

//Possible qu'il y ait besoin de cette annotation en fonction du compilateur
@Serializable //KotlinX impose cette annotation
data class Weather(
    var id: Int, //id d'un point météo
    var name: String,
    var temp: Double, //Température
    var speed: Double, //Vitesse du vent
    var description: String, //1er description
    var icon: String //1er icone
) {
    fun getResume() = """
            Il fait $temp° à $name (id=$id) avec un vent de $speed m/s
            -Description : $description
            -Icône : $icon
        """.trimIndent()
}
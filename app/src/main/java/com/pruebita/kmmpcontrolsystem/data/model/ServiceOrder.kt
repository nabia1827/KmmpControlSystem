package com.pruebita.kmmpcontrolsystem.data.model

data class ServiceOrder(
    val serviceOrderId:Int = 0,
    var phase:String = "Armado",
    var status: String = "TO DO",
    var selected: Boolean = false
)

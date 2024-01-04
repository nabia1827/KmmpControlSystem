package com.pruebita.kmmpcontrolsystem.data.repository.interfaces

import com.pruebita.kmmpcontrolsystem.data.model.ServiceOrder

interface ServiceOrderRepository {

    fun initToDoList():MutableList<ServiceOrder>
    fun initInProcessList():MutableList<ServiceOrder>
    fun initDoneList():MutableList<ServiceOrder>
}
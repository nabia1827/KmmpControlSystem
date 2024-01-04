package com.pruebita.kmmpcontrolsystem.data.repository.repositories

import com.pruebita.kmmpcontrolsystem.data.model.ServiceOrder
import com.pruebita.kmmpcontrolsystem.data.repository.interfaces.ServiceOrderRepository

class ServiceOrderRepoImpl: ServiceOrderRepository {
    override fun initToDoList(): MutableList<ServiceOrder> {
        return mutableListOf(
            ServiceOrder(4,"None","TO DO"),
            ServiceOrder(5,"None","TO DO")
        )
    }

    override fun initInProcessList(): MutableList<ServiceOrder> {
        return mutableListOf(
            ServiceOrder(2,"Armado","IN PROCESS"),
            ServiceOrder(3,"Desarmado","IN PROCESS")
        )
    }

    override fun initDoneList(): MutableList<ServiceOrder> {
        return mutableListOf(
            ServiceOrder(1,"Armado","DONE")
        )
    }

}
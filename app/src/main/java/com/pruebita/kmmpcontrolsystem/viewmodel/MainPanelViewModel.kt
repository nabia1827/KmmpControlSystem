package com.pruebita.kmmpcontrolsystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pruebita.kmmpcontrolsystem.data.model.ServiceOrder
import com.pruebita.kmmpcontrolsystem.data.repository.repositories.ServiceOrderRepoImpl
import com.pruebita.kmmpcontrolsystem.data.source.currentId
import java.util.Timer
import java.util.TimerTask

class MainPanelViewModel : ViewModel() {
    private val repoOS:ServiceOrderRepoImpl = ServiceOrderRepoImpl()
    private val _toDoList = MutableLiveData<MutableList<ServiceOrder>>(mutableListOf())
    val toDoList: LiveData<MutableList<ServiceOrder>> = _toDoList

    private val _inProcessList = MutableLiveData<MutableList<ServiceOrder>>(mutableListOf())
    val inProcessList: LiveData<MutableList<ServiceOrder>> = _inProcessList

    private val _doneList = MutableLiveData<MutableList<ServiceOrder>>(mutableListOf())
    val doneList: LiveData<MutableList<ServiceOrder>> = _doneList
    private val _cont = MutableLiveData<Int>(0)
    val cont: LiveData<Int> = _cont


    init{
        _doneList.value = repoOS.initDoneList()
        _inProcessList.value = repoOS.initInProcessList()
        _toDoList.value = repoOS.initToDoList()

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val v = _cont.value
                if(v!=null && v<=500){
                    _cont.postValue(v + 1)
                }
                else{
                    _cont.postValue(0)
                }
            }
        }, 0, 1000)
    }

    fun addOs(){
        val tmp = _toDoList.value
        currentId += 1
        tmp?.add(ServiceOrder(
            serviceOrderId = currentId,
            phase="None",
            status = "TO DO")
        )
        _toDoList.value = tmp
    }
    fun changeToDoSelected(index:Int){
        val tmp = _toDoList.value
        var os = tmp?.get(index)
        if (os != null) {
            os.selected = !os.selected
            tmp?.set(index,os)
            _toDoList.value = tmp
        }

    }
    fun changeInProcessSelected(index:Int){
        val tmp = _inProcessList.value
        var os = tmp?.get(index)
        if (os != null) {
            os.selected = !os.selected
            tmp?.set(index,os)
            _inProcessList.value = tmp
        }

    }
    fun changeDoneSelected(index:Int){
        val tmp = _doneList.value
        var os = tmp?.get(index)
        if (os != null) {
            os.selected = !os.selected
            tmp?.set(index,os)
            _doneList.value = tmp
        }

    }

    fun initOs(option:String){
        val toDoTmp = _toDoList.value
        val toDoTmp2 = mutableListOf<ServiceOrder>()
        val selectedList = mutableListOf<ServiceOrder>()
        if(toDoTmp != null){
            for (i in 0 until toDoTmp.size){
                if(toDoTmp[i].status=="TO DO" && toDoTmp[i].selected){
                    toDoTmp[i].phase = option
                    toDoTmp[i].selected = false
                    toDoTmp[i].status= "IN PROCESS"
                    selectedList.add(toDoTmp[i])
                }else{
                    toDoTmp2.add(toDoTmp[i])
                }
            }
        }
        _toDoList.value = toDoTmp2
        val inProcessTmp = _inProcessList.value
        if(inProcessTmp!=null){
            inProcessTmp.addAll(selectedList)
            _inProcessList.value = inProcessTmp
        }

    }

    fun finishOs(){
        val inProcessTmp = _inProcessList.value
        val inProcessTmp2 = mutableListOf<ServiceOrder>()
        val selectedList = mutableListOf<ServiceOrder>()
        if(inProcessTmp != null){
            for (i in 0 until inProcessTmp.size){
                if(inProcessTmp[i].status=="IN PROCESS" && inProcessTmp[i].selected){
                    inProcessTmp[i].selected = false
                    inProcessTmp[i].status= "DONE"
                    selectedList.add(inProcessTmp[i])
                }
                else{
                    inProcessTmp2.add(inProcessTmp[i])
                }
            }
        }
        _inProcessList.value = inProcessTmp2

        val doneTmp = _doneList.value
        if(doneTmp!=null){
            doneTmp.addAll(selectedList)
            _doneList.value = doneTmp
        }
    }

}
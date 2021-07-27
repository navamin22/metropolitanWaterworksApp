package com.example.landmarkapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.landmarkapp.Network.Repository
import com.example.landmarkapp.model.Retrofit.response.AccountResponse
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.StaticData.Companion.currentChannelNo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class RateViewModel: ViewModel() {
    private val repo: Repository = Repository()
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var account: AccountResponse? = null
    var name: MutableLiveData<CharSequence> = MutableLiveData()
    var surname: MutableLiveData<CharSequence> = MutableLiveData()
    var employeeId: MutableLiveData<CharSequence> = MutableLiveData()

    var mutableLiveData: MutableLiveData<AccountResponse> = repo.getAccountFromCounter(
        currentChannelNo,this
    )

    fun getAccountCounter(serviceCounter: Int){
        repo.getAccountFromCounter(serviceCounter,this)
    }

    fun setAccountCounter(account: AccountResponse){
        this.account = account
    }

    fun setName(emp_name: CharSequence){
        name.value = emp_name
    }

    fun setSurname(emp_surname: CharSequence){
        surname.value = emp_surname
    }

    fun setEmpId(emp_id: CharSequence){
        employeeId.value = emp_id
    }

    fun getName(): LiveData<CharSequence>{
        return name
    }

    fun getSurname(): LiveData<CharSequence>{
        return surname
    }

    fun getEmpId(): LiveData<CharSequence>{
        return employeeId
    }
}
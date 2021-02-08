package com.example.landmarkapp.Network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.landmarkapp.model.Retrofit.QueueApi
import com.example.landmarkapp.model.Retrofit.RateApi
import com.example.landmarkapp.model.Retrofit.ReportApi
import com.example.landmarkapp.model.Retrofit.SoundQueueApi
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.QueueResponse
import com.example.landmarkapp.model.Retrofit.response.SoundQueueResponse
import com.example.landmarkapp.viewmodel.DisplayViewModel
import com.example.landmarkapp.viewmodel.PRViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class Repository {
    private var queueList = ArrayList<QueueResponse>()
    private var soundQueueList = ArrayList<SoundQueueResponse>()
    private var mutableLiveData = MutableLiveData<ArrayList<QueueResponse>>()
    private var soundQueueLiveData = MutableLiveData<ArrayList<SoundQueueResponse>>()
    var job: Job? = null

    fun insertQueue(queueNum: String, time: String){
        val call = QueueApi()
            .insertQueue(QueueRequest("insert_queue",queueNum,time,"Prepare"))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertSoundQueue(queueId: Int, queueNum: String, message: String, active: String){
        val call = SoundQueueApi()
            .insertSoundQueue(SoundQueueRequest("insert_sound_queue",queueId,queueNum,message,active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

//    suspend fun insertRate(rateScore: Int, rateTitle: String, active: String){
//        job = GlobalScope.launch {
//            val response = RateApi().insertRate(RateRequest("insertRate",rateScore,rateTitle,active))
//            withContext(Dispatchers.IO){
//                if (response.isSuccessful){
//                    val result = response.body()
//                    println("Result is $result")
//                } else {
//                    println("Error is ${response.message()}")
//                }
//            }
//        }
//    }

    fun insertRate(rateScore: Int, rateTitle: String, active: String, rateStatus: String){
        val call = RateApi()
            .insertRate(RateRequest("insert_rate", rateScore, rateTitle, active, rateStatus))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun insertReport(queueId: Int, queueNum: String, serviceChannel: String, employee: String, active: String){
        val call = ReportApi()
            .insertReport(ReportRequest("insert_report", queueId, queueNum, serviceChannel, employee, active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                Log.v("Error retrofit","Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun updateSoundQueueData(soundQueueId: Int, queueId: Int, active: String){
        val call = SoundQueueApi().updateSoundQueue(SoundQueueUpdateRequest("update_sound_queue",soundQueueId,queueId,active))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun updateQueueData(queueId: String, queueStatus: String){
        val call = QueueApi()
            .updateQueue(QueueUpdateRequest("update_queue",queueId,queueStatus))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result = response.body()
                println("Result is $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

    fun getSoundQueueLiveData(viewModel: DisplayViewModel): MutableLiveData<ArrayList<SoundQueueResponse>>{
        val call = SoundQueueApi().getSoundQueue(NormalRequest("get_sound_queue"))
        call.enqueue(object : retrofit2.Callback<List<SoundQueueResponse>> {
            override fun onResponse(
                call: Call<List<SoundQueueResponse>>,
                response: Response<List<SoundQueueResponse>>

            ) {
                val result = response.body()
                soundQueueList.clear()
                println("Result is $result")
                result?.let {
                    soundQueueList.addAll(it)
                    soundQueueLiveData.value = soundQueueList
                }

            }

            override fun onFailure(call: Call<List<SoundQueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return soundQueueLiveData
    }

    fun getQueueLiveData(): MutableLiveData<ArrayList<QueueResponse>>{
        val call = QueueApi().getQueue(NormalRequest("get_queue"))
        call.enqueue(object : retrofit2.Callback<List<QueueResponse>> {
            override fun onResponse(
                call: Call<List<QueueResponse>>,
                response: Response<List<QueueResponse>>
            ) {
                val result = response.body()
                queueList.clear()
                //println("Result is $result")
                result?.let { queueList.addAll(it) }
                mutableLiveData.value = queueList
            }

            override fun onFailure(call: Call<List<QueueResponse>>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })

        return mutableLiveData
    }

    fun clearQueue(viewModel: PRViewModel){
        val call = QueueApi().clearQueue(NormalRequest("clear_queue"))
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                val result = response.body()
                println(result)
                viewModel.getQueue()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println("Error is : ${t.message}")
            }
        })
    }

}
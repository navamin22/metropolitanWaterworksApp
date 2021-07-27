package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.AccountService
import com.example.landmarkapp.model.Retrofit.Interface.TQueueService
import com.example.landmarkapp.model.Retrofit.RequestValue.InsertAccountRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.LoginEmployeeRequest
import com.example.landmarkapp.model.Retrofit.RequestValue.TransactionQueueRequest
import com.example.landmarkapp.model.Retrofit.response.LoginEmployeeResponse
import com.example.landmarkapp.model.Retrofit.response.RegisterResponse
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.TruthCertificates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class TransactionQueueApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(StaticData.url + StaticData.folder + StaticData.php + StaticData.t_queue_php)
//        .client(TruthCertificates.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(TQueueService::class.java)

    fun insertTQueue(request: TransactionQueueRequest): Call<String> {
        return api.insertTQueue(request)
    }
}
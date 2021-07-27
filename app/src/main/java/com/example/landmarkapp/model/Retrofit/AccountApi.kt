package com.example.landmarkapp.model.Retrofit

import com.example.landmarkapp.model.Retrofit.Interface.AccountService
import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.AccountResponse
import com.example.landmarkapp.model.Retrofit.response.LoginEmployeeResponse
import com.example.landmarkapp.model.Retrofit.response.RegisterResponse
import com.example.landmarkapp.utils.StaticData
import com.example.landmarkapp.utils.TruthCertificates
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class AccountApi {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(StaticData.url + StaticData.folder + StaticData.php + StaticData.account_php)
        //.client(TruthCertificates.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(AccountService::class.java)

    fun insertAccount(request: InsertAccountRequest): Call<RegisterResponse> {
        return api.insertAccount(request)
    }

    fun insertTLogin(request: InsertTLoginRequest): Call<String>{
        return api.insertTLogin(request)
    }

    fun employeeLogin(request: LoginEmployeeRequest): Call<LoginEmployeeResponse> {
        return api.loginEmployee(request)
    }

    fun employeeLogout(request: LogoutEmployeeRequest): Call<String> {
        return api.logoutEmployee(request)
    }

    fun getAccountFromCounter(request: GetAccountCounterRequest): Call<AccountResponse>{
        return api.getAccountFromCounter(request)
    }

    fun getAccount(request: NormalRequest): Call<List<AccountResponse>>{
        return api.getAccount(request)
    }
}
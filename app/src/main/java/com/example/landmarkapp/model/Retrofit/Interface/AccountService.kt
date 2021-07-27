package com.example.landmarkapp.model.Retrofit.Interface

import com.example.landmarkapp.model.Retrofit.RequestValue.*
import com.example.landmarkapp.model.Retrofit.response.AccountResponse
import com.example.landmarkapp.model.Retrofit.response.LoginEmployeeResponse
import com.example.landmarkapp.model.Retrofit.response.RegisterResponse
import com.example.landmarkapp.utils.StaticData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {
    @POST(StaticData.account_php)
    fun insertAccount(
        @Body request: InsertAccountRequest
    ): Call<RegisterResponse>

    @POST(StaticData.account_php)
    fun insertTLogin(
        @Body request: InsertTLoginRequest
    ): Call<String>

    @POST(StaticData.account_php)
    fun loginEmployee(
        @Body request: LoginEmployeeRequest
    ): Call<LoginEmployeeResponse>

    @POST(StaticData.account_php)
    fun logoutEmployee(
        @Body request: LogoutEmployeeRequest
    ): Call<String>

    @POST(StaticData.account_php)
    fun getAccountFromCounter(
        @Body request: GetAccountCounterRequest
    ): Call<AccountResponse>

    @POST(StaticData.account_php)
    fun getAccount(
        @Body request: NormalRequest
    ): Call<List<AccountResponse>>

}
package com.example.tbc_test4.repository

import com.example.tbc_test4.conversations_fragment.Conversation
import retrofit2.Call
import retrofit2.http.GET

interface IResponseList {
    @GET("v3/744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    fun getData(): Call<List<Conversation>>
}
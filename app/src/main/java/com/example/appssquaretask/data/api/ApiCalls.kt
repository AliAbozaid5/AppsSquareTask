package com.example.appssquaretask.data.api

import com.example.appssquaretask.data.model.Posts
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback



object ApiCalls {
    val service = ApiClient.client.create(ApiInterface::class.java)
    fun getPosts(
        callback: Callback<List<Posts>>
    ) {
        val posts = service.getPosts()
        posts.enqueue(callback)
    }



}
package com.example.appssquaretask.data.api
import com.example.appssquaretask.data.model.Posts
import com.example.appssquaretask.utils.Constants
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET(Constants.posts)
    fun getPosts(): Call<List<Posts>>

}

package com.example.appssquaretask.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appssquaretask.data.api.ApiCalls
import com.example.appssquaretask.data.model.Posts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel(private val apiCalls: ApiCalls):ViewModel() {
    private val posts = MutableLiveData<List<Posts>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
         apiCalls.getPosts(object :Callback<List<Posts>>{
             override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                 posts.postValue(null)

             }

             override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                 posts.postValue(response.body())
             }
         })

    }

    fun getPosts(): LiveData<List<Posts>> {
        return posts
    }


}
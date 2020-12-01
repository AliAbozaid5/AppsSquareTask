package com.example.appssquaretask.ui.main.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appssquaretask.data.api.ApiCalls

class ViewModelFactory(private val apiCalls : ApiCalls) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            return PostsViewModel(apiCalls) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
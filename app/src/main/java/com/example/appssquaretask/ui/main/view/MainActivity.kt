package com.example.appssquaretask.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appssquaretask.R
import com.example.appssquaretask.data.api.ApiCalls
import com.example.appssquaretask.data.model.Posts
import com.example.appssquaretask.ui.main.viewmodel.ViewModelFactory
import com.example.appssquaretask.ui.main.adapter.PostsAdapter
import com.example.appssquaretask.ui.main.viewmodel.PostsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: PostsViewModel
    private lateinit var adapter: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }


    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostsAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getPosts().observe(this, Observer {

            when(it.isEmpty()){
                false->{
                    progressBar.visibility = View.GONE
                    renderList(it)
                    recyclerView.visibility = View.VISIBLE
                }



            }
        })
    }

    private fun renderList(users: List<Posts>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiCalls)
        ).get(PostsViewModel::class.java)
    }
}

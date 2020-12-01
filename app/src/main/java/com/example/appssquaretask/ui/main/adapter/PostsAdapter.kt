package com.example.appssquaretask.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appssquaretask.R
import com.example.appssquaretask.data.model.Posts
import kotlinx.android.synthetic.main.item_layout.view.*

class PostsAdapter (
    private val posts: ArrayList<Posts>
) : RecyclerView.Adapter<PostsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(posts: Posts) {
            itemView.textViewPostTitle.text = posts.title
            itemView.textViewPostBody.text = posts.body
            itemView.textViewPostTitleId.text=posts.id.toString()+"- "

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(posts[position])

    fun addData(list: List<Posts>) {
        posts.addAll(list)
    }

}
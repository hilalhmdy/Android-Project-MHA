package com.example.storyapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.storyapp.databinding.StoryCardBinding
import com.example.storyapp.model.response.ListStoryItem
import com.example.storyapp.view.detail.DetailStoryActivity

class StoryListAdapter(private val context: Context) : PagingDataAdapter<ListStoryItem, StoryListAdapter.ViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = StoryCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view,context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            viewHolder.bind(data)
            viewHolder.itemView.setOnClickListener {
                val intentDetail = Intent(viewHolder.itemView.context, DetailStoryActivity::class.java)
                intentDetail.putExtra("DATA", data)
                viewHolder.itemView.context.startActivity(intentDetail)

            }
        }

    }

    class ViewHolder(private val binding: StoryCardBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.usernameStory.text = data.name
            binding.descriptionStory.text = data.description
            Glide.with(context)
                .load(data.photoUrl)
                .apply(RequestOptions().centerCrop())
                .into(binding.photoStory)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

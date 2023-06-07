package com.example.mygithubuser.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithubuser.R
import com.example.mygithubuser.database.Favorite
import com.example.mygithubuser.model.ItemsItem
import com.example.mygithubuser.ui.detail.DetailUserActivity

class FavoriteAdapter(private val listUser: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = listUser[position].nameUser
        Glide.with(viewHolder.itemView)
            .load(listUser[position].avatars)
            .apply(RequestOptions().centerCrop())
            .into(viewHolder.imgPhoto)

        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.EXTRA_USER, listUser[position])
            viewHolder.itemView.context.startActivity(intentDetail)

            Log.i("coba", listUser[position].toString())
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.profile_image)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
    }


}
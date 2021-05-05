package com.masudinn.consumerApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.masudinn.consumerApp.databinding.ItemBinding
import com.masudinn.consumerApp.model.GithubUser

class FavoriteAdapter(private val githubUsers: ArrayList<GithubUser>, private val clickListener: (GithubUser) -> Unit)
    : RecyclerView.Adapter<FavoriteAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(githubUser: GithubUser, click: (GithubUser) -> Unit) {
            binding.data = githubUser
            binding.root.transitionName = githubUser.login
            binding.root.setOnClickListener { click(githubUser) }
        }
    }

    fun setData(items: List<GithubUser>){
        githubUsers.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(ItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) = holder.bind(githubUsers[position], clickListener)

    override fun getItemCount(): Int = githubUsers.size
}
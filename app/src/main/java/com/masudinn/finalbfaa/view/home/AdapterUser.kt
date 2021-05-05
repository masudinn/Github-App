package com.masudinn.finalbfaa.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masudinn.finalbfaa.databinding.ItemUserBinding
import com.masudinn.finalbfaa.model.GithubUser

class AdapterUser (private val githubUsers: ArrayList<GithubUser>, private val clickListener: (String, View) -> Unit) : RecyclerView.Adapter<AdapterUser.UsersViewHolder>() {

    fun setData(items: List<GithubUser>){
        githubUsers.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) = holder.bind(githubUsers[position], clickListener)

    override fun getItemCount(): Int = githubUsers.size

    inner class UsersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(githubUser: GithubUser, click: (String, View) -> Unit) {
            binding.data = githubUser
            binding.root.transitionName = githubUser.login
            binding.root.setOnClickListener { click(githubUser.login, binding.root) }
        }
    }
}
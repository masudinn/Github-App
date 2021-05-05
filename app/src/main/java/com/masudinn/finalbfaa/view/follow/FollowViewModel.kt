package com.masudinn.finalbfaa.view.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.masudinn.finalbfaa.Utils.DetailPager
import com.masudinn.finalbfaa.Utils.Resource
import com.masudinn.finalbfaa.data.Repository
import com.masudinn.finalbfaa.model.GithubUser

class FollowViewModel : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    private lateinit var type: DetailPager

    val dataFollow: LiveData<Resource<List<GithubUser>>> = Transformations
        .switchMap(username) {
            when (type) {
                DetailPager.FOLLOWER -> {
                    Repository.getFollowers(it)
                }
                DetailPager.FOLLOWING -> {
                    Repository.getFollowing(it)
                }
            }
        }

    fun setFollow(user: String, typeView: DetailPager) {
        if (username.value == user) {
            return
        }
        username.value = user
        type = typeView
    }
}
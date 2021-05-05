package com.masudinn.finalbfaa.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.masudinn.finalbfaa.Utils.Resource
import com.masudinn.finalbfaa.data.Repository
import com.masudinn.finalbfaa.model.GithubUser

class ViewModelHome : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    val searchResult: LiveData<Resource<List<GithubUser>>> = Transformations
        .switchMap(username) {
            Repository.searchUsers(it)
        }

    fun setSearch(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }
}
package com.masudinn.consumerApp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {

    fun getUserList(): LiveData<List<GithubUser>> = liveData(Dispatchers.IO){
        emit(source.getUsers())
    }
}
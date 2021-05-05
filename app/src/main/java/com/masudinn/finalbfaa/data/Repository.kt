package com.masudinn.finalbfaa.data

import androidx.lifecycle.liveData
import com.masudinn.finalbfaa.Utils.Resource
import com.masudinn.finalbfaa.database.UserDao
import com.masudinn.finalbfaa.server.RetroClient
import kotlinx.coroutines.Dispatchers

object Repository {

    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val userSearch = RetroClient.apiClient.searchUsers(query)
            emit(Resource.success(userSearch.items))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(RetroClient.apiClient.userFollower(username)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowing(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(RetroClient.apiClient.userFollowing(username)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun  getFavorite(userDao: UserDao) = userDao.getUserList()
}
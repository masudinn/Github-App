package com.masudinn.finalbfaa.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.masudinn.finalbfaa.Utils.Resource
import com.masudinn.finalbfaa.database.UserDao
import com.masudinn.finalbfaa.model.GithubUser
import com.masudinn.finalbfaa.server.RetroClient
import kotlinx.coroutines.Dispatchers

class UserFavoriteRepository(private val userDao: UserDao) {
    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val user = userDao.getUserDetail(username)
        if (user != null) {
            favorite.postValue(true)
            emit(Resource.success(user))
        } else {
            favorite.postValue(false)
            try {
                emit(Resource.success(RetroClient.apiClient.userDetail(username)))
            } catch (e: Exception) {
                emit(Resource.error(null, e.message ?: "Error"))
            }
        }
    }

    suspend fun insert(user: GithubUser) {
        userDao.insertUser(user)
        favorite.value = true
    }

    suspend fun delete(user: GithubUser) {
        userDao.deleteUser(user)
        favorite.value = false
    }

    val isFavorite: LiveData<Boolean> = favorite
}

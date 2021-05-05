package com.masudinn.finalbfaa.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.masudinn.finalbfaa.Utils.Resource
import com.masudinn.finalbfaa.data.UserFavoriteRepository
import com.masudinn.finalbfaa.database.UserDao
import com.masudinn.finalbfaa.database.UserDatabase
import com.masudinn.finalbfaa.model.GithubUser
import kotlinx.coroutines.launch

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private var userDao: UserDao = UserDatabase.getDatabase(app).userDao()
    private var userFavoriteRepos: UserFavoriteRepository

    init {
        userFavoriteRepos = UserFavoriteRepository(userDao)
    }

    fun data(username: String): LiveData<Resource<GithubUser>> = userFavoriteRepos.getDetailUser(username)

    fun addFavorite(githubUser: GithubUser) = viewModelScope.launch {
        userFavoriteRepos.insert(githubUser)
    }

    fun removeFavorite(githubUser: GithubUser) = viewModelScope.launch {
        userFavoriteRepos.delete(githubUser)
    }

    val isFavorite: LiveData<Boolean> = userFavoriteRepos.isFavorite
}
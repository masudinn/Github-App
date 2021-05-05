package com.masudinn.finalbfaa.view.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.masudinn.finalbfaa.data.Repository
import com.masudinn.finalbfaa.database.UserDatabase
import com.masudinn.finalbfaa.model.GithubUser

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<GithubUser>>

    init {
        val userDao = UserDatabase.getDatabase(app).userDao()
        dataFavorite = Repository.getFavorite(userDao)
    }
}
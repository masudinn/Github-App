package com.masudinn.consumerApp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.masudinn.consumerApp.model.UserDataSource
import com.masudinn.consumerApp.model.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val source =
            UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }

    var userLists = repository.getUserList()
}
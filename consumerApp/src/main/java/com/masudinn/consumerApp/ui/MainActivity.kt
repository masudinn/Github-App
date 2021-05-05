package com.masudinn.consumerApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.masudinn.consumerApp.R
import com.masudinn.consumerApp.adapter.FavoriteAdapter
import com.masudinn.consumerApp.viewmodel.UserViewModel
import com.masudinn.consumerApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UserViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var usersAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        usersAdapter = FavoriteAdapter(ArrayList()) { githubUser ->
            val fragment = DetailFavoriteFragment.newInstance(githubUser)
        }

        mainBinding.mainRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        usersViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(UserViewModel::class.java)
        visible()
        usersViewModel.userLists.observe(this, Observer { users ->
            if (!users.isNullOrEmpty()) {
                gone(false)
                usersAdapter.setData(users)
            } else {
                gone(true)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language_settings){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun visible(){
        mainBinding.progress.visibility = View.VISIBLE
    }

    private fun gone(error: Boolean){
        if (error){
            mainBinding.apply {
                progress.visibility = View.GONE
                errLayout.visibility = View.VISIBLE
            }
        } else {
            mainBinding.progress.visibility = View.GONE
        }
    }
}

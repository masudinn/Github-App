package com.masudinn.finalbfaa.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.masudinn.finalbfaa.R
import com.masudinn.finalbfaa.Utils.StateMovie
import com.masudinn.finalbfaa.databinding.FragmentFavoriteBinding
import com.masudinn.finalbfaa.view.home.AdapterUser


class FavoriteFragment : Fragment(), StateMovie {

    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: AdapterUser
    private val favoriteViewModel: FavoriteViewModel by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.resources?.getString(R.string.favorite)
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = AdapterUser(arrayListOf()) { username, iv ->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsDestination(username),
                FragmentNavigatorExtras(iv to username)
            )
        }

        favoriteBinding.recyclerFav.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        observeFavorite()
    }

    private fun observeFavorite() {
        favoriteLoading(favoriteBinding)
        favoriteViewModel.dataFavorite.observe(viewLifecycleOwner, Observer {
            it?.let { users ->
                if (!users.isNullOrEmpty()){
                    favoriteSuccess(favoriteBinding)
                    favoriteAdapter.setData(users)
                } else {
                    favoriteError(
                        favoriteBinding,
                        resources.getString(R.string.not_have, "", resources.getString(R.string.favorite))
                    )
                }
            }
        })
    }

    override fun favoriteLoading(favoriteFragmentBinding: FragmentFavoriteBinding): Int? {
        favoriteBinding.apply {
            errlayout.mainNotFound.visibility = gone
            progress.start()
            progress.loadingColor = R.color.purple_500
            recyclerFav.visibility = gone
        }
        return super.favoriteLoading(favoriteFragmentBinding)
    }

    override fun favoriteSuccess(favoriteFragmentBinding: FragmentFavoriteBinding): Int? {
        favoriteBinding.apply {
            errlayout.mainNotFound.visibility = gone
            progress.stop()
            recyclerFav.visibility = visible
        }
        return super.favoriteSuccess(favoriteFragmentBinding)
    }

    override fun favoriteError(
        favoriteFragmentBinding: FragmentFavoriteBinding,
        message: String?
    ): Int? {
        favoriteBinding.apply {
            errlayout.apply {
                mainNotFound.visibility = visible
                emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            progress.stop()
            recyclerFav.visibility = gone
        }
        return super.favoriteError(favoriteFragmentBinding, message)
    }
}
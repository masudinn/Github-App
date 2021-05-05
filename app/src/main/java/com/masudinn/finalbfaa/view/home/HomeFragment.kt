package com.masudinn.finalbfaa.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.masudinn.finalbfaa.R
import com.masudinn.finalbfaa.Utils.State
import com.masudinn.finalbfaa.Utils.StateMovie
import com.masudinn.finalbfaa.databinding.FragmentHomeBinding


class HomeFragment : Fragment(),StateMovie {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeAdapter: AdapterUser
    private val homeViewModel: ViewModelHome by navGraphViewModels(R.id.my_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding =  FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        homeBinding.errLayout.emptyText.text = resources.getString(R.string.search_hint)

        homeAdapter = AdapterUser(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.detailsAction(username),
                FragmentNavigatorExtras(
                    iv to username
                )
            )
        }

        homeBinding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        homeBinding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.setSearch(query)
                    homeBinding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
        observeHome()
    }

    private fun observeHome() {
        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.state) {
                    State.SUCCESS -> {
                        resource.data?.let { users ->
                            if (!users.isNullOrEmpty()) {
                                homeSuccess(homeBinding)
                                homeAdapter.setData(users)
                            } else {
                                homeError(homeBinding, null)
                            }
                        }
                    }
                    State.LOADING -> homeLoading(homeBinding)
                    State.ERROR -> homeError(homeBinding, it.message)
                }
            }
        })
    }

    override fun homeLoading(homeBinding: FragmentHomeBinding): Int? {
        homeBinding.apply {
            errLayout.mainNotFound.visibility = gone
            progress.start()
            progress.loadingColor = R.color.purple_500
            recyclerHome.visibility = gone
        }
        return super.homeLoading(homeBinding)
    }

    override fun homeSuccess(homeBinding: FragmentHomeBinding): Int? {
        homeBinding.apply {
            errLayout.mainNotFound.visibility = gone
            progress.stop()
            recyclerHome.visibility = visible
        }
        return super.homeSuccess(homeBinding)
    }

    override fun homeError(homeBinding: FragmentHomeBinding, message: String?): Int? {
        homeBinding.apply {
            errLayout.apply {
                mainNotFound.visibility = visible
                emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            progress.stop()
            recyclerHome.visibility = gone
        }
        return super.homeError(homeBinding, message)
    }
}
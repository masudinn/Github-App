package com.masudinn.finalbfaa.view.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.masudinn.finalbfaa.R
import com.masudinn.finalbfaa.Utils.DetailPager
import com.masudinn.finalbfaa.Utils.State
import com.masudinn.finalbfaa.Utils.StateMovie
import com.masudinn.finalbfaa.databinding.FragmentFollowBinding
import com.masudinn.finalbfaa.view.home.AdapterUser
import com.shashank.sony.fancytoastlib.FancyToast


class FollowFragment : Fragment(),StateMovie {

    companion object {
        fun newInstance(username: String, type: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                    putString(TYPE, type)
                }
            }
        private const val TYPE = "type"
        private const val USERNAME = "username"
    }

    private lateinit var followBinding: FragmentFollowBinding
    private lateinit var usersAdapter: AdapterUser
    private lateinit var followViewModel: FollowViewModel
    private lateinit var username: String
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
            type = it.getString(TYPE)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        followBinding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return followBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        usersAdapter = AdapterUser(arrayListOf()) { user, _ ->
            FancyToast.makeText(context, user, Toast.LENGTH_SHORT, FancyToast.INFO, false).show()
        }

        followBinding.recylerFollow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        when (type) {
            resources.getString(R.string.following) -> followViewModel.setFollow(username, DetailPager.FOLLOWING)
            resources.getString(R.string.followers) -> followViewModel.setFollow(username, DetailPager.FOLLOWER)
            else -> followError(followBinding, null)
        }
        observeFollow()
    }

    private fun observeFollow() {
        followViewModel.dataFollow.observe(viewLifecycleOwner, Observer {
            when (it.state) {
                State.SUCCESS ->
                    if (!it.data.isNullOrEmpty()) {
                        followSuccess(followBinding)
                        usersAdapter.run { setData(it.data) }
                    } else {
                        followError(followBinding, resources.getString(R.string.not_have, username, type))
                    }
                State.LOADING -> followLoading(followBinding)
                State.ERROR -> followError(followBinding, it.message)
            }
        })
    }

    override fun followLoading(followBinding: FragmentFollowBinding): Int? {
        followBinding.apply {
            errLayout.mainNotFound.visibility = gone
            progress.start()
            progress.loadingColor = R.color.purple_500
            recylerFollow.visibility = gone
        }
        return super.followLoading(followBinding)
    }

    override fun followSuccess(followBinding: FragmentFollowBinding): Int? {
        followBinding.apply {
            errLayout.mainNotFound.visibility = gone
            progress.stop()
            recylerFollow.visibility = visible
        }
        return super.followSuccess(followBinding)
    }

    override fun followError(followBinding: FragmentFollowBinding, message: String?): Int? {
        followBinding.apply {
            errLayout.apply {
                mainNotFound.visibility = visible
                emptyText.text = message ?: resources.getString(R.string.not_found)
            }
            progress.stop()
            recylerFollow.visibility = gone
        }
        return super.followError(followBinding, message)
    }
}
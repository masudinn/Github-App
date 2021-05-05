package com.masudinn.consumerApp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.masudinn.consumerApp.R
import com.masudinn.consumerApp.model.GithubUser


class DetailFavoriteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_favorite, container, false)
    }
    companion object {
        private const val USER = "USER"

        @JvmStatic
        fun newInstance(githubUser: GithubUser) =
            DetailFavoriteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, githubUser)
                }
            }
    }
 }
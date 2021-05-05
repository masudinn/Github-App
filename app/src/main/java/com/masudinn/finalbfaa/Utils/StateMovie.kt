package com.masudinn.finalbfaa.Utils

import android.view.View
import com.masudinn.finalbfaa.databinding.FragmentFavoriteBinding
import com.masudinn.finalbfaa.databinding.FragmentFollowBinding
import com.masudinn.finalbfaa.databinding.FragmentHomeBinding

interface StateMovie {

    fun homeLoading(homeBinding: FragmentHomeBinding): Int? = null
    fun homeSuccess(homeBinding: FragmentHomeBinding): Int? = null
    fun homeError(homeBinding: FragmentHomeBinding, message: String?): Int? = null

    fun followLoading(followBinding: FragmentFollowBinding): Int? = null
    fun followSuccess(followBinding: FragmentFollowBinding): Int? = null
    fun followError(followBinding: FragmentFollowBinding, message: String?): Int? = null

    fun favoriteLoading(favoriteFragmentBinding: FragmentFavoriteBinding): Int? = null
    fun favoriteSuccess(favoriteFragmentBinding: FragmentFavoriteBinding): Int? = null
    fun favoriteError(favoriteFragmentBinding: FragmentFavoriteBinding, message: String?): Int? = null

    val gone: Int
    get() = View.GONE

    val visible: Int
    get() = View.VISIBLE
}
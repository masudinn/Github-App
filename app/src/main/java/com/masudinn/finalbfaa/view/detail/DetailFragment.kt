package com.masudinn.finalbfaa.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.masudinn.finalbfaa.R
import com.masudinn.finalbfaa.Utils.State
import com.masudinn.finalbfaa.databinding.FragmentDetailBinding
import com.masudinn.finalbfaa.model.GithubUser
import com.masudinn.finalbfaa.view.follow.FollowFragment
import com.shashank.sony.fancytoastlib.FancyToast

class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var githubUser: GithubUser
    private val args: DetailFragmentArgs by navArgs()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return detailBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailBinding.content.transitionName = args.Username
        detailBinding.fabFavorite.setOnClickListener { addOrRemoveFavorite() }
        val tabList = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.following)
        )
        pagerAdapter = PagerAdapter(tabList, args.Username, this)
        detailBinding.pager.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabs, detailBinding.pager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail() {
        detailViewModel.data(args.Username).observe(viewLifecycleOwner, Observer {
            if(it.state == State.SUCCESS){
                githubUser = it.data!!
                detailBinding.data = it.data
            }
        })

        detailViewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            isFavorite = it
            changeFavorite(it)
        })
    }

    private fun addOrRemoveFavorite(){
        if (!isFavorite){
            detailViewModel.addFavorite(githubUser)
            FancyToast.makeText(
                context, resources.getString(R.string.favorite_add, githubUser.login), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false
            ).show()
        } else {
            detailViewModel.removeFavorite(githubUser)
            FancyToast.makeText(
                context, resources.getString(R.string.favorite_remove, githubUser.login), Toast.LENGTH_SHORT, FancyToast.ERROR, false
            ).show()
        }
    }

    private fun changeFavorite(condition: Boolean){
        if (condition){
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            detailBinding.fabFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = tabList.size

        override fun createFragment(position: Int): Fragment =
            FollowFragment.newInstance(username, tabList[position])
    }
}
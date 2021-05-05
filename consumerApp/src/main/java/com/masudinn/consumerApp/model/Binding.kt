package com.masudinn.consumerApp.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.masudinn.consumerApp.R

@BindingAdapter("avatar")
fun avatar(imageView: ImageView, avatar: String) =
        Glide.with(imageView)
                .load(avatar)
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_baseline_settings_24))
                .into(imageView)
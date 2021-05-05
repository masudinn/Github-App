package com.masudinn.finalbfaa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    val total_count : String,
    val incomplete_results: Boolean? = null,
    val items : List<GithubUser>
): Parcelable
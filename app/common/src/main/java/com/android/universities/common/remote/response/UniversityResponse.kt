package com.android.universities.common.remote.response

import com.google.gson.annotations.SerializedName

data class UniversityResponse(
    val name: String,

    @SerializedName("state-province")
    val state: String?,

    val country: String,

    @SerializedName("alpha_two_code")
    val countryCode: String,

    @SerializedName("web_pages")
    val webPages: List<String>?,
)
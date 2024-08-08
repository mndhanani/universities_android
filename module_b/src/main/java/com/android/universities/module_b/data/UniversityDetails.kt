package com.android.universities.module_b.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityDetails(
    val name: String,
    val state: String?,
    val country: String,
    val countryCode: String,
    val webPage: String?,
) : Parcelable {
    override fun toString(): String {
        return "UniversityDetails(name='$name', state=$state, country='$country', countryCode='$countryCode', webPage=$webPage)"
    }
}
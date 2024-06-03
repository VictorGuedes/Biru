package com.oligue.app.biru.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Brewerie(
    val id: String,
    val address_1: String,
    val brewery_type: String,
    val city: String,
    val country: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val phone: String,
    val postal_code: String,
    val state: String,
    val state_province: String,
    val street: String,
    val website_url: String
): Parcelable
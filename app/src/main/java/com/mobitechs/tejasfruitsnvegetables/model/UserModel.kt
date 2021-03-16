package com.mobitechs.tejasfruitsnvegetables.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class UserModel(
    var userId: String,
    var cardNo: String,
    var name: String,
    var mobileNo: String,
    var emailId: String,
    var gender: String,
    var dob: String,
    var address: String,
    var password: String,
    var userType: String,
    var discountPercent: String,
    var minimumAmount: String,
    var vendorLogo: String,
    var vendorBanner: String,
    var category: String,
    var description: String
):Parcelable
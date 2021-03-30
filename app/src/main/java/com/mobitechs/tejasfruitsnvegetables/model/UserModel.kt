package com.mobitechs.tejasfruitsnvegetables.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class UserModel(
    var userId: String,
    var userName: String,
    var mobileNo: String,
    var emailId: String,
    var address: String,
    var password: String,
    var userImgPath: String,
    var userType: String,
):Parcelable
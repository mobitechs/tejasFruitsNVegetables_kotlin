package com.mobitechs.tejasfruitsnvegetables.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MyOrderResponse(
    val Response: List<MyOrderListItems>,
    val code: Int,
    val status: Int
)

@SuppressLint("ParcelCreator")
@Parcelize
data class MyOrderListItems(
    val Amount: String,
    val deliveryCharges: String,
    val discountedAmount: String,
    val address: String,
    val addressId: String,
    val area: String,
    val city: String,
    val clientBusinessId: String,
    val createdDate: String,
    val editedBy: String,
    val editedDate: String,
    val emailId: String,
    val isDelete: String,
    val landMark: String,
    val mobileNo: String,
    val orderDetails: String,
    val orderId: String,
    val otp: String,
    val password: String,
    val paymentDetails: String,
    val pincode: String,
    val status: String,
    val updatedDate: String,
    val userId: String,
    val userName: String,
    val userType: String
):Parcelable
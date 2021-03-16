package com.mobitechs.tejasfruitsnvegetables.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductListItemsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("Response")
    val productListItems: List<ProductListItems>,
    @SerializedName("status")
    val status: Int
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class ProductListItems(
    val amount: String,
    val categoryId: String,
    val categoryName: String,
    val clientBusinessId: String,
    val createdBy: String,
    val createdDate: String,
    val description: String,
    val editedBy: String,
    val editedDate: String,
    val finalPrice: String,
    val img: String,
    val isDeleted: String,
    val productId: String,
    val productName: String,
    val uniteType: String,
    val weight: String,
    var _qty: Int = 1,
    var _qtyWisePrice: Int = 0

) : Parcelable
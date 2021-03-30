package com.mobitechs.tejasfruitsnvegetables.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.AddressListItems
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiGetCall
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import com.mobitechs.tejasfruitsnvegetables.view.fragment.CartFragment


class VendorListActivityRepository(val application: Application) : ApiResponse
{

    val showProgressBar = MutableLiveData<Boolean>()
    val listItems = MutableLiveData<ArrayList<ProductListItems>>()
    val allProductListItems = MutableLiveData<ArrayList<ProductListItems>>()
    val addressListItems = MutableLiveData<ArrayList<AddressListItems>>()
    val myOrderListItems = MutableLiveData<ArrayList<MyOrderListItems>>()
    val adminOrderListItems = MutableLiveData<ArrayList<MyOrderListItems>>()

    var method = ""
    var userId=""


    fun changeState() {
        showProgressBar.value = !(showProgressBar != null && showProgressBar.value!!)
    }


    fun getProductList(categoryId: String) {
        showProgressBar.value = true

        method = "GetCategoryWiseProductList"
        var url = Constants.BASE_URL + "?method=$method&categoryId=$categoryId&clientBusinessId=${Constants.clientBusinessId}"

        apiGetCall(url, this, method)

    }
    fun getAllProduct() {

        method = "GetProductList"
        var url = Constants.BASE_URL + "?method=$method&clientBusinessId=${Constants.clientBusinessId}"
        apiGetCall(url, this, method)

    }


    fun getMyOrderList() {
        showProgressBar.value = true
        if(SharePreferenceManager.getInstance(application).getUserLogin(Constants.USERDATA) != null){
            userId = SharePreferenceManager.getInstance(application).getUserLogin(Constants.USERDATA)!!.get(0).userId
        }

        method = "GetMyOrder"
        var url = Constants.BASE_URL + "?method=$method&userId=$userId&clientBusinessId=${Constants.clientBusinessId}"
        apiGetCall(url, this, method)

    }
    fun getAdminOrderList() {
        showProgressBar.value = true
        method = "GetAllOrder"
        var url = Constants.BASE_URL + "?method=$method&clientBusinessId=${Constants.clientBusinessId}"
        apiGetCall(url, this, method)

    }

    fun getAddressList() {
        showProgressBar.value = true

        if(SharePreferenceManager.getInstance(application).getUserLogin(Constants.USERDATA)!!.get(0).userId != null){
            userId = SharePreferenceManager.getInstance(application).getUserLogin(Constants.USERDATA)!!.get(0).userId
        }
        method = "GetAllAddress"
        var url = Constants.BASE_URL + "?method=$method&userId=$userId&clientBusinessId=${Constants.clientBusinessId}"

        apiGetCall(url, this, method)

    }


    override fun onSuccess(data: Any, tag: String) {
        showProgressBar.value = false

        if (data.equals("List not available")) {
            application.showToastMsg(data.toString())
        } else {
            val gson = Gson()

            if(method == "GetCategoryWiseProductList" ){
                val type = object : TypeToken<ArrayList<ProductListItems>>() {}.type
                var productListItems: ArrayList<ProductListItems>? = gson.fromJson(data.toString(), type)
                listItems.value = productListItems
            }
            else if(method == "GetProductList"){
                val type = object : TypeToken<ArrayList<ProductListItems>>() {}.type
                var productListItems: ArrayList<ProductListItems>? = gson.fromJson(data.toString(), type)
                allProductListItems.value = productListItems
            }
            else  if(method == "GetMyOrder"){
                val type = object : TypeToken<ArrayList<MyOrderListItems>>() {}.type
                var items: ArrayList<MyOrderListItems>? = gson.fromJson(data.toString(), type)
                myOrderListItems.value = items
            } else  if(method == "GetAllOrder"){
                val type = object : TypeToken<ArrayList<MyOrderListItems>>() {}.type
                var items: ArrayList<MyOrderListItems>? = gson.fromJson(data.toString(), type)
                adminOrderListItems.value = items
            }
            else  if(method == "GetAllAddress"){
                val type = object : TypeToken<ArrayList<AddressListItems>>() {}.type
                var items: ArrayList<AddressListItems>? = gson.fromJson(data.toString(), type)
                addressListItems.value = items
            }
        }


    }

    override fun onFailure(message: String) {
        showProgressBar.value = false
    }



}
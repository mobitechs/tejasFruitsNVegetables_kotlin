package com.mobitechs.tejasfruitsnvegetables.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mobitechs.tejasfruitsnvegetables.model.AddressListItems
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.repository.VendorListActivityRepository

class VendorListActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VendorListActivityRepository(application)
    val showProgressBar: LiveData<Boolean>
    val listItems: LiveData<ArrayList<ProductListItems>>
    val allProductListItems: LiveData<ArrayList<ProductListItems>>
    val addressListItems: LiveData<ArrayList<AddressListItems>>
    val myOrderListItems: LiveData<ArrayList<MyOrderListItems>>


    init {
        this.showProgressBar = repository.showProgressBar
        this.listItems = repository.listItems
        this.addressListItems = repository.addressListItems
        this.myOrderListItems = repository.myOrderListItems
        this.allProductListItems = repository.allProductListItems
    }

    fun changeState() {
        repository.changeState()
    }


    fun getProductList(categoryId: String) {
        repository.getProductList(categoryId)
    }

    fun getMyOrderList() {
        repository.getMyOrderList()
    }

   fun getAddressList() {
        repository.getAddressList()
    }
    fun getAllProduct() {
        repository.getAllProduct()
    }


}
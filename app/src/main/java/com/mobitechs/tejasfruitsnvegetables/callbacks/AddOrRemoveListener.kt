package com.mobitechs.tejasfruitsnvegetables.callbacks

import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems

interface AddOrRemoveListener {
    fun addToCart(item: ProductListItems, position: Int)
    fun removeFromCart(item: ProductListItems, position: Int)
    fun editProduct(item: ProductListItems, position: Int)
    fun deleteProduct(item: ProductListItems, position: Int)
}
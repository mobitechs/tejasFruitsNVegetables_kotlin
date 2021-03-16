package com.mobitechs.tejasfruitsnvegetables.callbacks

import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems

interface AddOrRemoveListener {
    fun addToCart(item: ProductListItems, position: Int)
    fun removeFromCart(item: ProductListItems, position: Int)
}
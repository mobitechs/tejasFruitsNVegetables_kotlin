package com.mobitechs.tejasfruitsnvegetables.callbacks

import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems

interface CartListUpdated {
    fun cartListUpdated(cartListItems: ArrayList<ProductListItems>)
}
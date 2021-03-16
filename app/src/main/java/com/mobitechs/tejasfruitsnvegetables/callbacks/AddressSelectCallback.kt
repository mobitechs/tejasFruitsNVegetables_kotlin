package com.mobitechs.tejasfruitsnvegetables.callbacks

import com.mobitechs.tejasfruitsnvegetables.model.AddressListItems

interface AddressSelectCallback {
     fun selectedAddress(item: AddressListItems)
     fun selectedAddressToEdit(item: AddressListItems)
     fun selectedAddressToDelete(item: AddressListItems, position: Int)
}
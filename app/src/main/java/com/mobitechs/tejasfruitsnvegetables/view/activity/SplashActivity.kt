package com.mobitechs.tejasfruitsnvegetables.view.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.openActivity
import com.mobitechs.tejasfruitsnvegetables.utils.openClearActivity
import com.mobitechs.tejasfruitsnvegetables.utils.setStatusColor
import com.mobitechs.tejasfruitsnvegetables.viewModel.VendorListActivityViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: VendorListActivityViewModel

    var allProductListItems = ArrayList<ProductListItems>()
    var tab1List = ArrayList<ProductListItems>()
    var tab2List = ArrayList<ProductListItems>()
    var tab3List = ArrayList<ProductListItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val window: Window = window
        setStatusColor(window,resources.getColor(R.color.colorPrimaryDark))

        Handler().postDelayed({ checkLogin() }, SPLASH_TIME_OUT.toLong())
       // getAllProducts()
    }

    private fun getAllProducts() {
        viewModel = ViewModelProvider(this).get(VendorListActivityViewModel::class.java)
        viewModel.getAllProduct()

        viewModel.allProductListItems.observe(this, Observer {
            allProductListItems = it

            SharePreferenceManager.getInstance(this).saveCartListItems(Constants.AllProductList, allProductListItems)

            for (i in 0..allProductListItems.size - 1) {
                var catId = allProductListItems.get(i).categoryId
                if (catId == "1") {
                    tab1List.add(allProductListItems.get(i))
                }
                else if (catId == "2") {
                    tab2List.add(allProductListItems.get(i))
                }
                else if (catId == "3") {
                    tab3List.add(allProductListItems.get(i))
                }
            }
            SharePreferenceManager.getInstance(this).saveCartListItems(Constants.tab1List, tab1List)
            SharePreferenceManager.getInstance(this).saveCartListItems(Constants.tab2List, tab2List)
            SharePreferenceManager.getInstance(this).saveCartListItems(Constants.tab3List, tab3List)

            checkLogin()
        })
    }


    fun checkLogin() {
        openClearActivity(HomeActivity::class.java)
    }


    companion object {
        private const val SPLASH_TIME_OUT = 2000
    }

}
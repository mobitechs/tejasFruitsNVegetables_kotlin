package com.mobitechs.tejasfruitsnvegetables.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.AlertDialogBtnClickedCallBack
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.*
import com.mobitechs.tejasfruitsnvegetables.view.fragment.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.contenair.*
import kotlinx.android.synthetic.main.drawer_layout.*


class HomeActivity : AppCompatActivity(), View.OnClickListener, AlertDialogBtnClickedCallBack {

    private var doubleBackToExitPressedOnce = false
    lateinit var toolbar: ActionBar
    var userType = ""
    var minAmt = ""
    var discount = ""
//    var searchText = ""
    var cartCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val window: Window = window
        setStatusColor(window, resources.getColor(R.color.colorPrimary))

        drawerInit()
        displayView(1)
        setupDrawer()

//        txtSearch.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                searchText = s.toString()
////                reviewAdapter.getFilter().filter(searchText)
//
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                // TODO Auto-generated method stub
//            }
//        })
    }

    fun drawerInit() {
        ivMenu.setOnClickListener(this)
        llHome.setOnClickListener(this)
        layoutCart.setOnClickListener(this)
        llProfile.setOnClickListener(this)
        llMyOrder.setOnClickListener(this)
        llShare.setOnClickListener(this)
        ivClose.setOnClickListener(this)
        llLogout.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivMenu -> {
                drawer.openDrawer(Gravity.LEFT)
            }
            R.id.ivClose -> {
                drawerOpenorClose()
            }
            R.id.llHome -> {
                displayView(1)
            }
            R.id.llProfile -> {
                displayView(2)
            }
            R.id.llMyOrder -> {
                displayView(3)
            }
            R.id.layoutCart -> {
                if (cartCount > 0) {
                    displayView(4)
                } else {
                    showToastMsg("Cart is empty.")
                }

            }
            R.id.llShare -> {
                ShareApp()
            }

            R.id.llLogout -> {
                //clear sesssion
                drawerOpenorClose()
                showAlertDialog("Confirmation", "Do you really want to logout?", "Yes", "NO", this)

            }
        }
    }

    fun drawerOpenorClose() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    private fun setupDrawer() {
        val userDetails = SharePreferenceManager.getInstance(this).getUserLogin(Constants.USERDATA)

        if (userDetails?.get(0)?.name != null) {
            txtUserName.setText(userDetails!![0].name)
            txtMobile.setText(userDetails!![0].mobileNo)
            txtEmail.setText(userDetails!![0].emailId)
        }

    }

    private fun ShareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Download the app from given url. \n\n "
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun logout() {
        SharePreferenceManager.getInstance(this).clearSharedPreference(this)
        finish()
    }

    fun displayView(pos: Int) {
        when (pos) {
            1 -> {
                toolbarTitle("Home")
//                addFragment(HomeFragment(), false, R.id.nav_host_fragment, "HomeFragment")
                replaceFragment(HomeFragment(), false, R.id.nav_host_fragment, "HomeFragment")
            }
            2 -> {
                toolbarTitle("Profile")
//                addFragment(
//                    ProfileFragment(),
//                    false,
//                    R.id.nav_host_fragment,
//                    "ProfileFragment"
//                )
                addFragment(
                    ProfileFragment(),
                    false,
                    R.id.nav_host_fragment,
                    "ProfileFragment"
                )
            }
            3 -> {
                toolbarTitle("My Orders")
                addFragment(
                    MyOrderFragment(),
                    false,
                    R.id.nav_host_fragment,
                    "MyOrderFragment"
                )
            }
            4 -> {
                toolbarTitle("Cart")
                addFragment(
                    CartFragment(),
                    false,
                    R.id.nav_host_fragment,
                    "CartFragment"
                )
            }
        }
        drawerOpenorClose()
    }

    private fun toolbarTitle(title: String) {
        tvToolbarTitle.text = title
//        txtSearch.visibility = View.VISIBLE
//        tvToolbarTitle.visibility = View.GONE
    }

    override fun onResume() {
        if(SharePreferenceManager.getInstance(this).getCartListItems(Constants.CartList)!= null){
            var cartListItems = SharePreferenceManager.getInstance(this)
                .getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
            updateCartCount(cartListItems.size)
        }

        super.onResume()
    }

    fun updateCartCount(size: Int) {
        cartCount = size
        if (size == 0) {
            layoutCartCount.visibility = View.GONE
        } else {
            layoutCartCount.visibility = View.VISIBLE
            txtCartCount.text = size.toString()
        }
    }

    fun OpenOrderDetails(bundle: Bundle) {
        addFragmentWithData(
            MyOrderDetailsFragment(),
            false,
            R.id.nav_host_fragment,
            "MyOrderDetailsFragment",
            bundle
        )
    }

    fun OpenAddressList() {
        addFragment(
            AddressListFragment(),
            false,
            R.id.nav_host_fragment,
            "AddressListFragment"
        )
    }

    fun OpenAddAddress(bundle: Bundle) {
        addFragmentWithData(
            AddAddressFragment(),
            false,
            R.id.nav_host_fragment,
            "AddAddressFragment",
            bundle
        )
    }

    override fun positiveBtnClicked() {
        logout()
    }

    override fun negativeBtnClicked() {

    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {

//             if (fragment != null && ((fragment is HomeFragment) || (fragment is TabVegetablesFragment) ||(fragment is TabExoticVegetalbesFragment) ||(fragment is TabFruitsFragment))) {
             if (fragment != null && ((fragment is TabListFragment)) ) {

                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.double_tap), Toast.LENGTH_SHORT).show()

                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

            } else {
                super.onBackPressed()
                displayView(1)
            }
        }
        super.onBackPressed()
    }

}
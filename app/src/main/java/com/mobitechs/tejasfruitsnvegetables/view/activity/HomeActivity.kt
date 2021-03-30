package com.mobitechs.tejasfruitsnvegetables.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
    var cartCount = 0
    var userType=""
    var alertFor=""
    var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val window: Window = window
        setStatusColor(window, resources.getColor(R.color.colorPrimary))

        drawerInit()
        setupDrawer()
        displayView(1)


        isLogin = SharePreferenceManager.getInstance(this).getValueBoolean(Constants.ISLOGIN)

       if(isLogin){
           noLoginLayout.visibility = View.GONE
           userDetailsLayout.visibility = View.VISIBLE
       }
       else{
           noLoginLayout.visibility = View.VISIBLE
           userDetailsLayout.visibility = View.GONE
       }
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
        txtLogin.setOnClickListener(this)

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
            R.id.txtLogin -> {
                openClearActivity(AuthActivity::class.java)
            }
            R.id.llProfile -> {
                if (isLogin) {
                    displayView(2)
                } else {
                    alertFor="Login"
                    showAlertDialog(
                        "Confirmation",
                        "You have not logged in yet, Do you want to login?",
                        "Yes",
                        "NO",
                        this
                    )
                }
            }
            R.id.llMyOrder -> {
                if (isLogin) {
                    displayView(3)
                } else {
                    alertFor="Login"
                    showAlertDialog(
                        "Confirmation",
                        "You have not logged in yet, Do you want to login?",
                        "Yes",
                        "NO",
                        this
                    )
                }

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

        if (userDetails?.get(0)?.userName != null) {
            userType = userDetails!![0].userType
            txtUserName.setText(userDetails!![0].userName)
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
                if(userType.equals(Constants.admin)){
                    replaceFragment(AdminHomeFragment(), false, R.id.nav_host_fragment, "AdminHomeFragment")
                }else{
                    replaceFragment(HomeFragment(), false, R.id.nav_host_fragment, "HomeFragment")
                }

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
                OpenOrderFragment()
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

    fun OpenOrderFragment() {
        addFragment(
            MyOrderFragment(),
            false,
            R.id.nav_host_fragment,
            "MyOrderFragment"
        )
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
            if (fragment != null && (fragment is TabListFragment)) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.double_tap), Toast.LENGTH_SHORT).show()

                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            } else {
                displayView(1)

            }
        }
    }

}
package com.mobitechs.tejasfruitsnvegetables.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.*
import kotlinx.android.synthetic.main.product_detail_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONException
import org.json.JSONObject


class ProductDetailActivity : AppCompatActivity() {


//    lateinit var list: ProductListItemsResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail_layout)

        val window: Window = window
        setStatusColor(window,resources.getColor(R.color.colorPrimaryDark))
//        list = intent.getParcelableExtra("detail")
//
//        ivProdImage.setImage(list.port_img!!, R.drawable.img_not_available)
//        tvProdutname.text = list.title
//        tvPrice.text = "Rs. " + list.price
//        txtProfit.text = getString(R.string.profit_text) + list.profit
//
//        txtType.text = list.VName + " " + list.lName

//        txtDestails.text= list.pro_detail

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            txtDestails.setText(Html.fromHtml(list.pro_detail, Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            txtDestails.setText(Html.fromHtml(list.pro_detail));
//        }
//
//        text_ratings_reviews.text = list.Likes
//        txtProfit.text = getString(R.string.profit_text) + list.profit
//
//        if (list.isCheck.equals("1")) {
//            cbCheck.setChecked(true)
//        } else {
//            cbCheck.setChecked(false)
//        }


    }






}
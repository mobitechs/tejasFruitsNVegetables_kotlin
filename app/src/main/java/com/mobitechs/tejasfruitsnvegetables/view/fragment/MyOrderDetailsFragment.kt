package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.utils.Constants


class MyOrderDetailsFragment : Fragment() {

    lateinit var rootView: View
    lateinit var listItem :MyOrderListItems
    lateinit var txtOrderId : AppCompatTextView
    lateinit var txtOrderStatus : AppCompatTextView
    lateinit var txtOrderDate : AppCompatTextView
    lateinit var txtOrderDetails : AppCompatTextView
    lateinit var txtAddress : AppCompatTextView
    lateinit var txtEmail : AppCompatTextView
    lateinit var txtMobileNo : AppCompatTextView
    lateinit var txtAmount : AppCompatTextView
    lateinit var txtDelivery : AppCompatTextView
    lateinit var txtTotal : AppCompatTextView
    lateinit var txtAdminEmail : AppCompatTextView
    lateinit var txtAdminMobileNo : AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_order_details, container, false)
        intView()
        return rootView
    }

    private fun intView() {
        listItem = arguments?.getParcelable("OrderDetails")!!

        txtOrderId = rootView.findViewById(R.id.txtOrderId)!!
        txtOrderStatus = rootView.findViewById(R.id.txtOrderStatus)!!
        txtOrderDate = rootView.findViewById(R.id.txtOrderDate)!!
        txtOrderDetails = rootView.findViewById(R.id.txtOrderDetails)!!
        txtAddress = rootView.findViewById(R.id.txtAddress)!!
        txtEmail = rootView.findViewById(R.id.txtEmail)!!
        txtMobileNo = rootView.findViewById(R.id.txtMobileNo)!!
        txtAmount = rootView.findViewById(R.id.txtAmount)!!
        txtDelivery = rootView.findViewById(R.id.txtDelivery)!!
        txtTotal = rootView.findViewById(R.id.txtTotal)!!
        txtAdminEmail = rootView.findViewById(R.id.txtAdminEmail)!!
        txtAdminMobileNo = rootView.findViewById(R.id.txtAdminMobileNo)!!

        var details = listItem.orderDetails.toString().replace(",","\n")


        txtOrderId.text = listItem.orderId
        txtOrderStatus.text = listItem.status
        txtOrderDate.text = listItem.createdDate
        txtOrderDetails.text = details
        txtAddress.text = listItem.address+" "+listItem.area+" "+listItem.city+" "+listItem.pincode
        txtEmail.text = listItem.emailId
        txtMobileNo.text = listItem.mobileNo
        txtAmount.text = " Rs. "+listItem.discountedAmount
        txtDelivery.text = " Rs. "+listItem.deliveryCharges
        txtTotal.text = " Rs. "+listItem.Amount

        txtAdminEmail.text = Constants.adminEmail
        txtAdminMobileNo.text = Constants.adminMobile

    }
}
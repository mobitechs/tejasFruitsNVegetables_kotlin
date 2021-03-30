package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiPostCall
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import org.json.JSONException
import org.json.JSONObject


class MyOrderDetailsFragment : Fragment(), ApiResponse {

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
    lateinit var spinner : AppCompatSpinner
    var userType=""

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

        userType = SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)
            ?.get(0)?.userType.toString()

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
        spinner = rootView.findViewById(R.id.spinner)!!

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


        setupOrderStatusSpinner()
    }

    private fun setupOrderStatusSpinner() {
        if(userType.equals(Constants.admin)){
            txtOrderStatus.visibility = View.GONE
            spinner.visibility = View.VISIBLE

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_layout,
                Constants.orderStatusArray
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.setAdapter(adapter)

            spinner.setSelection(Constants.orderStatusArray.indexOf(listItem.status))
            spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var status = Constants.orderStatusArray[p2]
                    if(!listItem.status.equals(status)){
                        callAPIToChangeOrderStatus(listItem.orderId,status)
                    }

                }
            })
        }
        else{
            txtOrderStatus.visibility = View.VISIBLE
            spinner.visibility = View.GONE
        }
    }

    private fun callAPIToChangeOrderStatus(orderId: String, status: String) {
        val method = "ChangeOrderStatus"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("method", method)
            jsonObject.put("orderId", orderId)
            jsonObject.put("orderStatus", status)
            jsonObject.put("clientBusinessId", Constants.clientBusinessId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        apiPostCall(Constants.BASE_URL, jsonObject, this, method)
    }

    override fun onSuccess(data: Any, tag: String) {
        if (data.equals("SUCCESS")) {
            requireContext().showToastMsg("Order status change successfully.")
        }
        else{
            requireContext().showToastMsg("Failed to change order status.")
        }
    }

    override fun onFailure(message: String) {
        requireContext().showToastMsg(message)
    }
}
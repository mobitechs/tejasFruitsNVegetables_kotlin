package com.mobitechs.tejasfruitsnvegetables.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiPostCall
import com.mobitechs.tejasfruitsnvegetables.utils.parseDateToddMMyyyy
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity
import org.json.JSONException
import org.json.JSONObject

class MyOrderListAdapter(
    activityContext: Context,
   val userType:String
) :
    RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>(), ApiResponse {

    private val listItems = ArrayList<MyOrderListItems>()
    var context: Context = activityContext

    fun updateListItems(categoryModel: ArrayList<MyOrderListItems>) {
        listItems.clear()
        listItems.addAll(categoryModel)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_list_items_my_order, parent, false)

        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item: MyOrderListItems = listItems.get(position)
        holder.txtOrderId.text = item.orderId
        holder.txtOrderAmount.text = "Rs."+item.Amount
        holder.txtOrderStatus.text = item.status

        var orderDate = parseDateToddMMyyyy(item.createdDate)
        holder.txtOrderDate.text = orderDate
//        holder.txtOrderDetails.text = item.orderDetails




        holder.itemView.setOnClickListener {
//            context.openActivity(ProductDetailActivity::class.java)
//            {
//                putParcelable("OrderDetails", item)
//            }

            var bundle = Bundle()
            bundle.putParcelable("OrderDetails", item)
            (context as HomeActivity?)!!.OpenOrderDetails(bundle)
        }


        if(userType.equals(Constants.admin)){
            holder.txtOrderStatus.visibility = View.GONE
            holder.spinner.visibility = View.VISIBLE
            val adapter = ArrayAdapter(
                context,
                R.layout.spinner_layout,
                Constants.orderStatusArray
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinner.setAdapter(adapter)

            holder.spinner.setSelection(Constants.qtyArray.indexOf(item.status))
            holder.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    var status = Constants.orderStatusArray[p2]
                    if(!item.status.equals(status)){
                        callAPIToChangeOrderStatus(item.orderId,status)
                    }

                }
            })
        }
        else{
            holder.txtOrderStatus.visibility = View.VISIBLE
            holder.spinner.visibility = View.GONE
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


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtOrderId: TextView = view.findViewById(R.id.txtOrderId)
        var txtOrderAmount: TextView = view.findViewById(R.id.txtOrderAmount)
        var txtOrderStatus: TextView = view.findViewById(R.id.txtOrderStatus)
        var txtOrderDate: TextView = view.findViewById(R.id.txtOrderDate)
        var spinner: AppCompatSpinner = view.findViewById(R.id.spinner)
//        var txtOrderDetails: TextView = view.findViewById(R.id.txtOrderDetails)

        val cardView: View = itemView

    }

    override fun onSuccess(data: Any, tag: String) {
        if (data.equals("SUCCESS")) {
            context.showToastMsg("Order status change successfully.")
        }
        else{
            context.showToastMsg("Failed to change order status.")
        }
    }

    override fun onFailure(message: String) {
        context.showToastMsg(message)
    }
}
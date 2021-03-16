package com.mobitechs.tejasfruitsnvegetables.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity

class MyOrderListAdapter(
    activityContext: Context
) :
    RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>() {

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
        holder.txtOrderDate.text = item.createdDate
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

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtOrderId: TextView = view.findViewById(R.id.txtOrderId)
        var txtOrderAmount: TextView = view.findViewById(R.id.txtOrderAmount)
        var txtOrderStatus: TextView = view.findViewById(R.id.txtOrderStatus)
        var txtOrderDate: TextView = view.findViewById(R.id.txtOrderDate)
//        var txtOrderDetails: TextView = view.findViewById(R.id.txtOrderDetails)

        val cardView: View = itemView

    }
}
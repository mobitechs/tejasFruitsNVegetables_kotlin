package com.mobitechs.tejasfruitsnvegetables.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.AddOrRemoveListener
import com.mobitechs.tejasfruitsnvegetables.callbacks.CartListUpdated
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.ThreeTwoImageView
import com.mobitechs.tejasfruitsnvegetables.utils.setImage
import kotlinx.android.synthetic.main.adapter_list_items_cart.view.*
import java.util.*
import kotlin.collections.ArrayList

class CartListAdapter(
    activityContext: Context,
    private val addOrRemoveListener: AddOrRemoveListener,
    private val cartUpdateListener: CartListUpdated

) :
    RecyclerView.Adapter<CartListAdapter.MyViewHolder>(){

    private val listItems = ArrayList<ProductListItems>()
    var check = false
    var context: Context = activityContext


    lateinit var adapter: ArrayAdapter<String>

    lateinit var holderItem: MyViewHolder

    fun updateListItems(categoryModel: ArrayList<ProductListItems>) {
        listItems.clear()
        listItems.addAll(categoryModel)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_list_items_cart, parent, false)

        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item: ProductListItems = listItems.get(position)

        val imagepath = item.img
        if (imagepath == null || imagepath == " ") {
            holder.productImage!!.background =
                context.resources.getDrawable(R.drawable.img_not_available)
            holder.imageLoader!!.visibility = View.GONE
        } else {
            holder.productImage!!.setImage(imagepath, R.drawable.img_not_available)
            holder.imageLoader!!.visibility = View.GONE
        }


        holder.txtProductName.text = item.productName
        holder.lblWeight.text = item.weight +" "+item.uniteType

//        holder.lblDiscountedPrice.text = "Offer Rs."+item.finalPrice
//        holder.lblPrice.text ="Rs."+ item.amount
//        holder.lblPrice.setPaintFlags(holder.lblPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        if(item.uniteType.equals("Grams")){
            holder.spinnerItemArray = Constants.weightArray
        }else{
            holder.spinnerItemArray = Constants.pieceArray
        }


        val adapter = ArrayAdapter(
            context,
            R.layout.spinner_layout,
            holder.spinnerItemArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinner.setAdapter(adapter)

        if(item.uniteType.equals("Grams")){
            holder.spinner.setSelection(Constants.qtyArray.indexOf(item._qty.toString()))
        }else{
            holder.spinner.setSelection(item._qty - 1)
        }

        holder.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
//            event.onNothingSelected()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (item.uniteType.equals("Grams")) {

                    var abc = holder.spinnerItemArray[p2].split(" ")
                    var pqr = abc[0].toDouble() // 250 500 750 ....

                    if (pqr > 100) {
                        holder.qty = (pqr / 250).toInt()
                    } else {
                        pqr = pqr * 1000
                        holder.qty = (pqr / 250).toInt()
                    }
                } else {
                    holder.qty = holder.spinnerItemArray[p2].toInt()
                }

                var totalPrice = holder.qty * (item.amount).toInt()
                var discountedPrice = holder.qty * (item.finalPrice).toInt()
                holder.lblDiscountedPrice.text = "Rs." + discountedPrice.toString()

                holder.lblPrice.text = "Rs." + totalPrice
                holder.lblPrice.setPaintFlags(holder.lblPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


                listItems[position]._qty = holder.qty
                listItems[position]._qtyWisePrice = discountedPrice

                cartUpdateListener.cartListUpdated(listItems)
                //save this list to sharedPref
                // need to assing call back to calculate final amount in cart fragment

            }
        })


        holder.itemView.btnWishList.setOnClickListener {


            addOrRemoveListener.removeFromCart(item, position)

        }


        holder.itemView.setOnClickListener {
//            context.openActivity(ProductDetailActivity::class.java)
//            {
//                putParcelable("detail", item)
//            }
        }

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageLoader: ProgressBar? = view.findViewById(R.id.progressBar)
        var productImage: ThreeTwoImageView? = view.findViewById(R.id.productImage)
        var txtProductName: TextView = view.findViewById(R.id.lblProductName)
        var lblWeight: TextView = view.findViewById(R.id.lblWeight)
        var lblPrice: TextView = view.findViewById(R.id.lblPrice)
        var lblDiscountedPrice: TextView = view.findViewById(R.id.lblDiscountedPrice)
        var spinner: AppCompatSpinner = view.findViewById(R.id.spinner)
        val cardView: View = itemView

        var spinnerItemArray = Constants.weightArray
        var qty = 1

    }

}
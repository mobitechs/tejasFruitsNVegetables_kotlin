package com.mobitechs.tejasfruitsnvegetables.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.AddOrRemoveListener
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.ThreeTwoImageView
import com.mobitechs.tejasfruitsnvegetables.utils.setImage
import kotlinx.android.synthetic.main.adapter_list_items_product.view.*


class TabVegetablesAdapter(
    activityContext: Context,
    private val addOrRemoveListener: AddOrRemoveListener,
    private var cartListItems: ArrayList<ProductListItems>,
    private var allProduct: ArrayList<ProductListItems>

) :
    RecyclerView.Adapter<TabVegetablesAdapter.MyViewHolder>() {

    private var listItems = ArrayList<ProductListItems>()
    var check = false
    var context: Context = activityContext

    lateinit var adapter: ArrayAdapter<String>

    lateinit var holderItem: MyViewHolder

    fun updateListItems(categoryModel: ArrayList<ProductListItems>) {
        if(SharePreferenceManager.getInstance(context).getCartListItems(Constants.CartList)  != null){
            cartListItems = SharePreferenceManager.getInstance(context).getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
        }
        listItems.clear()
        listItems.addAll(categoryModel)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_list_items_product, parent, false)

        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item: ProductListItems = listItems.get(position)

        holder.txtProductName.text = item.productName
        holder.lblWeight.text = item.weight +" "+item.uniteType

        holder.lblDiscountedPrice.text = "Offer Rs."+item.finalPrice
        holder.lblPrice.text ="Rs."+ item.amount
        holder.lblPrice.setPaintFlags(holder.lblPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        val imagepath = item.img
        if (imagepath == null || imagepath == " ") {
            holder.productImage!!.background =
                context.resources.getDrawable(R.drawable.img_not_available)
            holder.imageLoader!!.visibility = View.GONE
        } else {
            holder.productImage!!.setImage(imagepath, R.drawable.img_not_available)
            holder.imageLoader!!.visibility = View.GONE
        }

        if(cartListItems.contains(item)){
            holder.itemView.btnWishList.setImageResource(R.drawable.ic_baseline_remove_circle_24)
        } else{
            holder.itemView.btnWishList.setImageResource(R.drawable.ic_outline_add_circle_24)
        }

        holder.itemView.btnWishList.setOnClickListener {

           if(SharePreferenceManager.getInstance(context).getCartListItems(Constants.CartList)  == null){
//               cartListItems.add(item)
               holder.itemView.btnWishList.setImageResource(R.drawable.ic_baseline_remove_circle_24)
               addOrRemoveListener.addToCart(item, position)
           }
            else{
               cartListItems = SharePreferenceManager.getInstance(context).getCartListItems(
                   Constants.CartList
               ) as ArrayList<ProductListItems>
               if (cartListItems.contains(item)) {
                   holder.itemView.btnWishList.setImageResource(R.drawable.ic_outline_add_circle_24)
//                   cartListItems.remove(item)
                   addOrRemoveListener.removeFromCart(item, position)
               } else {
                   holder.itemView.btnWishList.setImageResource(R.drawable.ic_baseline_remove_circle_24)
//                   cartListItems.add(item)
                   addOrRemoveListener.addToCart(item, position)
               }
            }

//            SharePreferenceManager.getInstance(context).saveCartListItems(Constants.CartList, cartListItems)
//            (context as HomeActivity)!!.updateCartCount(cartListItems.size)
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
        val cardView: View = itemView

    }

    fun getFilter(): Filter? {
        return object : Filter() {
             override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listItems = allProduct
                } else {
                    val filteredList: MutableList<ProductListItems> = ArrayList()
                    for (row in allProduct) {
                        if (row.productName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    listItems = filteredList as ArrayList<ProductListItems>
                }
                val filterResults = FilterResults()
                filterResults.values = listItems
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                listItems = filterResults.values as ArrayList<ProductListItems>
                notifyDataSetChanged()
            }
        }
    }
}
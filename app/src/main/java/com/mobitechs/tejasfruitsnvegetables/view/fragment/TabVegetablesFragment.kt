package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.adapter.TabVegetablesAdapter
import com.mobitechs.tejasfruitsnvegetables.callbacks.AddOrRemoveListener
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.*
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity
import com.mobitechs.tejasfruitsnvegetables.viewModel.VendorListActivityViewModel
import kotlinx.android.synthetic.main.contenair.*
import org.json.JSONException
import org.json.JSONObject

class TabVegetablesFragment : Fragment(), AddOrRemoveListener, ApiResponse {

    lateinit var rootView: View
    lateinit var viewModel: VendorListActivityViewModel
    lateinit var listAdapter: TabVegetablesAdapter
    lateinit var mLayoutManager: GridLayoutManager
    var listItems = ArrayList<ProductListItems>()
    var cartListItems = ArrayList<ProductListItems>()

    var allProductListItems = ArrayList<ProductListItems>()

    var categoryId = "1"
    var searchText = ""
    var userId = ""
    var userType = ""
    var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_tab_vegetables, container, false)
        intView()
        return rootView
    }

    private fun intView() {
        userId = SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)
            ?.get(0)?.userId.toString()

        userType = SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)
            ?.get(0)?.userType.toString()

        if (SharePreferenceManager.getInstance(requireContext())
                .getCartListItems(Constants.CartList) != null
        ) {
            cartListItems = SharePreferenceManager.getInstance(requireContext())
                .getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
        }
        allProductListItems = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.AllProductList) as ArrayList<ProductListItems>
        listItems = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.tab1List) as ArrayList<ProductListItems>
        setupRecyclerView()

        val edSearch: TextInputEditText = rootView.findViewById(R.id.edSearch)!!
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchText = s.toString()
                listAdapter.getFilter()!!.filter(searchText)
            }
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
        })
    }

    private fun setupRecyclerView() {
        viewModel = ViewModelProvider(this).get(VendorListActivityViewModel::class.java)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)!!
        val progressBar: ProgressBar = rootView.findViewById(R.id.progressBar)!!

        mLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        listAdapter = TabVegetablesAdapter(requireActivity(), this, cartListItems,allProductListItems,userType)
        recyclerView.adapter = listAdapter

        listAdapter.updateListItems(listItems)

//        viewModel.showProgressBar.observe(requireActivity(), Observer {
//            if (it) {
//                progressBar.visibility = View.VISIBLE
//            } else {
//                progressBar.visibility = View.GONE
//            }
//        })
//
//        viewModel.getProductList(categoryId)
//
//        viewModel.listItems.observe(requireActivity(), Observer {
//            listAdapter.updateListItems(it)
//        })
    }


    override fun addToCart(item: ProductListItems, position: Int) {
        requireContext().addToCart(item)
    }

    override fun removeFromCart(item: ProductListItems, position: Int) {
        requireContext().removeToCart(item)
    }

    override fun editProduct(item: ProductListItems, position: Int) {
        var bundle = Bundle()
        bundle.putParcelable("item", item)
        (context as HomeActivity?)!!.OpenEditProductFragment(bundle)
    }

    override fun deleteProduct(item: ProductListItems, pos: Int) {
        position = pos
        //call an api to delete product
        val method = "DeleteProduct"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("method", method)
            jsonObject.put("productId", item.productId)
            jsonObject.put("userId", userId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        apiPostCall(Constants.BASE_URL, jsonObject, this, method)
    }

    override fun onResume() {
        super.onResume()
        intView()
    }


    override fun onSuccess(data: Any, tag: String) {
        if (data.equals("SUCCESS")) {
            requireContext().showToastMsg("Product deleted successfully.")

            listItems!!.removeAt(position)
            listAdapter.notifyItemRemoved(position)
            listAdapter.notifyItemRangeChanged(position, listItems!!.size)
            listAdapter.notifyDataSetChanged()

        } else {
            requireContext().showToastMsg("Failed to product delete.")
        }
    }

    override fun onFailure(message: String) {
        requireContext().showToastMsg(message)
    }
}
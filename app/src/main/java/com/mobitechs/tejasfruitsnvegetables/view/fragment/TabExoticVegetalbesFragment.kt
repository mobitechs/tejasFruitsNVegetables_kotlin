package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.adapter.TabVegetablesAdapter
import com.mobitechs.tejasfruitsnvegetables.callbacks.AddOrRemoveListener
import com.mobitechs.tejasfruitsnvegetables.model.ProductListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.addToCart
import com.mobitechs.tejasfruitsnvegetables.utils.removeToCart
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import com.mobitechs.tejasfruitsnvegetables.viewModel.VendorListActivityViewModel

class TabExoticVegetalbesFragment : Fragment(), AddOrRemoveListener {

    lateinit var rootView: View
    lateinit var viewModel: VendorListActivityViewModel
    lateinit var listAdapter: TabVegetablesAdapter
    var listItems = ArrayList<ProductListItems>()
    var cartListItems = ArrayList<ProductListItems>()
    var allProductListItems = ArrayList<ProductListItems>()
    lateinit var mLayoutManager: GridLayoutManager

    var categoryId = "2"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_tab_vegetables, container, false)
        intView()
        return rootView
    }


    private fun intView() {

        if (SharePreferenceManager.getInstance(requireContext())
                .getCartListItems(Constants.CartList) != null
        ) {
            cartListItems = SharePreferenceManager.getInstance(requireContext())
                .getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
        }
        allProductListItems = SharePreferenceManager.getInstance(requireContext())
            .getCartListItems(Constants.AllProductList) as ArrayList<ProductListItems>

        listItems = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.tab2List) as ArrayList<ProductListItems>
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        viewModel = ViewModelProvider(this).get(VendorListActivityViewModel::class.java)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)!!
        val progressBar: ProgressBar = rootView.findViewById(R.id.progressBar)!!

        mLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        listAdapter = TabVegetablesAdapter(requireActivity(), this, cartListItems,allProductListItems)
        recyclerView.adapter = listAdapter

//        for (i in 0..allProductListItems.size - 1) {
//            var catId = allProductListItems.get(i).categoryId
//            if (catId == categoryId) {
//                listItems.add(allProductListItems.get(i))
//            }
//        }
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
//        cartListItems = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
//        cartListItems2 = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.CartList2) as ArrayList<ProductListItems>
//        if(!cartListItems.contains(item)){
//            cartListItems.add(item)
//            cartListItems2.add(item)
//        }
//        (activity as HomeActivity)!!.updateCartCount(cartListItems.size)
//        SharePreferenceManager.getInstance(requireContext()).saveCartListItems(Constants.CartList, cartListItems)
//        SharePreferenceManager.getInstance(requireContext()).saveCartListItems(Constants.CartList2, cartListItems2)
        requireContext().addToCart(item)
    }

    override fun removeFromCart(item: ProductListItems, position: Int) {
//        cartListItems = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.CartList) as ArrayList<ProductListItems>
//        cartListItems2 = SharePreferenceManager.getInstance(requireContext()).getCartListItems(Constants.CartList2) as ArrayList<ProductListItems>
//        if(cartListItems.contains(item)){
//            cartListItems.remove(item)
//            cartListItems2.remove(item)
//        }
//        (activity as HomeActivity)!!.updateCartCount(cartListItems.size)
//        SharePreferenceManager.getInstance(requireContext()).saveCartListItems(Constants.CartList, cartListItems)
//        SharePreferenceManager.getInstance(requireContext()).saveCartListItems(Constants.CartList2, cartListItems2)
        requireContext().removeToCart(item)
    }

    fun cartUpdated(cartListItems: ArrayList<ProductListItems>) {
        listAdapter.updateListItems(cartListItems)
    }


    override fun onPause() {
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
//        requireActivity().showToastMsg("exo vegi resumed called")
        intView()
    }

}
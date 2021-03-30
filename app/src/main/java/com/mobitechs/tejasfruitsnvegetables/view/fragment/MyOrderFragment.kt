package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.adapter.MyOrderListAdapter
import com.mobitechs.tejasfruitsnvegetables.model.MyOrderListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.viewModel.VendorListActivityViewModel


class MyOrderFragment : Fragment() {

    lateinit var rootView: View
    lateinit var viewModel: VendorListActivityViewModel
    lateinit var listAdapter: MyOrderListAdapter
    var listItems = ArrayList<MyOrderListItems>()
    lateinit var mLayoutManager: LinearLayoutManager
    var userId =""
    var userType =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_order, container, false)
        intView()
        return rootView

    }
    private fun intView() {
        userType = SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)
            ?.get(0)?.userType.toString()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        viewModel = ViewModelProvider(this).get(VendorListActivityViewModel::class.java)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)!!
        val progressBar: ProgressBar = rootView.findViewById(R.id.progressBar)!!

        mLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()



        listAdapter = MyOrderListAdapter(requireActivity(),userType)
        recyclerView.adapter = listAdapter
        listAdapter.updateListItems(listItems)

        viewModel.showProgressBar.observe(requireActivity(), Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        viewModel.getMyOrderList()

        viewModel.myOrderListItems.observe(requireActivity(), Observer {
            listAdapter.updateListItems(it)
        })

    }


}
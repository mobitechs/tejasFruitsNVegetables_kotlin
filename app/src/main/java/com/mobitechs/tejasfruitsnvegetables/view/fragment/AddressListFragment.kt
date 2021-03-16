package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.adapter.AddressListAdapter
import com.mobitechs.tejasfruitsnvegetables.callbacks.AddressSelectCallback
import com.mobitechs.tejasfruitsnvegetables.callbacks.AlertDialogBtnClickedCallBack
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.AddressListItems
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiPostCall
import com.mobitechs.tejasfruitsnvegetables.utils.showAlertDialog
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity
import com.mobitechs.tejasfruitsnvegetables.viewModel.VendorListActivityViewModel
import org.json.JSONException
import org.json.JSONObject


class AddressListFragment : Fragment(), AddressSelectCallback, AlertDialogBtnClickedCallBack,
    ApiResponse {

    lateinit var rootView: View
    lateinit var listAdapter: AddressListAdapter
    var listItems = ArrayList<AddressListItems>()
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var viewModel: VendorListActivityViewModel
    lateinit var fab: FloatingActionButton
    var position=0

    var addressId = ""
    var orderDetails =""
    var totalDiscountedAmount =""
    var delCharges =""
    var totalAmount =""
    var userId =""
    var method=""

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_address_list, container, false)
        intView()
        return rootView
    }

    private fun intView() {
        setupRecyclerView()
        val btnPlaceOrder: RelativeLayout = rootView.findViewById(R.id.btnPlaceOrder)
        val fab: FloatingActionButton = rootView.findViewById(R.id.fab)

        btnPlaceOrder.setOnClickListener {
            if (addressId.equals("")) {
                requireContext().showToastMsg("Please delivery address")
            } else {
                placeOrder()
            }
        }

        fab.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("IamFor", "Add")
            (activity as HomeActivity)!!.OpenAddAddress(bundle)
        }
    }


    private fun setupRecyclerView() {
        viewModel = ViewModelProvider(this).get(VendorListActivityViewModel::class.java)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)!!
        val progressBar: ProgressBar = rootView.findViewById(R.id.progressBar)!!

        mLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        listAdapter = AddressListAdapter(requireActivity(), this)
        recyclerView.adapter = listAdapter
        listAdapter.updateListItems(listItems)

        viewModel.showProgressBar.observe(requireActivity(), Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        viewModel.getAddressList()

        viewModel.addressListItems.observe(requireActivity(), Observer {
            listItems=it
            listAdapter.updateListItems(listItems)
        })
    }

    private fun placeOrder() {
        userId = SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)!![0].userId
        orderDetails = SharePreferenceManager.getInstance(requireContext()).getValueString(Constants.finalOrderMsg).toString()
        totalDiscountedAmount = SharePreferenceManager.getInstance(requireContext()).getValueString(Constants.totalDiscountedAmount).toString()
        delCharges = SharePreferenceManager.getInstance(requireContext()).getValueString(Constants.delCharges).toString()
        totalAmount = SharePreferenceManager.getInstance(requireContext()).getValueString(Constants.totalAmount).toString()

        var msgToDisplay = orderDetails+",Total Amount = " + totalDiscountedAmount+" Rs."+",Delivery Charges = " + delCharges+" Rs."+",Total Payable = " + totalAmount+" Rs."
        msgToDisplay= msgToDisplay.replace(",","\n")
        requireContext().showAlertDialog("Confirmation",msgToDisplay.toString(),"Confirm","NO",this)
    }

    override fun selectedAddress(item: AddressListItems) {
        addressId = item.addressId
    }

    override fun selectedAddressToEdit(item: AddressListItems) {
        var bundle = Bundle()
        bundle.putString("IamFor", "Edit")
        bundle.putParcelable("addressDetailsItem", item)
        (activity as HomeActivity)!!.OpenAddAddress(bundle)
    }

    override fun selectedAddressToDelete(item: AddressListItems, pos: Int) {
        position = pos

        method = "DeleteAddress"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("method", method)
            jsonObject.put("addressId", item.addressId)
            jsonObject.put("userId", SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)!![0].userId)
            jsonObject.put("clientBusinessId", Constants.clientBusinessId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        apiPostCall(Constants.BASE_URL, jsonObject, this, method)
    }

    override fun positiveBtnClicked() {
        method = "AddOrder"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("method", method)
            jsonObject.put("addressId", addressId)
            jsonObject.put("orderDetails", orderDetails)
            jsonObject.put("discountedAmount", totalDiscountedAmount)
            jsonObject.put("deliveryCharges", delCharges)
            jsonObject.put("Amount", totalAmount)
            jsonObject.put("userId", userId)
            jsonObject.put("clientBusinessId", Constants.clientBusinessId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        apiPostCall(Constants.BASE_URL, jsonObject, this, method)
    }

    override fun negativeBtnClicked() {

    }

    override fun onSuccess(data: Any, tag: String) {
        if(method.equals("DeleteAddress")){
            if (data.equals("Address successfully deleted")) {
                requireActivity().showToastMsg("Address successfully deleted")
//                listItems!!.removeAt(position)
//                listAdapter.notifyItemRemoved(position)
//                listAdapter.notifyItemRangeChanged(position, listItems!!.size)
//                listAdapter.notifyDataSetChanged()
            }else{
                requireActivity().showToastMsg("Failed to delete address")
            }
        }
        else{
            if (data.equals("EMAIL_SUCCESSFULLY_SENT")) {
                requireActivity().showToastMsg("Order successfully placed.")
                SharePreferenceManager.getInstance(requireActivity()).clearCart()

                (activity as HomeActivity).updateCartCount(0)
                (activity as HomeActivity).displayView(1)
            } else {
                requireActivity().showToastMsg("Failed to place order")
            }
        }

    }

    override fun onFailure(message: String) {
        requireActivity().showToastMsg(message)
    }

}
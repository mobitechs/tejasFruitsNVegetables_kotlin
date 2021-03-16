package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.UserModel
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiPostCall
import com.mobitechs.tejasfruitsnvegetables.utils.openActivity
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : Fragment(), ApiResponse {

    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_login, container, false)

        initView()
        return rootView
    }

    private fun initView() {
        val navController = activity?.let { Navigation.findNavController(it, R.id.fragment) }
        val btnSignUp: TextView = rootView.findViewById(R.id.btnSignUp)!!
        val forgetPassword: TextView = rootView.findViewById(R.id.forgetPassword)!!
        val btnSignIn: Button = rootView.findViewById(R.id.btnSignIn)!!
        val etEmail: TextInputEditText = rootView.findViewById(R.id.etEmail)!!
        val etPassword: TextInputEditText = rootView.findViewById(R.id.etPassword)!!

        btnSignUp.setOnClickListener {
            navController?.navigate(R.id.action_login_to_register)
        }

        forgetPassword.setOnClickListener {
            navController?.navigate(R.id.action_login_to_forgotPasswordFragment)
        }

        btnSignIn.setOnClickListener {
            if (etEmail.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Email Id/Mobile No")
            } else if (etPassword.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Password")
            } else {

                val method = "userLogin"
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("method", method)
                    jsonObject.put("userLoginId", etEmail.text.toString())
                    jsonObject.put("password", etPassword.text.toString())
                    jsonObject.put("clientBusinessId", Constants.clientBusinessId)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                apiPostCall(Constants.BASE_URL, jsonObject, this, method)
            }
        }

    }

    override fun onSuccess(data: Any, tag: String) {

        if (data.equals("FAILED_LOGIN")) {
            requireContext().showToastMsg("Login Failed")
        } else {
            requireContext().showToastMsg("Login Successful")
                val gson = Gson()
                val type = object : TypeToken<ArrayList<UserModel>>() {}.type
                var user: ArrayList<UserModel>? = gson.fromJson(data.toString(), type)

            Log.d("user", "" + user)
            SharePreferenceManager.getInstance(requireContext()).save(Constants.ISLOGIN, true)
            SharePreferenceManager.getInstance(requireContext()).saveUserLogin(Constants.USERDATA, user)

//
//            val userType =
//                SharePreferenceManager.getInstance(requireContext()).getUserLogin(Constants.USERDATA)
//                    ?.get(0)?.userType.toString()
//            if (userType.equals("Vendor")) {
//                requireContext().openActivity(VendorHomeActivity::class.java)
//            } else {
//                requireContext().openActivity(VendorListActivity::class.java)
//            }

            requireContext().openActivity(HomeActivity::class.java)
        }

    }

    override fun onFailure(message: String) {
        requireContext().showToastMsg(message)
    }

}
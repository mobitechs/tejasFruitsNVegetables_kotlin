package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.utils.Constants
import com.mobitechs.tejasfruitsnvegetables.utils.apiPostCall
import com.mobitechs.tejasfruitsnvegetables.utils.showToastMsg
import kotlinx.android.synthetic.main.fragment_set_password.*
import org.json.JSONException
import org.json.JSONObject

class SetPasswordFragment : Fragment(), ApiResponse {

    var email = ""
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_set_password, container, false)

        initView()
        return rootView
    }

    private fun initView() {
        val newPasswordSubmit: Button = rootView.findViewById(R.id.newPasswordSubmit)
        email = arguments?.getString("email").toString()
        newPasswordSubmit.setOnClickListener {
            if (etOtp.text.toString().equals("")) {
                requireContext().showToastMsg("Enter OTP")
            } else if (etPassword.text.toString().equals("")) {
                requireContext().showToastMsg("Enter Password")
            } else if (etConfirmPassword.text.toString().equals("")) {
                requireContext().showToastMsg("Enter Confirm Password ")
            } else {

                if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
                    requireContext().showToastMsg("Passwords are not matched")
                } else {
                    val method = "SetPassword"
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("method", method)
                        jsonObject.put("otp", etOtp.text.toString())
                        jsonObject.put("email", email)
                        jsonObject.put("password", etPassword.text.toString())
                        jsonObject.put("clientBusinessId", Constants.clientBusinessId)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    apiPostCall(Constants.BASE_URL, jsonObject, this, method)
                }

            }
        }

    }

    override fun onSuccess(data: Any, tag: String) {

        if (data.equals("NEW_PASSWORD_SUCCESSFULLY_SET")) {
            requireContext().showToastMsg("Password Set Successfully")
            val navController = activity?.let { Navigation.findNavController(it, R.id.fragment) }
            navController?.navigate(R.id.action_setPasswordFragment_to_login)
        } else {
            requireContext().showToastMsg(data.toString())
        }

    }

    override fun onFailure(message: String) {
        requireContext().showToastMsg(message)
    }

}
package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.callbacks.ApiResponse
import com.mobitechs.tejasfruitsnvegetables.model.UserModel
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.*
import com.mobitechs.tejasfruitsnvegetables.view.activity.HomeActivity
import org.json.JSONException
import org.json.JSONObject


class RegisterFragment : Fragment(), ApiResponse {

    lateinit var rootView: View
    lateinit var layoutLoader: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_register, container, false)

        initView()
        return rootView
    }

    private fun initView() {

        layoutLoader = rootView.findViewById(R.id.layoutLoader)
        val btnSignUp: Button = rootView.findViewById(R.id.btnSignUp)!!
        val etName: TextInputEditText = rootView.findViewById(R.id.etName)!!
        val etEmail: TextInputEditText = rootView.findViewById(R.id.etEmail)
        val etMobileNo: TextInputEditText = rootView.findViewById(R.id.etMobileNo)
        val etPassword: TextInputEditText = rootView.findViewById(R.id.etPassword)
        val etConfirmPassword: TextInputEditText = rootView.findViewById(R.id.etConfirmPassword)

        val navController = activity?.let { Navigation.findNavController(it, R.id.fragment) }

        btnSignUp.setOnClickListener {

            if (etName.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Name")
            } else if (etEmail.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Email Id")
            } else if (etMobileNo.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Mobile No")
            } else if (etPassword.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Password")
            } else if (etConfirmPassword.text.toString().equals("")) {
                requireActivity().showToastMsg("Enter Confirm Password ")
            } else {
                if (isEmailValid(etEmail.text.toString()) != true) {
                    requireActivity().showToastMsg("Email is not valid")
                } else if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
                    requireActivity().showToastMsg("Passwords are not matched")
                } else {
                    layoutLoader.visibility = View.VISIBLE
                    val method = "userRegister"
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("method", method)
                        jsonObject.put("userName", etName.text.toString())
                        jsonObject.put("mobileNo", etMobileNo.text.toString())
                        jsonObject.put("emailId", etEmail.text.toString())
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

        if (data.equals("USER_ALREADY_REGISTER")) {
            requireActivity().showToastMsg("User details already exist")
        }else if (data.equals("REGISTRATION_FAILED")) {
            requireActivity().showToastMsg("Registration failed")
        }
        else {
            requireActivity().showToastMsg("Registration successfully done")
            val gson = Gson()
            val type = object : TypeToken<ArrayList<UserModel>>() {}.type
            var user: ArrayList<UserModel>? = gson.fromJson(data.toString(), type)

            Log.d("user", "" + user)
            SharePreferenceManager.getInstance(requireActivity()).save(Constants.ISLOGIN, true)
            SharePreferenceManager.getInstance(requireActivity())
                .saveUserLogin(Constants.USERDATA, user)

            requireActivity().openActivity(HomeActivity::class.java)
        }
        layoutLoader.visibility = View.GONE

    }

    override fun onFailure(message: String) {
        requireActivity().showToastMsg(message)
        layoutLoader.visibility = View.GONE
    }
}
package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants


class ProfileFragment : Fragment() {

    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        initView()
        return rootView
    }

    private fun initView() {
        val userId =
            SharePreferenceManager.getInstance(requireActivity()).getUserLogin(Constants.USERDATA)
                ?.get(0)?.userId.toString()
        val userType =
            SharePreferenceManager.getInstance(requireActivity()).getUserLogin(Constants.USERDATA)
                ?.get(0)?.userType.toString()
        val name =
            SharePreferenceManager.getInstance(requireActivity()).getUserLogin(Constants.USERDATA)
                ?.get(0)?.name.toString()
        val email =
            SharePreferenceManager.getInstance(requireActivity()).getUserLogin(Constants.USERDATA)
                ?.get(0)?.emailId.toString()
        val mobile =
            SharePreferenceManager.getInstance(requireActivity()).getUserLogin(Constants.USERDATA)
                ?.get(0)?.mobileNo.toString()


        val tvName: TextView = rootView.findViewById(R.id.tvName)!!
        val tvEmail: TextView = rootView.findViewById(R.id.tvEmail)!!
        val tvMobile: TextView = rootView.findViewById(R.id.tvMobile)!!

        tvName.text = name
        tvEmail.text = email
        tvMobile.text = mobile

    }
}
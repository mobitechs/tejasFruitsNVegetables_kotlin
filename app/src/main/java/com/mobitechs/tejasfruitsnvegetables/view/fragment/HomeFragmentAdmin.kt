package com.mobitechs.tejasfruitsnvegetables.view.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.session.SharePreferenceManager
import com.mobitechs.tejasfruitsnvegetables.utils.Constants

class HomeFragmentAdmin : Fragment() {
    lateinit var rootView: View
    lateinit var txtCollection: TextView
    lateinit var txtOrderCount: TextView

    var todaysOrderCount=""
    var todaysCollection=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_home_admin, container, false)
        initView()
        return rootView
    }

    private fun initView() {
        txtCollection = rootView.findViewById(R.id.txtCollection)!!
        txtOrderCount = rootView.findViewById(R.id.txtOrderCount)!!
        updateUI()

    }

    private fun updateUI() {
        txtOrderCount.text = todaysOrderCount

        if(todaysCollection.equals("") || todaysCollection.equals("0")){
            txtCollection.visibility = View.GONE
        }
        else{
            txtCollection.visibility = View.VISIBLE
            txtCollection.text = todaysCollection
        }


    }
}
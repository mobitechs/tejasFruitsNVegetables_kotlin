package com.mobitechs.tejasfruitsnvegetables.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mobitechs.tejasfruitsnvegetables.R
import com.mobitechs.tejasfruitsnvegetables.adapter.TabsPagerAdapter

class TabListFragment : Fragment() {

    lateinit var rootView: View
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab_list, container, false)
        intView()
        return rootView
    }

    private fun intView() {
        tabLayout = rootView.findViewById(R.id.tabLayout)!!
        viewPager = rootView.findViewById(R.id.viewPager)!!
        configureTabLayout()

    }

    private fun configureTabLayout() {

        tabLayout.addTab(tabLayout.newTab().setText("Vegetables"))
        tabLayout.addTab(tabLayout.newTab().setText("Exotic Vegetables"))
        tabLayout.addTab(tabLayout.newTab().setText("Fruits"))

        val adapter = TabsPagerAdapter(childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        )
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }
}
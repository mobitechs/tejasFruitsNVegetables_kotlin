package com.mobitechs.tejasfruitsnvegetables.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobitechs.tejasfruitsnvegetables.view.fragment.TabExoticVegetalbesFragment
import com.mobitechs.tejasfruitsnvegetables.view.fragment.TabFruitsFragment
import com.mobitechs.tejasfruitsnvegetables.view.fragment.TabVegetablesFragment


class TabsPagerAdapter(fm: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> return TabVegetablesFragment()
            1 -> return TabExoticVegetalbesFragment()
            2 -> return TabFruitsFragment()
            else -> return TabVegetablesFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}
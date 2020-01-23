package com.yazan98.culttrip.client.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yazan98.culttrip.client.fragment.main.MainFragment

class CulttripViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            else -> MainFragment()
        }
    }

    override fun getCount(): Int {
        return 1
    }
}
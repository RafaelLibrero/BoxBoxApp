package com.boxbox.app.ui.season.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DriversTabFragment()
            1 -> TeamsTabFragment()
            else -> throw IllegalStateException("Unknown position")
        }
    }
}
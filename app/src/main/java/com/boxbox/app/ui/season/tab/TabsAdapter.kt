package com.boxbox.app.ui.season.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.boxbox.app.ui.season.tab.drivers.DriversTabFragment
import com.boxbox.app.ui.season.tab.races.RacesTabFragment
import com.boxbox.app.ui.season.tab.teams.TeamsTabFragment

class TabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DriversTabFragment()
            1 -> TeamsTabFragment()
            2 -> RacesTabFragment()
            else -> throw IllegalStateException("Unknown position")
        }
    }
}
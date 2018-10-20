package id.anhs.footballapps.ui.activity.teamdetails

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.anhs.footballapps.ui.fragment.teamoverview.TeamOverviewFragment
import id.anhs.footballapps.ui.fragment.teamplayers.TeamPlayersFragment

class TeamDetailsViewPagerAdapter(fm: FragmentManager,
                                  private var tabTitles: Array<CharSequence>,
                                  private var totalTabs: Int,
                                  private var idTeam: String,
                                  private var teamOverview: String)
    : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamOverviewFragment.newInstance(teamOverview)
            1 -> TeamPlayersFragment.newInstance(idTeam)
            else -> TeamOverviewFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}
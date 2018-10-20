package id.anhs.footballapps.ui.fragment.matcheshome

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.anhs.footballapps.ui.fragment.matchesnext.MatchesNextFragment
import id.anhs.footballapps.ui.fragment.matchesprev.MatchesPrevFragment

class MathesHomeViewPagerAdapter(fm: FragmentManager,
                                 private var tabTitles: Array<CharSequence>,
                                 private var totalTabs: Int)
    : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchesNextFragment()
            1 -> MatchesPrevFragment()
            else -> MatchesNextFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}
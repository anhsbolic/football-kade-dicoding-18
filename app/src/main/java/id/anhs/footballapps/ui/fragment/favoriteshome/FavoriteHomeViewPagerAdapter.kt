package id.anhs.footballapps.ui.fragment.favoriteshome

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.anhs.footballapps.ui.fragment.favoritesmatches.FavoriteMatchesFragment
import id.anhs.footballapps.ui.fragment.favoritesteams.FavoriteTeamsFragment


class FavoriteHomeViewPagerAdapter(fm: FragmentManager,
                                   private var tabTitles: Array<CharSequence>,
                                   private var totalTabs: Int)
    : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMatchesFragment()
            1 -> FavoriteTeamsFragment()
            else -> FavoriteMatchesFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}
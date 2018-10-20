package id.anhs.footballapps.ui.fragment.favoriteshome

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.anhs.footballapps.R
import kotlinx.android.synthetic.main.fragment_favorites_home.*

class FavoritesHomeFragment : Fragment() {
    private val tabTitles = arrayOf<CharSequence>("Matches", "Teams")
    private val totalTabs = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteHomeViewPagerAdapter(fragmentManager!!, tabTitles, totalTabs)
        favorite_home_viewpager.adapter = adapter
        favorite_home_tab_layout.setupWithViewPager(favorite_home_viewpager)
    }

}

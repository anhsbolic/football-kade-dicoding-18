package id.anhs.footballapps.ui.activity.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import id.anhs.footballapps.R
import id.anhs.footballapps.ui.fragment.favoriteshome.FavoritesHomeFragment
import id.anhs.footballapps.ui.fragment.matcheshome.MatchesHomeFragment
import id.anhs.footballapps.ui.fragment.teamshome.TeamsHomeFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment
    private lateinit var lastFragment: Fragment
    private var firstFragmentOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Setup View
        setTitle(R.string.dashboard_page_title)
        setSupportActionBar(dashboard_toolbar)

        // bottom navigation listener
        dashboard_bot_nav.setOnNavigationItemSelectedListener {
            displaySelectedPage(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        // open prev match page
        displaySelectedPage(R.id.dashboard_bot_nav_matches)
    }

    private fun displaySelectedPage(itemId: Int) {
        when (itemId) {
            R.id.dashboard_bot_nav_matches -> {
                currentFragment = MatchesHomeFragment()
                openFragment()
            }
            R.id.dashboard_bot_nav_teams -> {
                currentFragment = TeamsHomeFragment()
                openFragment()
            }
            R.id.dashboard_bot_nav_favorites -> {
                currentFragment = FavoritesHomeFragment()
                openFragment()
            }
            else -> {
                currentFragment = MatchesHomeFragment()
                openFragment()
            }
        }
    }

    private fun openFragment() {
        if (firstFragmentOpened) {
            supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.dashboard_frame_layout, currentFragment, currentFragment::class.java.simpleName)
                    .commit()
            firstFragmentOpened = false
            lastFragment = currentFragment
        } else {
            if (!Objects.equals(currentFragment::class.java.simpleName, lastFragment::class.java.simpleName)) {
                supportFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.dashboard_frame_layout, currentFragment, currentFragment::class.java.simpleName)
                        .addToBackStack(lastFragment::class.java.simpleName)
                        .commit()
                lastFragment = currentFragment
            }
        }
    }
}

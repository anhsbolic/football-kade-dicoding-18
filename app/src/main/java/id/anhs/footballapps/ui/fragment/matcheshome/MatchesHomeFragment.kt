package id.anhs.footballapps.ui.fragment.matcheshome

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import id.anhs.footballapps.R
import id.anhs.footballapps.ui.activity.searchmatch.SearchMatchActivity
import kotlinx.android.synthetic.main.fragment_matches_home.*
import org.jetbrains.anko.support.v4.startActivity

class MatchesHomeFragment : Fragment() {
    private val tabTitles = arrayOf<CharSequence>("Next", "Last")
    private val totalTabs = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MathesHomeViewPagerAdapter(fragmentManager!!, tabTitles, totalTabs)
        matches_home_viewpager.adapter = adapter
        matches_home_tab_layout.setupWithViewPager(matches_home_viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.matches_home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.matches_home_menu_search -> {
                startActivity<SearchMatchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

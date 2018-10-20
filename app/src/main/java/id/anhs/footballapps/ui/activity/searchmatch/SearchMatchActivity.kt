package id.anhs.footballapps.ui.activity.searchmatch

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import id.anhs.footballapps.ui.adapter.MatchEventAdapter
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity


class SearchMatchActivity : AppCompatActivity(), SearchMatchContract.View {

    private lateinit var presenter: SearchMatchPresenter
    private var loading = false
    private lateinit var adapterRvMatchEvents: RecyclerView.Adapter<*>
    private var matchEvents: MutableList<MatchEvent> = mutableListOf()
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null

        // init presenter
        presenter = SearchMatchPresenter(this@SearchMatchActivity, ApiServices.getTheSportDBApiServices())

        // init views
        initRvMatchEvents()

        // swipe refresh handler
        search_match_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                if (query.isNotEmpty()) {
                    presenter.searchMatches(query)
                } else {
                    search_match_swipe_refresh.isRefreshing = false
                }
            } else {
                search_match_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvMatchEvents() {
        adapterRvMatchEvents = MatchEventAdapter(matchEvents, false,
                // on item click
                {idMatch ->
                    startActivity<MatchDetailsActivity>(MatchDetailsActivity.INTENT_ID_MATCH to idMatch)
                },
                // on alarm click
                {/*disabled*/}
        )
        search_match_rv_matches.layoutManager = LinearLayoutManager(ctx)
        search_match_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        search_match_rv_matches.adapter = adapterRvMatchEvents
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuSearch = menu.findItem(R.id.search_menu_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menuSearch.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.queryHint = "search match"
        searchView.isIconified
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (!loading) {
                    if (!p0.isNullOrEmpty()) {
                        query = p0 ?: ""
                        presenter.searchMatches(query)
                        hideKeyboard()
                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
        menuSearch.expandActionView()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun loading(loading: Boolean) {
        this.loading = loading
        search_match_swipe_refresh.isRefreshing = loading
    }

    override fun setMatchesSearchResult(matchEvents: List<MatchEvent>) {
        search_match_img_no_result.gone()
        search_match_rv_matches.visible()
        this.matchEvents.clear()
        this.matchEvents.addAll(matchEvents)
        adapterRvMatchEvents.notifyDataSetChanged()
    }

    override fun setNoResult() {
        search_match_rv_matches.gone()
        search_match_img_no_result.visible()
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focus = currentFocus
        if (focus != null) {
            imm.hideSoftInputFromWindow(focus.windowToken, 0)
        }
    }

}

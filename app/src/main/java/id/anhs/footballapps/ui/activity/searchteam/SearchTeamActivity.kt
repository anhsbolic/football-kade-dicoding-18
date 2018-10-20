package id.anhs.footballapps.ui.activity.searchteam

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
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.ui.activity.teamdetails.TeamDetailsActivity
import id.anhs.footballapps.ui.adapter.TeamAdapter
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.activity_search_team.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

class SearchTeamActivity : AppCompatActivity(), SearchTeamContract.View {

    private lateinit var presenter: SearchTeamPresenter
    private var loading = false
    private lateinit var adapterRvTeams: RecyclerView.Adapter<*>
    private var teams: MutableList<Team> = mutableListOf()
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null

        // init presenter
        presenter = SearchTeamPresenter(this@SearchTeamActivity, ApiServices.getTheSportDBApiServices())

        // init views
        initRvTeams()

        // swipe refresh handler
        search_team_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                if (query.isNotEmpty()) {
                    presenter.searchTeam(query)
                } else {
                    search_team_swipe_refresh.isRefreshing = false
                }
            } else {
                search_team_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvTeams() {
        adapterRvTeams = TeamAdapter(teams) { team ->
            startActivity<TeamDetailsActivity>(TeamDetailsActivity.INTENT_TEAM to team)
        }
        search_team_rv_matches.layoutManager = LinearLayoutManager(ctx)
        search_team_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        search_team_rv_matches.adapter = adapterRvTeams
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuSearch = menu.findItem(R.id.search_menu_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menuSearch.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.queryHint = "search team"
        searchView.isIconified
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (!loading) {
                    if (!p0.isNullOrEmpty()) {
                        query = p0 ?: ""
                        presenter.searchTeam(query)
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
        search_team_swipe_refresh.isRefreshing = loading
    }

    override fun setTeamsSearchResult(teams: List<Team>) {
        search_team_img_no_result.gone()
        search_team_rv_matches.visible()
        this.teams.clear()
        this.teams.addAll(teams)
        adapterRvTeams.notifyDataSetChanged()
    }

    override fun setNoResult() {
        search_team_rv_matches.gone()
        search_team_img_no_result.visible()
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

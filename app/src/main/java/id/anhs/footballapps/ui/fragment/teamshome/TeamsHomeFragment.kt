package id.anhs.footballapps.ui.fragment.teamshome

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.ui.activity.searchteam.SearchTeamActivity
import id.anhs.footballapps.ui.activity.teamdetails.TeamDetailsActivity
import id.anhs.footballapps.ui.adapter.TeamAdapter
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.fragment_teams_home.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class TeamsHomeFragment : Fragment(), TeamsHomeContract.View {

    private lateinit var presenter: TeamsHomePresenter
    private var loading = false
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var adapterRvTeams: RecyclerView.Adapter<*>
    private var teams: MutableList<Team> = mutableListOf()
    private var idLeague = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Views
        initRvTeams()

        // init presenter
        presenter = TeamsHomePresenter(this@TeamsHomeFragment, ApiServices.getTheSportDBApiServices())

        // load spinner data
        presenter.loadSpinnerData()

        // swipe refresh handler
        teams_home_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                if (idLeague.isNotEmpty()) {
                    presenter.getTeamsInLeague(idLeague)
                } else {
                    teams_home_swipe_refresh.isRefreshing = false
                }
            } else {
                teams_home_swipe_refresh.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.team_home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.team_home_menu_search -> {
                startActivity<SearchTeamActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRvTeams() {
        adapterRvTeams = TeamAdapter(teams) { team ->
            startActivity<TeamDetailsActivity>(TeamDetailsActivity.INTENT_TEAM to team)
        }
        teams_home_rv_matches.layoutManager = LinearLayoutManager(ctx)
        teams_home_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        teams_home_rv_matches.adapter = adapterRvTeams
    }

    override fun loadingSpinnerData(loading: Boolean) {
        this.loading = loading

        if (loading) {
            teams_home_progress_bar?.visible()
        } else {
            teams_home_progress_bar?.gone()
        }
    }

    override fun setSpinnerData(leagues: List<League>) {
        this.leagues.clear()
        this.leagues.addAll(leagues)

        val spinnerItems = arrayOfNulls<String>(leagues.size)
        for (i in leagues.indices) {
            spinnerItems[i] = leagues[i].name
        }

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        teams_home_spinner.adapter = spinnerAdapter
        teams_home_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagues[position].id ?: ""
                if (idLeague.isNotEmpty()) presenter.getTeamsInLeague(idLeague)
            }

        }
    }

    override fun loadingTeamsData(loading: Boolean) {
        this.loading = loading
        teams_home_swipe_refresh?.isRefreshing = loading
    }

    override fun setTeams(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)
        adapterRvTeams.notifyDataSetChanged()
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

}

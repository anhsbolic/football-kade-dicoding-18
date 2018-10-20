package id.anhs.footballapps.ui.fragment.favoritesteams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.anhs.footballapps.R
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.ui.activity.teamdetails.TeamDetailsActivity
import id.anhs.footballapps.ui.adapter.TeamAdapter
import kotlinx.android.synthetic.main.fragment_favorite_teams.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class FavoriteTeamsFragment : Fragment(), FavoriteTeamsContract.View {

    private lateinit var presenter: FavoriteTeamsPresenter
    private var loading = false
    private lateinit var adapterRvTeams: RecyclerView.Adapter<*>
    private var teams: MutableList<Team> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Views
        initRvTeams()

        // init presenter
        presenter = FavoriteTeamsPresenter(this@FavoriteTeamsFragment, ctx)

        // load data
        presenter.loadFavoriteTeams()

        // swipe refresh handler
        favorite_team_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                presenter.loadFavoriteTeams()
            } else {
                favorite_team_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvTeams() {
        adapterRvTeams = TeamAdapter(teams) { team ->
            startActivity<TeamDetailsActivity>(TeamDetailsActivity.INTENT_TEAM to team)
        }
        favorite_team_rv_teams.layoutManager = LinearLayoutManager(ctx)
        favorite_team_rv_teams.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        favorite_team_rv_teams.adapter = adapterRvTeams
    }

    override fun loading(loading: Boolean) {
        this.loading = loading
        favorite_team_swipe_refresh?.isRefreshing = loading
    }

    override fun setFavoriteTeams(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)
        adapterRvTeams.notifyDataSetChanged()
    }

}

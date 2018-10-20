package id.anhs.footballapps.ui.fragment.teamplayers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.Player
import id.anhs.footballapps.ui.activity.playerdetails.PlayerDetailsActivity
import id.anhs.footballapps.ui.adapter.PlayerAdapter
import kotlinx.android.synthetic.main.fragment_team_players.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class TeamPlayersFragment : Fragment(), TeamPlayersContract.View {

    private var idTeam: String? = null
    private lateinit var presenter: TeamPlayersPresenter
    private lateinit var adapterRvPlayers: RecyclerView.Adapter<*>
    private val players: MutableList<Player> = mutableListOf()
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTeam = it.getString(ARG_ID_TEAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init views
        initRvPlayers()

        // init presenter
        presenter = TeamPlayersPresenter(this@TeamPlayersFragment, ApiServices.getTheSportDBApiServices())

        // load data
        idTeam?.let {
            if (it.isNotEmpty()) presenter.loadPlayersData(it)
        }
    }

    private fun initRvPlayers() {
        adapterRvPlayers = PlayerAdapter(players) { player ->
            startActivity<PlayerDetailsActivity>(PlayerDetailsActivity.INTENT_PLAYER to player)
        }
        team_players_rv_matches.layoutManager = LinearLayoutManager(ctx)
        team_players_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        team_players_rv_matches.adapter = adapterRvPlayers
    }

    override fun loadingData(loading: Boolean) {
        this.loading = loading
        team_players_swipe_refresh.isRefreshing = loading
    }

    override fun setPlayers(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        adapterRvPlayers.notifyDataSetChanged()
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

    override fun onStop() {
        presenter.clearComposite()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.disposeComposite()
        super.onDestroy()
    }

    companion object {
        private const val ARG_ID_TEAM = "idTeam"

        @JvmStatic
        fun newInstance(idTeam: String) =
                TeamPlayersFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_ID_TEAM, idTeam)
                    }
                }
    }
}

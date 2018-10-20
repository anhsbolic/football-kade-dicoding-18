package id.anhs.footballapps.ui.fragment.favoritesmatches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.anhs.footballapps.R
import id.anhs.footballapps.ui.adapter.MatchEventAdapter
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import kotlinx.android.synthetic.main.fragment_favorite_matches.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchesFragment : Fragment(), FavoriteMatchesContract.View {

    private lateinit var presenter: FavoriteMatchesPresenter
    private var loading = false
    private lateinit var adapterRvMatches: RecyclerView.Adapter<*>
    private var matches: MutableList<MatchEvent> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Views
        initRvMatches()

        // init presenter
        presenter = FavoriteMatchesPresenter(this@FavoriteMatchesFragment, ctx)

        // load data
        presenter.loadFavoriteMatches()

        // swipe refresh handler
        favorite_matches_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                presenter.loadFavoriteMatches()
            } else {
                favorite_matches_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvMatches() {
        adapterRvMatches = MatchEventAdapter(matches, false,
                // on item click
                {idMatch ->
                    startActivity<MatchDetailsActivity>(MatchDetailsActivity.INTENT_ID_MATCH to idMatch)
                },
                // on alarm click
                {/*disabled*/}
        )
        favorite_matches_rv_matches.layoutManager = LinearLayoutManager(ctx)
        favorite_matches_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        favorite_matches_rv_matches.adapter = adapterRvMatches
    }

    override fun loading(loading: Boolean) {
        this.loading = loading
        favorite_matches_swipe_refresh?.isRefreshing = loading
    }

    override fun setFavoriteMatches(matches: List<MatchEvent>) {
        this.matches.clear()
        this.matches.addAll(matches)
        adapterRvMatches.notifyDataSetChanged()
    }

}

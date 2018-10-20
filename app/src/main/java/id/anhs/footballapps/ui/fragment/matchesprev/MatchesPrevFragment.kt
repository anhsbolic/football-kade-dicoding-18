package id.anhs.footballapps.ui.fragment.matchesprev

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import id.anhs.footballapps.ui.adapter.MatchEventAdapter
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.fragment_matches_prev.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class MatchesPrevFragment : Fragment(), MatchesPrevContract.View {

    private lateinit var presenter: MatchesPrevPresenter
    private var loading = false
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var adapterRvMatchEvents: RecyclerView.Adapter<*>
    private var matchEvents: MutableList<MatchEvent> = mutableListOf()
    private var idLeague = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches_prev, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Views
        initRvMatchEvents()

        // init presenter
        presenter = MatchesPrevPresenter(this@MatchesPrevFragment, ApiServices.getTheSportDBApiServices())

        // load spinner data
        presenter.loadSpinnerData()

        // swipe refresh handler
        prev_matches_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                if (idLeague.isNotEmpty()) {
                    presenter.loadPrevMatchEvents(idLeague)
                } else {
                    prev_matches_swipe_refresh.isRefreshing = false
                }
            } else {
                prev_matches_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvMatchEvents() {
        adapterRvMatchEvents = MatchEventAdapter(matchEvents, false,
                // on item click
                { idMatch ->
                    startActivity<MatchDetailsActivity>(MatchDetailsActivity.INTENT_ID_MATCH to idMatch)
                },
                // on alarm click
                {/*disabled*/}
        )
        prev_matches_rv_matches.layoutManager = LinearLayoutManager(ctx)
        prev_matches_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        prev_matches_rv_matches.adapter = adapterRvMatchEvents
    }

    override fun loadingSpinnerData(loading: Boolean) {
        this.loading = loading

        if (loading) {
            prev_matches_progress_bar?.visible()
        } else {
            prev_matches_progress_bar?.gone()
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
        prev_matches_spinner.adapter = spinnerAdapter
        prev_matches_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagues[position].id ?: ""
                if (idLeague.isNotEmpty()) presenter.loadPrevMatchEvents(idLeague)
            }

        }
    }

    override fun loadingPrevMatchesData(loading: Boolean) {
        this.loading = loading
        prev_matches_swipe_refresh?.isRefreshing = loading
    }

    override fun setMatchEvents(matchEvents: List<MatchEvent>) {
        this.matchEvents.clear()
        this.matchEvents.addAll(matchEvents)
        adapterRvMatchEvents.notifyDataSetChanged()
    }

    override fun onStop() {
        presenter.clearComposite()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.disposeComposite()
        super.onDestroy()
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

}
